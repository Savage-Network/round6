package rip.rafa.event.hub.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import rip.rafa.event.hub.HubPlugin;
import rip.rafa.event.hub.account.Account;
import rip.rafa.event.hub.account.AccountManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TabManager {
    
    private final HubPlugin plugin;
    private final Map<UUID, Scoreboard> scoreboards;
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    
    public TabManager(HubPlugin plugin) {
        this.plugin = plugin;
        this.scoreboards = new HashMap<>();
        
        // Atualizar tab a cada 2 segundos
        Bukkit.getScheduler().runTaskTimer(plugin, this::updateAll, 40L, 40L);
    }
    
    public void setupPlayer(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        scoreboards.put(player.getUniqueId(), scoreboard);
        player.setScoreboard(scoreboard);
        
        updatePlayer(player);
    }
    
    public void removePlayer(Player player) {
        scoreboards.remove(player.getUniqueId());
    }
    
    public void updatePlayer(Player player) {
        // Atualizar este jogador em todos os scoreboards
        for (Player online : Bukkit.getOnlinePlayers()) {
            Scoreboard scoreboard = scoreboards.get(online.getUniqueId());
            if (scoreboard == null) continue;
            
            updatePlayerInScoreboard(scoreboard, player);
        }
    }
    
    private void updatePlayerInScoreboard(Scoreboard scoreboard, Player player) {
        Account account = AccountManager.get(player.getUniqueId());
        if (account == null) return;
        
        String teamName = "r6_" + player.getName().substring(0, Math.min(player.getName().length(), 12));
        Team team = scoreboard.getTeam(teamName);
        
        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
        }
        
        // Limpar entradas antigas
        team.getEntries().forEach(team::removeEntry);
        
        // Prefixo: Rank
        String prefix = account.getRank().getPrefix();
        
        // Sufixo: vazio por padrão
        String suffix = "";
        
        team.setPrefix(colorize(prefix));
        team.setSuffix(colorize(suffix));
        team.addEntry(player.getName());
        
        // Atualizar display name acima da cabeça
        player.setDisplayName(colorize(prefix + player.getName()));
        player.setPlayerListName(colorize(prefix + player.getName()));
    }
    
    private void updateAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            updatePlayer(player);
        }
    }
    
    private String colorize(String message) {
        Matcher matcher = HEX_PATTERN.matcher(message);
        StringBuffer buffer = new StringBuffer();
        
        while (matcher.find()) {
            matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of("#" + matcher.group(1)).toString());
        }
        
        matcher.appendTail(buffer);
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', buffer.toString());
    }
}
