package rip.rafa.event.game.manager;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import rip.rafa.event.game.GamePlugin;

import java.util.*;

public class GameManager {
    
    private final GamePlugin plugin;
    private final Set<UUID> players;
    private final Set<UUID> alivePlayers;
    private GameState state;
    private BukkitTask countdownTask;
    private BukkitTask gameTask;
    private int countdown;
    private int gameTime;
    
    public GameManager(GamePlugin plugin) {
        this.plugin = plugin;
        this.players = new HashSet<>();
        this.alivePlayers = new HashSet<>();
        this.state = GameState.WAITING;
        this.gameTime = 0;
    }
    
    public void addPlayer(Player player) {
        players.add(player.getUniqueId());
        alivePlayers.add(player.getUniqueId());
        
        // Teleportar para lobby
        teleportToLobby(player);
        
        // Configurar jogador
        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.getInventory().clear();
        
        // Criar scoreboard
        plugin.getScoreboardManager().createScoreboard(player);
        
        // Mensagem de boas-vindas
        sendTitle(player, "&#FF1744ʀᴏᴜɴᴅ 6", "&#FFFFFFʙᴇᴍ-ᴠɪɴᴅᴏ ᴀᴏ ᴊᴏɢᴏ!", 10, 40, 10);
        player.sendMessage(colorize(getMessage("join-game").replace("{player}", player.getName())));
        
        // Broadcast
        broadcast(getMessage("player-joined")
                .replace("{player}", player.getName())
                .replace("{current}", String.valueOf(players.size()))
                .replace("{min}", String.valueOf(getMinPlayers())));
        
        // Verificar se pode iniciar
        if (state == GameState.WAITING && players.size() >= getMinPlayers()) {
            startCountdown();
        }
    }
    
    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
        alivePlayers.remove(player.getUniqueId());
        
        plugin.getScoreboardManager().removeScoreboard(player);
        
        broadcast(getMessage("player-left")
                .replace("{player}", player.getName())
                .replace("{current}", String.valueOf(players.size())));
        
        // Verificar se deve cancelar countdown
        if (state == GameState.COUNTDOWN && players.size() < getMinPlayers()) {
            cancelCountdown();
        }
        
        // Verificar se deve terminar o jogo
        if (state == GameState.PLAYING && alivePlayers.size() <= 1) {
            endGame();
        }
    }
    
    public boolean isInGame(Player player) {
        return players.contains(player.getUniqueId());
    }
    
    public boolean isAlive(Player player) {
        return alivePlayers.contains(player.getUniqueId());
    }
    
    public void eliminatePlayer(Player player) {
        if (!isAlive(player)) return;
        
        alivePlayers.remove(player.getUniqueId());
        
        player.setGameMode(GameMode.SPECTATOR);
        
        broadcast(getMessage("player-eliminated").replace("{player}", player.getName()));
        sendTitle(player, "&#FF1744ᴠᴏᴄᴇ̂ ꜰᴏɪ ᴇʟɪᴍɪɴᴀᴅᴏ!", "", 10, 60, 20);
        
        // Verificar vencedor
        if (alivePlayers.size() == 1) {
            endGame();
        } else if (alivePlayers.isEmpty()) {
            endGame();
        }
    }
    
    private void startCountdown() {
        state = GameState.COUNTDOWN;
        countdown = plugin.getConfig().getInt("round6.waiting-time", 30);
        
        countdownTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (countdown <= 0) {
                startGame();
                return;
            }
            
            // Mensagens e sons
            if (countdown <= 5 || countdown == 10 || countdown == 30) {
                broadcast(getMessage("game-starting").replace("{time}", String.valueOf(countdown)));
                playSound(Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
            }
            
            if (countdown <= 5) {
                for (UUID uuid : players) {
                    Player p = Bukkit.getPlayer(uuid);
                    if (p != null) {
                        sendTitle(p, "&#FF1744" + countdown, "", 0, 25, 5);
                    }
                }
            }
            
            countdown--;
        }, 0L, 20L);
    }
    
    private void cancelCountdown() {
        if (countdownTask != null) {
            countdownTask.cancel();
            countdownTask = null;
        }
        state = GameState.WAITING;
        broadcast(getMessage("countdown-cancelled"));
    }
    
    private void startGame() {
        if (countdownTask != null) {
            countdownTask.cancel();
            countdownTask = null;
        }
        
        state = GameState.PLAYING;
        gameTime = 0;
        
        // Teleportar jogadores para arena
        teleportToArena();
        
        // Mensagens e efeitos
        broadcast(getMessage("game-started"));
        playSound(Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0f, 1.0f);
        
        for (UUID uuid : players) {
            Player p = Bukkit.getPlayer(uuid);
            if (p != null) {
                sendTitle(p, "&#FF1744ʀᴏᴜɴᴅ 6", "&#FFFFFFʙᴏᴀ ꜱᴏʀᴛᴇ!", 10, 40, 10);
                p.setGameMode(GameMode.SURVIVAL);
            }
        }
        
        // Timer do jogo
        gameTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            gameTime++;
            
            // Aqui você pode adicionar eventos do jogo
            // Por exemplo: spawnar mobs, mudar arena, etc.
            
        }, 0L, 20L);
    }
    
    private void endGame() {
        state = GameState.ENDING;
        
        if (gameTask != null) {
            gameTask.cancel();
            gameTask = null;
        }
        
        // Encontrar vencedor
        Player winner = null;
        if (alivePlayers.size() == 1) {
            UUID winnerUUID = alivePlayers.iterator().next();
            winner = Bukkit.getPlayer(winnerUUID);
        }
        
        if (winner != null) {
            broadcast(getMessage("player-won").replace("{player}", winner.getName()));
            
            // Dar prêmio
            int reward = plugin.getConfig().getInt("round6.win-reward", 1000);
            // TODO: Implementar sistema de economia
            
            // Efeitos para o vencedor
            sendTitle(winner, "&#FF1744ᴠɪᴛᴏ́ʀɪᴀ!", "&#FFFFFFᴠᴏᴄᴇ̂ ɢᴀɴʜᴏᴜ " + reward + " ᴄᴏɪɴꜱ!", 10, 60, 20);
            winner.playSound(winner.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.0f);
        } else {
            broadcast(getMessage("no-winner"));
        }
        
        broadcast(getMessage("game-ended"));
        
        // Aguardar antes de reiniciar
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            // Remover todos os jogadores
            for (UUID uuid : new HashSet<>(players)) {
                Player p = Bukkit.getPlayer(uuid);
                if (p != null) {
                    // Enviar para hub ou kickar
                    p.sendMessage(colorize("&#FF1744[Round 6] &#FFFFFFᴏ ᴊᴏɢᴏ ᴛᴇʀᴍɪɴᴏᴜ!"));
                }
            }
            
            // Limpar
            players.clear();
            alivePlayers.clear();
            state = GameState.WAITING;
            gameTime = 0;
            
        }, 100L);
    }
    
    private void teleportToLobby(Player player) {
        Location lobby = getLobbyLocation();
        player.teleport(lobby);
    }
    
    private void teleportToArena() {
        // TODO: Implementar sistema de spawn points
        Location arena = getArenaLocation();
        for (UUID uuid : players) {
            Player p = Bukkit.getPlayer(uuid);
            if (p != null) {
                p.teleport(arena);
            }
        }
    }
    
    private Location getLobbyLocation() {
        String world = plugin.getConfig().getString("round6.lobby-location.world", "world");
        double x = plugin.getConfig().getDouble("round6.lobby-location.x", 0);
        double y = plugin.getConfig().getDouble("round6.lobby-location.y", 100);
        double z = plugin.getConfig().getDouble("round6.lobby-location.z", 0);
        float yaw = (float) plugin.getConfig().getDouble("round6.lobby-location.yaw", 0);
        float pitch = (float) plugin.getConfig().getDouble("round6.lobby-location.pitch", 0);
        
        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }
    
    private Location getArenaLocation() {
        String world = plugin.getConfig().getString("round6.arena-location.world", "world");
        double x = plugin.getConfig().getDouble("round6.arena-location.x", 0);
        double y = plugin.getConfig().getDouble("round6.arena-location.y", 100);
        double z = plugin.getConfig().getDouble("round6.arena-location.z", 0);
        float yaw = (float) plugin.getConfig().getDouble("round6.arena-location.yaw", 0);
        float pitch = (float) plugin.getConfig().getDouble("round6.arena-location.pitch", 0);
        
        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }
    
    public void shutdown() {
        if (countdownTask != null) {
            countdownTask.cancel();
        }
        if (gameTask != null) {
            gameTask.cancel();
        }
        players.clear();
        alivePlayers.clear();
    }
    
    private void broadcast(String message) {
        String formatted = colorize(plugin.getConfig().getString("messages.prefix", "") + message);
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
    
    private void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        player.sendTitle(colorize(title), colorize(subtitle), fadeIn, stay, fadeOut);
    }
    
    private String getMessage(String key) {
        return plugin.getConfig().getString("messages." + key, key);
    }
    
    private String colorize(String text) {
        // Suporte para cores hex &#RRGGBB
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("&#([0-9A-Fa-f]{6})");
        java.util.regex.Matcher matcher = pattern.matcher(text);
        
        while (matcher.find()) {
            String color = matcher.group(1);
            text = text.replace("&#" + color, net.md_5.bungee.api.ChatColor.of("#" + color).toString());
        }
        
        return text.replace("&", "§");
    }
    
    private int getMinPlayers() {
        return plugin.getConfig().getInt("round6.min-players", 2);
    }
    
    public GameState getState() {
        return state;
    }
    
    public int getPlayerCount() {
        return players.size();
    }
    
    public int getAliveCount() {
        return alivePlayers.size();
    }
    
    public int getGameTime() {
        return gameTime;
    }
    
    public void onMinigameEnd(Set<UUID> survivors) {
        // Callback quando um minigame termina
        broadcast(getMessage("minigame-ended")
                .replace("{survivors}", String.valueOf(survivors.size())));
        
        // Atualizar jogadores vivos
        alivePlayers.clear();
        alivePlayers.addAll(survivors);
        
        // Aguardar antes de próximo jogo ou votação
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            // TODO: Implementar votação ou próximo jogo
            state = GameState.WAITING;
        }, 100L);
    }
    
    public enum GameState {
        WAITING,
        COUNTDOWN,
        PLAYING,
        ENDING
    }
}
