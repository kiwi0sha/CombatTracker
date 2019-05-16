package g.shuan.combattracker;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DataManager extends SQLiteOpenHelper {

    //hard coded scheme for the sqllite database. contains unknown error/warning involving the static String arrays
    private static final String DATABASE_NAME = "combatinfo.db";
    private static final int DATABASE_VER = 1;
    private static final String PK = "_rowid_";
    private static final String CREATURE_TABLE = "creatures";
    private static final String[] CREATURE_COL = {"name","maxHP","player"};
    private static final String PLAYER_TABLE = "players";
    private static final String[] PLAYER_COL = {"name"};
    private static final String STATUS_TABLE = "status";
    private static final String[] STATUS_COL = {"name"};
    private static final String COMBAT_TABLE = "combat";
    private static final String[] COMBAT_COL = {"date","party","encounter"};
    private static final String TURN_TABLE = "combatTurn";
    private static final String[] TURN_COL = {"combatID","owner","effecting","condition","amount"};
    private static final String PARTY_TABLE = "party";
    private static final String[] PARTY_COL = {"dateFormed"};
    private static final String PARTY_COMP_TABLE = "partyComp";
    private static final String[] PARTY_COMP_COL = {"partyID","playerID","creatureID"};
    private static final String ENCOUNTER_TABLE = "encounter";
    private static final String[] ENCOUNTER_COL = {"dateFormed"};
    private static final String ENCOUNTER_COMP_TABLE = "encounterComp";
    private static final String[] ENCOUNTER_COMP_COL = {"encounterID","creatureID"};

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    private SQLiteDatabase db = getWritableDatabase();//shortens most method creation and writing by doing this here


    public DataManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {//checks if all tables exsist each time the object is created and creates them if they don't exsist
        //create Creature table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `"+CREATURE_TABLE+"` (" +
                "`"+CREATURE_COL[0]+"`	TEXT NOT NULL, " +
                "`"+CREATURE_COL[1]+"`	INTEGER NOT NULL, " +
                "`"+CREATURE_COL[2]+"`	INTEGER);");
        //create player table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `"+PLAYER_TABLE+"` (`"+PLAYER_COL[0]+"`	TEXT NOT NULL);");
        //create partyComp table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `"+PARTY_COMP_TABLE+"` (" +
                "`"+PARTY_COMP_COL[0]+"`	INTEGER NOT NULL, " +
                "`"+PARTY_COMP_COL[1]+"`	INTEGER NOT NULL, " +
                "`"+PARTY_COMP_COL[2]+"`	INTEGER NOT NULL);");
        //create combat table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `"+COMBAT_TABLE+"` (" +
                "`"+COMBAT_COL[0]+"`	INTEGER NOT NULL, " +
                "`"+COMBAT_COL[1]+"`	INTEGER NOT NULL, " +
                "`"+COMBAT_COL[2]+"`	INTEGER NOT NULL);");
        //create status table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `"+STATUS_TABLE+"` (`"+STATUS_COL[0]+"` TEXT NOT NULL);");
        //create encounter table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `"+ENCOUNTER_TABLE+"` (`"+ENCOUNTER_COL[0]+"` TEXT NOT NULL);");
        //create encounterComp table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `"+ENCOUNTER_COMP_TABLE+"` (" +
                "`"+ ENCOUNTER_COMP_COL[0]+"`	INTEGER NOT NULL, " +
                "`"+ ENCOUNTER_COMP_COL[1]+"`	INTEGER NOT NULL);");
        //create party table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `"+PARTY_TABLE+"` (`"+PARTY_COL[0]+"` INTEGER NOT NULL);");
        //create turn table "combatID","owner","effecting","condition","amount"
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `"+TURN_TABLE+"` " +
                "(`"+TURN_COL[0]+"`	INTEGER NOT NULL, " +
                "`"+TURN_COL[1]+"`	INTEGER NOT NULL, " +
                "`"+TURN_COL[2]+"`	INTEGER NOT NULL, " +
                "`"+TURN_COL[3]+"`	INTEGER, " +
                "`"+TURN_COL[4]+"`	INTEGER);");


        //populates Creature table if it is empty
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM `"+CREATURE_TABLE+"`",null);
        if(!(res.getCount() > 0)){
            sqLiteDatabase.execSQL("INSERT INTO `"+CREATURE_TABLE+"`(`"+CREATURE_COL[0]+"`,`"+CREATURE_COL[1]+"`,`"+CREATURE_COL[2]+"`) VALUES ('Goblin',20,NULL);");
            sqLiteDatabase.execSQL("INSERT INTO `"+CREATURE_TABLE+"`(`"+CREATURE_COL[0]+"`,`"+CREATURE_COL[1]+"`,`"+CREATURE_COL[2]+"`) VALUES ('Orc',60,NULL);");
            sqLiteDatabase.execSQL("INSERT INTO `"+CREATURE_TABLE+"`(`"+CREATURE_COL[0]+"`,`"+CREATURE_COL[1]+"`,`"+CREATURE_COL[2]+"`) VALUES ('Troll',45,NULL);");
            sqLiteDatabase.execSQL("INSERT INTO `"+CREATURE_TABLE+"`(`"+CREATURE_COL[0]+"`,`"+CREATURE_COL[1]+"`,`"+CREATURE_COL[2]+"`) VALUES ('Red Dragon',180,NULL);");
            sqLiteDatabase.execSQL("INSERT INTO `"+CREATURE_TABLE+"`(`"+CREATURE_COL[0]+"`,`"+CREATURE_COL[1]+"`,`"+CREATURE_COL[2]+"`) VALUES ('Cat',5,NULL);");
        }
        //populate status table if it is empty
        res = sqLiteDatabase.rawQuery("SELECT * FROM `"+STATUS_TABLE+"`",null);
        if(!(res.getCount() > 0)){
            sqLiteDatabase.execSQL("INSERT INTO `"+STATUS_TABLE+"`(`"+STATUS_COL[0]+"`) VALUES ('Blinded');");
            sqLiteDatabase.execSQL("INSERT INTO `"+STATUS_TABLE+"`(`"+STATUS_COL[0]+"`) VALUES ('Charmed');");
            sqLiteDatabase.execSQL("INSERT INTO `"+STATUS_TABLE+"`(`"+STATUS_COL[0]+"`) VALUES ('Deafened');");
            sqLiteDatabase.execSQL("INSERT INTO `"+STATUS_TABLE+"`(`"+STATUS_COL[0]+"`) VALUES ('Fatigued');");
            sqLiteDatabase.execSQL("INSERT INTO `"+STATUS_TABLE+"`(`"+STATUS_COL[0]+"`) VALUES ('Frightened');");
            sqLiteDatabase.execSQL("INSERT INTO `"+STATUS_TABLE+"`(`"+STATUS_COL[0]+"`) VALUES ('Grappled');");
            sqLiteDatabase.execSQL("INSERT INTO `"+STATUS_TABLE+"`(`"+STATUS_COL[0]+"`) VALUES ('Incapacitated');");
            sqLiteDatabase.execSQL("INSERT INTO `"+STATUS_TABLE+"`(`"+STATUS_COL[0]+"`) VALUES ('Invisible');");
            sqLiteDatabase.execSQL("INSERT INTO `"+STATUS_TABLE+"`(`"+STATUS_COL[0]+"`) VALUES ('Paralyzed');");
            sqLiteDatabase.execSQL("INSERT INTO `"+STATUS_TABLE+"`(`"+STATUS_COL[0]+"`) VALUES ('Petrified');");
            sqLiteDatabase.execSQL("INSERT INTO `"+STATUS_TABLE+"`(`"+STATUS_COL[0]+"`) VALUES ('Poisoned');");
            sqLiteDatabase.execSQL("INSERT INTO `"+STATUS_TABLE+"`(`"+STATUS_COL[0]+"`) VALUES ('Prone');");
            sqLiteDatabase.execSQL("INSERT INTO `"+STATUS_TABLE+"`(`"+STATUS_COL[0]+"`) VALUES ('Restrained');");
            sqLiteDatabase.execSQL("INSERT INTO `"+STATUS_TABLE+"`(`"+STATUS_COL[0]+"`) VALUES ('Stunned');");
            sqLiteDatabase.execSQL("INSERT INTO `"+STATUS_TABLE+"`(`"+STATUS_COL[0]+"`) VALUES ('Unconscious');");
            sqLiteDatabase.execSQL("INSERT INTO `"+STATUS_TABLE+"`(`"+STATUS_COL[0]+"`) VALUES ('Exhaustion');");
        }
        res.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {//purge and recreate database
        //nuke and pave
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TURN_TABLE+";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+PARTY_COMP_TABLE+";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+PARTY_TABLE+";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ENCOUNTER_COMP_TABLE+";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ENCOUNTER_TABLE+";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+STATUS_TABLE+";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+PLAYER_TABLE+";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+CREATURE_TABLE+";");
        onCreate(sqLiteDatabase);

    }

    //creates new Creature entry and returns its PK, or returns -1 if it fails
    public long newCreature(Creature mob){
        String insert = "INSERT INTO "+CREATURE_TABLE+"( "+CREATURE_COL[0]+", "+CREATURE_COL[1]+") VALUES ('"+mob.getCreatureName()+"', '"+mob.getMaxHP()+"' );";
        Cursor res = db.rawQuery(insert,null);
        if(res.moveToFirst())
            return res.getLong(0);
        return -1;
    }

    //update Creature table, player can be null
    public boolean editCreature(Creature mob, @Nullable String playerName){
        Cursor cur = db.rawQuery("SELECT "+PK+" FROM "+ENCOUNTER_COMP_TABLE+" WHERE `"+ENCOUNTER_COMP_COL[1] +"` = "+mob.getPk(),null);
        if(cur.getCount() == 0) {
            if (playerName == null) {
                db.execSQL("UPDATE `" + CREATURE_TABLE + "` SET `" + CREATURE_COL[0] + "` = \"" + mob.getCreatureName() + "\",`"
                        + CREATURE_COL[1] + "` = \"" + mob.getMaxHP() + "\" WHERE `" + PK + "` = \"" + mob.getPk() + "\";");
                return true;
            }
            else {
                //do stuff
            }
        }
        return false;
    }

    //delete Creature from database if it is not used else where
    public boolean delCreature(Creature mob){
        Cursor cur = db.rawQuery("SELECT "+PK+" FROM "+ENCOUNTER_COMP_TABLE+" WHERE `"+ENCOUNTER_COMP_COL[1] +"` = "+mob.getPk(),null);
        if(cur.getCount() == 0) {
            db.execSQL("DELETE FROM `" + CREATURE_TABLE + "` WHERE `" + PK + "` IN ('" + mob.getPk() + "');");
        }
        return false;
    }

    //get Creature row from a given id, returns null if fails
    public Creature getCreature(long id){
        String select = "SELECT * FROM "+CREATURE_TABLE+" WHERE "+PK+" = '"+ id+"'";
        Cursor res = db.rawQuery(select,null);
        if(res.moveToFirst())
            return new Creature(res.getString(0),res.getInt(1), id);
        else
            return null;
    }


    //gets all creatures from database as a arraylist
    public ArrayList<Creature> getAllCreatures(){
        ArrayList<Creature> ret = new ArrayList<Creature>();
        if(db.rawQuery("SELECT * FROM `"+CREATURE_TABLE+"`",null).getCount()>0) {
            String select = "SELECT `" + PK + "`, * FROM `" + CREATURE_TABLE + "` ORDER BY `" + CREATURE_COL[0] + "`;";
            Cursor res = db.rawQuery(select, null);
            while (res.moveToNext()) {
                ret.add(new Creature(res.getString(1), res.getInt(2), res.getLong(0)));
            }
        }
        return ret;
    }

    public Encounter getEncounter(long ID){//SELECT `creatureID` FROM `encounterComp` WHERE `encounterID` = 1;
        Cursor out = db.rawQuery("SELECT `" + PK + "`,`"+ENCOUNTER_COL[0]+"` FROM `" + ENCOUNTER_TABLE + "` WHERE `"+PK+"` = "+ID+";", null);
        out.moveToFirst();
        Cursor in = db.rawQuery("SELECT `"+ENCOUNTER_COMP_COL[1]+"` FROM `"+ENCOUNTER_COMP_TABLE+"` WHERE `"+ENCOUNTER_COMP_COL[1]+"` = "+ID+";", null);
        ArrayList<Creature> res = new ArrayList<>();
        while(in.moveToNext()){
                res.add(getCreature(in.getLong(0)));
            }
            if(res.size()>0){
                try {
                    return new Encounter(DATE_FORMAT.parse(out.getString(1)),out.getLong(0),res);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    return new Encounter(DATE_FORMAT.parse(out.getString(1)),out.getLong(0));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return null;
    }

    public ArrayList<Encounter> getAllEncounters(){
        Cursor out = db.rawQuery("SELECT `" + PK + "` FROM `" + ENCOUNTER_TABLE + "` ORDER BY `" + ENCOUNTER_COL[0] + "`;", null);
        ArrayList<Encounter> ret = new ArrayList<Encounter>();
        if(out.getCount() > 0){
            while (out.moveToNext()){
                ret.add( getEncounter(out.getLong(0)));
            }
        }
        return ret;
    }
    //creates new Encounter entry and returns its PK, or returns -1 if it fails
    public long newEncounter(Encounter enc){
        ContentValues cv = new ContentValues();
        cv.put(ENCOUNTER_COL[0],DATE_FORMAT.format(enc.getDate()));
        long pk = db.insert(ENCOUNTER_TABLE,null,cv);
        cv.remove(ENCOUNTER_COL[0]);
        cv.put(ENCOUNTER_COMP_COL[0],pk);
        for (int i =0;i<enc.getCreatureList().size();i++){
            cv.put(ENCOUNTER_COMP_COL[1],enc.getCreatureList().get(i).getPk());
            db.insert(ENCOUNTER_COMP_TABLE,null,cv);
            cv.remove(ENCOUNTER_COMP_COL[1]);
        }
            return pk;
    }

    public boolean updateEncounter(Encounter enc){
        Cursor cur = db.rawQuery("SELECT "+PK+" FROM "+COMBAT_TABLE+" WHERE `"+COMBAT_COL[2] +"` = "+enc.getPk(),null);
        if(cur.getCount() == 0) {
            long pk = enc.getPk();
            ContentValues cv = new ContentValues();
            cv.put(ENCOUNTER_COMP_COL[0], pk);
            db.delete(ENCOUNTER_COMP_TABLE, ENCOUNTER_COMP_COL[0] + " = (" + pk + ")", null);
            for (int i = 0; i < enc.getCreatureList().size(); i++) {
                cv.put(ENCOUNTER_COMP_COL[1], enc.getCreatureList().get(i).getPk());
                db.insert(ENCOUNTER_COMP_TABLE, null, cv);
                cv.remove(ENCOUNTER_COMP_COL[1]);
            }
            return true;
        }
        return false;
    }

    public boolean delEncounter(Encounter enc){
        Cursor cur = db.rawQuery("SELECT "+PK+" FROM "+COMBAT_TABLE+" WHERE `"+COMBAT_COL[2] +"` = "+enc.getPk(),null);
        if(cur.getCount() == 0){
            db.delete(ENCOUNTER_COMP_TABLE,ENCOUNTER_COMP_COL[0]+" = ("+enc.getPk()+")",null);
            db.delete(ENCOUNTER_TABLE,"`"+PK+"` = "+enc.getPk(),null);
            return true;
        }
        return false;
    }
}
