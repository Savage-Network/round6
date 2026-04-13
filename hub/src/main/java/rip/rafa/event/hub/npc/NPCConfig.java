package rip.rafa.event.hub.npc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NPCConfig {
    
    private List<NPCData> npcs = new ArrayList<>();
    
    public static NPCConfig load(File file) {
        if (!file.exists()) {
            return createDefault(file);
        }
        
        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, NPCConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new NPCConfig();
        }
    }
    
    public void save(File file) {
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            
            try (FileWriter writer = new FileWriter(file)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(this, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static NPCConfig createDefault(File file) {
        NPCConfig config = new NPCConfig();
        
        NPCData npc = new NPCData();
        npc.setId("round6_main");
        npc.setName("§c§lROUND 6");
        npc.setType("ARMOR_STAND");
        npc.setUseGuardSkin(true);
        
        NPCData.LocationData location = new NPCData.LocationData();
        location.setWorld("world");
        location.setX(0.5);
        location.setY(64.0);
        location.setZ(0.5);
        location.setYaw(0.0f);
        location.setPitch(0.0f);
        npc.setLocation(location);
        
        NPCData.PropertiesData properties = new NPCData.PropertiesData();
        properties.setVisible(false);
        properties.setGravity(false);
        properties.setMarker(true);
        properties.setCustomNameVisible(true);
        // setGlowing() não existe em 1.8.8
        npc.setProperties(properties);
        
        NPCData.ActionData action = new NPCData.ActionData();
        action.setType("CONNECT_SERVER");
        action.setServer("game");
        npc.setAction(action);
        
        NPCData.HologramData hologram = new NPCData.HologramData();
        hologram.setEnabled(true);
        hologram.setHeight(2.0);
        List<String> lines = new ArrayList<>();
        lines.add("§c§l⬛ ROUND 6 ⬛");
        lines.add("§7Clique para jogar");
        lines.add("");
        lines.add("§fJogadores: §a{online}");
        hologram.setLines(lines);
        npc.setHologram(hologram);
        
        config.npcs.add(npc);
        
        config.save(file);
        return config;
    }
    
    public List<NPCData> getNpcs() {
        return npcs;
    }
}
