package rip.rafa.event.game.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rip.rafa.event.game.GamePlugin;
import rip.rafa.event.game.model.GameType;

import java.util.*;

public class GameMasterManager {
    
    private final GamePlugin plugin;
    private UUID gameMaster;
    private final Set<UUID> guards;
    
    public GameMasterManager(GamePlugin plugin) {
        this.plugin = plugin;
        this.guards = new HashSet<>();
    }
    
    public void setGameMaster(Player player) {
        this.gameMaster = player.getUniqueId();
        giveGameMasterItems(player);
        player.sendMessage(colorize("&#FF1744[Round 6] &#FFFFFFᴠᴏᴄᴇ̂ ᴀɢᴏʀᴀ ᴇ́ ᴏ &#FF1744GAMEMASTER&#FFFFFF!"));
    }
    
    public void removeGameMaster() {
        if (gameMaster != null) {
            Player player = Bukkit.getPlayer(gameMaster);
            if (player != null) {
                player.getInventory().clear();
                player.sendMessage(colorize("&#FF1744[Round 6] &#FFFFFFᴠᴏᴄᴇ̂ ɴᴀ̃ᴏ ᴇ́ ᴍᴀɪꜱ ᴏ GAMEMASTER."));
            }
        }
        this.gameMaster = null;
    }
    
    public boolean isGameMaster(Player player) {
        return gameMaster != null && gameMaster.equals(player.getUniqueId());
    }
    
    public Player getGameMaster() {
        return gameMaster != null ? Bukkit.getPlayer(gameMaster) : null;
    }
    
    public void addGuard(Player player) {
        guards.add(player.getUniqueId());
        giveGuardItems(player);
        player.sendMessage(colorize("&#FF1744[Round 6] &#FFFFFFᴠᴏᴄᴇ̂ ᴀɢᴏʀᴀ ᴇ́ ᴜᴍ &#FF1744GUARDA&#FFFFFF!"));
    }
    
    public void removeGuard(Player player) {
        guards.remove(player.getUniqueId());
        player.getInventory().clear();
        player.sendMessage(colorize("&#FF1744[Round 6] &#FFFFFFᴠᴏᴄᴇ̂ ɴᴀ̃ᴏ ᴇ́ ᴍᴀɪꜱ ᴜᴍ ɢᴜᴀʀᴅᴀ."));
    }
    
    public boolean isGuard(Player player) {
        return guards.contains(player.getUniqueId());
    }
    
    public Set<UUID> getGuards() {
        return new HashSet<>(guards);
    }
    
    private void giveGameMasterItems(Player player) {
        player.getInventory().clear();
        
        // Slot 0: Menu de Jogos
        ItemStack gamesMenu = createItem(Material.NETHER_STAR, 
                "&#FF1744ᴍᴇɴᴜ ᴅᴇ ᴊᴏɢᴏꜱ",
                "&#FFFFFFᴄʟɪǫᴜᴇ ᴘᴀʀᴀ ꜱᴇʟᴇᴄɪᴏɴᴀʀ ᴏ ᴊᴏɢᴏ");
        player.getInventory().setItem(0, gamesMenu);
        
        // Slot 1: Iniciar Jogo
        ItemStack startGame = createItem(Material.LIME_DYE,
                "&#FF1744ɪɴɪᴄɪᴀʀ ᴊᴏɢᴏ",
                "&#FFFFFFᴄʟɪǫᴜᴇ ᴘᴀʀᴀ ɪɴɪᴄɪᴀʀ");
        player.getInventory().setItem(1, startGame);
        
        // Slot 2: Parar Jogo
        ItemStack stopGame = createItem(Material.RED_DYE,
                "&#FF1744ᴘᴀʀᴀʀ ᴊᴏɢᴏ",
                "&#FFFFFFᴄʟɪǫᴜᴇ ᴘᴀʀᴀ ᴘᴀʀᴀʀ");
        player.getInventory().setItem(2, stopGame);
        
        // Slot 3: Gerenciar Guardas
        ItemStack manageGuards = createItem(Material.IRON_SWORD,
                "&#FF1744ɢᴇʀᴇɴᴄɪᴀʀ ɢᴜᴀʀᴅᴀꜱ",
                "&#FFFFFFᴄʟɪǫᴜᴇ ᴘᴀʀᴀ ɢᴇʀᴇɴᴄɪᴀʀ");
        player.getInventory().setItem(3, manageGuards);
        
        // Slot 4: Teleporte
        ItemStack teleport = createItem(Material.ENDER_PEARL,
                "&#FF1744ᴛᴇʟᴇᴘᴏʀᴛᴇ",
                "&#FFFFFFᴄʟɪǫᴜᴇ ᴘᴀʀᴀ ᴛᴇʟᴇᴘᴏʀᴛᴀʀ");
        player.getInventory().setItem(4, teleport);
        
        // Slot 5: Configurações
        ItemStack settings = createItem(Material.COMPARATOR,
                "&#FF1744ᴄᴏɴꜰɪɢᴜʀᴀᴄ̧ᴏ̃ᴇꜱ",
                "&#FFFFFFᴄʟɪǫᴜᴇ ᴘᴀʀᴀ ᴄᴏɴꜰɪɢᴜʀᴀʀ");
        player.getInventory().setItem(5, settings);
        
        // Slot 6: Estatísticas
        ItemStack stats = createItem(Material.BOOK,
                "&#FF1744ᴇꜱᴛᴀᴛɪ́ꜱᴛɪᴄᴀꜱ",
                "&#FFFFFFᴠᴇʀ ᴇꜱᴛᴀᴛɪ́ꜱᴛɪᴄᴀꜱ ᴅᴏ ᴊᴏɢᴏ");
        player.getInventory().setItem(6, stats);
        
        // Slot 8: Eliminar Jogador
        ItemStack eliminate = createItem(Material.TNT,
                "&#FF1744ᴇʟɪᴍɪɴᴀʀ ᴊᴏɢᴀᴅᴏʀ",
                "&#FFFFFFᴄʟɪǫᴜᴇ ᴘᴀʀᴀ ᴇʟɪᴍɪɴᴀʀ");
        player.getInventory().setItem(8, eliminate);
    }
    
    private void giveGuardItems(Player player) {
        player.getInventory().clear();
        
        // Slot 0: Espada
        ItemStack sword = createItem(Material.IRON_SWORD,
                "&#FF1744ᴇꜱᴘᴀᴅᴀ ᴅᴏ ɢᴜᴀʀᴅᴀ",
                "&#FFFFFFᴜꜱᴇ ᴘᴀʀᴀ ᴇʟɪᴍɪɴᴀʀ ᴊᴏɢᴀᴅᴏʀᴇꜱ");
        player.getInventory().setItem(0, sword);
        
        // Slot 1: Arco
        ItemStack bow = createItem(Material.BOW,
                "&#FF1744ᴀʀᴄᴏ ᴅᴏ ɢᴜᴀʀᴅᴀ",
                "&#FFFFFFᴜꜱᴇ ᴘᴀʀᴀ ᴇʟɪᴍɪɴᴀʀ ᴀ̀ ᴅɪꜱᴛᴀ̂ɴᴄɪᴀ");
        player.getInventory().setItem(1, bow);
        
        // Slot 8: Flecha
        player.getInventory().setItem(8, new ItemStack(Material.ARROW, 64));
    }
    
    public void openGamesMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, colorize("&#FF1744ꜱᴇʟᴇᴄɪᴏɴᴇ ᴏ ᴊᴏɢᴏ"));
        
        int slot = 10;
        for (GameType gameType : GameType.values()) {
            ItemStack item = createItem(getMaterialForGame(gameType),
                    gameType.getDisplayName(),
                    "&#FFFFFFᴄʟɪǫᴜᴇ ᴘᴀʀᴀ ꜱᴇʟᴇᴄɪᴏɴᴀʀ");
            inv.setItem(slot++, item);
        }
        
        player.openInventory(inv);
    }
    
    private Material getMaterialForGame(GameType gameType) {
        switch (gameType) {
            case GREEN_LIGHT_RED_LIGHT:
                return Material.REDSTONE_LAMP;
            case HONEYCOMB:
                return Material.HONEYCOMB;
            case TUG_OF_WAR:
                return Material.LEAD;
            case MARBLES:
                return Material.SNOWBALL;
            case GLASS_BRIDGE:
                return Material.GLASS;
            case SQUID_GAME:
                return Material.TRIDENT;
            default:
                return Material.BARRIER;
        }
    }
    
    private ItemStack createItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(colorize(name));
            if (lore.length > 0) {
                List<String> loreList = new ArrayList<>();
                for (String line : lore) {
                    loreList.add(colorize(line));
                }
                meta.setLore(loreList);
            }
            item.setItemMeta(meta);
        }
        return item;
    }
    
    private String colorize(String text) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("&#([0-9A-Fa-f]{6})");
        java.util.regex.Matcher matcher = pattern.matcher(text);
        
        while (matcher.find()) {
            String color = matcher.group(1);
            text = text.replace("&#" + color, net.md_5.bungee.api.ChatColor.of("#" + color).toString());
        }
        
        return text.replace("&", "§");
    }
}
