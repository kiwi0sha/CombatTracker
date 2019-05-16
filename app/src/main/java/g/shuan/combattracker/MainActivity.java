package g.shuan.combattracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void manCreatures(View view){
        Intent intent = new Intent(this,ManageCreatures.class);
        startActivity(intent);
    }
    public void manEncounter(View view){
        Intent intent = new Intent(this,ManageEncounters.class);
        startActivity(intent);
    }
    public void aboutView(View view){
        Intent intent = new Intent(this,About.class);
        startActivity(intent);
    }
}
