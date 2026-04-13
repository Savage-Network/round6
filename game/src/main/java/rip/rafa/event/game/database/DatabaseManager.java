package rip.rafa.event.game.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import rip.rafa.event.game.GamePlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {
    
    private final GamePlugin plugin;
    private HikariDataSource dataSource;
    
    public DatabaseManager(GamePlugin plugin) {
        this.plugin = plugin;
    }
    
    public boolean connect() {
        try {
            HikariConfig config = new HikariConfig();
            
            String host = plugin.getConfig().getString("database.host");
            int port = plugin.getConfig().getInt("database.port");
            String database = plugin.getConfig().getString("database.database");
            String username = plugin.getConfig().getString("database.username");
            String password = plugin.getConfig().getString("database.password");
            
            config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
            config.setUsername(username);
            config.setPassword(password);
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
            
            config.setMaximumPoolSize(plugin.getConfig().getInt("database.pool.maximum-pool-size", 10));
            config.setMinimumIdle(plugin.getConfig().getInt("database.pool.minimum-idle", 2));
            config.setConnectionTimeout(plugin.getConfig().getLong("database.pool.connection-timeout", 30000));
            config.setIdleTimeout(plugin.getConfig().getLong("database.pool.idle-timeout", 600000));
            config.setMaxLifetime(plugin.getConfig().getLong("database.pool.max-lifetime", 1800000));
            
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.addDataSourceProperty("useServerPrepStmts", "true");
            config.addDataSourceProperty("useLocalSessionState", "true");
            config.addDataSourceProperty("rewriteBatchedStatements", "true");
            config.addDataSourceProperty("cacheResultSetMetadata", "true");
            config.addDataSourceProperty("cacheServerConfiguration", "true");
            config.addDataSourceProperty("elideSetAutoCommits", "true");
            config.addDataSourceProperty("maintainTimeStats", "false");
            config.addDataSourceProperty("autoReconnect", "true");
            
            dataSource = new HikariDataSource(config);
            
            plugin.getLogger().info("Conexão com MySQL estabelecida!");
            
            createTables();
            
            return true;
        } catch (Exception e) {
            plugin.getLogger().severe("Erro ao conectar ao MySQL: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    private void createTables() {
        try (Connection conn = getConnection()) {
            // Tabela de jogos
            String gamesTable = "CREATE TABLE IF NOT EXISTS round6_games (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "status VARCHAR(20) NOT NULL," +
                    "players INT NOT NULL," +
                    "winner VARCHAR(36)," +
                    "started_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "ended_at TIMESTAMP NULL" +
                    ")";
            
            // Tabela de jogadores em jogo
            String playersTable = "CREATE TABLE IF NOT EXISTS round6_players (" +
                    "uuid VARCHAR(36) PRIMARY KEY," +
                    "game_id INT," +
                    "status VARCHAR(20) NOT NULL," +
                    "joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (game_id) REFERENCES round6_games(id)" +
                    ")";
            
            try (PreparedStatement stmt = conn.prepareStatement(gamesTable)) {
                stmt.executeUpdate();
            }
            
            try (PreparedStatement stmt = conn.prepareStatement(playersTable)) {
                stmt.executeUpdate();
            }
            
            plugin.getLogger().info("Tabelas do banco de dados criadas/verificadas!");
            
        } catch (SQLException e) {
            plugin.getLogger().severe("Erro ao criar tabelas: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    
    public void disconnect() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            plugin.getLogger().info("Conexão com MySQL fechada!");
        }
    }
}
