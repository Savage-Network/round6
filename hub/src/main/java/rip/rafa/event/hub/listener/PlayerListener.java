package rip.rafa.event.hub.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import rip.rafa.event.hub.HubPlugin;
import rip.rafa.event.hub.account.Account;
import rip.rafa.event.hub.account.AccountManager;
import rip.rafa.event.hub.scoreboard.ScoreboardManager;

public class PlayerListener implements Listener {
    
    private final HubPlugin plugin;
    
    public PlayerListener(HubPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);
        
        // Carrega conta do jogador
        AccountManager.load(player.getUniqueId(), player.getName());
        Account account = AccountManager.get(player.getUniqueId());
        
        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setExp(0);
        
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        
        // Atualiza tag do jogador
        plugin.getTagManager().updatePlayerTag(player);
        
        // Configura tab list
        plugin.getTabManager().setupPlayer(player);
        
        ScoreboardManager.updateScoreboard(player);
        
        player.sendMessage("");
        player.sendMessage("§c§l⬛ ROUND 6 ⬛");
        player.sendMessage("");
        player.sendMessage("§7Bem-vindo ao lobby!");
        player.sendMessage("§7Use §c/play §7para jogar");
        player.sendMessage("");
    }
    
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        
        // Remove do tab manager
        plugin.getTabManager().removePlayer(event.getPlayer());
        
        // Descarrega conta do jogador
        AccountManager.unload(event.getPlayer().getUniqueId());
    }
    
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}
