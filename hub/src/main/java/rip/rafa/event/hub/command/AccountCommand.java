package rip.rafa.event.hub.command;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.rafa.event.hub.HubPlugin;
import rip.rafa.event.hub.account.Account;
import rip.rafa.event.hub.account.AccountManager;
import rip.rafa.event.hub.rank.Rank;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountCommand implements CommandExecutor {
    
    private final HubPlugin plugin;
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    
    public AccountCommand(HubPlugin plugin) {
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
            showAccountInfo(sender, player.getName());
            return true;
        }
        
        // Com 1 argumento: mostra informações do jogador especificado
        if (args.length == 1) {
            if (!sender.hasPermission("round6.admin")) {
                sender.sendMessage(colorize("&#FF1744ᴠᴏᴄᴇ̂ ɴᴀ̃ᴏ ᴛᴇᴍ ᴘᴇʀᴍɪꜱꜱᴀ̃ᴏ ᴘᴀʀᴀ ᴠᴇʀ ᴀ ᴄᴏɴᴛᴀ ᴅᴇ ᴏᴜᴛʀᴏꜱ ᴊᴏɢᴀᴅᴏʀᴇꜱ."));
                return true;
            }
            
            showAccountInfo(sender, args[0]);
            return true;
        }
        
        // Com 2+ argumentos: subcomandos administrativos
        if (!sender.hasPermission("round6.admin")) {
            sender.sendMessage(colorize("&#FF1744ᴠᴏᴄᴇ̂ ɴᴀ̃ᴏ ᴛᴇᴍ ᴘᴇʀᴍɪꜱꜱᴀ̃ᴏ ᴘᴀʀᴀ ᴜꜱᴀʀ ᴇꜱᴛᴇ ᴄᴏᴍᴀɴᴅᴏ."));
            return true;
        }
        
        String targetName = args[0];
        String subCommand = args[1].toLowerCase();
        
        switch (subCommand) {
            case "rank":
                handleRank(sender, targetName, args);
                break;
                
            case "info":
                showAccountInfo(sender, targetName);
                break;
                
            default:
                sendHelp(sender);
                break;
        }
        
        return true;
    }
    
    private void handleRank(CommandSender sender, String targetName, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(colorize("&#FF1744ᴜꜱᴇ: /acc <jogador> rank <set|remove|info>"));
            return;
        }
        
        String action = args[2].toLowerCase();
        
        switch (action) {
            case "set":
                if (args.length < 4) {
                    sender.sendMessage(colorize("&#FF1744ᴜꜱᴇ: /acc <jogador> rank set <rank>"));
                    return;
                }
                setRank(sender, targetName, args[3]);
                break;
                
            case "remove":
                removeRank(sender, targetName);
                break;
                
            case "info":
                showRankInfo(sender, targetName);
                break;
                
            default:
                sender.sendMessage(colorize("&#FF1744ᴀᴄ̧ᴀ̃ᴏ ɪɴᴠᴀ́ʟɪᴅᴀ. ᴜꜱᴇ: set, remove ou info"));
                break;
        }
    }
    
    private void setRank(CommandSender sender, String targetName, String rankName) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            Account account = AccountManager.getByName(targetName);
            
            if (account == null) {
                sender.sendMessage(colorize("&#FF1744ᴊᴏɢᴀᴅᴏʀ ɴᴀ̃ᴏ ᴇɴᴄᴏɴᴛʀᴀᴅᴏ."));
                return;
            }
            
            Rank rank;
            try {
                rank = Rank.valueOf(rankName.toUpperCase());
            } catch (IllegalArgumentException e) {
                sender.sendMessage(colorize("&#FF1744ʀᴀɴᴋ ɪɴᴠᴀ́ʟɪᴅᴏ. ᴅɪꜱᴘᴏɴɪ́ᴠᴇɪꜱ: DEFAULT, VIP, ADMIN, OWNER"));
                return;
            }
            
            account.setRank(rank);
            AccountManager.save(account);
            
            sender.sendMessage(colorize("&#FFFFFFʀᴀɴᴋ &#FF1744" + rank.name() + " &#FFFFFFᴅᴇꜰɪɴɪᴅᴏ ᴘᴀʀᴀ &#FF1744" + targetName));
            
            // Atualiza jogador se estiver online
            Player target = Bukkit.getPlayer(targetName);
            if (target != null) {
                target.sendMessage(colorize("&#FFFFFFꜱᴇᴜ ʀᴀɴᴋ ꜰᴏɪ ᴀʟᴛᴇʀᴀᴅᴏ ᴘᴀʀᴀ: " + rank.getPrefix() + rank.name()));
            }
            
            plugin.getLogger().info("[ADMIN] " + sender.getName() + " definiu rank " + rank.name() + " para " + targetName);
        });
    }
    
    private void removeRank(CommandSender sender, String targetName) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            Account account = AccountManager.getByName(targetName);
            
            if (account == null) {
                sender.sendMessage(colorize("&#FF1744ᴊᴏɢᴀᴅᴏʀ ɴᴀ̃ᴏ ᴇɴᴄᴏɴᴛʀᴀᴅᴏ."));
                return;
            }
            
            account.setRank(Rank.DEFAULT);
            AccountManager.save(account);
            
            sender.sendMessage(colorize("&#FFFFFFʀᴀɴᴋ ᴅᴇ &#FF1744" + targetName + " &#FFFFFFʀᴇᴍᴏᴠɪᴅᴏ (ᴠᴏʟᴛᴏᴜ ᴘᴀʀᴀ DEFAULT)"));
            
            // Atualiza jogador se estiver online
            Player target = Bukkit.getPlayer(targetName);
            if (target != null) {
                target.sendMessage(colorize("&#FFFFFFꜱᴇᴜ ʀᴀɴᴋ ꜰᴏɪ ʀᴇᴍᴏᴠɪᴅᴏ."));
            }
            
            plugin.getLogger().info("[ADMIN] " + sender.getName() + " removeu rank de " + targetName);
        });
    }
    
    private void showRankInfo(CommandSender sender, String targetName) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            Account account = AccountManager.getByName(targetName);
            
            if (account == null) {
                sender.sendMessage(colorize("&#FF1744ᴊᴏɢᴀᴅᴏʀ ɴᴀ̃ᴏ ᴇɴᴄᴏɴᴛʀᴀᴅᴏ."));
                return;
            }
            
            Rank rank = account.getRank();
            
            sender.sendMessage("");
            sender.sendMessage(colorize("&#FF1744§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
            sender.sendMessage(colorize("&#FFFFFF                    ɪɴꜰᴏʀᴍᴀᴄ̧ᴏ̃ᴇꜱ ᴅᴇ ʀᴀɴᴋ"));
            sender.sendMessage(colorize("&#FF1744§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
            sender.sendMessage("");
            sender.sendMessage(colorize("&#FFFFFF §7▪ §fᴊᴏɢᴀᴅᴏʀ: &#FF1744" + targetName));
            sender.sendMessage(colorize("&#FFFFFF §7▪ §fʀᴀɴᴋ: " + rank.getPrefix() + rank.name()));
            sender.sendMessage(colorize("&#FFFFFF §7▪ §fᴘʀᴇꜰɪxᴏ: " + rank.getPrefix() + "ᴇxᴇᴍᴘʟᴏ"));
            sender.sendMessage("");
            sender.sendMessage(colorize("&#FF1744§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
            sender.sendMessage("");
        });
    }
    
    private void showAccountInfo(CommandSender sender, String targetName) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            Account account = AccountManager.getByName(targetName);
            
            if (account == null) {
                sender.sendMessage(colorize("&#FF1744ᴊᴏɢᴀᴅᴏʀ ɴᴀ̃ᴏ ᴇɴᴄᴏɴᴛʀᴀᴅᴏ."));
                return;
            }
            
            sender.sendMessage("");
            sender.sendMessage(colorize("&#FF1744§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
            sender.sendMessage(colorize("&#FFFFFF                    ɪɴꜰᴏʀᴍᴀᴄ̧ᴏ̃ᴇꜱ ᴅᴀ ᴄᴏɴᴛᴀ"));
            sender.sendMessage(colorize("&#FF1744§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
            sender.sendMessage("");
            sender.sendMessage(colorize("&#FFFFFF §7▪ §fᴜꜱᴜᴀ́ʀɪᴏ: &#FF1744" + account.getName()));
            sender.sendMessage(colorize("&#FFFFFF §7▪ §fᴜᴜɪᴅ: &#FF1744" + account.getUuid().toString()));
            sender.sendMessage(colorize("&#FFFFFF §7▪ §fʀᴀɴᴋ: " + account.getRank().getPrefix() + account.getRank().name()));
            sender.sendMessage(colorize("&#FFFFFF §7▪ §fɴᴜ́ᴍᴇʀᴏ: &#FF1744" + (account.getNumber() > 0 ? String.format("%03d", account.getNumber()) : "ɴᴀ̃ᴏ ᴀᴛʀɪʙᴜɪ́ᴅᴏ")));
            sender.sendMessage("");
            sender.sendMessage(colorize("&#FFFFFF §7▪ §fᴠɪᴛᴏ́ʀɪᴀꜱ: &#FF1744" + account.getRound6Wins()));
            sender.sendMessage(colorize("&#FFFFFF §7▪ §fᴅᴇʀʀᴏᴛᴀꜱ: &#FF1744" + account.getRound6Losses()));
            sender.sendMessage(colorize("&#FFFFFF §7▪ §fᴋᴅʀ: &#FF1744" + String.format("%.2f", account.getKDR())));
            sender.sendMessage(colorize("&#FFFFFF §7▪ §fᴡɪɴʀᴀᴛᴇ: &#FF1744" + String.format("%.1f%%", account.getWinRate())));
            sender.sendMessage("");
            sender.sendMessage(colorize("&#FF1744§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
            sender.sendMessage("");
        });
    }
    
    private void sendHelp(CommandSender sender) {
        sender.sendMessage("");
        sender.sendMessage(colorize("&#FF1744§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        sender.sendMessage(colorize("&#FFFFFF                    ᴄᴏᴍᴀɴᴅᴏꜱ ᴅᴇ ᴄᴏɴᴛᴀ"));
        sender.sendMessage(colorize("&#FF1744§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        sender.sendMessage("");
        sender.sendMessage(colorize("&#FFFFFF §7▪ &#FF1744/acc §7- §fᴠᴇʀ ꜱᴜᴀꜱ ɪɴꜰᴏʀᴍᴀᴄ̧ᴏ̃ᴇꜱ"));
        sender.sendMessage(colorize("&#FFFFFF §7▪ &#FF1744/acc <jogador> §7- §fᴠᴇʀ ɪɴꜰᴏʀᴍᴀᴄ̧ᴏ̃ᴇꜱ ᴅᴇ ᴀʟɢᴜᴇ́ᴍ"));
        sender.sendMessage("");
        
        if (sender.hasPermission("round6.admin")) {
            sender.sendMessage(colorize("&#FFFFFF §7§l▪ ᴄᴏᴍᴀɴᴅᴏꜱ ᴀᴅᴍɪɴɪꜱᴛʀᴀᴛɪᴠᴏꜱ:"));
            sender.sendMessage(colorize("&#FFFFFF §7▪ &#FF1744/acc <jogador> rank set <rank> §7- §fᴅᴇꜰɪɴɪʀ ʀᴀɴᴋ"));
            sender.sendMessage(colorize("&#FFFFFF §7▪ &#FF1744/acc <jogador> rank remove §7- §fʀᴇᴍᴏᴠᴇʀ ʀᴀɴᴋ"));
            sender.sendMessage(colorize("&#FFFFFF §7▪ &#FF1744/acc <jogador> rank info §7- §fɪɴꜰᴏ ᴅᴏ ʀᴀɴᴋ"));
            sender.sendMessage("");
            sender.sendMessage(colorize("&#FFFFFF §7ʀᴀɴᴋꜱ ᴅɪꜱᴘᴏɴɪ́ᴠᴇɪꜱ: §fDEFAULT, VIP, ADMIN, OWNER"));
        }
        
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
