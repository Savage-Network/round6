package rip.rafa.event.hub.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import rip.rafa.event.hub.rank.Rank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AccountTabCompleter implements TabCompleter {
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            // Primeiro argumento: nome do jogador
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        if (args.length == 2) {
            // Segundo argumento: subcomandos
            if (sender.hasPermission("round6.admin")) {
                completions.addAll(Arrays.asList("rank", "info"));
            }
            return completions.stream()
                    .filter(s -> s.toLowerCase().startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        if (args.length == 3 && args[1].equalsIgnoreCase("rank")) {
            // Terceiro argumento para rank: set, remove, info
            completions.addAll(Arrays.asList("set", "remove", "info"));
            return completions.stream()
                    .filter(s -> s.toLowerCase().startsWith(args[2].toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        if (args.length == 4 && args[1].equalsIgnoreCase("rank") && args[2].equalsIgnoreCase("set")) {
            // Quarto argumento para rank set: tipos de rank
            return Arrays.stream(Rank.values())
                    .map(Enum::name)
                    .filter(s -> s.toLowerCase().startsWith(args[3].toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        return completions;
    }
}
