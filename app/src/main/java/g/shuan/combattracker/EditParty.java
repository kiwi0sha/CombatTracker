package g.shuan.combattracker;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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

    public void startCombat(View view){
        ArrayList<String> creatureNames = new ArrayList<>();
        ArrayList<Character> creatureMod = new ArrayList<>();

        for(int i = 0; i < alPrt.size();i++){
            for (final Creature part : alPrt.get(i).getCreatureList()) {
                Boolean newName = true;
                for(int j = 0;j< creatureNames.size();j++){
                    if(part.getCreatureName().equals(creatureNames.get(j))){ //modify name if it already exsists to be unique
                        part.setCreatureName(part.getCreatureName()+" "+creatureMod.get(j));
                        char temp = creatureMod.get(j);
                        temp +=1;
                        creatureMod.set(j,temp);
                        newName = false;
                        break;
                    }
                }
                if(newName){
                    creatureNames.add(part.getCreatureName());
                    creatureMod.add('A');
                }
                LayoutInflater li = LayoutInflater.from(this);
                View promptV = li.inflate(R.layout.initiative_prompt,null);
                AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);
                adBuilder.setView(R.layout.initiative_prompt);
                final EditText newIni = (EditText) promptV.findViewById(R.id.tarIni);
                TextView userPrompt = promptV.findViewById(R.id.iniPrmpt);
                userPrompt.setText(getString(R.string.del_dialog).replace("%s",part.getCreatureName()));
                adBuilder
                        .setCancelable(false)
                        .setPositiveButton(R.string.con_btn, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(newIni.getText().toString() != "")
                                    part.setInitiative(Integer.parseInt(newIni.getText().toString()));
                                else
                                    part.setInitiative(0);
                            }
                        })
                        .setNegativeButton(R.string.cancel_btn, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                adBuilder.create().show();
            }
            }
    }
}
