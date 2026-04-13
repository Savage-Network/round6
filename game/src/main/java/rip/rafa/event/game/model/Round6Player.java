package rip.rafa.event.game.model;

import java.util.UUID;

public class Round6Player {
    
    private final UUID uuid;
    private int playerNumber;
    private boolean alive;
    private boolean guard;
    private int kills;
    private int gamesWon;
    private long moneyEarned;
    
    public Round6Player(UUID uuid) {
        this.uuid = uuid;
        this.alive = true;
        this.guard = false;
        this.kills = 0;
        this.gamesWon = 0;
        this.moneyEarned = 0;
    }
    
    public UUID getUuid() {
        return uuid;
    }
    
    public int getPlayerNumber() {
        return playerNumber;
    }
    
    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }
    
    public boolean isAlive() {
        return alive;
    }
    
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    
    public boolean isGuard() {
        return guard;
    }
    
    public void setGuard(boolean guard) {
        this.guard = guard;
    }
    
    public int getKills() {
        return kills;
    }
    
    public void addKill() {
        this.kills++;
    }
    
    public int getGamesWon() {
        return gamesWon;
    }
    
    public void addGameWon() {
        this.gamesWon++;
    }
    
    public long getMoneyEarned() {
        return moneyEarned;
    }
    
    public void addMoney(long amount) {
        this.moneyEarned += amount;
    }
    
    public String getFormattedNumber() {
        return String.format("%03d", playerNumber);
    }
}
