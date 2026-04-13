package rip.rafa.event.hub.util;

import com.viaversion.viaversion.api.Via;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {
    
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    
    // Mapeamento de caracteres normais para small caps
    private static final String NORMAL = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final String SMALL = "ᴀʙᴄᴅᴇꜰɢʜɪᴊᴋʟᴍɴᴏᴘǫʀꜱᴛᴜᴠᴡxʏᴢ⁰¹²³⁴⁵⁶⁷⁸⁹";
    
    /**
     * Converte cores hexadecimais e códigos de cor tradicionais
     * Suporta: &#RRGGBB e &c
     */
    public static String colorize(String text) {
        if (text == null) return null;
        
        // Converte hex colors
        Matcher matcher = HEX_PATTERN.matcher(text);
        StringBuffer buffer = new StringBuffer();
        
        while (matcher.find()) {
            String hexCode = matcher.group(1);
            String replacement = net.md_5.bungee.api.ChatColor.of("#" + hexCode).toString();
            matcher.appendReplacement(buffer, replacement);
        }
        matcher.appendTail(buffer);
        
        // Converte códigos tradicionais
        return ChatColor.translateAlternateColorCodes('&', buffer.toString());
    }
    
    /**
     * Converte texto para small caps (Unicode)
     */
    public static String toSmallCaps(String text) {
        if (text == null) return null;
        
        StringBuilder result = new StringBuilder();
        for (char c : text.toLowerCase().toCharArray()) {
            int index = NORMAL.indexOf(c);
            if (index != -1) {
                result.append(SMALL.charAt(index));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
    
    /**
     * Verifica se o jogador está usando uma versão que suporta hex colors
     */
    public static boolean supportsHexColors(Player player) {
        try {
            int version = Via.getAPI().getPlayerVersion(player.getUniqueId());
            // 1.16+ suporta hex colors (protocol 735+)
            return version >= 735;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Aplica cores baseado na versão do jogador
     */
    public static String colorizeForPlayer(Player player, String text) {
        if (supportsHexColors(player)) {
            return colorize(text);
        } else {
            // Remove hex colors e usa apenas cores tradicionais
            String cleaned = HEX_PATTERN.matcher(text).replaceAll("");
            return ChatColor.translateAlternateColorCodes('&', cleaned);
        }
    }
    
    /**
     * Cria um gradiente de cores entre duas cores hex
     */
    public static String gradient(String text, String startHex, String endHex) {
        if (text == null || text.isEmpty()) return "";
        
        int length = text.length();
        StringBuilder result = new StringBuilder();
        
        int startR = Integer.parseInt(startHex.substring(0, 2), 16);
        int startG = Integer.parseInt(startHex.substring(2, 4), 16);
        int startB = Integer.parseInt(startHex.substring(4, 6), 16);
        
        int endR = Integer.parseInt(endHex.substring(0, 2), 16);
        int endG = Integer.parseInt(endHex.substring(2, 4), 16);
        int endB = Integer.parseInt(endHex.substring(4, 6), 16);
        
        for (int i = 0; i < length; i++) {
            float ratio = (float) i / (length - 1);
            
            int r = (int) (startR + ratio * (endR - startR));
            int g = (int) (startG + ratio * (endG - startG));
            int b = (int) (startB + ratio * (endB - startB));
            
            String hex = String.format("%02x%02x%02x", r, g, b);
            result.append(net.md_5.bungee.api.ChatColor.of("#" + hex)).append(text.charAt(i));
        }
        
        return result.toString();
    }
    
    /**
     * Formata texto com estilo Round6
     */
    public static String round6Style(String text) {
        return colorize("&#FF1744" + toSmallCaps(text));
    }
}
