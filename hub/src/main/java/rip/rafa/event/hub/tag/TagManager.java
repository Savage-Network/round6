package rip.rafa.event.hub.tag;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import rip.rafa.event.hub.HubPlugin;
import rip.rafa.event.hub.util.TextUtil;

import java.util.HashMap;
import java.util.Map;

public class TagManager {
    
    private final HubPlugin plugin;
    private final Map<String, TagData> tags = new HashMap<>();
    private boolean placeholderAPIEnabled = false;
    
    public TagManager(HubPlugin plugin) {
        this.plugin = plugin;
        this.placeholderAPIEnabled = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
        
        if (!placeholderAPIEnabled) {
            plugin.getLogger().warning("PlaceholderAPI não encontrado! Tags não funcionarão corretamente.");
        }
        
        loadDefaultTags();
    }
    
    private void loadDefaultTags() {
        // Tags padrão do Round6
        tags.put("default", new TagData("&#808080ᴊᴏɢᴀᴅᴏʀ", 999));
        tags.put("vip", new TagData("&#FFD700ᴠɪᴘ", 100));
        tags.put("vip+", new TagData("&#00FF00ᴠɪᴘ⁺", 90));
        tags.put("mvp", new TagData("&#00FFFF&#FFD700ᴍᴠᴘ", 80));
        tags.put("mvp+", new TagData("&#FF1744&#FFD700ᴍᴠᴘ⁺", 70));
        tags.put("youtuber", new TagData("&#FF0000ʏᴏᴜᴛᴜʙᴇʀ", 60));
        tags.put("helper", new TagData("&#00FF00ᴀᴊᴜᴅᴀɴᴛᴇ", 50));
        tags.put("mod", new TagData("&#FF8C00ᴍᴏᴅᴇʀᴀᴅᴏʀ", 40));
        tags.put("admin", new TagData("&#FF1744ᴀᴅᴍɪɴ", 30));
        tags.put("dono", new TagData("&#8B00FF&#FF1744ᴅᴏɴᴏ", 10));
    }
    
    public void updatePlayerTag(Player player) {
        if (!placeholderAPIEnabled) return;
        
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String tagKey = PlaceholderAPI.setPlaceholders(player, "%luckperms_primary_group%");
            TagData tagData = tags.getOrDefault(tagKey.toLowerCase(), tags.get("default"));
            
            Bukkit.getScheduler().runTask(plugin, () -> {
                setPlayerTag(player, tagData);
            });
        });
    }
    
    private void setPlayerTag(Player player, TagData tagData) {
        Scoreboard scoreboard = player.getScoreboard();
        if (scoreboard == Bukkit.getScoreboardManager().getMainScoreboard()) {
            scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            player.setScoreboard(scoreboard);
        }
        
        String teamName = String.format("%03d_%s", tagData.getPriority(), player.getName());
        if (teamName.length() > 16) {
            teamName = teamName.substring(0, 16);
        }
        
        Team team = scoreboard.getTeam(teamName);
        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
        }
        
        String prefix = TextUtil.colorize(tagData.getPrefix() + " §r");
        
        // Divide o prefix se for muito longo (limite de 16 caracteres no 1.8)
        if (prefix.length() > 16) {
            team.setPrefix(prefix.substring(0, 16));
        } else {
            team.setPrefix(prefix);
        }
        
        if (!team.hasEntry(player.getName())) {
            team.addEntry(player.getName());
        }
        
        // Atualiza para todos os jogadores
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (!online.equals(player)) {
                updatePlayerTagForViewer(online, player, tagData);
            }
        }
    }
    
    private void updatePlayerTagForViewer(Player viewer, Player target, TagData tagData) {
        Scoreboard scoreboard = viewer.getScoreboard();
        if (scoreboard == Bukkit.getScoreboardManager().getMainScoreboard()) {
            scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            viewer.setScoreboard(scoreboard);
        }
        
        String teamName = String.format("%03d_%s", tagData.getPriority(), target.getName());
        if (teamName.length() > 16) {
            teamName = teamName.substring(0, 16);
        }
        
        Team team = scoreboard.getTeam(teamName);
        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
        }
        
        String prefix = TextUtil.colorizeForPlayer(viewer, tagData.getPrefix() + " §r");
        
        if (prefix.length() > 16) {
            team.setPrefix(prefix.substring(0, 16));
        } else {
            team.setPrefix(prefix);
        }
        
        if (!team.hasEntry(target.getName())) {
            team.addEntry(target.getName());
        }
    }
    
    public void updateAllTags() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            updatePlayerTag(player);
        }
    }
    
    public void addTag(String key, String prefix, int priority) {
        tags.put(key.toLowerCase(), new TagData(prefix, priority));
    }
    
    public static class TagData {
        private final String prefix;
        private final int priority;
        
        public TagData(String prefix, int priority) {
            this.prefix = prefix;
            this.priority = priority;
        }
        
        public String getPrefix() {
            return prefix;
        }
        
        public int getPriority() {
            return priority;
        }
    }
}
