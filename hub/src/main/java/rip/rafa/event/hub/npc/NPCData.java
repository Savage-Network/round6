package rip.rafa.event.hub.npc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;

public class NPCData {
    
    private String id;
    private String name;
    private String type;
    private boolean useGuardSkin;
    private LocationData location;
    private PropertiesData properties;
    private ActionData action;
    private HologramData hologram;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public boolean isUseGuardSkin() {
        return useGuardSkin;
    }
    
    public void setUseGuardSkin(boolean useGuardSkin) {
        this.useGuardSkin = useGuardSkin;
    }
    
    public LocationData getLocation() {
        return location;
    }
    
    public void setLocation(LocationData location) {
        this.location = location;
    }
    
    public PropertiesData getProperties() {
        return properties;
    }
    
    public void setProperties(PropertiesData properties) {
        this.properties = properties;
    }
    
    public ActionData getAction() {
        return action;
    }
    
    public void setAction(ActionData action) {
        this.action = action;
    }
    
    public HologramData getHologram() {
        return hologram;
    }
    
    public void setHologram(HologramData hologram) {
        this.hologram = hologram;
    }
    
    public static class LocationData {
        private String world;
        private double x;
        private double y;
        private double z;
        private float yaw;
        private float pitch;
        
        public Location toLocation(World world) {
            return new Location(world, x, y, z, yaw, pitch);
        }
        
        public String getWorld() {
            return world;
        }
        
        public void setWorld(String world) {
            this.world = world;
        }
        
        public double getX() {
            return x;
        }
        
        public void setX(double x) {
            this.x = x;
        }
        
        public double getY() {
            return y;
        }
        
        public void setY(double y) {
            this.y = y;
        }
        
        public double getZ() {
            return z;
        }
        
        public void setZ(double z) {
            this.z = z;
        }
        
        public float getYaw() {
            return yaw;
        }
        
        public void setYaw(float yaw) {
            this.yaw = yaw;
        }
        
        public float getPitch() {
            return pitch;
        }
        
        public void setPitch(float pitch) {
            this.pitch = pitch;
        }
    }
    
    public static class PropertiesData {
        private boolean visible;
        private boolean gravity;
        private boolean marker;
        private boolean customNameVisible;
        private boolean glowing;
        
        public boolean isVisible() {
            return visible;
        }
        
        public void setVisible(boolean visible) {
            this.visible = visible;
        }
        
        public boolean isGravity() {
            return gravity;
        }
        
        public void setGravity(boolean gravity) {
            this.gravity = gravity;
        }
        
        public boolean isMarker() {
            return marker;
        }
        
        public void setMarker(boolean marker) {
            this.marker = marker;
        }
        
        public boolean isCustomNameVisible() {
            return customNameVisible;
        }
        
        public void setCustomNameVisible(boolean customNameVisible) {
            this.customNameVisible = customNameVisible;
        }
        
        public boolean isGlowing() {
            return glowing;
        }
        
        public void setGlowing(boolean glowing) {
            this.glowing = glowing;
        }
    }
    
    public static class ActionData {
        private String type;
        private String server;
        
        public String getType() {
            return type;
        }
        
        public void setType(String type) {
            this.type = type;
        }
        
        public String getServer() {
            return server;
        }
        
        public void setServer(String server) {
            this.server = server;
        }
    }
    
    public static class HologramData {
        private boolean enabled;
        private List<String> lines;
        private double height;
        
        public boolean isEnabled() {
            return enabled;
        }
        
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
        
        public List<String> getLines() {
            return lines;
        }
        
        public void setLines(List<String> lines) {
            this.lines = lines;
        }
        
        public double getHeight() {
            return height;
        }
        
        public void setHeight(double height) {
            this.height = height;
        }
    }
}
