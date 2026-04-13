package rip.rafa.event.auth.command;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.rafa.event.auth.AuthPlugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePasswordCommand implements CommandExecutor {
    
    private final AuthPlugin plugin;
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    
    public ChangePasswordCommand(AuthPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (!plugin.getAuthManager().isAuthenticated(player)) {
            player.sendMessage(colorize(plugin.getConfig().getString("messages.prefix") + 
                "&#FF1744ᴠᴏᴄᴇ̂ ᴘʀᴇᴄɪꜱᴀ ᴇꜱᴛᴀʀ ʟᴏɢᴀᴅᴏ ᴘᴀʀᴀ ᴛʀᴏᴄᴀʀ ꜱᴜᴀ ꜱᴇɴʜᴀ!"));
            return true;
        }
        
        if (args.length != 2) {
            player.sendMessage(colorize(plugin.getConfig().getString("messages.prefix") + 
                "&#FFFFFFᴜꜱᴇ: &#FF1744/acc <senha_atual> <nova_senha>"));
            return true;
        }
        
        String currentPassword = args[0];
        String newPassword = args[1];
        
        // Validar senha atual
        if (!plugin.getDatabaseManager().checkPassword(player.getUniqueId(), currentPassword)) {
            player.sendMessage(colorize(plugin.getConfig().getString("messages.prefix") + 
                "&#FF1744ꜱᴇɴʜᴀ ᴀᴛᴜᴀʟ ɪɴᴄᴏʀʀᴇᴛᴀ!"));
            return true;
        }
        
        // Validar nova senha
        if (newPassword.length() < 4) {
            player.sendMessage(colorize(plugin.getConfig().getString("messages.prefix") + 
                "&#FF1744ᴀ ɴᴏᴠᴀ ꜱᴇɴʜᴀ ᴅᴇᴠᴇ ᴛᴇʀ ɴᴏ ᴍɪ́ɴɪᴍᴏ 4 ᴄᴀʀᴀᴄᴛᴇʀᴇꜱ!"));
            return true;
        }
        
        if (newPassword.length() > 32) {
            player.sendMessage(colorize(plugin.getConfig().getString("messages.prefix") + 
                "&#FF1744ᴀ ɴᴏᴠᴀ ꜱᴇɴʜᴀ ᴅᴇᴠᴇ ᴛᴇʀ ɴᴏ ᴍᴀ́xɪᴍᴏ 32 ᴄᴀʀᴀᴄᴛᴇʀᴇꜱ!"));
            return true;
        }
        
        // Atualizar senha
        plugin.getDatabaseManager().updatePassword(player.getUniqueId(), newPassword);
        
        player.sendMessage(colorize(plugin.getConfig().getString("messages.prefix") + 
            "&#FFFFFFꜱᴇɴʜᴀ ᴀʟᴛᴇʀᴀᴅᴀ ᴄᴏᴍ ꜱᴜᴄᴇꜱꜱᴏ!"));
        
        plugin.getLogger().info("Jogador " + player.getName() + " alterou sua senha.");
        
        return true;
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
