package rip.rafa.event.auth.user;

import java.util.UUID;

public class AuthUser {
    
    private UUID uuid;
    private String name;
    private String password;
    private long registeredAt;
    private long lastLogin;
    
    public AuthUser(UUID uuid, String name, String password) {
        this.uuid = uuid;
        this.name = name;
        this.password = password;
        this.registeredAt = System.currentTimeMillis();
        this.lastLogin = System.currentTimeMillis();
    }
    
    public UUID getUuid() {
        return uuid;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPassword() {
        return password;
    }
    
    public long getRegisteredAt() {
        return registeredAt;
    }
    
    public long getLastLogin() {
        return lastLogin;
    }
    
    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }
}
