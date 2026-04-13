package rip.rafa.event.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import rip.rafa.event.bungee.listener.PlayerConnectionListener;

public class EventBungee extends Plugin {
    
    private static EventBungee instance;
    
    @Override
    public void onEnable() {
        instance = this;
        
        // Registrar listener
        getProxy().getPluginManager().registerListener(this, new PlayerConnectionListener(this));
        
        getLogger().info("EventBungee habilitado!");
        getLogger().info("Sistema de detecção de jogadores originais ativo!");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("EventBungee desabilitado!");
    }
    
    public static EventBungee getInstance() {
        return instance;
    }
}
