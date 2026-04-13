package rip.rafa.event.game.minigame;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import rip.rafa.event.game.GamePlugin;
import rip.rafa.event.game.model.GameType;

import java.util.*;

public class GreenLightRedLightGame extends Minigame {
    
    private final Set<UUID> players;
    private final Map<UUID, Location> lastLocations;
    private boolean greenLight;
    private BukkitTask gameTask;
    private int timeLeft;
    private int roundTime;
    
    public GreenLightRedLightGame(GamePlugin plugin) {
        super(plugin, GameType.GREEN_LIGHT_RED_LIGHT);
        this.players = new HashSet<>();
        this.lastLocations = new HashMap<>();
        this.greenLight = false;
        this.timeLeft = 180; // 3 minutos
        this.roundTime = 0;
    }
    
    @Override
    public void start(Set<UUID> playerUUIDs) {
        this.players.addAll(playerUUIDs);
        this.running = true;
        
        // Mensagem inicial
        broadcast("&#FF1744ʟᴜᴢ ᴠᴇʀᴅᴇ, ʟᴜᴢ ᴠᴇʀᴍᴇʟʜᴀ");
        broadcast("&#FFFFFFᴄʜᴇɢᴜᴇ ᴀᴏ ꜰɪɴᴀʟ ꜱᴇᴍ ꜱᴇʀ ᴘᴇɢᴏ ꜱᴇ ᴍᴏᴠᴇɴᴅᴏ!");
        
        // Iniciar ciclo de luzes
        gameTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            tick();
        }, 0L, 20L);
    }
    
    @Override
    public void stop() {
        if (gameTask != null) {
            gameTask.cancel();
            gameTask = null;
        }
        running = false;
        players.clear();
        lastLocations.clear();
    }
    
    @Override
    public void tick() {
        timeLeft--;
        roundTime++;
        
        // Alternar luzes a cada 3-7 segundos
        if (roundTime >= (greenLight ? 5 : 3)) {
            toggleLight();
            roundTime = 0;
        }
        
        // Verificar movimento durante luz vermelha
        if (!greenLight) {
            checkMovement();
        }
        
        // Verificar fim do tempo
        if (timeLeft <= 0) {
            endGame();
        }
    }
    
    private void toggleLight() {
        greenLight = !greenLight;
        
        if (greenLight) {
            // LUZ VERDE - Pode se mover
            broadcast("&#00FF00ʟᴜᴢ ᴠᴇʀᴅᴇ!");
            playSound(Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 2.0f);
            
            // Salvar posições atuais
            for (UUID uuid : players) {
                Player player = Bukkit.getPlayer(uuid);
                if (player != null && player.isOnline()) {
                    lastLocations.put(uuid, player.getLocation().clone());
                }
            }
        } else {
            // LUZ VERMELHA - Não pode se mover
            broadcast("&#FF1744ʟᴜᴢ ᴠᴇʀᴍᴇʟʜᴀ!");
            playSound(Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 0.5f);
            
            // Salvar posições para verificação
            for (UUID uuid : players) {
                Player player = Bukkit.getPlayer(uuid);
                if (player != null && player.isOnline()) {
                    lastLocations.put(uuid, player.getLocation().clone());
                }
            }
        }
    }
    
    private void checkMovement() {
        for (UUID uuid : new HashSet<>(players)) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null || !player.isOnline()) {
                players.remove(uuid);
                continue;
            }
            
            Location lastLoc = lastLocations.get(uuid);
            if (lastLoc == null) continue;
            
            Location currentLoc = player.getLocation();
            
            // Verificar se moveu (ignorar rotação da cabeça)
            double distance = Math.sqrt(
                Math.pow(currentLoc.getX() - lastLoc.getX(), 2) +
                Math.pow(currentLoc.getY() - lastLoc.getY(), 2) +
                Math.pow(currentLoc.getZ() - lastLoc.getZ(), 2)
            );
            
            if (distance > 0.1) {
                // ELIMINADO!
                eliminatePlayer(player);
            }
        }
    }
    
    private void eliminatePlayer(Player player) {
        players.remove(player.getUniqueId());
        
        // Efeitos dramáticos
        player.getWorld().strikeLightningEffect(player.getLocation());
        player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);
        
        // Matar jogador
        player.setHealth(0);
        
        broadcast("&#FF1744" + player.getName() + " &#FFFFFFꜰᴏɪ ᴇʟɪᴍɪɴᴀᴅᴏ ᴘᴏʀ ꜱᴇ ᴍᴏᴠᴇʀ!");
        
        // Verificar se acabou
        if (players.size() <= 1) {
            endGame();
        }
    }
    
    private void endGame() {
        broadcast("&#FF1744ʟᴜᴢ ᴠᴇʀᴅᴇ, ʟᴜᴢ ᴠᴇʀᴍᴇʟʜᴀ &#FFFFFFᴛᴇʀᴍɪɴᴏᴜ!");
        broadcast("&#FFFFFF" + players.size() + " &#FFFFFFᴊᴏɢᴀᴅᴏʀᴇꜱ ꜱᴏʙʀᴇᴠɪᴠᴇʀᴀᴍ!");
        
        stop();
        
        // Notificar GameManager
        plugin.getGameManager().onMinigameEnd(players);
    }
    
    @Override
    public void onPlayerMove(Player player) {
        // Verificação é feita no tick()
    }
    
    @Override
    public void onPlayerDeath(Player player) {
        players.remove(player.getUniqueId());
        lastLocations.remove(player.getUniqueId());
    }
    
    private void broadcast(String message) {
        String formatted = colorize("&#FF1744[Round 6] " + message);
        for (UUID uuid : players) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null && player.isOnline()) {
                player.sendMessage(formatted);
            }
        }
    }
    
    private void playSound(Sound sound, float volume, float pitch) {
        for (UUID uuid : players) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null && player.isOnline()) {
                player.playSound(player.getLocation(), sound, volume, pitch);
            }
        }
    }
}
