package rip.rafa.event.hub.npc;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import rip.rafa.event.hub.HubPlugin;

import java.io.File;
import java.util.*;

public class NPCManager implements Listener {
    
    private final HubPlugin plugin;
    private final NPCConfig config;
    private final Map<String, List<ArmorStand>> npcs = new HashMap<>();
    
    public NPCManager(HubPlugin plugin) {
        this.plugin = plugin;
        
        File configFile = new File(plugin.getDataFolder(), "npcs.json");
        this.config = NPCConfig.load(configFile);
        
        Bukkit.getPluginManager().registerEvents(this, plugin);
        
        Bukkit.getScheduler().runTaskLater(plugin, this::loadNPCs, 20L);
        
        Bukkit.getScheduler().runTaskTimer(plugin, this::updateHolograms, 20L, 20L);
    }
    
    private void loadNPCs() {
        for (NPCData npcData : config.getNpcs()) {
            createNPC(npcData);
        }
        
        plugin.getLogger().info("Carregados " + config.getNpcs().size() + " NPCs");
    }
    
    public void createNPC(NPCData npcData) {
        World world = Bukkit.getWorld(npcData.getLocation().getWorld());
        if (world == null) {
            plugin.getLogger().warning("Mundo " + npcData.getLocation().getWorld() + " não encontrado");
            return;
        }
        
        Location location = npcData.getLocation().toLocation(world);
        
        ArmorStand npc = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);
        npc.setCustomName(npcData.getName());
        npc.setCustomNameVisible(npcData.getProperties().isCustomNameVisible());
        npc.setGravity(npcData.getProperties().isGravity());
        npc.setVisible(npcData.getProperties().isVisible());
        npc.setMarker(npcData.getProperties().isMarker());
        // setGlowing() não existe em 1.8.8
        
        // Nota: Skin customizada requer ProtocolLib instalado no servidor
        // Para usar skins, instale ProtocolLib: https://www.spigotmc.org/resources/protocollib.1997/
        
        List<ArmorStand> npcList = npcs.computeIfAbsent(npcData.getId(), k -> new ArrayList<>());
        npcList.add(npc);
        
        if (npcData.getHologram() != null && npcData.getHologram().isEnabled()) {
            createHologram(npc, npcData.getHologram());
        }
    }
    

    private void createHologram(ArmorStand npc, NPCData.HologramData hologramData) {
        Location location = npc.getLocation().clone();
        location.add(0, hologramData.getHeight(), 0);
        
        List<ArmorStand> hologramLines = new ArrayList<>();
        
        for (int i = hologramData.getLines().size() - 1; i >= 0; i--) {
            String line = hologramData.getLines().get(i);
            
            ArmorStand hologramLine = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
            hologramLine.setCustomName(line);
            hologramLine.setCustomNameVisible(true);
            hologramLine.setGravity(false);
            hologramLine.setVisible(false);
            hologramLine.setMarker(true);
            hologramLine.setSmall(true);
            
            hologramLines.add(hologramLine);
            
            location.subtract(0, 0.25, 0);
        }
        
        npcs.get(getNPCId(npc)).addAll(hologramLines);
    }
    
    private void updateHolograms() {
        for (Map.Entry<String, List<ArmorStand>> entry : npcs.entrySet()) {
            NPCData npcData = config.getNpcs().stream()
                .filter(n -> n.getId().equals(entry.getKey()))
                .findFirst()
                .orElse(null);
            
            if (npcData == null || npcData.getHologram() == null || !npcData.getHologram().isEnabled()) {
                continue;
            }
            
            // Conta jogadores online no servidor de jogo via BungeeCord
            int onlinePlayers = 0; // TODO: Implementar contagem via plugin messaging
            
            List<ArmorStand> hologramLines = entry.getValue().subList(1, entry.getValue().size());
            
            for (int i = 0; i < hologramLines.size() && i < npcData.getHologram().getLines().size(); i++) {
                String line = npcData.getHologram().getLines().get(npcData.getHologram().getLines().size() - 1 - i);
                line = line.replace("{online}", String.valueOf(onlinePlayers));
                
                hologramLines.get(i).setCustomName(line);
            }
        }
    }
    
    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        if (!(event.getRightClicked() instanceof ArmorStand)) return;
        
        ArmorStand entity = (ArmorStand) event.getRightClicked();
        String npcId = getNPCId(entity);
        
        if (npcId == null) return;
        
        event.setCancelled(true);
        
        NPCData npcData = config.getNpcs().stream()
            .filter(n -> n.getId().equals(npcId))
            .findFirst()
            .orElse(null);
        
        if (npcData == null || npcData.getAction() == null) return;
        
        Player player = event.getPlayer();
        
        if (npcData.getAction().getType().equals("CONNECT_SERVER")) {
            String serverName = npcData.getAction().getServer();
            
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(serverName);
            
            player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
            player.sendMessage("§aConectando ao " + serverName + "...");
        }
    }
    
    private String getNPCId(ArmorStand entity) {
        for (Map.Entry<String, List<ArmorStand>> entry : npcs.entrySet()) {
            if (entry.getValue().contains(entity)) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    public void removeAllNPCs() {
        for (List<ArmorStand> npcList : npcs.values()) {
            for (ArmorStand npc : npcList) {
                npc.remove();
            }
        }
        npcs.clear();
    }
    
    public void reloadNPCs() {
        removeAllNPCs();
        
        File configFile = new File(plugin.getDataFolder(), "npcs.json");
        NPCConfig newConfig = NPCConfig.load(configFile);
        config.getNpcs().clear();
        config.getNpcs().addAll(newConfig.getNpcs());
        
        loadNPCs();
    }
}
