package rip.rafa.event.hub.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import rip.rafa.event.hub.HubPlugin;
import rip.rafa.event.hub.account.Account;
import rip.rafa.event.hub.account.AccountManager;
import rip.rafa.event.hub.rank.Rank;

public class Round6Expansion extends PlaceholderExpansion {
    
    private final HubPlugin plugin;
    
    public Round6Expansion(HubPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public @NotNull String getIdentifier() {
        return "round6";
    }
    
    @Override
    public @NotNull String getAuthor() {
        return "Rafa";
    }
    
    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }
    
    @Override
    public boolean persist() {
        return true;
    }
    
    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return "";
        }
        
        Account account = AccountManager.get(player.getUniqueId());
        if (account == null) {
            return "";
        }
        
        Rank rank = account.getRank();
        
        switch (params.toLowerCase()) {
            case "rank":
                return rank.getName();
                
            case "rank_prefix":
            case "prefix":
                return rank.getPrefix();
                
            case "rank_color":
            case "color":
                return rank.getColor();
                
            case "rank_priority":
                return String.valueOf(rank.getPriority());
                
            case "number":
                int number = account.getNumber();
                return number > 0 ? String.format("%03d", number) : "";
                
            case "number_prefix":
                int num = account.getNumber();
                return num > 0 ? String.format("§x§0§6§4§0§2§B[§f%03d§x§0§6§4§0§2§B] ", num) : "";
                
            case "display_name":
                String prefix = rank.getPrefix();
                String numberPrefix = "";
                if (account.getNumber() > 0 && rank == Rank.DEFAULT) {
                    numberPrefix = String.format("§x§0§6§4§0§2§B[§f%03d§x§0§6§4§0§2§B] ", account.getNumber());
                }
                return prefix + numberPrefix + player.getName();
                
            case "tag":
                return rank.getPrefix() + rank.getName() + " ";
                
            case "wins":
                return String.valueOf(account.getRound6Wins());
                
            case "losses":
                return String.valueOf(account.getRound6Losses());
                
            case "kills":
                return String.valueOf(account.getRound6Kills());
                
            case "deaths":
                return String.valueOf(account.getRound6Deaths());
                
            case "kdr":
                return String.format("%.2f", account.getKDR());
                
            case "winrate":
                return String.format("%.1f%%", account.getWinRate());
                
            default:
                return null;
        }
    }
}
