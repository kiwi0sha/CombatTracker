package g.shuan.combattracker;


import java.io.Serializable;
import java.util.ArrayList;

public class Creature implements Serializable {
    //container class for Creature data, is serializable to allow it to be passed between activities
    private String creatureName;
    private int maxHP,currHP, tempHP,initiative;
    private long pk;
    private ArrayList<String> condition;

    public Creature(String creatureName, int maxHP, long pkid) {
        this.creatureName = creatureName;
        this.maxHP = maxHP;
        this.currHP = this.maxHP;
        this.pk = pkid;
        condition = new ArrayList <String>();
    }
    public Creature(String creatureName, int maxHP) {
        this.creatureName = creatureName;
        this.maxHP = maxHP;
        this.currHP = this.maxHP;
        pk = -1;
        condition = new ArrayList <String>();
    }

    public String getCreatureName() {
        return creatureName;
    }

    public long getPk() {
        return pk;
    }

    public void setCreatureName(String creatureName) {
        this.creatureName = creatureName;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getCurrHP() {
        return currHP;
    }

    public void setCurrHP(int currHP) {
        this.currHP = currHP;
    }

    public int getTempHP() {
        return tempHP;
    }

    public void setTempHP(int tempHP) {
        this.tempHP = tempHP;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public ArrayList<String> getCondition() {
        return condition;
    }

    public void setCondition(ArrayList<String> condition) {
        this.condition = condition;
    }

    @Override//allows it to be written onto the list view
    public String toString() {
        String ret = ""+this.creatureName+"\nMax HP: "+this.getMaxHP()+"\n";
        if(initiative > 0 )
            return ret +"ini: " + getInitiative() + "\n";
        else
            return ret;
    }
}
