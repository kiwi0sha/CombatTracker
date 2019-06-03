package g.shuan.combattracker;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

public class EditParty extends AppCompatActivity {
    private DataManager db;
    private ListView lvAll,lvEnc;
    private ArrayList<Encounter> alAll, alPrt;
    private Encounter addTar, rmvTar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_encounter);
        db = new DataManager(this);
        lvAll = findViewById(R.id.allEncounters);
        lvEnc = findViewById(R.id.encCreatures);
        lvAll.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                findViewById(R.id.addCreatureToEncBtn).setEnabled(true);
                addTar = alAll.get(position);
                for(int i = 0; i < parent.getChildCount(); i++){//clears previous highlighting
                    parent.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                }
                parent.getChildAt(position).setBackgroundColor(Color.BLUE);//highlights currently selected Creature
            }
        });
        lvEnc.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                findViewById(R.id.rmvCreatureToEncBtn).setEnabled(true);
                rmvTar = alPrt.get(position);
                for(int i = 0; i < parent.getChildCount(); i++){//clears previous highlighting
                    parent.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                }
                parent.getChildAt(position).setBackgroundColor(Color.BLUE);//highlights currently selected Creature
            }
        });
        alPrt = new ArrayList<>();
        refreshList();

    }

    public void refreshList(){
        alAll = db.getAllEncounters();

        if(alAll.size() > 0){
            lvAll.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,alAll));
            lvAll.setVisibility(View.VISIBLE);}
        else{
            lvAll.setVisibility(View.INVISIBLE);
        }
        if(alPrt.size() > 0){
            lvEnc.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, alPrt));
            lvEnc.setVisibility(View.VISIBLE);}
        else{
            lvEnc.setVisibility(View.INVISIBLE);
        }
    }

    public void addCreature(View view){
        alPrt.add(addTar);
        refreshList();
    }

    public void rmvCreature(View view){
        alPrt.remove(rmvTar);
        refreshList();
    }

    public void clrSelection(View view) {//clear selection button code
        findViewById(R.id.addCreatureToEncBtn).setEnabled(false);
        findViewById(R.id.rmvCreatureToEncBtn).setEnabled(false);
        addTar = null;
        rmvTar = null;
        for(int i = 0; i < lvEnc.getChildCount(); i++){
            lvEnc.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
        }
        for(int i = 0; i < lvAll.getChildCount(); i++){
            lvAll.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
        }
        refreshList();
    }
    @Override
    public void onBackPressed() {//override back button to allow for quicker save and exit and hinder accidental back button presses

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked;
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        finish();
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Save Changes?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
}
