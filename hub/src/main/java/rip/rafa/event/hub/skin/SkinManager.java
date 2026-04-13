package rip.rafa.event.hub.skin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import rip.rafa.event.hub.HubPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkinManager {
    
    private final HubPlugin plugin;
    private final Map<UUID, SkinData> skinCache = new HashMap<>();
    
    public SkinManager(HubPlugin plugin) {
        this.plugin = plugin;
    }
    
    public void applySkin(Player player, String skinName) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                SkinData skinData = getSkinData(skinName);
                if (skinData != null) {
                    skinCache.put(player.getUniqueId(), skinData);
                    
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        try {
                            setSkin(player, skinData);
                            refreshPlayer(player);
                            player.sendMessage("§aSkin aplicada com sucesso!");
                        } catch (Exception e) {
                            player.sendMessage("§cErro ao aplicar skin.");
                            plugin.getLogger().warning("Erro ao aplicar skin: " + e.getMessage());
                        }
                    });
                } else {
                    Bukkit.getScheduler().runTask(plugin, () -> 
                        player.sendMessage("§cJogador não encontrado ou skin inválida.")
                    );
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Erro ao buscar skin: " + e.getMessage());
            }
        });
    }
    
    private SkinData getSkinData(String playerName) {
        try {
            // Busca UUID do jogador
            URL uuidUrl = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);
            HttpURLConnection uuidConn = (HttpURLConnection) uuidUrl.openConnection();
            uuidConn.setRequestMethod("GET");
            uuidConn.setConnectTimeout(5000);
            uuidConn.setReadTimeout(5000);
            
            if (uuidConn.getResponseCode() != 200) {
                return null;
            }
            
            BufferedReader uuidReader = new BufferedReader(new InputStreamReader(uuidConn.getInputStream()));
            StringBuilder uuidResponse = new StringBuilder();
            String line;
            while ((line = uuidReader.readLine()) != null) {
                uuidResponse.append(line);
            }
            uuidReader.close();
            
            JsonObject uuidJson = new JsonParser().parse(uuidResponse.toString()).getAsJsonObject();
            String uuid = uuidJson.get("id").getAsString();
            
            // Busca dados da skin
            URL skinUrl = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            HttpURLConnection skinConn = (HttpURLConnection) skinUrl.openConnection();
            skinConn.setRequestMethod("GET");
            skinConn.setConnectTimeout(5000);
            skinConn.setReadTimeout(5000);
            
            if (skinConn.getResponseCode() != 200) {
                return null;
            }
            
            BufferedReader skinReader = new BufferedReader(new InputStreamReader(skinConn.getInputStream()));
            StringBuilder skinResponse = new StringBuilder();
            while ((line = skinReader.readLine()) != null) {
                skinResponse.append(line);
            }
            skinReader.close();
            
            JsonObject skinJson = new JsonParser().parse(skinResponse.toString()).getAsJsonObject();
            JsonObject properties = skinJson.getAsJsonArray("properties").get(0).getAsJsonObject();
            
            String texture = properties.get("value").getAsString();
            String signature = properties.has("signature") ? properties.get("signature").getAsString() : "";
            
            return new SkinData(texture, signature);
            
        } catch (Exception e) {
            plugin.getLogger().warning("Erro ao buscar skin de " + playerName + ": " + e.getMessage());
            return null;
        }
    }
    
    private void setSkin(Player player, SkinData skinData) throws Exception {
        Object craftPlayer = player.getClass().getMethod("getHandle").invoke(player);
        Object profile = craftPlayer.getClass().getMethod("getProfile").invoke(craftPlayer);
        
        if (profile instanceof GameProfile) {
            GameProfile gameProfile = (GameProfile) profile;
            gameProfile.getProperties().removeAll("textures");
            gameProfile.getProperties().put("textures", 
                new Property("textures", skinData.getTexture(), skinData.getSignature()));
        }
    }
    
    private void refreshPlayer(Player player) {
        try {
            // Esconde o jogador de todos
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (!online.equals(player)) {
                    online.hidePlayer(player);
                }
            }
            
            // Mostra novamente após 1 tick
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (!online.equals(player)) {
                        online.showPlayer(player);
                    }
                }
            }, 1L);
            
        } catch (Exception e) {
            plugin.getLogger().warning("Erro ao atualizar jogador: " + e.getMessage());
        }
    }
    
    public SkinData getCachedSkin(UUID uuid) {
        return skinCache.get(uuid);
    }
    
    public static class SkinData {
        private final String texture;
        private final String signature;
        
        public SkinData(String texture, String signature) {
            this.texture = texture;
            this.signature = signature;
        }
        
        public String getTexture() {
            return texture;
        }
        
        public String getSignature() {
            return signature;
        }
    }
}
