package rip.rafa.event.bungee.listener;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import rip.rafa.event.bungee.EventBungee;

public class ServerConnectListener implements Listener {
    
    private final EventBungee plugin;
    
    public ServerConnectListener(EventBungee plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onServerConnect(ServerConnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        
        // Verifica se é o primeiro login
        if (event.getReason() == ServerConnectEvent.Reason.JOIN_PROXY) {
            // Detecta se o jogador é pirata ou original
            boolean isPirate = !player.getPendingConnection().isOnlineMode();
            
            if (isPirate) {
                // Jogador pirata - envia para auth
                ServerInfo authServer = plugin.getProxy().getServerInfo("auth");
                
                if (authServer != null) {
                    event.setTarget(authServer);
                    plugin.getLogger().info("Jogador pirata " + player.getName() + " redirecionado para auth");
                } else {
                    player.disconnect("§cServidor de autenticação não encontrado!");
                    plugin.getLogger().warning("Servidor 'auth' não configurado no BungeeCord!");
                }
            } else {
                // Jogador original - envia direto para hub
                ServerInfo hubServer = plugin.getProxy().getServerInfo("hub");
                
                if (hubServer != null) {
                    event.setTarget(hubServer);
                    plugin.getLogger().info("Jogador original " + player.getName() + " conectado ao hub");
                } else {
                    player.disconnect("§cServidor hub não encontrado!");
                    plugin.getLogger().warning("Servidor 'hub' não configurado no BungeeCord!");
                }
            }
        }
    }
}
