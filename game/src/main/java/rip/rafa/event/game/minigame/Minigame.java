package rip.rafa.event.game.minigame;

import org.bukkit.entity.Player;
import rip.rafa.event.game.GamePlugin;
import rip.rafa.event.game.model.GameType;

import java.util.Set;
import java.util.UUID;

public abstract class Minigame {
    
    protected final GamePlugin plugin;
    protected final GameType gameType;
    protected boolean running;
    
    public Minigame(GamePlugin plugin, GameType gameType) {
        this.plugin = plugin;
        this.gameType = gameType;
        this.running = false;
    }
    
    public abstract void start(Set<UUID> players);
    
    public abstract void stop();
    
    public abstract void onPlayerMove(Player player);
    
    public abstract void onPlayerDeath(Player player);
    
    public abstract void tick();
    
    public GameType getGameType() {
        return gameType;
    }
    
    public boolean isRunning() {
        return running;
    }
    
    protected String colorize(String text) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("&#([0-9A-Fa-f]{6})");
        java.util.regex.Matcher matcher = pattern.matcher(text);
        
        while (matcher.find()) {
            String color = matcher.group(1);
            text = text.replace("&#" + color, net.md_5.bungee.api.ChatColor.of("#" + color).toString());
        }
        
        return text.replace("&", "§");
    }
}
