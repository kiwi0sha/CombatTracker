package g.shuan.combattracker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ManageEncounters extends AppCompatActivity {
    private DataManager db;
    private ListView lv;
    private ArrayList<Encounter> temp;
    private Encounter tar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_creatures);
        //do not put code above this comment in this method
        db = new DataManager(this);
        lv = findViewById(R.id.creatureList);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tar = temp.get(position);
                findViewById(R.id.newEncounterBTN).setEnabled(true);
                findViewById(R.id.clrBTN).setEnabled(true);
                for(int i = 0; i < parent.getChildCount(); i++){//clears previous highlighting
                    parent.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                }
                parent.getChildAt(position).setBackgroundColor(Color.BLUE);//highlights currently selected Creature
            }
        });
        Toolbar appBar = findViewById(R.id.app_bar);
        setSupportActionBar(appBar);//enables toolbar menu


    }

    public void refreshList(){//refreshes Creature list
        temp = db.getAllEncounters();
        lv = findViewById(R.id.creatureList);
        if(temp.size() > 0){
            lv.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,temp));
            lv.setVisibility(View.VISIBLE);}
        else{
        lv.setVisibility(View.INVISIBLE);
    }
    }

    public void launchEditActivity(View view) {//launches add/edit Creature activity
        Intent intent = new Intent(this,EditEncounter.class);
        intent.putExtra("Encounter",tar);
        startActivity(intent);
    }


    public void clrSelection(View view) {//clear selection button code
        findViewById(R.id.newEncounterBTN).setEnabled(false);
        findViewById(R.id.clrBTN).setEnabled(false);
        tar = null;
        for(int i = 0; i < lv.getChildCount(); i++){
            lv.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onResume() {//clears the selection and refreshes the Creature list when the activity resumes
        super.onResume();
        clrSelection(null);
        refreshList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//toolbar menu options
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_bar_menu_encounter,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//toolbar menu listeners
        Intent intent;
        switch (item.getItemId()){
            case R.id.About:
                intent = new Intent(this,About.class);
                startActivity(intent);
                break;
            case R.id.add_Encounter:
                intent = new Intent(this,EditEncounter.class);
                startActivity(intent);
                break;
            case R.id.del_Encounter:
                db.delEncounter(tar);
                clrSelection(null);
                refreshList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
