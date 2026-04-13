package rip.rafa.event.game.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import rip.rafa.event.game.GamePlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Round6TabCompleter implements TabCompleter {
    
    private final GamePlugin plugin;
    
    public Round6TabCompleter(GamePlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (!(sender instanceof Player)) {
            return completions;
        }
        
        Player player = (Player) sender;
        
        // /r6 <subcommand>
        if (args.length == 1) {
            List<String> subcommands = Arrays.asList("join", "leave");
            
            // Adicionar comandos admin
            if (player.hasPermission("round6.admin") || 
                plugin.getGameMasterManager().isGameMaster(player)) {
                subcommands = new ArrayList<>(subcommands);
                subcommands.addAll(Arrays.asList("gm", "gamemaster", "guard", "guarda", "start"));
            }
            
            return subcommands.stream()
                .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                .collect(Collectors.toList());
        }
        
        // /r6 gm <player|remove>
        if (args.length == 2 && (args[0].equalsIgnoreCase("gm") || args[0].equalsIgnoreCase("gamemaster"))) {
            if (player.hasPermission("round6.admin")) {
                completions.add("remove");
                completions.addAll(Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .collect(Collectors.toList()));
                
                return completions.stream()
                    .filter(s -> s.toLowerCase().startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
            }
        }
        
        // /r6 guard <add|remove|list>
        if (args.length == 2 && (args[0].equalsIgnoreCase("guard") || args[0].equalsIgnoreCase("guarda"))) {
            if (player.hasPermission("round6.admin") || 
                plugin.getGameMasterManager().isGameMaster(player)) {
                return Arrays.asList("add", "remove", "list").stream()
                    .filter(s -> s.toLowerCase().startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
            }
        }
        
        // /r6 guard add|remove <player>
        if (args.length == 3 && (args[0].equalsIgnoreCase("guard") || args[0].equalsIgnoreCase("guarda"))) {
            if (args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("remove")) {
                if (player.hasPermission("round6.admin") || 
                    plugin.getGameMasterManager().isGameMaster(player)) {
                    return Bukkit.getOnlinePlayers().stream()
                        .map(Player::getName)
                        .filter(s -> s.toLowerCase().startsWith(args[2].toLowerCase()))
                        .collect(Collectors.toList());
                }
            }
        }
        
        return completions;
    }
}
