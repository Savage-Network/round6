package rip.rafa.event.hub.account;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import rip.rafa.event.hub.HubPlugin;
import rip.rafa.event.hub.rank.Rank;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AccountManager {
    
    private static final Map<UUID, Account> accounts = new ConcurrentHashMap<>();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static HubPlugin plugin;
    
    public static void init(HubPlugin plugin) {
        AccountManager.plugin = plugin;
    }
    
    public static Account get(UUID uuid) {
        return accounts.get(uuid);
    }
    
    public static Account getByName(String name) {
        return accounts.values().stream()
                .filter(acc -> acc.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
    
    public static void load(UUID uuid, String name) {
        // Tenta carregar do banco de dados
        Account account = loadFromDatabase(uuid);
        
        if (account == null) {
            // Cria nova conta
            account = new Account(uuid, name);
            saveToDatabase(account);
        } else {
            // Atualiza nome se mudou
            if (!account.getName().equals(name)) {
                account.setName(name);
                saveToDatabase(account);
            }
        }
        
        accounts.put(uuid, account);
    }
    
    public static void unload(UUID uuid) {
        Account account = accounts.remove(uuid);
        if (account != null) {
            saveToDatabase(account);
        }
    }
    
    public static void save(Account account) {
        saveToDatabase(account);
    }
    
    private static Account loadFromDatabase(UUID uuid) {
        String query = "SELECT * FROM accounts WHERE uuid = ?";
        
        try (Connection conn = plugin.getDatabaseManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, uuid.toString());
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Account account = new Account(uuid, rs.getString("name"));
                account.setRank(Rank.fromString(rs.getString("rank")));
                account.setNumber(rs.getInt("number"));
                account.setRound6Wins(rs.getInt("round6_wins"));
                account.setRound6Losses(rs.getInt("round6_losses"));
                account.setRound6Kills(rs.getInt("round6_kills"));
                account.setRound6Deaths(rs.getInt("round6_deaths"));
                return account;
            }
            
        } catch (SQLException e) {
            plugin.getLogger().severe("Erro ao carregar conta: " + e.getMessage());
        }
        
        return null;
    }
    
    private static void saveToDatabase(Account account) {
        String query = "INSERT INTO accounts (uuid, name, rank, number, round6_wins, round6_losses, round6_kills, round6_deaths) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                      "ON DUPLICATE KEY UPDATE " +
                      "name = VALUES(name), " +
                      "rank = VALUES(rank), " +
                      "number = VALUES(number), " +
                      "round6_wins = VALUES(round6_wins), " +
                      "round6_losses = VALUES(round6_losses), " +
                      "round6_kills = VALUES(round6_kills), " +
                      "round6_deaths = VALUES(round6_deaths)";
        
        try (Connection conn = plugin.getDatabaseManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, account.getUuid().toString());
            stmt.setString(2, account.getName());
            stmt.setString(3, account.getRank().name());
            stmt.setInt(4, account.getNumber());
            stmt.setInt(5, account.getRound6Wins());
            stmt.setInt(6, account.getRound6Losses());
            stmt.setInt(7, account.getRound6Kills());
            stmt.setInt(8, account.getRound6Deaths());
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            plugin.getLogger().severe("Erro ao salvar conta: " + e.getMessage());
        }
    }
    
    public static void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS accounts (" +
                      "uuid VARCHAR(36) PRIMARY KEY, " +
                      "name VARCHAR(16) NOT NULL, " +
                      "rank VARCHAR(20) DEFAULT 'DEFAULT', " +
                      "number INT DEFAULT 0, " +
                      "round6_wins INT DEFAULT 0, " +
                      "round6_losses INT DEFAULT 0, " +
                      "round6_kills INT DEFAULT 0, " +
                      "round6_deaths INT DEFAULT 0, " +
                      "INDEX idx_name (name)" +
                      ")";
        
        try (Connection conn = plugin.getDatabaseManager().getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate(query);
            plugin.getLogger().info("Tabela de contas criada/verificada!");
            
        } catch (SQLException e) {
            plugin.getLogger().severe("Erro ao criar tabela: " + e.getMessage());
        }
    }
}
