package rip.rafa.event.hub;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import rip.rafa.event.hub.account.AccountManager;
import rip.rafa.event.hub.command.*;
import rip.rafa.event.hub.database.DatabaseManager;
import rip.rafa.event.hub.listener.PlayerListener;
import rip.rafa.event.hub.manager.TabManager;
import rip.rafa.event.hub.npc.NPCManager;
import rip.rafa.event.hub.placeholder.Round6Expansion;
import rip.rafa.event.hub.scoreboard.ScoreboardManager;
import rip.rafa.event.hub.skin.SkinManager;
import rip.rafa.event.hub.tag.TagManager;

public class HubPlugin extends JavaPlugin {
    
    private static HubPlugin instance;
    private DatabaseManager databaseManager;
    private SkinManager skinManager;
    private TagManager tagManager;
    private TabManager tabManager;
    
    @Override
    public void onEnable() {
        instance = this;
        
        saveDefaultConfig();
        
        // Registra canal BungeeCord
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        
        // Inicializa managers
        this.databaseManager = new DatabaseManager(this);
        this.skinManager = new SkinManager(this);
        this.tagManager = new TagManager(this);
        this.tabManager = new TabManager(this);
        
        // Inicializa AccountManager
        AccountManager.init(this);
        AccountManager.createTable();
        
        // Registra PlaceholderAPI expansion
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new Round6Expansion(this).register();
            getLogger().info("PlaceholderAPI expansion registrada!");
        }
        
        // Registra listeners
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        
        // Registra comandos
        getCommand("play").setExecutor(new PlayCommand());
        getCommand("skin").setExecutor(new SkinCommand(this));
        getCommand("plugins").setExecutor(new PluginsCommand());
        getCommand("pl").setExecutor(new PluginsCommand());
        getCommand("acc").setExecutor(new AccountCommand(this));
        
        // Registra tab completers
        getCommand("acc").setTabCompleter(new AccountTabCompleter());
        getCommand("skin").setTabCompleter(new SkinTabCompleter());
        
        // Atualiza scoreboard
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (org.bukkit.entity.Player player : Bukkit.getOnlinePlayers()) {
                ScoreboardManager.updateScoreboard(player);
            }
        }, 20L, 20L);
        
        getLogger().info("Hub Plugin habilitado!");
        getLogger().info("Canal BungeeCord registrado!");
        getLogger().info("Sistema de skins embutido ativo");
        getLogger().info("Banco de dados MySQL conectado");
        getLogger().info("Sistema de ranks carregado!");
        getLogger().info("Suporte a ViaVersion: " + Bukkit.getPluginManager().isPluginEnabled("ViaVersion"));
        getLogger().info("PlaceholderAPI: " + Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"));
    }
    
    @Override
    public void onDisable() {
        // Remove canal BungeeCord
        getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
        
        if (databaseManager != null) {
            databaseManager.close();
        }
        getLogger().info("Hub Plugin desabilitado!");
    }
    
    public static HubPlugin getInstance() {
        return instance;
    }
    
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
    
    public SkinManager getSkinManager() {
        return skinManager;
    }
    
    public TagManager getTagManager() {
        return tagManager;
    }
    
    public TabManager getTabManager() {
        return tabManager;
    }
}
