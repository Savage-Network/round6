package rip.rafa.event.game;

import org.bukkit.plugin.java.JavaPlugin;
import rip.rafa.event.game.database.DatabaseManager;
import rip.rafa.event.game.manager.GameManager;
import rip.rafa.event.game.manager.GameMasterManager;
import rip.rafa.event.game.manager.ScoreboardManager;
import rip.rafa.event.game.manager.WhitelistManager;
import rip.rafa.event.game.command.Round6Command;
import rip.rafa.event.game.listener.GameListener;

public class GamePlugin extends JavaPlugin {
    
    private static GamePlugin instance;
    private DatabaseManager databaseManager;
    private GameManager gameManager;
    private GameMasterManager gameMasterManager;
    private ScoreboardManager scoreboardManager;
    private WhitelistManager whitelistManager;
    
    @Override
    public void onEnable() {
        instance = this;
        
        // Salvar config padrão
        saveDefaultConfig();
        
        // Registra canal BungeeCord
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        
        // Inicializar database
        databaseManager = new DatabaseManager(this);
        if (!databaseManager.connect()) {
            getLogger().severe("Falha ao conectar ao MySQL! Plugin desabilitado.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        // Inicializar managers
        gameMasterManager = new GameMasterManager(this);
        scoreboardManager = new ScoreboardManager(this);
        gameManager = new GameManager(this);
        whitelistManager = new WhitelistManager(this);
        
        // Registrar comandos
        getCommand("round6").setExecutor(new Round6Command(this));
        getCommand("round6").setTabCompleter(new rip.rafa.event.game.command.Round6TabCompleter(this));
        getCommand("acc").setExecutor(new rip.rafa.event.game.command.AccountCommand(this));
        getCommand("whitelist").setExecutor(new rip.rafa.event.game.command.WhitelistCommand(this));
        
        // Registrar listeners
        getServer().getPluginManager().registerEvents(new GameListener(this), this);
        getServer().getPluginManager().registerEvents(new rip.rafa.event.game.listener.GameMasterListener(this), this);
        getServer().getPluginManager().registerEvents(new rip.rafa.event.game.listener.WhitelistListener(this), this);
        
        getLogger().info("Game Plugin habilitado!");
        getLogger().info("Canal BungeeCord registrado!");
        getLogger().info("Round 6 do Rafa - Sistema completo ativo!");
        getLogger().info("Banco de dados MySQL conectado");
    }
    
    @Override
    public void onDisable() {
        // Enviar todos os jogadores para o Hub antes de desligar
        getLogger().info("Enviando todos os jogadores para o Hub...");
        for (org.bukkit.entity.Player player : getServer().getOnlinePlayers()) {
            sendToHub(player);
        }
        
        // Remove canal BungeeCord
        getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
        
        if (gameManager != null) {
            gameManager.shutdown();
        }
        
        if (databaseManager != null) {
            databaseManager.disconnect();
        }
        
        getLogger().info("Game Plugin desabilitado!");
    }
    
    private void sendToHub(org.bukkit.entity.Player player) {
        try {
            java.io.ByteArrayOutputStream b = new java.io.ByteArrayOutputStream();
            java.io.DataOutputStream out = new java.io.DataOutputStream(b);
            
            out.writeUTF("Connect");
            out.writeUTF("hub");
            
            player.sendPluginMessage(this, "BungeeCord", b.toByteArray());
            player.sendMessage("§c§l⬛ ROUND 6 ⬛");
            player.sendMessage("§7Servidor fechando... Enviando para o Hub!");
        } catch (Exception e) {
            getLogger().warning("Erro ao enviar " + player.getName() + " para o Hub: " + e.getMessage());
        }
    }
    
    public static GamePlugin getInstance() {
        return instance;
    }
    
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
    
    public GameManager getGameManager() {
        return gameManager;
    }
    
    public GameMasterManager getGameMasterManager() {
        return gameMasterManager;
    }
    
    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }
    
    public WhitelistManager getWhitelistManager() {
        return whitelistManager;
    }
}
