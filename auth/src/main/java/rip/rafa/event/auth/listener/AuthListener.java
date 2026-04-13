package rip.rafa.event.auth.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import rip.rafa.event.auth.AuthPlugin;

public class AuthListener implements Listener {
    
    private final AuthPlugin plugin;
    
    public AuthListener(AuthPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);
        
        player.setWalkSpeed(0.0f);
        player.setFlySpeed(0.0f);
        
        // Aplica skin padrão usando comando do SkinsRestorer
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "skin clear " + player.getName());
        }, 5L);
        
        if (plugin.getAuthManager().isRegistered(player)) {
            player.sendMessage("");
            player.sendMessage("§a§lBEM-VINDO DE VOLTA!");
            player.sendMessage("");
            player.sendMessage("§7Use §f/login <senha> §7para entrar");
            player.sendMessage("");
        } else {
            player.sendMessage("");
            player.sendMessage("§e§lPRIMEIRO ACESSO!");
            player.sendMessage("");
            player.sendMessage("§7Use §f/register <senha> <senha> §7para criar uma conta");
            player.sendMessage("");
        }
    }
    
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        plugin.getAuthManager().logout(event.getPlayer());
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event) {
        if (!plugin.getAuthManager().isAuthenticated(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§cVocê precisa fazer login primeiro!");
        }
    }
    
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().toLowerCase();
        
        if (!plugin.getAuthManager().isAuthenticated(event.getPlayer())) {
            if (!command.startsWith("/login") && !command.startsWith("/register")) {
                event.setCancelled(true);
                event.getPlayer().sendMessage("§cVocê precisa fazer login primeiro!");
            }
        }
    }
    
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!plugin.getAuthManager().isAuthenticated(event.getPlayer())) {
            if (event.getFrom().getX() != event.getTo().getX() || 
                event.getFrom().getZ() != event.getTo().getZ()) {
                event.setTo(event.getFrom());
            }
        }
    }
    
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (!plugin.getAuthManager().isAuthenticated(player)) {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!plugin.getAuthManager().isAuthenticated(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!plugin.getAuthManager().isAuthenticated(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (!plugin.getAuthManager().isAuthenticated(player)) {
                event.setCancelled(true);
            }
        }
    }
}
