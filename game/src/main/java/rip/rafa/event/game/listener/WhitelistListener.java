package rip.rafa.event.game.listener;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import rip.rafa.event.game.GamePlugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WhitelistListener implements Listener {
    
    private final GamePlugin plugin;
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    
    public WhitelistListener(GamePlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogin(PlayerLoginEvent event) {
        // Verifica se tem permissão de admin (bypass whitelist)
        if (event.getPlayer().hasPermission("round6.admin")) {
            return;
        }
        
        // Verifica whitelist
        if (!plugin.getWhitelistManager().canJoin(event.getPlayer().getUniqueId())) {
            event.disallow(
                PlayerLoginEvent.Result.KICK_WHITELIST,
                colorize("\n&#FF1744§l⬛ ROUND 6 ⬛\n\n&#FFFFFFᴠᴏᴄᴇ̂ ɴᴀ̃ᴏ ᴇꜱᴛᴀ́ ɴᴀ ᴡʜɪᴛᴇʟɪꜱᴛ!\n\n&#FF1744ᴄᴏɴᴛᴀᴛᴇ ᴜᴍ ᴀᴅᴍɪɴɪꜱᴛʀᴀᴅᴏʀ\n")
            );
            plugin.getLogger().info("[WHITELIST] " + event.getPlayer().getName() + " tentou entrar mas não está na whitelist");
        }
    }
    
    private String colorize(String message) {
        Matcher matcher = HEX_PATTERN.matcher(message);
        StringBuffer buffer = new StringBuffer();
        
        while (matcher.find()) {
            matcher.appendReplacement(buffer, ChatColor.of("#" + matcher.group(1)).toString());
        }
        
        matcher.appendTail(buffer);
        return ChatColor.translateAlternateColorCodes('&', buffer.toString());
    }
}
