package rip.rafa.event.game.command;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.rafa.event.game.GamePlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountCommand implements CommandExecutor {
    
    private final GamePlugin plugin;
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    
    public AccountCommand(GamePlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Sem argumentos: mostra informações do próprio executor
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cConsole não possui conta.");
                return true;
            }
            
            Player player = (Player) sender;
            showAccountInfo(sender, player.getName(), player.getUniqueId());
            return true;
        }
        
        // Com 1 argumento: mostra informações do jogador especificado
        if (args.length == 1) {
            if (!sender.hasPermission("round6.admin")) {
                sender.sendMessage(colorize("&#FF1744ᴠᴏᴄᴇ̂ ɴᴀ̃ᴏ ᴛᴇᴍ ᴘᴇʀᴍɪꜱꜱᴀ̃ᴏ ᴘᴀʀᴀ ᴠᴇʀ ᴀ ᴄᴏɴᴛᴀ ᴅᴇ ᴏᴜᴛʀᴏꜱ ᴊᴏɢᴀᴅᴏʀᴇꜱ."));
                return true;
            }
            
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(colorize("&#FF1744ᴊᴏɢᴀᴅᴏʀ ɴᴀ̃ᴏ ᴇɴᴄᴏɴᴛʀᴀᴅᴏ."));
                return true;
            }
            
            showAccountInfo(sender, target.getName(), target.getUniqueId());
            return true;
        }
        
        sender.sendMessage(colorize("&#FF1744ᴜꜱᴇ: /acc [jogador]"));
        return true;
    }
    
    private void showAccountInfo(CommandSender sender, String targetName, UUID uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try (Connection conn = plugin.getDatabaseManager().getConnection()) {
                String query = "SELECT * FROM accounts WHERE uuid = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, uuid.toString());
                
                ResultSet rs = stmt.executeQuery();
                
                if (!rs.next()) {
                    sender.sendMessage(colorize("&#FF1744ᴄᴏɴᴛᴀ ɴᴀ̃ᴏ ᴇɴᴄᴏɴᴛʀᴀᴅᴀ."));
                    return;
                }
                
                String name = rs.getString("name");
                String rank = rs.getString("rank");
                int number = rs.getInt("number");
                int wins = rs.getInt("round6_wins");
                int losses = rs.getInt("round6_losses");
                int kills = rs.getInt("round6_kills");
                int deaths = rs.getInt("round6_deaths");
                
                double kdr = deaths > 0 ? (double) kills / deaths : kills;
                double winrate = (wins + losses) > 0 ? (double) wins / (wins + losses) * 100 : 0;
                
                sender.sendMessage("");
                sender.sendMessage(colorize("&#FF1744§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
                sender.sendMessage(colorize("&#FFFFFF                    ɪɴꜰᴏʀᴍᴀᴄ̧ᴏ̃ᴇꜱ ᴅᴀ ᴄᴏɴᴛᴀ"));
                sender.sendMessage(colorize("&#FF1744§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
                sender.sendMessage("");
                sender.sendMessage(colorize("&#FFFFFF §7▪ §fᴜꜱᴜᴀ́ʀɪᴏ: &#FF1744" + name));
                sender.sendMessage(colorize("&#FFFFFF §7▪ §fᴜᴜɪᴅ: &#FF1744" + uuid.toString()));
                sender.sendMessage(colorize("&#FFFFFF §7▪ §fʀᴀɴᴋ: &#FF1744" + rank));
                sender.sendMessage(colorize("&#FFFFFF §7▪ §fɴᴜ́ᴍᴇʀᴏ: &#FF1744" + (number > 0 ? String.format("%03d", number) : "ɴᴀ̃ᴏ ᴀᴛʀɪʙᴜɪ́ᴅᴏ")));
                sender.sendMessage("");
                sender.sendMessage(colorize("&#FFFFFF §7▪ §fᴠɪᴛᴏ́ʀɪᴀꜱ: &#FF1744" + wins));
                sender.sendMessage(colorize("&#FFFFFF §7▪ §fᴅᴇʀʀᴏᴛᴀꜱ: &#FF1744" + losses));
                sender.sendMessage(colorize("&#FFFFFF §7▪ §fᴋɪʟʟꜱ: &#FF1744" + kills));
                sender.sendMessage(colorize("&#FFFFFF §7▪ §fᴅᴇᴀᴛʜꜱ: &#FF1744" + deaths));
                sender.sendMessage(colorize("&#FFFFFF §7▪ §fᴋᴅʀ: &#FF1744" + String.format("%.2f", kdr)));
                sender.sendMessage(colorize("&#FFFFFF §7▪ §fᴡɪɴʀᴀᴛᴇ: &#FF1744" + String.format("%.1f%%", winrate)));
                sender.sendMessage("");
                sender.sendMessage(colorize("&#FF1744§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
                sender.sendMessage("");
                
            } catch (Exception e) {
                sender.sendMessage(colorize("&#FF1744ᴇʀʀᴏ ᴀᴏ ʙᴜꜱᴄᴀʀ ɪɴꜰᴏʀᴍᴀᴄ̧ᴏ̃ᴇꜱ ᴅᴀ ᴄᴏɴᴛᴀ."));
                plugin.getLogger().severe("Erro ao buscar conta: " + e.getMessage());
            }
        });
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
