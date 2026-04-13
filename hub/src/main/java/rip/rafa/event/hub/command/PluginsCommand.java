package rip.rafa.event.hub.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.rafa.event.hub.util.TextUtil;

public class PluginsCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (!player.hasPermission("round6.admin")) {
            // Mostra apenas plugins próprios
            player.sendMessage(TextUtil.colorize("&#FF1744Plugins (3): &#FFFFFFAuth, Hub, EventBungee"));
            return true;
        }
        
        // Admin vê todos os plugins
        return false; // Deixa o comando padrão executar
    }
}
