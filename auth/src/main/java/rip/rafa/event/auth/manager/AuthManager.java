package rip.rafa.event.auth.manager;

import org.bukkit.entity.Player;
import rip.rafa.event.auth.AuthPlugin;
import rip.rafa.event.auth.database.DatabaseManager;

import java.security.MessageDigest;
import java.util.*;

public class AuthManager {
    
    private final AuthPlugin plugin;
    private final DatabaseManager database;
    private final Set<UUID> authenticated;
    
    public AuthManager(AuthPlugin plugin) {
        this.plugin = plugin;
        this.database = plugin.getDatabaseManager();
        this.authenticated = new HashSet<>();
    }
    
    public boolean isRegistered(Player player) {
        return database.isRegistered(player.getUniqueId());
    }
    
    public boolean isAuthenticated(Player player) {
        return authenticated.contains(player.getUniqueId());
    }
    
    public boolean register(Player player, String password) {
        if (isRegistered(player)) {
            return false;
        }
        
        String hashedPassword = hashPassword(password);
        boolean success = database.registerUser(player.getUniqueId(), player.getName(), hashedPassword);
        
        if (success) {
            plugin.getLogger().info("Usuário " + player.getName() + " registrado com sucesso!");
        }
        
        return success;
    }
    
    public boolean login(Player player, String password) {
        String storedPassword = database.getPassword(player.getUniqueId());
        if (storedPassword == null) {
            return false;
        }
        
        if (!storedPassword.equals(hashPassword(password))) {
            return false;
        }
        
        authenticated.add(player.getUniqueId());
        database.updateLastLogin(player.getUniqueId(), player.getAddress().getAddress().getHostAddress());
        
        plugin.getLogger().info("Usuário " + player.getName() + " autenticado com sucesso!");
        return true;
    }
    
    public void logout(Player player) {
        authenticated.remove(player.getUniqueId());
    }
    
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
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
}
