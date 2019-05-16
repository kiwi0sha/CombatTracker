package g.shuan.combattracker;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Encounter implements Serializable {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    private Date date;
    private long pk;
    private ArrayList<Creature> creatureList;

    public Encounter(Date date, long pk) {
        this.date = date;
        this.pk = pk;
        creatureList = new ArrayList<>();
    }

    public Encounter(Date date, long pk, ArrayList<Creature> creatureList) {
        this.date = date;
        this.pk = pk;
        this.creatureList = creatureList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getPk() {
        return pk;
    }


    public ArrayList<Creature> getCreatureList() {
        return creatureList;
    }

    public void setCreatureList(ArrayList<Creature> creatureList) {
        this.creatureList = creatureList;
    }

    @Override
    public String toString() {
        String ret = "formed: " + DATE_FORMAT.format(date);
        if(creatureList.size() > 0) {
            ret = ret + "\nContains: ";
            for (Creature creature : creatureList) {
                ret += creature.toString() + ", ";
            }
        }
        else
            ret +="\n has no creatures assigned";
        return ret;

    }
}
