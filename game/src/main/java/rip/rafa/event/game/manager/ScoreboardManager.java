package rip.rafa.event.game.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import rip.rafa.event.game.GamePlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardManager {
    
    private final GamePlugin plugin;
    private final Map<UUID, Scoreboard> scoreboards;
    
    public ScoreboardManager(GamePlugin plugin) {
        this.plugin = plugin;
        this.scoreboards = new HashMap<>();
        
        // Atualizar scoreboards a cada segundo
        Bukkit.getScheduler().runTaskTimer(plugin, this::updateAll, 20L, 20L);
    }
    
    public void createScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("round6", "dummy", colorize(plugin.getConfig().getString("scoreboard.title", "&#FF1744ʀᴏᴜɴᴅ 6")));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        
        player.setScoreboard(scoreboard);
        scoreboards.put(player.getUniqueId(), scoreboard);
        
        updateScoreboard(player);
        updateTabList(player);
    }
    
    public void updateTabList(Player player) {
        // Atualizar nome no tab para todos os jogadores
        for (Player online : Bukkit.getOnlinePlayers()) {
            Scoreboard scoreboard = scoreboards.get(online.getUniqueId());
            if (scoreboard == null) continue;
            
            // Criar team para o jogador se não existir
            Team team = scoreboard.getTeam(player.getName());
            if (team == null) {
                team = scoreboard.registerNewTeam(player.getName());
            }
            
            // Definir prefixo baseado no gamemaster/guard
            String prefix = "";
            if (plugin.getGameMasterManager().isGameMaster(player)) {
                prefix = colorize("&#FF1744[GM] ");
            } else if (plugin.getGameMasterManager().isGuard(player)) {
                prefix = colorize("&#FF1744[GUARD] ");
            }
            
            team.setPrefix(prefix);
            team.addEntry(player.getName());
        }
    }
    
    public void removeScoreboard(Player player) {
        scoreboards.remove(player.getUniqueId());
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }
    
    public void updateScoreboard(Player player) {
        Scoreboard scoreboard = scoreboards.get(player.getUniqueId());
        if (scoreboard == null) return;
        
        Objective objective = scoreboard.getObjective("round6");
        if (objective == null) return;
        
        GameManager gameManager = plugin.getGameManager();
        int line = 15;
        
        // Linhas da scoreboard
        for (String lineText : plugin.getConfig().getStringList("scoreboard.lines")) {
            lineText = lineText
                    .replace("{players}", String.valueOf(gameManager.getPlayerCount()))
                    .replace("{alive}", String.valueOf(gameManager.getAliveCount()))
                    .replace("{time}", formatTime(gameManager.getGameTime()))
                    .replace("{state}", getStateText(gameManager.getState()))
                    .replace("{server}", "admin.savagemc.net");
            
            String colorized = colorize(lineText);
            
            // Usar um identificador único para cada linha para evitar piscadas
            String entry = colorized + "§" + line;
            if (entry.length() > 40) {
                entry = entry.substring(0, 40);
            }
            
            Score score = objective.getScore(entry);
            score.setScore(line--);
        }
    }
    
    private void updateAll() {
        for (UUID uuid : scoreboards.keySet()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null && player.isOnline()) {
                updateScoreboard(player);
            }
        }
    }
    
    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }
    
    private String getStateText(GameManager.GameState state) {
        switch (state) {
            case WAITING:
                return "&#FFFFFFᴀɢᴜᴀʀᴅᴀɴᴅᴏ";
            case COUNTDOWN:
                return "&#FF1744ɪɴɪᴄɪᴀɴᴅᴏ";
            case PLAYING:
                return "&#FF1744ᴇᴍ ᴊᴏɢᴏ";
            case ENDING:
                return "&#FFFFFFꜰɪɴᴀʟɪᴢᴀɴᴅᴏ";
            default:
                return "";
        }
    }
    
    private String colorize(String text) {
        // Suporte para cores hex &#RRGGBB
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("&#([0-9A-Fa-f]{6})");
        java.util.regex.Matcher matcher = pattern.matcher(text);
        
        while (matcher.find()) {
            String color = matcher.group(1);
            text = text.replace("&#" + color, net.md_5.bungee.api.ChatColor.of("#" + color).toString());
        }
        
        return text.replace("&", "§");
    }
}
