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
    private static final String[] COMBAT_COL = {"date"};
    private static final String TURN_TABLE = "combatTurn";
    private static final String[] TURN_COL = {"combatID","owner","effecting","condition","amount"};
    private static final String COMBAT_PART_TABLE = "comPart";
    private static final String[] COMBAT_PART_COL = {"dateFormed"};
    private static final String COMBAT_PART_COMP_TABLE = "comPartComp";
    private static final String[] COMBAT_PART_COMP_COL = {"comPartID","grpNumber","creatureName","creatureMaxHP","initiative"};
    private static final String ENCOUNTER_TABLE = "encounter";
    private static final String[] ENCOUNTER_COL = {"dateFormed"};
    private static final String ENCOUNTER_COMP_TABLE = "encounterComp";
    private static final String[] ENCOUNTER_COMP_COL = {"encounterID","creatureID"};

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



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
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `"+ COMBAT_PART_COMP_TABLE +"` (" +
                "`"+ COMBAT_PART_COMP_COL[0]+"`	INTEGER NOT NULL, " +
                "`"+ COMBAT_PART_COMP_COL[1]+"`	INTEGER NOT NULL, " +
                "`"+ COMBAT_PART_COMP_COL[2]+"`	TEXT NOT NULL, " +
                "`"+ COMBAT_PART_COMP_COL[3]+"`	INTEGER NOT NULL, " +
                "`"+ COMBAT_PART_COMP_COL[4]+"`	INTEGER NOT NULL);");
        //create combat table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `"+COMBAT_TABLE+"` (" +
                "`"+COMBAT_COL[0]+"`	INTEGER NOT NULL);");
        //create status table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `"+STATUS_TABLE+"` (`"+STATUS_COL[0]+"` TEXT NOT NULL);");
        //create encounter table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `"+ENCOUNTER_TABLE+"` (`"+ENCOUNTER_COL[0]+"` TEXT NOT NULL);");
        //create encounterComp table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `"+ENCOUNTER_COMP_TABLE+"` (" +
                "`"+ ENCOUNTER_COMP_COL[0]+"`	INTEGER NOT NULL, " +
                "`"+ ENCOUNTER_COMP_COL[1]+"`	INTEGER NOT NULL);");
        //create party table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `"+ COMBAT_PART_TABLE +"` (`"+ COMBAT_PART_COL[0]+"` INTEGER NOT NULL);");
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
            sqLiteDatabase.execSQL("INSERT INTO `"+STATUS_TABLE+"`(`"+STATUS_COL[0]+"`) VALUES ('Attacked');");
            sqLiteDatabase.execSQL("INSERT INTO `"+STATUS_TABLE+"`(`"+STATUS_COL[0]+"`) VALUES ('Blinded');");
            sqLiteDatabase.execSQL("INSERT INTO `"+STATUS_TABLE+"`(`"+STATUS_COL[0]+"`) VALUES ('Charmed');");
            sqLiteDatabase.execSQL("INSERT INTO `"+STATUS_TABLE+"`(`"+STATUS_COL[0]+"`) VALUES ('Deafened');");
            sqLiteDatabase.execSQL("INSERT INTO `"+STATUS_TABLE+"`(`"+STATUS_COL[0]+"`) VALUES ('End Turn');");
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
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ COMBAT_PART_COMP_TABLE +";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ COMBAT_TABLE +";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ COMBAT_PART_TABLE +";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ENCOUNTER_COMP_TABLE+";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ENCOUNTER_TABLE+";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+STATUS_TABLE+";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+PLAYER_TABLE+";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+CREATURE_TABLE+";");
        onCreate(sqLiteDatabase);

    }

    //creates new Creature entry and returns its PK, or returns -1 if it fails
    public long newCreature(Creature mob){
        SQLiteDatabase db = getWritableDatabase();
        String insert = "INSERT INTO "+CREATURE_TABLE+"( "+CREATURE_COL[0]+", "+CREATURE_COL[1]+") VALUES ('"+mob.getCreatureName()+"', '"+mob.getMaxHP()+"' );";
        Cursor res = db.rawQuery(insert,null);
        if(res.moveToFirst()){
            db.close();
            return res.getLong(0);}
        db.close();
        return -1;
    }

    //update Creature table, player can be null
    public boolean editCreature(Creature mob, @Nullable String playerName){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT "+PK+" FROM "+ENCOUNTER_COMP_TABLE+" WHERE `"+ENCOUNTER_COMP_COL[1] +"` = "+mob.getPk(),null);
        if(cur.getCount() == 0) {
            if (playerName == null) {
                db.execSQL("UPDATE `" + CREATURE_TABLE + "` SET `" + CREATURE_COL[0] + "` = \"" + mob.getCreatureName() + "\",`"
                        + CREATURE_COL[1] + "` = \"" + mob.getMaxHP() + "\" WHERE `" + PK + "` = \"" + mob.getPk() + "\";");
                db.close();
                return true;
            }
            else {
                //do stuff
            }
        }
        db.close();
        return false;
    }

    //delete Creature from database if it is not used else where
    public boolean delCreature(Creature mob){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT "+PK+" FROM "+ENCOUNTER_COMP_TABLE+" WHERE `"+ENCOUNTER_COMP_COL[1] +"` = "+mob.getPk(),null);
        if(cur.getCount() == 0) {
            db.execSQL("DELETE FROM `" + CREATURE_TABLE + "` WHERE `" + PK + "` IN ('" + mob.getPk() + "');");
            db.close();
            return true;
        }
        db.close();
        return false;
    }

    //get Creature row from a given id, returns null if fails
    public Creature getCreature(long id){
        SQLiteDatabase db = getWritableDatabase();
        String select = "SELECT * FROM "+CREATURE_TABLE+" WHERE "+PK+" = '"+ id+"'";
        Cursor res = db.rawQuery(select,null);
        if(res.moveToFirst()){
            db.close();
            return new Creature(res.getString(0),res.getInt(1), id);}
        else
            db.close();
            return null;
    }


    //gets all creatures from database as a arraylist
    public ArrayList<Creature> getAllCreatures(){
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Creature> ret = new ArrayList<Creature>();
        if(db.rawQuery("SELECT * FROM `"+CREATURE_TABLE+"`",null).getCount()>0) {
            String select = "SELECT `" + PK + "`, * FROM `" + CREATURE_TABLE + "` ORDER BY `" + CREATURE_COL[0] + "`;";
            Cursor res = db.rawQuery(select, null);
            while (res.moveToNext()) {
                ret.add(new Creature(res.getString(1), res.getInt(2), res.getLong(0)));
            }
        }
        db.close();
        return ret;
    }

    public Encounter getEncounter(long ID){//SELECT `creatureID` FROM `encounterComp` WHERE `encounterID` = 1;{"encounterID","creatureID"};
        SQLiteDatabase db = getWritableDatabase();
        Cursor out = db.rawQuery("SELECT `" + PK + "`,`"+ENCOUNTER_COL[0]+"` FROM `" + ENCOUNTER_TABLE + "` WHERE `"+PK+"` = "+ID+";", null);
        out.moveToFirst();
        Cursor in = db.rawQuery("SELECT `"+ENCOUNTER_COMP_COL[1]+"` FROM `"+ENCOUNTER_COMP_TABLE+"` WHERE `"+ENCOUNTER_COMP_COL[0]+"` = "+ID+";", null);
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
        db.close();
            return null;
    }

    public ArrayList<Encounter> getAllEncounters(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor out = db.rawQuery("SELECT `" + PK + "` FROM `" + ENCOUNTER_TABLE + "` ORDER BY `" + ENCOUNTER_COL[0] + "`;", null);
        ArrayList<Encounter> ret = new ArrayList<Encounter>();
        if(out.getCount() > 0){
            while (out.moveToNext()){
                ret.add( getEncounter(out.getLong(0)));
            }
        }
        db.close();
        return ret;
    }
    //creates new Encounter entry and returns its PK, or returns -1 if it fails
    public long newEncounter(Encounter enc){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ENCOUNTER_COL[0],DATE_FORMAT.format(enc.getDate()));
        long pk = db.insert(ENCOUNTER_TABLE,null,cv);
        Log.d("SQL", "newEncounter: new encounter pk ="+pk);
        cv.remove(ENCOUNTER_COL[0]);
        cv.put(ENCOUNTER_COMP_COL[0],pk);
        for (int i =0;i<enc.getCreatureList().size();i++){
            String insert = "INSERT INTO `"+ENCOUNTER_COMP_TABLE+"`(`"+ENCOUNTER_COMP_COL[0]+"`,`"+ENCOUNTER_COMP_COL[1]+"` ) VALUES ('"+pk+"', "+enc.getCreatureList().get(i).getPk()+");";
            db.execSQL(insert);
            Log.d("SQL", "newEncounter: "+insert);
        }
        db.close();
        return pk;
    }

    public boolean updateEncounter(Encounter enc){
        SQLiteDatabase db = getWritableDatabase();
            long pk = enc.getPk();
            ContentValues cv = new ContentValues();
            cv.put(ENCOUNTER_COMP_COL[0], pk);
            db.delete(ENCOUNTER_COMP_TABLE, ENCOUNTER_COMP_COL[0] + " = (" + pk + ")", null);
            for (int i = 0; i < enc.getCreatureList().size(); i++) {
                String insert = "INSERT INTO `"+ENCOUNTER_COMP_TABLE+"`(`"+ENCOUNTER_COMP_COL[0]+"`,`"+ENCOUNTER_COMP_COL[1]+"` ) VALUES ('"+pk+"', "+enc.getCreatureList().get(i).getPk()+");";
                db.execSQL(insert);
                Log.d("SQL", "updateEncounter: "+insert);
            }
            db.close();
            return true;
    }

    public boolean delEncounter(Encounter enc){
        SQLiteDatabase db = getWritableDatabase();
            db.delete(ENCOUNTER_COMP_TABLE,ENCOUNTER_COMP_COL[0]+" = ("+enc.getPk()+")",null);
            db.delete(ENCOUNTER_TABLE,"`"+PK+"` = "+enc.getPk(),null);
            db.close();
            return true;
    }


    //COMBAT_PART_TABLE = "comPart";
    //COMBAT_PART_COL = {"dateFormed"};
    //COMBAT_PART_COMP_TABLE = "comPartComp";
    //COMBAT_PART_COMP_COL = {"comPartID","grpNumber","creatureName","creatureMaxHP","initiative"};

    public boolean newCombat(ArrayList<Encounter> participents){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COMBAT_PART_COL[0],DATE_FORMAT.format(new Date()));
        long pk = db.insert(ENCOUNTER_TABLE,null,cv);
        Log.d("SQL", "newCombat: new combat participant list pk ="+pk);
        cv.remove(COMBAT_PART_COL[0]);
        cv.put(COMBAT_PART_COMP_COL[0],pk);
        for(int i = 0; i < participents.size();i++){
            cv.put(COMBAT_PART_COMP_COL[1],i);
            for (Creature part : participents.get(i).getCreatureList()) {
                cv.put(COMBAT_PART_COMP_COL[2],part.getCreatureName());
                cv.put(COMBAT_PART_COMP_COL[3],part.getMaxHP());
                cv.put(COMBAT_PART_COMP_COL[4],part.getInitiative());
                db.insert(COMBAT_PART_COMP_TABLE,null,cv);
            }
        }

        return false;
    }
}
