package rip.rafa.event.game.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import rip.rafa.event.game.GamePlugin;

public class GameListener implements Listener {
    
    private final GamePlugin plugin;
    
    public GameListener(GamePlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        // Auto-join: Adicionar jogador automaticamente ao jogo
        plugin.getGameManager().addPlayer(player);
        
        // Remover mensagem padrão de join
        event.setJoinMessage(null);
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        
        if (plugin.getGameManager().isInGame(player)) {
            plugin.getGameManager().removePlayer(player);
        }
        
        // Remover mensagem padrão de quit
        event.setQuitMessage(null);
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        
        // Remover mensagem de morte
        event.setDeathMessage(null);
        
        // Eliminar jogador
        if (plugin.getGameManager().isInGame(player)) {
            plugin.getGameManager().eliminatePlayer(player);
        }
    }
    
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        
        Player player = (Player) event.getEntity();
        
        // Proteção no lobby e countdown
        if (plugin.getGameManager().getState() == rip.rafa.event.game.manager.GameManager.GameState.WAITING ||
            plugin.getGameManager().getState() == rip.rafa.event.game.manager.GameManager.GameState.COUNTDOWN) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        
        // Desabilitar fome no lobby e countdown
        if (plugin.getGameManager().getState() == rip.rafa.event.game.manager.GameManager.GameState.WAITING ||
            plugin.getGameManager().getState() == rip.rafa.event.game.manager.GameManager.GameState.COUNTDOWN) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        
        // Bloquear quebrar blocos no lobby
        if (plugin.getGameManager().getState() == rip.rafa.event.game.manager.GameManager.GameState.WAITING ||
            plugin.getGameManager().getState() == rip.rafa.event.game.manager.GameManager.GameState.COUNTDOWN) {
            if (!player.hasPermission("round6.admin")) {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        
        // Bloquear colocar blocos no lobby
        if (plugin.getGameManager().getState() == rip.rafa.event.game.manager.GameManager.GameState.WAITING ||
            plugin.getGameManager().getState() == rip.rafa.event.game.manager.GameManager.GameState.COUNTDOWN) {
            if (!player.hasPermission("round6.admin")) {
                event.setCancelled(true);
            }
        }
    }
}
