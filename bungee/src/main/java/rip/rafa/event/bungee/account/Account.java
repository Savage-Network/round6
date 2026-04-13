package rip.rafa.event.bungee.account;

import lombok.Data;
import rip.rafa.event.bungee.rank.Rank;

import java.util.UUID;

@Data
public class Account {
    
    private final UUID uuid;
    private String name;
    private Rank rank;
    
    public Account(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.rank = Rank.DEFAULT;
    }
    
    public String getDisplayName() {
        return rank.getFormattedPrefix() + name;
    }
}
