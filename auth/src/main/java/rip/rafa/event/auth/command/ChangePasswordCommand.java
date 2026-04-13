package rip.rafa.event.auth.command;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.rafa.event.auth.AuthPlugin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePasswordCommand implements CommandExecutor {
    
    private final AuthPlugin plugin;
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    
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
        
        // Verificar se está autenticado
        if (!plugin.getAuthManager().isAuthenticated(player)) {
            player.sendMessage(colorize(plugin.getConfig().getString("messages.prefix") + 
                "&#FF1744ᴠᴏᴄᴇ̂ ᴘʀᴇᴄɪꜱᴀ ᴇꜱᴛᴀʀ ʟᴏɢᴀᴅᴏ!"));
            return true;
        }
        
        // Sem argumentos: mostrar informações da conta
        if (args.length == 0) {
            showAccountInfo(player);
            return true;
        }
        
        // Subcomando: changepassword ou password
        if (args.length >= 1) {
            String subCommand = args[0].toLowerCase();
            
            switch (subCommand) {
                case "changepassword":
                case "password":
                case "senha":
                    handlePasswordChange(player, args);
                    break;
                    
                case "info":
                    showAccountInfo(player);
                    break;
                    
                case "email":
                    handleEmail(player, args);
                    break;
                    
                default:
                    sendHelp(player);
                    break;
            }
        }
        
        return true;
    }
    
    private void handlePasswordChange(Player player, String[] args) {
        if (args.length != 3) {
            player.sendMessage(colorize(plugin.getConfig().getString("messages.prefix") + 
                "&#FFFFFFᴜꜱᴇ: &#FF1744/acc password <senha_atual> <nova_senha>"));
            return;
        }
        
        String currentPassword = args[1];
        String newPassword = args[2];
        
        // Validar senha atual
        if (!plugin.getDatabaseManager().checkPassword(player.getUniqueId(), currentPassword)) {
            player.sendMessage(colorize(plugin.getConfig().getString("messages.prefix") + 
                "&#FF1744ꜱᴇɴʜᴀ ᴀᴛᴜᴀʟ ɪɴᴄᴏʀʀᴇᴛᴀ!"));
            return;
        }
        
        // Validar nova senha
        if (newPassword.length() < 4) {
            player.sendMessage(colorize(plugin.getConfig().getString("messages.prefix") + 
                "&#FF1744ᴀ ɴᴏᴠᴀ ꜱᴇɴʜᴀ ᴅᴇᴠᴇ ᴛᴇʀ ɴᴏ ᴍɪ́ɴɪᴍᴏ 4 ᴄᴀʀᴀᴄᴛᴇʀᴇꜱ!"));
            return;
        }
        
        if (newPassword.length() > 32) {
            player.sendMessage(colorize(plugin.getConfig().getString("messages.prefix") + 
                "&#FF1744ᴀ ɴᴏᴠᴀ ꜱᴇɴʜᴀ ᴅᴇᴠᴇ ᴛᴇʀ ɴᴏ ᴍᴀ́xɪᴍᴏ 32 ᴄᴀʀᴀᴄᴛᴇʀᴇꜱ!"));
            return;
        }
        
        // Não pode ser igual à senha atual
        if (currentPassword.equals(newPassword)) {
            player.sendMessage(colorize(plugin.getConfig().getString("messages.prefix") + 
                "&#FF1744ᴀ ɴᴏᴠᴀ ꜱᴇɴʜᴀ ɴᴀ̃ᴏ ᴘᴏᴅᴇ ꜱᴇʀ ɪɢᴜᴀʟ ᴀ̀ ᴀɴᴛɪɢᴀ!"));
            return;
        }
        
        // Atualizar senha
        boolean success = plugin.getDatabaseManager().updatePassword(player.getUniqueId(), newPassword);
        
        if (success) {
            player.sendMessage(colorize(plugin.getConfig().getString("messages.prefix") + 
                "&#FFFFFFꜱᴇɴʜᴀ ᴀʟᴛᴇʀᴀᴅᴀ ᴄᴏᴍ ꜱᴜᴄᴇꜱꜱᴏ!"));
            player.sendMessage(colorize("&#FFFFFF§7ᴜꜱᴇ ꜱᴜᴀ ɴᴏᴠᴀ ꜱᴇɴʜᴀ ɴᴏ ᴘʀᴏ́xɪᴍᴏ ʟᴏɢɪɴ."));
            
            plugin.getLogger().info("[SECURITY] Jogador " + player.getName() + " alterou sua senha.");
        } else {
            player.sendMessage(colorize(plugin.getConfig().getString("messages.prefix") + 
                "&#FF1744ᴇʀʀᴏ ᴀᴏ ᴀʟᴛᴇʀᴀʀ ꜱᴇɴʜᴀ. ᴛᴇɴᴛᴇ ɴᴏᴠᴀᴍᴇɴᴛᴇ."));
        }
    }
    
    private void handleEmail(Player player, String[] args) {
        if (args.length == 1) {
            // Mostrar email atual
            String email = plugin.getDatabaseManager().getEmail(player.getUniqueId());
            if (email == null || email.isEmpty()) {
                player.sendMessage(colorize(plugin.getConfig().getString("messages.prefix") + 
                    "&#FFFFFFᴠᴏᴄᴇ̂ ɴᴀ̃ᴏ ᴘᴏꜱꜱᴜɪ ᴇᴍᴀɪʟ ᴄᴀᴅᴀꜱᴛʀᴀᴅᴏ."));
                player.sendMessage(colorize("&#FFFFFF§7ᴜꜱᴇ: &#FF1744/acc email set <email>"));
            } else {
                player.sendMessage(colorize(plugin.getConfig().getString("messages.prefix") + 
                    "&#FFFFFFꜱᴇᴜ ᴇᴍᴀɪʟ: &#FF1744" + email));
            }
            return;
        }
        
        if (args.length == 3 && args[1].equalsIgnoreCase("set")) {
            String email = args[2];
            
            // Validar formato de email
            if (!isValidEmail(email)) {
                player.sendMessage(colorize(plugin.getConfig().getString("messages.prefix") + 
                    "&#FF1744ᴇᴍᴀɪʟ ɪɴᴠᴀ́ʟɪᴅᴏ!"));
                return;
            }
            
            // Atualizar email
            boolean success = plugin.getDatabaseManager().updateEmail(player.getUniqueId(), email);
            
            if (success) {
                player.sendMessage(colorize(plugin.getConfig().getString("messages.prefix") + 
                    "&#FFFFFFᴇᴍᴀɪʟ ᴅᴇꜰɪɴɪᴅᴏ ᴄᴏᴍ ꜱᴜᴄᴇꜱꜱᴏ!"));
                plugin.getLogger().info("[SECURITY] Jogador " + player.getName() + " definiu email: " + email);
            } else {
                player.sendMessage(colorize(plugin.getConfig().getString("messages.prefix") + 
                    "&#FF1744ᴇʀʀᴏ ᴀᴏ ᴅᴇꜰɪɴɪʀ ᴇᴍᴀɪʟ."));
            }
            return;
        }
        
        player.sendMessage(colorize(plugin.getConfig().getString("messages.prefix") + 
            "&#FFFFFFᴜꜱᴇ: &#FF1744/acc email set <email>"));
    }
    
    private void showAccountInfo(Player player) {
        player.sendMessage("");
        player.sendMessage(colorize("&#FF1744§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        player.sendMessage(colorize("&#FFFFFF                    ɪɴꜰᴏʀᴍᴀᴄ̧ᴏ̃ᴇꜱ ᴅᴀ ᴄᴏɴᴛᴀ"));
        player.sendMessage(colorize("&#FF1744§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        player.sendMessage("");
        
        // Buscar informações do banco
        String[] accountInfo = plugin.getDatabaseManager().getAccountInfo(player.getUniqueId());
        
        if (accountInfo != null) {
            String username = accountInfo[0];
            String registeredAt = accountInfo[1];
            String lastLogin = accountInfo[2];
            String lastIp = accountInfo[3];
            String email = accountInfo[4];
            
            player.sendMessage(colorize("&#FFFFFF §7▪ §fᴜꜱᴜᴀ́ʀɪᴏ: &#FF1744" + username));
            player.sendMessage(colorize("&#FFFFFF §7▪ §fᴜᴜɪᴅᴅ: &#FF1744" + player.getUniqueId().toString()));
            player.sendMessage(colorize("&#FFFFFF §7▪ §fᴛɪᴘᴏ: &#FF1744" + (isPremium(player) ? "Original" : "Pirata")));
            player.sendMessage("");
            player.sendMessage(colorize("&#FFFFFF §7▪ §fʀᴇɢɪꜱᴛʀᴏ: &#FF1744" + registeredAt));
            player.sendMessage(colorize("&#FFFFFF §7▪ §fᴜ́ʟᴛɪᴍᴏ ʟᴏɢɪɴ: &#FF1744" + lastLogin));
            player.sendMessage(colorize("&#FFFFFF §7▪ §fᴜ́ʟᴛɪᴍᴏ ɪᴘ: &#FF1744" + lastIp));
            
            if (email != null && !email.isEmpty()) {
                player.sendMessage(colorize("&#FFFFFF §7▪ §fᴇᴍᴀɪʟ: &#FF1744" + email));
            } else {
                player.sendMessage(colorize("&#FFFFFF §7▪ §fᴇᴍᴀɪʟ: &#FF1744ɴᴀ̃ᴏ ᴅᴇꜰɪɴɪᴅᴏ"));
            }
        }
        
        player.sendMessage("");
        player.sendMessage(colorize("&#FF1744§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        player.sendMessage("");
    }
    
    private void sendHelp(Player player) {
        player.sendMessage("");
        player.sendMessage(colorize("&#FF1744§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        player.sendMessage(colorize("&#FFFFFF                    ᴄᴏᴍᴀɴᴅᴏꜱ ᴅᴇ ᴄᴏɴᴛᴀ"));
        player.sendMessage(colorize("&#FF1744§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        player.sendMessage("");
        player.sendMessage(colorize("&#FFFFFF §7▪ &#FF1744/acc §7- §fᴠᴇʀ ɪɴꜰᴏʀᴍᴀᴄ̧ᴏ̃ᴇꜱ ᴅᴀ ꜱᴜᴀ ᴄᴏɴᴛᴀ"));
        player.sendMessage(colorize("&#FFFFFF §7▪ &#FF1744/acc password <atual> <nova> §7- §fᴛʀᴏᴄᴀʀ ꜱᴇɴʜᴀ"));
        player.sendMessage(colorize("&#FFFFFF §7▪ &#FF1744/acc email §7- §fᴠᴇʀ ꜱᴇᴜ ᴇᴍᴀɪʟ"));
        player.sendMessage(colorize("&#FFFFFF §7▪ &#FF1744/acc email set <email> §7- §fᴅᴇꜰɪɴɪʀ ᴇᴍᴀɪʟ"));
        player.sendMessage("");
        player.sendMessage(colorize("&#FF1744§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        player.sendMessage("");
    }
    
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }
    
    private boolean isPremium(Player player) {
        // Verifica se o UUID é válido (premium) ou offline (pirata)
        // UUIDs offline começam com "00000000-0000-3"
        String uuid = player.getUniqueId().toString();
        return !uuid.startsWith("00000000-0000-3");
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
