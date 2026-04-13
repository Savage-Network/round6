package rip.rafa.event.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import rip.rafa.event.bungee.account.AccountManager;
import rip.rafa.event.bungee.command.RankCommand;
import rip.rafa.event.bungee.listener.PlayerConnectionListener;

public class EventBungee extends Plugin {
    
    private static EventBungee instance;
    
    @Override
    public void onEnable() {
        instance = this;
        
        // Inicializar AccountManager
        AccountManager.init(this);
        
        // Registrar listener
        getProxy().getPluginManager().registerListener(this, new PlayerConnectionListener(this));
        
        // Registrar comandos
        getProxy().getPluginManager().registerCommand(this, new RankCommand(this));
        
        getLogger().info("EventBungee habilitado!");
        getLogger().info("Sistema de detecção de jogadores originais ativo!");
        getLogger().info("Sistema de ranks carregado!");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("EventBungee desabilitado!");
    }
    
    public static EventBungee getInstance() {
        return instance;
    }
}
