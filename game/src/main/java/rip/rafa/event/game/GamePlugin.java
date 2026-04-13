package rip.rafa.event.game;

import org.bukkit.plugin.java.JavaPlugin;
import rip.rafa.event.game.database.DatabaseManager;
import rip.rafa.event.game.manager.GameManager;
import rip.rafa.event.game.manager.GameMasterManager;
import rip.rafa.event.game.manager.ScoreboardManager;
import rip.rafa.event.game.command.Round6Command;
import rip.rafa.event.game.listener.GameListener;

public class GamePlugin extends JavaPlugin {
    
    private static GamePlugin instance;
    private DatabaseManager databaseManager;
    private GameManager gameManager;
    private GameMasterManager gameMasterManager;
    private ScoreboardManager scoreboardManager;
    
    @Override
    public void onEnable() {
        instance = this;
        
        // Salvar config padrão
        saveDefaultConfig();
        
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
        
        // Registrar comandos
        getCommand("round6").setExecutor(new Round6Command(this));
        
        // Registrar listeners
        getServer().getPluginManager().registerEvents(new GameListener(this), this);
        getServer().getPluginManager().registerEvents(new rip.rafa.event.game.listener.GameMasterListener(this), this);
        
        getLogger().info("Game Plugin habilitado!");
        getLogger().info("Round 6 do Rafa - Sistema completo ativo!");
        getLogger().info("Banco de dados MySQL conectado");
    }
    
    @Override
    public void onDisable() {
        if (gameManager != null) {
            gameManager.shutdown();
        }
        
        if (databaseManager != null) {
            databaseManager.disconnect();
        }
        
        getLogger().info("Game Plugin desabilitado!");
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
}
