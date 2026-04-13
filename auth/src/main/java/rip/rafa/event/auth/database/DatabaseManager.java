package rip.rafa.event.auth.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import rip.rafa.event.auth.AuthPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseManager {
    
    private final AuthPlugin plugin;
    private HikariDataSource dataSource;
    
    public DatabaseManager(AuthPlugin plugin) {
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
        String createUsers = "CREATE TABLE IF NOT EXISTS round6_users (" +
                "uuid VARCHAR(36) PRIMARY KEY," +
                "username VARCHAR(16) NOT NULL," +
                "password VARCHAR(255) NOT NULL," +
                "email VARCHAR(255)," +
                "registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "last_login TIMESTAMP NULL," +
                "last_ip VARCHAR(45)," +
                "INDEX idx_username (username)" +
                ")";
        
        String createSkins = "CREATE TABLE IF NOT EXISTS round6_skins (" +
                "uuid VARCHAR(36) PRIMARY KEY," +
                "texture TEXT NOT NULL," +
                "signature TEXT," +
                "skin_name VARCHAR(16) NOT NULL," +
                "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
                "FOREIGN KEY (uuid) REFERENCES round6_users(uuid) ON DELETE CASCADE" +
                ")";
        
        try (Connection conn = getConnection()) {
            conn.createStatement().execute(createUsers);
            conn.createStatement().execute(createSkins);
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
    
    public boolean registerUser(UUID uuid, String username, String password) {
        String sql = "INSERT INTO round6_users (uuid, username, password) VALUES (?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, uuid.toString());
            stmt.setString(2, username);
            stmt.setString(3, password);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            plugin.getLogger().warning("Erro ao registrar usuário: " + e.getMessage());
            return false;
        }
    }
    
    public String getPassword(UUID uuid) {
        String sql = "SELECT password FROM round6_users WHERE uuid = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, uuid.toString());
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("password");
            }
        } catch (SQLException e) {
            plugin.getLogger().warning("Erro ao buscar senha: " + e.getMessage());
        }
        
        return null;
    }
    
    public boolean isRegistered(UUID uuid) {
        String sql = "SELECT 1 FROM round6_users WHERE uuid = ? LIMIT 1";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, uuid.toString());
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            plugin.getLogger().warning("Erro ao verificar registro: " + e.getMessage());
            return false;
        }
    }
    
    public void updateLastLogin(UUID uuid, String ip) {
        String sql = "UPDATE round6_users SET last_login = CURRENT_TIMESTAMP, last_ip = ? WHERE uuid = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, ip);
            stmt.setString(2, uuid.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().warning("Erro ao atualizar último login: " + e.getMessage());
        }
    }
    
    public void saveSkin(UUID uuid, String texture, String signature, String skinName) {
        String sql = "INSERT INTO round6_skins (uuid, texture, signature, skin_name) " +
                "VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE texture = ?, signature = ?, skin_name = ?, updated_at = CURRENT_TIMESTAMP";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, uuid.toString());
            stmt.setString(2, texture);
            stmt.setString(3, signature);
            stmt.setString(4, skinName);
            stmt.setString(5, texture);
            stmt.setString(6, signature);
            stmt.setString(7, skinName);
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().warning("Erro ao salvar skin: " + e.getMessage());
        }
    }
    
    public SkinData getSkin(UUID uuid) {
        String sql = "SELECT texture, signature, skin_name FROM round6_skins WHERE uuid = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, uuid.toString());
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new SkinData(
                    rs.getString("texture"),
                    rs.getString("signature"),
                    rs.getString("skin_name")
                );
            }
        } catch (SQLException e) {
            plugin.getLogger().warning("Erro ao buscar skin: " + e.getMessage());
        }
        
        return null;
    }
    
    public boolean checkPassword(UUID uuid, String password) {
        String storedPassword = getPassword(uuid);
        if (storedPassword == null) {
            return false;
        }
        
        // Hash da senha fornecida
        String hashedPassword = hashPassword(password);
        return storedPassword.equals(hashedPassword);
    }
    
    public boolean updatePassword(UUID uuid, String newPassword) {
        String sql = "UPDATE round6_users SET password = ? WHERE uuid = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Hash da nova senha
            String hashedPassword = hashPassword(newPassword);
            stmt.setString(1, hashedPassword);
            stmt.setString(2, uuid.toString());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            plugin.getLogger().warning("Erro ao atualizar senha: " + e.getMessage());
            return false;
        }
    }
    
    private String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (Exception e) {
            plugin.getLogger().severe("Erro ao fazer hash da senha: " + e.getMessage());
            return Integer.toHexString(password.hashCode());
        }
    }
    
    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            plugin.getLogger().info("Conexão com MySQL fechada!");
        }
    }
    
    public static class SkinData {
        private final String texture;
        private final String signature;
        private final String skinName;
        
        public SkinData(String texture, String signature, String skinName) {
            this.texture = texture;
            this.signature = signature;
            this.skinName = skinName;
        }
        
        public String getTexture() {
            return texture;
        }
        
        public String getSignature() {
            return signature;
        }
        
        public String getSkinName() {
            return skinName;
        }
    }
}
