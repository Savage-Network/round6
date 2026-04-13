package rip.rafa.event.bungee.listener;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import rip.rafa.event.bungee.EventBungee;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class PlayerConnectionListener implements Listener {
    
    private final EventBungee plugin;
    private final ConcurrentHashMap<String, Boolean> premiumCache = new ConcurrentHashMap<>();
    
    public PlayerConnectionListener(EventBungee plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onServerConnect(ServerConnectEvent event) {
        // Verificar se é a primeira conexão (indo para o servidor padrão)
        if (event.getPlayer().getServer() != null) {
            return; // Não é primeira conexão
        }
        
        String playerName = event.getPlayer().getName();
        
        // Verificar se o jogador é premium de forma assíncrona
        plugin.getProxy().getScheduler().runAsync(plugin, () -> {
            boolean isPremium = isPremiumPlayer(playerName);
            
            // Armazenar no cache por 5 minutos
            premiumCache.put(playerName.toLowerCase(), isPremium);
            plugin.getProxy().getScheduler().schedule(plugin, () -> {
                premiumCache.remove(playerName.toLowerCase());
            }, 5, TimeUnit.MINUTES);
            
            // Redirecionar no thread principal
            plugin.getProxy().getScheduler().runAsync(plugin, () -> {
                ServerInfo targetServer;
                
                if (isPremium) {
                    // Jogador ORIGINAL - Vai direto para o Hub
                    targetServer = plugin.getProxy().getServerInfo("hub");
                    
                    if (targetServer != null) {
                        event.getPlayer().connect(targetServer);
                        plugin.getLogger().info("Jogador ORIGINAL " + playerName + " conectado - Enviando para Hub");
                    }
                } else {
                    // Jogador PIRATA - Vai para Auth
                    targetServer = plugin.getProxy().getServerInfo("auth");
                    
                    if (targetServer != null) {
                        event.getPlayer().connect(targetServer);
                        plugin.getLogger().info("Jogador PIRATA " + playerName + " conectado - Enviando para Auth");
                    }
                }
            });
        });
    }
    
    private boolean isPremiumPlayer(String playerName) {
        // Verificar cache primeiro
        if (premiumCache.containsKey(playerName.toLowerCase())) {
            return premiumCache.get(playerName.toLowerCase());
        }
        
        try {
            // Verificar na API da Mojang se o jogador existe
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            int responseCode = connection.getResponseCode();
            
            if (responseCode == 200) {
                // Jogador existe na Mojang = Premium
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = reader.readLine();
                reader.close();
                
                return response != null && !response.isEmpty();
            } else if (responseCode == 204 || responseCode == 404) {
                // Jogador não existe na Mojang = Pirata
                return false;
            }
            
            connection.disconnect();
        } catch (Exception e) {
            plugin.getLogger().warning("Erro ao verificar se jogador " + playerName + " é premium: " + e.getMessage());
            // Em caso de erro, assumir que é pirata (mais seguro)
            return false;
        }
        
        return false;
    }
}
