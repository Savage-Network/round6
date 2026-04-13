package rip.rafa.event.bungee.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import rip.rafa.event.bungee.EventBungee;
import rip.rafa.event.bungee.account.Account;
import rip.rafa.event.bungee.account.AccountManager;
import rip.rafa.event.bungee.rank.Rank;

public class RankCommand extends Command {
    
    private final EventBungee plugin;
    
    public RankCommand(EventBungee plugin) {
        super("rank", "round6.admin", "cargo");
        this.plugin = plugin;
    }
    
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sendHelp(sender);
            return;
        }
        
        String playerName = args[0];
        String action = args[1].toLowerCase();
        
        Account target = AccountManager.getByName(playerName);
        if (target == null) {
            sender.sendMessage(new TextComponent("§cJogador não encontrado!"));
            return;
        }
        
        Account executor = null;
        if (sender instanceof ProxiedPlayer) {
            executor = AccountManager.get(((ProxiedPlayer) sender).getUniqueId());
        }
        
        switch (action) {
            case "set":
            case "definir":
                if (args.length < 3) {
                    sender.sendMessage(new TextComponent("§cUso: /rank <jogador> set <rank>"));
                    return;
                }
                handleSet(sender, executor, target, args[2]);
                break;
                
            case "remove":
            case "remover":
                handleRemove(sender, executor, target);
                break;
                
            case "info":
                handleInfo(sender, target);
                break;
                
            default:
                sendHelp(sender);
                break;
        }
    }
    
    private void handleSet(CommandSender sender, Account executor, Account target, String rankName) {
        Rank rank = Rank.fromString(rankName);
        
        if (rank == Rank.DEFAULT) {
            sender.sendMessage(new TextComponent("§cRank inválido!"));
            sender.sendMessage(new TextComponent("§7Ranks disponíveis: §fGAMEMASTER, GUARDA, CREATOR"));
            return;
        }
        
        // Verifica hierarquia
        if (executor != null) {
            if (!executor.getRank().isHigherThan(rank)) {
                sender.sendMessage(new TextComponent("§cVocê não pode definir um rank igual ou superior ao seu!"));
                return;
            }
            
            if (!executor.getRank().isHigherThan(target.getRank())) {
                sender.sendMessage(new TextComponent("§cVocê não pode modificar o rank de alguém igual ou superior a você!"));
                return;
            }
        }
        
        target.setRank(rank);
        AccountManager.save(target);
        
        sender.sendMessage(new TextComponent("§aRank de " + target.getName() + " definido para " + rank.getPrefix() + rank.getName()));
        
        ProxiedPlayer targetPlayer = plugin.getProxy().getPlayer(target.getName());
        if (targetPlayer != null) {
            targetPlayer.sendMessage(new TextComponent("§aSeu rank foi atualizado para " + rank.getPrefix() + rank.getName()));
        }
    }
    
    private void handleRemove(CommandSender sender, Account executor, Account target) {
        if (executor != null) {
            if (!executor.getRank().isHigherThan(target.getRank())) {
                sender.sendMessage(new TextComponent("§cVocê não pode modificar o rank de alguém igual ou superior a você!"));
                return;
            }
        }
        
        target.setRank(Rank.DEFAULT);
        AccountManager.save(target);
        
        sender.sendMessage(new TextComponent("§aRank de " + target.getName() + " removido!"));
        
        ProxiedPlayer targetPlayer = plugin.getProxy().getPlayer(target.getName());
        if (targetPlayer != null) {
            targetPlayer.sendMessage(new TextComponent("§cSeu rank foi removido!"));
        }
    }
    
    private void handleInfo(CommandSender sender, Account target) {
        Rank rank = target.getRank();
        
        sender.sendMessage(new TextComponent("§a§l===== RANK INFO ====="));
        sender.sendMessage(new TextComponent("§aJogador: §f" + target.getName()));
        sender.sendMessage(new TextComponent("§aRank: " + rank.getPrefix() + rank.getName()));
        sender.sendMessage(new TextComponent("§aCor: " + rank.getColor() + rank.getColor()));
        sender.sendMessage(new TextComponent("§aPrioridade: §f" + rank.getPriority()));
        sender.sendMessage(new TextComponent("§a§l====================="));
    }
    
    private void sendHelp(CommandSender sender) {
        sender.sendMessage(new TextComponent("§c§l===== COMANDO /RANK ====="));
        sender.sendMessage(new TextComponent("§f/rank <jogador> set <rank> §7- Define rank"));
        sender.sendMessage(new TextComponent("§f/rank <jogador> remove §7- Remove rank"));
        sender.sendMessage(new TextComponent("§f/rank <jogador> info §7- Ver informações"));
        sender.sendMessage(new TextComponent(""));
        sender.sendMessage(new TextComponent("§aRanks disponíveis:"));
        sender.sendMessage(new TextComponent("§7- §x§f§f§c§0§c§b§oGAMEMASTER"));
        sender.sendMessage(new TextComponent("§7- §x§3§D§3§D§3§D§lGUARDA"));
        sender.sendMessage(new TextComponent("§7- §x§1§1§1§1§8§4§lCREATOR"));
        sender.sendMessage(new TextComponent("§c§l========================="));
    }
}
