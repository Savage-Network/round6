package rip.rafa.event.game.command;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.rafa.event.game.GamePlugin;
import rip.rafa.event.game.manager.WhitelistManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WhitelistCommand implements CommandExecutor {
    
    private final GamePlugin plugin;
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    
    public WhitelistCommand(GamePlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("round6.admin")) {
            sender.sendMessage(colorize("&#FF1744ᴠᴏᴄᴇ̂ ɴᴀ̃ᴏ ᴛᴇᴍ ᴘᴇʀᴍɪꜱꜱᴀ̃ᴏ!"));
            return true;
        }
        
        WhitelistManager wl = plugin.getWhitelistManager();
        
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }
        
        switch (args[0].toLowerCase()) {
            case "on":
            case "ativar":
                wl.setEnabled(true);
                sender.sendMessage(colorize("&#FFFFFFᴡʜɪᴛᴇʟɪꜱᴛ &#FF1744ᴀᴛɪᴠᴀᴅᴀ"));
                plugin.getLogger().info("[WHITELIST] Ativada por " + sender.getName());
                break;
                
            case "off":
            case "desativar":
                wl.setEnabled(false);
                sender.sendMessage(colorize("&#FFFFFFᴡʜɪᴛᴇʟɪꜱᴛ &#FF1744ᴅᴇꜱᴀᴛɪᴠᴀᴅᴀ"));
                plugin.getLogger().info("[WHITELIST] Desativada por " + sender.getName());
                break;
                
            case "add":
            case "adicionar":
                if (args.length < 2) {
                    sender.sendMessage(colorize("&#FF1744ᴜꜱᴇ: /whitelist add <jogador>"));
                    return true;
                }
                
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(colorize("&#FF1744ᴊᴏɢᴀᴅᴏʀ ɴᴀ̃ᴏ ᴇɴᴄᴏɴᴛʀᴀᴅᴏ!"));
                    return true;
                }
                
                wl.addPlayer(target.getUniqueId(), target.getName());
                sender.sendMessage(colorize("&#FFFFFF" + target.getName() + " &#FF1744ᴀᴅɪᴄɪᴏɴᴀᴅᴏ ᴀ̀ ᴡʜɪᴛᴇʟɪꜱᴛ"));
                plugin.getLogger().info("[WHITELIST] " + target.getName() + " adicionado por " + sender.getName());
                break;
                
            case "remove":
            case "remover":
                if (args.length < 2) {
                    sender.sendMessage(colorize("&#FF1744ᴜꜱᴇ: /whitelist remove <jogador>"));
                    return true;
                }
                
                target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(colorize("&#FF1744ᴊᴏɢᴀᴅᴏʀ ɴᴀ̃ᴏ ᴇɴᴄᴏɴᴛʀᴀᴅᴏ!"));
                    return true;
                }
                
                wl.removePlayer(target.getUniqueId());
                sender.sendMessage(colorize("&#FFFFFF" + target.getName() + " &#FF1744ʀᴇᴍᴏᴠɪᴅᴏ ᴅᴀ ᴡʜɪᴛᴇʟɪꜱᴛ"));
                plugin.getLogger().info("[WHITELIST] " + target.getName() + " removido por " + sender.getName());
                break;
                
            case "list":
            case "lista":
                sender.sendMessage("");
                sender.sendMessage(colorize("&#FF1744§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
                sender.sendMessage(colorize("&#FFFFFF                    ᴡʜɪᴛᴇʟɪꜱᴛ"));
                sender.sendMessage(colorize("&#FF1744§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
                sender.sendMessage("");
                sender.sendMessage(colorize("&#FFFFFF §7▪ §fꜱᴛᴀᴛᴜꜱ: " + (wl.isEnabled() ? "&#FF1744ᴀᴛɪᴠᴀᴅᴀ" : "&#FFFFFFᴅᴇꜱᴀᴛɪᴠᴀᴅᴀ")));
                sender.sendMessage(colorize("&#FFFFFF §7▪ §fᴊᴏɢᴀᴅᴏʀᴇꜱ: &#FF1744" + wl.getWhitelistedPlayers().size()));
                sender.sendMessage("");
                
                if (!wl.getWhitelistedPlayers().isEmpty()) {
                    sender.sendMessage(colorize("&#FFFFFF §7ʟɪꜱᴛᴀ:"));
                    for (String name : wl.getWhitelistedPlayers().values()) {
                        sender.sendMessage(colorize("&#FFFFFF §7▪ &#FF1744" + name));
                    }
                    sender.sendMessage("");
                }
                
                sender.sendMessage(colorize("&#FF1744§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
                sender.sendMessage("");
                break;
                
            case "status":
                sender.sendMessage(colorize("&#FFFFFFᴡʜɪᴛᴇʟɪꜱᴛ: " + (wl.isEnabled() ? "&#FF1744ᴀᴛɪᴠᴀᴅᴀ" : "&#FFFFFFᴅᴇꜱᴀᴛɪᴠᴀᴅᴀ")));
                sender.sendMessage(colorize("&#FFFFFFᴊᴏɢᴀᴅᴏʀᴇꜱ: &#FF1744" + wl.getWhitelistedPlayers().size()));
                break;
                
            default:
                sendHelp(sender);
                break;
        }
        
        return true;
    }
    
    private void sendHelp(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(colorize("&#FF1744§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        sender.sendMessage(colorize("&#FFFFFF                    ᴄᴏᴍᴀɴᴅᴏꜱ ᴅᴇ ᴡʜɪᴛᴇʟɪꜱᴛ"));
        sender.sendMessage(colorize("&#FF1744§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        sender.sendMessage("");
        sender.sendMessage(colorize("&#FFFFFF §7▪ &#FF1744/whitelist on §7- §fᴀᴛɪᴠᴀʀ ᴡʜɪᴛᴇʟɪꜱᴛ"));
        sender.sendMessage(colorize("&#FFFFFF §7▪ &#FF1744/whitelist off §7- §fᴅᴇꜱᴀᴛɪᴠᴀʀ ᴡʜɪᴛᴇʟɪꜱᴛ"));
        sender.sendMessage(colorize("&#FFFFFF §7▪ &#FF1744/whitelist add <jogador> §7- §fᴀᴅɪᴄɪᴏɴᴀʀ ᴊᴏɢᴀᴅᴏʀ"));
        sender.sendMessage(colorize("&#FFFFFF §7▪ &#FF1744/whitelist remove <jogador> §7- §fʀᴇᴍᴏᴠᴇʀ ᴊᴏɢᴀᴅᴏʀ"));
        sender.sendMessage(colorize("&#FFFFFF §7▪ &#FF1744/whitelist list §7- §fʟɪꜱᴛᴀʀ ᴊᴏɢᴀᴅᴏʀᴇꜱ"));
        sender.sendMessage(colorize("&#FFFFFF §7▪ &#FF1744/whitelist status §7- §fᴠᴇʀ ꜱᴛᴀᴛᴜꜱ"));
        sender.sendMessage("");
        sender.sendMessage(colorize("&#FF1744§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        sender.sendMessage("");
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
