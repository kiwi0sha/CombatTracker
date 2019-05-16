package g.shuan.combattracker;

public class playerChar extends Creature {//container class for player controlled creatures, not needed for part one
    private String playerName;

    public playerChar( String playerName, String creatureName, int maxHP) {
        super(creatureName, maxHP);
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
