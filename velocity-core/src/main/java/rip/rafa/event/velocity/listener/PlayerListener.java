package rip.rafa.event.velocity.listener;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import rip.rafa.event.velocity.VelocityCore;

public class PlayerListener {
    
    private final VelocityCore plugin;
    
    public PlayerListener(VelocityCore plugin) {
        this.plugin = plugin;
    }
    
    @Subscribe(order = PostOrder.FIRST)
    public void onPostLogin(PostLoginEvent event) {
        Player player = event.getPlayer();
        plugin.getPlayerManager().onPlayerJoin(player);
    }
    
    @Subscribe(order = PostOrder.LAST)
    public void onDisconnect(DisconnectEvent event) {
        Player player = event.getPlayer();
        plugin.getPlayerManager().onPlayerDisconnect(player);
    }
    
    @Subscribe(order = PostOrder.NORMAL)
    public void onServerConnected(ServerConnectedEvent event) {
        Player player = event.getPlayer();
        String toServer = event.getServer().getServerInfo().getName();
        String fromServer = event.getPreviousServer()
            .map(server -> server.getServerInfo().getName())
            .orElse(null);
        
        plugin.getPlayerManager().onServerSwitch(player, fromServer, toServer);
    }
}
