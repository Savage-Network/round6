package rip.rafa.event.hub.rank;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Rank {
    
    DEFAULT("Membro", "§7", "§7", 5, "nametag.default"),
    GAMEMASTER("GameMaster", "§x§f§f§c§0§c§b", "§x§f§f§c§0§c§b&o", 3, "round6.gamemaster"),
    GUARDA("Guarda", "§x§3§D§3§D§3§D", "§x§3§D§3§D§3§D§l", 2, "round6.guard"),
    CREATOR("Creator", "§x§1§1§1§1§8§4", "§x§1§1§1§1§8§4§l", 1, "round6.admin");
    
    private final String name;
    private final String color;
    private final String prefix;
    private final int priority;
    private final String permission;
    
    public static Rank fromString(String name) {
        for (Rank rank : values()) {
            if (rank.name().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return DEFAULT;
    }
    
    public boolean isHigherThan(Rank other) {
        return this.priority < other.priority;
    }
    
    public boolean isHigherOrEqual(Rank other) {
        return this.priority <= other.priority;
    }
    
    public String getFormattedPrefix() {
        if (this == DEFAULT) {
            return "";
        }
        return prefix + name + " §r";
    }
}
