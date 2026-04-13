package rip.rafa.event.hub.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.rafa.event.hub.HubPlugin;

public class NPCCommand implements CommandExecutor {
    
    private final HubPlugin plugin;
    
    public NPCCommand(HubPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (!player.hasPermission("event.admin")) {
            player.sendMessage("§cVocê não tem permissão!");
            return true;
        }
        
        if (args.length == 0) {
            player.sendMessage("§cUso: /npc reload");
            return true;
        }
        
        if (args[0].equalsIgnoreCase("reload")) {
            plugin.getNpcManager().reloadNPCs();
            player.sendMessage("§aNPCs recarregados!");
        } else {
            player.sendMessage("§cUso: /npc reload");
        }
        
        return true;
    }
}
