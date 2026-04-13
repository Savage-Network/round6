package rip.rafa.event.hub.account;

import lombok.Data;
import rip.rafa.event.hub.rank.Rank;

import java.util.UUID;

@Data
public class Account {
    
    private final UUID uuid;
    private String name;
    private Rank rank;
    private int number; // Número do Round 6 (001-456)
    
    // Estatísticas do Round 6
    private int round6Wins;
    private int round6Losses;
    private int round6Kills;
    private int round6Deaths;
    
    public Account(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.rank = Rank.DEFAULT;
        this.number = 0;
        this.round6Wins = 0;
        this.round6Losses = 0;
        this.round6Kills = 0;
        this.round6Deaths = 0;
    }
    
    public double getKDR() {
        if (round6Deaths == 0) {
            return round6Kills;
        }
        return (double) round6Kills / round6Deaths;
    }
    
    public double getWinRate() {
        int total = round6Wins + round6Losses;
        if (total == 0) {
            return 0.0;
        }
        return ((double) round6Wins / total) * 100.0;
    }
    
    public String getDisplayName() {
        String prefix = rank.getFormattedPrefix();
        String numberPrefix = "";
        
        if (number > 0 && rank == Rank.DEFAULT) {
            numberPrefix = String.format("§x§0§6§4§0§2§B[§f%03d§x§0§6§4§0§2§B] ", number);
        }
        
        return prefix + numberPrefix + name;
    }
}
