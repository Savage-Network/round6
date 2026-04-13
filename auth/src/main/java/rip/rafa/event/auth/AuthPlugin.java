package rip.rafa.event.auth;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import rip.rafa.event.auth.command.ChangePasswordCommand;
import rip.rafa.event.auth.command.LoginCommand;
import rip.rafa.event.auth.command.RegisterCommand;
import rip.rafa.event.auth.database.DatabaseManager;
import rip.rafa.event.auth.listener.AuthListener;
import rip.rafa.event.auth.manager.AuthManager;
import rip.rafa.event.auth.skin.SkinManager;

public class AuthPlugin extends JavaPlugin {
    
    private static AuthPlugin instance;
    private DatabaseManager databaseManager;
    private AuthManager authManager;
    private SkinManager skinManager;
    
    @Override
    public void onEnable() {
        instance = this;
        
        saveDefaultConfig();
        
        this.databaseManager = new DatabaseManager(this);
        this.authManager = new AuthManager(this);
        this.skinManager = new SkinManager(this);
        
        Bukkit.getPluginManager().registerEvents(new AuthListener(this), this);
        
        getCommand("login").setExecutor(new LoginCommand(this));
        getCommand("register").setExecutor(new RegisterCommand(this));
        getCommand("acc").setExecutor(new ChangePasswordCommand(this));
        getCommand("acc").setTabCompleter(new rip.rafa.event.auth.command.ChangePasswordTabCompleter());
        
        // Bloqueia movimento
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!authManager.isAuthenticated(player)) {
                    player.setWalkSpeed(0.0f);
                }
            }
        }, 0L, 20L);
        
        getLogger().info("Auth Plugin habilitado!");
        getLogger().info("Sistema de skins embutido ativo");
        getLogger().info("Banco de dados MySQL conectado");
    }
    
    @Override
    public void onDisable() {
        if (databaseManager != null) {
            databaseManager.close();
        }
        getLogger().info("Auth Plugin desabilitado!");
    }
    
    public static AuthPlugin getInstance() {
        return instance;
    }
    
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
    
    public AuthManager getAuthManager() {
        return authManager;
    }
    
    public SkinManager getSkinManager() {
        return skinManager;
    }
}
