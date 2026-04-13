package rip.rafa.event.game.model;

public enum GameType {
    GREEN_LIGHT_RED_LIGHT("Luz Verde, Luz Vermelha", "&#FF1744ʟᴜᴢ ᴠᴇʀᴅᴇ, ʟᴜᴢ ᴠᴇʀᴍᴇʟʜᴀ"),
    HONEYCOMB("Biscoito de Açúcar", "&#FF1744ʙɪꜱᴄᴏɪᴛᴏ ᴅᴇ ᴀᴄ̧ᴜ́ᴄᴀʀ"),
    TUG_OF_WAR("Cabo de Guerra", "&#FF1744ᴄᴀʙᴏ ᴅᴇ ɢᴜᴇʀʀᴀ"),
    MARBLES("Bolinhas de Gude", "&#FF1744ʙᴏʟɪɴʜᴀꜱ ᴅᴇ ɢᴜᴅᴇ"),
    GLASS_BRIDGE("Ponte de Vidro", "&#FF1744ᴘᴏɴᴛᴇ ᴅᴇ ᴠɪᴅʀᴏ"),
    SQUID_GAME("Jogo da Lula", "&#FF1744ᴊᴏɢᴏ ᴅᴀ ʟᴜʟᴀ");
    
    private final String name;
    private final String displayName;
    
    GameType(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
