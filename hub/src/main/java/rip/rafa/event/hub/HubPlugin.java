package rip.rafa.event.hub;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import rip.rafa.event.hub.command.*;
import rip.rafa.event.hub.database.DatabaseManager;
import rip.rafa.event.hub.listener.PlayerListener;
import rip.rafa.event.hub.npc.NPCManager;
import rip.rafa.event.hub.scoreboard.ScoreboardManager;
import rip.rafa.event.hub.skin.SkinManager;
import rip.rafa.event.hub.tag.TagManager;

public class HubPlugin extends JavaPlugin {
    
    private static HubPlugin instance;
    private DatabaseManager databaseManager;
    private NPCManager npcManager;
    private SkinManager skinManager;
    private TagManager tagManager;
    
    @Override
    public void onEnable() {
        instance = this;
        
        saveDefaultConfig();
        
        // Inicializa managers
        this.databaseManager = new DatabaseManager(this);
        this.skinManager = new SkinManager(this);
        this.tagManager = new TagManager(this);
        this.npcManager = new NPCManager(this);
        
        // Registra listeners
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        
        // Registra comandos
        getCommand("play").setExecutor(new PlayCommand());
        getCommand("npc").setExecutor(new NPCCommand(this));
        getCommand("skin").setExecutor(new SkinCommand(this));
        getCommand("plugins").setExecutor(new PluginsCommand());
        getCommand("pl").setExecutor(new PluginsCommand());
        
        // Atualiza scoreboard
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (org.bukkit.entity.Player player : Bukkit.getOnlinePlayers()) {
                ScoreboardManager.updateScoreboard(player);
            }
        }, 20L, 20L);
        
        getLogger().info("Hub Plugin habilitado!");
        getLogger().info("Sistema de skins embutido ativo");
        getLogger().info("Banco de dados MySQL conectado");
        getLogger().info("Suporte a ViaVersion: " + Bukkit.getPluginManager().isPluginEnabled("ViaVersion"));
        getLogger().info("PlaceholderAPI: " + Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"));
    }
    
    @Override
    public void onDisable() {
        if (npcManager != null) {
            npcManager.removeAllNPCs();
        }
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
    
    public NPCManager getNpcManager() {
        return npcManager;
    }
    
    public SkinManager getSkinManager() {
        return skinManager;
    }
    
    public TagManager getTagManager() {
        return tagManager;
    }
}
