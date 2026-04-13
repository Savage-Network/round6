package rip.rafa.event.bungee.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import rip.rafa.event.bungee.EventBungee;

public class AccountCommand extends Command {
    
    private final EventBungee plugin;
    
    public AccountCommand(EventBungee plugin) {
        super("acc", "event.admin", "account");
        this.plugin = plugin;
    }
    
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando!");
            return;
        }
        
        ProxiedPlayer player = (ProxiedPlayer) sender;
        
        if (args.length == 0) {
            sendHelp(player);
            return;
        }
        
        String subCommand = args[0].toLowerCase();
        
        switch (subCommand) {
            case "info":
                if (args.length < 2) {
                    player.sendMessage("§cUso: /acc info <jogador>");
                    return;
                }
                
                ProxiedPlayer target = plugin.getProxy().getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage("§cJogador não encontrado!");
                    return;
                }
                
                boolean isPirate = !target.getPendingConnection().isOnlineMode();
                
                player.sendMessage("");
                player.sendMessage("§e§lINFORMAÇÕES DA CONTA");
                player.sendMessage("§7Jogador: §f" + target.getName());
                player.sendMessage("§7UUID: §f" + target.getUniqueId());
                player.sendMessage("§7Tipo: " + (isPirate ? "§cPirata" : "§aOriginal"));
                player.sendMessage("§7Servidor: §f" + target.getServer().getInfo().getName());
                player.sendMessage("");
                break;
                
            case "send":
                if (args.length < 3) {
                    player.sendMessage("§cUso: /acc send <jogador> <servidor>");
                    return;
                }
                
                ProxiedPlayer targetPlayer = plugin.getProxy().getPlayer(args[1]);
                if (targetPlayer == null) {
                    player.sendMessage("§cJogador não encontrado!");
                    return;
                }
                
                String serverName = args[2];
                if (plugin.getProxy().getServerInfo(serverName) == null) {
                    player.sendMessage("§cServidor não encontrado!");
                    return;
                }
                
                targetPlayer.connect(plugin.getProxy().getServerInfo(serverName));
                player.sendMessage("§aJogador " + targetPlayer.getName() + " enviado para " + serverName);
                break;
                
            default:
                sendHelp(player);
                break;
        }
    }
    
    private void sendHelp(ProxiedPlayer player) {
        player.sendMessage("");
        player.sendMessage("§e§lCOMANDOS DE CONTA");
        player.sendMessage("§7/acc info <jogador> §f- Ver informações");
        player.sendMessage("§7/acc send <jogador> <servidor> §f- Enviar para servidor");
        player.sendMessage("");
    }
}
