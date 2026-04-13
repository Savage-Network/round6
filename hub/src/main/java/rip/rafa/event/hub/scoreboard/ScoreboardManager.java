package rip.rafa.event.hub.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager {
    
    public static void updateScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        
        Objective objective = scoreboard.registerNewObjective("hub", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§c§l⬛ ROUND 6 ⬛");
        
        int line = 10;
        
        setScore(objective, "§7", line--);
        setScore(objective, "§fJogador:", line--);
        setScore(objective, "§7" + player.getName(), line--);
        setScore(objective, "§8", line--);
        setScore(objective, "§fOnline:", line--);
        setScore(objective, "§a" + Bukkit.getOnlinePlayers().size(), line--);
        setScore(objective, "§9", line--);
        setScore(objective, "§7Use §f/play §7para jogar", line--);
        setScore(objective, "§6", line--);
        
        player.setScoreboard(scoreboard);
    }
    
    private static void setScore(Objective objective, String text, int score) {
        objective.getScore(text).setScore(score);
    }
}
