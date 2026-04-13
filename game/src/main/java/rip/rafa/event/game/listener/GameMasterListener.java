package rip.rafa.event.game.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import rip.rafa.event.game.GamePlugin;

public class GameMasterListener implements Listener {
    
    private final GamePlugin plugin;
    
    public GameMasterListener(GamePlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        
        // Verificar se é gamemaster
        if (!plugin.getGameMasterManager().isGameMaster(player)) {
            return;
        }
        
        // Verificar se clicou com item
        if (event.getAction() != Action.RIGHT_CLICK_AIR && 
            event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        
        ItemStack item = event.getItem();
        if (item == null || !item.hasItemMeta()) {
            return;
        }
        
        String displayName = item.getItemMeta().getDisplayName();
        if (displayName == null) {
            return;
        }
        
        event.setCancelled(true);
        
        // Remover cores para comparação
        String cleanName = displayName.replaceAll("§[0-9a-fk-or]", "");
        
        switch (cleanName.toLowerCase()) {
            case "menu de jogos":
            case "ᴍᴇɴᴜ ᴅᴇ ᴊᴏɢᴏꜱ":
                plugin.getGameMasterManager().openGamesMenu(player);
                break;
                
            case "iniciar jogo":
            case "ɪɴɪᴄɪᴀʀ ᴊᴏɢᴏ":
                player.sendMessage("§aIniciando jogo...");
                // TODO: Iniciar jogo selecionado
                break;
                
            case "parar jogo":
            case "ᴘᴀʀᴀʀ ᴊᴏɢᴏ":
                player.sendMessage("§cParando jogo...");
                // TODO: Parar jogo atual
                break;
                
            case "gerenciar guardas":
            case "ɢᴇʀᴇɴᴄɪᴀʀ ɢᴜᴀʀᴅᴀꜱ":
                player.sendMessage("§eUse: /r6 guard <add|remove|list>");
                break;
                
            case "teleporte":
            case "ᴛᴇʟᴇᴘᴏʀᴛᴇ":
                player.sendMessage("§eSistema de teleporte em desenvolvimento...");
                // TODO: Menu de teleporte
                break;
                
            case "configurações":
            case "ᴄᴏɴꜰɪɢᴜʀᴀᴄ̧ᴏ̃ᴇꜱ":
                player.sendMessage("§eConfigurações em desenvolvimento...");
                // TODO: Menu de configurações
                break;
                
            case "estatísticas":
            case "ᴇꜱᴛᴀᴛɪ́ꜱᴛɪᴄᴀꜱ":
                player.sendMessage("§eEstatísticas em desenvolvimento...");
                // TODO: Menu de estatísticas
                break;
                
            case "eliminar jogador":
            case "ᴇʟɪᴍɪɴᴀʀ ᴊᴏɢᴀᴅᴏʀ":
                player.sendMessage("§eClique em um jogador para eliminar");
                // TODO: Sistema de eliminação
                break;
        }
    }
}
