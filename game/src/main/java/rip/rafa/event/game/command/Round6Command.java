package rip.rafa.event.game.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.rafa.event.game.GamePlugin;

public class Round6Command implements CommandExecutor {
    
    private final GamePlugin plugin;
    
    public Round6Command(GamePlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (args.length == 0) {
            sendHelp(player);
            return true;
        }
        
        switch (args[0].toLowerCase()) {
            case "gm":
            case "gamemaster":
                handleGameMaster(player, args);
                break;
                
            case "guard":
            case "guarda":
                handleGuard(player, args);
                break;
                
            case "join":
                if (plugin.getGameManager().isInGame(player)) {
                    player.sendMessage("§cVocê já está no jogo!");
                    return true;
                }
                plugin.getGameManager().addPlayer(player);
                break;
                
            case "leave":
                if (!plugin.getGameManager().isInGame(player)) {
                    player.sendMessage("§cVocê não está no jogo!");
                    return true;
                }
                plugin.getGameManager().removePlayer(player);
                break;
                
            case "start":
                if (!player.hasPermission("round6.admin") && 
                    !plugin.getGameMasterManager().isGameMaster(player)) {
                    player.sendMessage("§cVocê não tem permissão!");
                    return true;
                }
                player.sendMessage("§aIniciando jogo...");
                // TODO: Implementar início forçado
                break;
                
            default:
                sendHelp(player);
                break;
        }
        
        return true;
    }
    
    private void handleGameMaster(Player player, String[] args) {
        if (!player.hasPermission("round6.admin")) {
            player.sendMessage("§cVocê não tem permissão!");
            return;
        }
        
        if (args.length < 2) {
            player.sendMessage("§cUso: /r6 gm <player|remove>");
            return;
        }
        
        if (args[1].equalsIgnoreCase("remove")) {
            plugin.getGameMasterManager().removeGameMaster();
            player.sendMessage("§aGamemaster removido!");
            return;
        }
        
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            player.sendMessage("§cJogador não encontrado!");
            return;
        }
        
        plugin.getGameMasterManager().setGameMaster(target);
        player.sendMessage("§a" + target.getName() + " agora é o Gamemaster!");
    }
    
    private void handleGuard(Player player, String[] args) {
        if (!player.hasPermission("round6.admin") && 
            !plugin.getGameMasterManager().isGameMaster(player)) {
            player.sendMessage("§cVocê não tem permissão!");
            return;
        }
        
        if (args.length < 2) {
            player.sendMessage("§cUso: /r6 guard <add|remove|list> [player]");
            return;
        }
        
        switch (args[1].toLowerCase()) {
            case "add":
                if (args.length < 3) {
                    player.sendMessage("§cUso: /r6 guard add <player>");
                    return;
                }
                Player target = Bukkit.getPlayer(args[2]);
                if (target == null) {
                    player.sendMessage("§cJogador não encontrado!");
                    return;
                }
                plugin.getGameMasterManager().addGuard(target);
                player.sendMessage("§a" + target.getName() + " agora é um guarda!");
                break;
                
            case "remove":
                if (args.length < 3) {
                    player.sendMessage("§cUso: /r6 guard remove <player>");
                    return;
                }
                target = Bukkit.getPlayer(args[2]);
                if (target == null) {
                    player.sendMessage("§cJogador não encontrado!");
                    return;
                }
                plugin.getGameMasterManager().removeGuard(target);
                player.sendMessage("§a" + target.getName() + " não é mais um guarda!");
                break;
                
            case "list":
                player.sendMessage("§aGuardas: " + plugin.getGameMasterManager().getGuards().size());
                break;
                
            default:
                player.sendMessage("§cUso: /r6 guard <add|remove|list> [player]");
                break;
        }
    }
    
    private void sendHelp(Player player) {
        player.sendMessage("§6§l=== Round 6 do Rafa ===");
        player.sendMessage("§e/r6 join §7- Entrar no jogo");
        player.sendMessage("§e/r6 leave §7- Sair do jogo");
        
        if (player.hasPermission("round6.admin") || 
            plugin.getGameMasterManager().isGameMaster(player)) {
            player.sendMessage("§c§lComandos Admin:");
            player.sendMessage("§e/r6 gm <player|remove> §7- Gerenciar gamemaster");
            player.sendMessage("§e/r6 guard <add|remove|list> §7- Gerenciar guardas");
            player.sendMessage("§e/r6 start §7- Iniciar jogo");
        }
    }
}
