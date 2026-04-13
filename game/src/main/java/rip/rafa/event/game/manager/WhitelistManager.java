package rip.rafa.event.game.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import rip.rafa.event.game.GamePlugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WhitelistManager {
    
    private final GamePlugin plugin;
    private final File whitelistFile;
    private final Gson gson;
    
    private boolean enabled;
    private Map<UUID, String> whitelistedPlayers;
    
    public WhitelistManager(GamePlugin plugin) {
        this.plugin = plugin;
        this.whitelistFile = new File(plugin.getDataFolder(), "whitelist.json");
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.whitelistedPlayers = new HashMap<>();
        
        load();
    }
    
    public void load() {
        if (!whitelistFile.exists()) {
            enabled = false;
            whitelistedPlayers = new HashMap<>();
            save();
            return;
        }
        
        try (FileReader reader = new FileReader(whitelistFile)) {
            Type type = new TypeToken<WhitelistData>(){}.getType();
            WhitelistData data = gson.fromJson(reader, type);
            
            if (data != null) {
                this.enabled = data.enabled;
                this.whitelistedPlayers = data.players != null ? data.players : new HashMap<>();
            }
            
            plugin.getLogger().info("Whitelist carregada: " + whitelistedPlayers.size() + " jogadores");
        } catch (Exception e) {
            plugin.getLogger().severe("Erro ao carregar whitelist: " + e.getMessage());
            enabled = false;
            whitelistedPlayers = new HashMap<>();
        }
    }
    
    public void save() {
        try {
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdirs();
            }
            
            WhitelistData data = new WhitelistData();
            data.enabled = this.enabled;
            data.players = this.whitelistedPlayers;
            
            try (FileWriter writer = new FileWriter(whitelistFile)) {
                gson.toJson(data, writer);
            }
            
        } catch (Exception e) {
            plugin.getLogger().severe("Erro ao salvar whitelist: " + e.getMessage());
        }
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        save();
    }
    
    public boolean isWhitelisted(UUID uuid) {
        return whitelistedPlayers.containsKey(uuid);
    }
    
    public void addPlayer(UUID uuid, String name) {
        whitelistedPlayers.put(uuid, name);
        save();
    }
    
    public void removePlayer(UUID uuid) {
        whitelistedPlayers.remove(uuid);
        save();
    }
    
    public Map<UUID, String> getWhitelistedPlayers() {
        return new HashMap<>(whitelistedPlayers);
    }
    
    public boolean canJoin(UUID uuid) {
        if (!enabled) {
            return true;
        }
        return isWhitelisted(uuid);
    }
    
    private static class WhitelistData {
        boolean enabled;
        Map<UUID, String> players;
    }
}
