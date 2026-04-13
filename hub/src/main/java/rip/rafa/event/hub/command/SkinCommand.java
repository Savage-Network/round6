package rip.rafa.event.hub.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.rafa.event.hub.HubPlugin;

public class SkinCommand implements CommandExecutor {
    
    private final HubPlugin plugin;
    
    public SkinCommand(HubPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (args.length == 0) {
            player.sendMessage("§cUso: /skin <jogador>");
            return true;
        }
        
        String skinName = args[0];
        player.sendMessage("§eBuscando skin de §f" + skinName + "§e...");
        
        plugin.getSkinManager().applySkin(player, skinName);
        
        return true;
    }
}
