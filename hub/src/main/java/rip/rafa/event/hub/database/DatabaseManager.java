package rip.rafa.event.hub.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import rip.rafa.event.hub.HubPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseManager {
    
    private final HubPlugin plugin;
    private HikariDataSource dataSource;
    
    public DatabaseManager(HubPlugin plugin) {
        this.plugin = plugin;
        setupDatabase();
        createTables();
    }
    
    private void setupDatabase() {
        String host = plugin.getConfig().getString("database.host", "mysql52-farm1.kinghost.net");
        int port = plugin.getConfig().getInt("database.port", 3306);
        String database = plugin.getConfig().getString("database.database", "zrfa");
        String username = plugin.getConfig().getString("database.username", "zrfa");
        String password = plugin.getConfig().getString("database.password", "Rafael141011");
        
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&autoReconnect=true");
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        
        try {
            dataSource = new HikariDataSource(config);
            plugin.getLogger().info("Conexão com MySQL estabelecida!");
        } catch (Exception e) {
            plugin.getLogger().severe("Erro ao conectar ao MySQL: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void createTables() {
        String createNPCs = "CREATE TABLE IF NOT EXISTS round6_npcs (" +
                "id VARCHAR(50) PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "world VARCHAR(50) NOT NULL," +
                "x DOUBLE NOT NULL," +
                "y DOUBLE NOT NULL," +
                "z DOUBLE NOT NULL," +
                "yaw FLOAT NOT NULL," +
                "pitch FLOAT NOT NULL," +
                "action_type VARCHAR(50)," +
                "action_server VARCHAR(50)," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
        
        String createStats = "CREATE TABLE IF NOT EXISTS round6_stats (" +
                "uuid VARCHAR(36) PRIMARY KEY," +
                "username VARCHAR(16) NOT NULL," +
                "kills INT DEFAULT 0," +
                "deaths INT DEFAULT 0," +
                "wins INT DEFAULT 0," +
                "losses INT DEFAULT 0," +
                "coins INT DEFAULT 0," +
                "level INT DEFAULT 1," +
                "exp INT DEFAULT 0," +
                "last_seen TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
                "INDEX idx_username (username)," +
                "INDEX idx_coins (coins DESC)," +
                "INDEX idx_wins (wins DESC)" +
                ")";
        
        try (Connection conn = getConnection()) {
            conn.createStatement().execute(createNPCs);
            conn.createStatement().execute(createStats);
            plugin.getLogger().info("Tabelas do banco de dados criadas/verificadas!");
        } catch (SQLException e) {
            plugin.getLogger().severe("Erro ao criar tabelas: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public Connection getConnection() throws SQLException {
        if (dataSource == null || dataSource.isClosed()) {
            setupDatabase();
        }
        return dataSource.getConnection();
    }
    
    public PlayerStats getStats(UUID uuid) {
        String sql = "SELECT * FROM round6_stats WHERE uuid = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, uuid.toString());
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new PlayerStats(
                    uuid,
                    rs.getString("username"),
                    rs.getInt("kills"),
                    rs.getInt("deaths"),
                    rs.getInt("wins"),
                    rs.getInt("losses"),
                    rs.getInt("coins"),
                    rs.getInt("level"),
                    rs.getInt("exp")
                );
            }
        } catch (SQLException e) {
            plugin.getLogger().warning("Erro ao buscar stats: " + e.getMessage());
        }
        
        return null;
    }
    
    public void createStats(UUID uuid, String username) {
        String sql = "INSERT INTO round6_stats (uuid, username) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE username = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, uuid.toString());
            stmt.setString(2, username);
            stmt.setString(3, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().warning("Erro ao criar stats: " + e.getMessage());
        }
    }
    
    public void updateStats(UUID uuid, String field, int value) {
        String sql = "UPDATE round6_stats SET " + field + " = " + field + " + ? WHERE uuid = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, value);
            stmt.setString(2, uuid.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().warning("Erro ao atualizar stats: " + e.getMessage());
        }
    }
    
    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            plugin.getLogger().info("Conexão com MySQL fechada!");
        }
    }
    
    public static class PlayerStats {
        private final UUID uuid;
        private final String username;
        private final int kills;
        private final int deaths;
        private final int wins;
        private final int losses;
        private final int coins;
        private final int level;
        private final int exp;
        
        public PlayerStats(UUID uuid, String username, int kills, int deaths, int wins, int losses, int coins, int level, int exp) {
            this.uuid = uuid;
            this.username = username;
            this.kills = kills;
            this.deaths = deaths;
            this.wins = wins;
            this.losses = losses;
            this.coins = coins;
            this.level = level;
            this.exp = exp;
        }
        
        public UUID getUuid() { return uuid; }
        public String getUsername() { return username; }
        public int getKills() { return kills; }
        public int getDeaths() { return deaths; }
        public int getWins() { return wins; }
        public int getLosses() { return losses; }
        public int getCoins() { return coins; }
        public int getLevel() { return level; }
        public int getExp() { return exp; }
        public double getKD() { return deaths == 0 ? kills : (double) kills / deaths; }
    }
}
