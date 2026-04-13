package rip.rafa.event.auth.command;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.rafa.event.auth.AuthPlugin;

public class LoginCommand implements CommandExecutor {
    
    private final AuthPlugin plugin;
    
    public LoginCommand(AuthPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (!plugin.getAuthManager().isRegistered(player)) {
            player.sendMessage("§cVocê não está registrado! Use /register <senha> <senha>");
            return true;
        }
        
        if (plugin.getAuthManager().isAuthenticated(player)) {
            player.sendMessage("§cVocê já está autenticado!");
            return true;
        }
        
        if (args.length != 1) {
            player.sendMessage("§cUso: /login <senha>");
            return true;
        }
        
        String password = args[0];
        
        if (plugin.getAuthManager().login(player, password)) {
            player.setWalkSpeed(0.2f);
            player.setFlySpeed(0.1f);
            
            player.sendMessage("");
            player.sendMessage("§a§lLOGIN REALIZADO COM SUCESSO!");
            player.sendMessage("");
            player.sendMessage("§7Aplicando skin...");
            player.sendMessage("");
            
            // Aplica skin do jogador
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                plugin.getSkinManager().applySkin(player, player.getName());
                
                // Conecta ao hub após aplicar skin
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    player.sendMessage("§7Conectando ao lobby...");
                    connectToHub(player);
                }, 20L);
            }, 5L);
        } else {
            player.sendMessage("§cSenha incorreta!");
        }
        
        return true;
    }
    
    private void connectToHub(Player player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("hub");
        
        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }
}
