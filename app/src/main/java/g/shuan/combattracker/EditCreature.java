package g.shuan.combattracker;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditCreature extends AppCompatActivity {
    private DataManager db;
    protected Creature editTar;
    protected EditText nameBox, healthBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_creature);
        nameBox = (EditText) findViewById(R.id.editName);
        healthBox = (EditText) findViewById(R.id.editHealth);
        db = new DataManager(this);
        editTar = (Creature) getIntent().getSerializableExtra("Creature");

        if (editTar != null) //checks if the activity needs to be in edit or add mode, by default it is in add and the activity changes to be edit
        {
            TextView hint = findViewById(R.id.editOrg); //adds the unedited ver of the Creature choosen for editing
            hint.setText( getString(R.string.edit_creature_og_title)+"\n"+editTar.toString());
            hint.setVisibility(View.VISIBLE);

            Button del = findViewById(R.id.cancel0);//changes the cancel button to a delete button
            del.setText(R.string.del_btn);
            del.setOnClickListener(new View.OnClickListener() { //are you sure you want to delete dialog
                @Override
                public void onClick(View view) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked;
                                    db.delCreature(editTar);
                                    canBtn(null);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage(getString(R.string.del_dialog).replace("%s",editTar.getCreatureName())).setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            });

            Button can = findViewById(R.id.cancel1); //enable cancel button that is hidden otherwise
            can.setVisibility(View.VISIBLE);

            nameBox.setText(editTar.getCreatureName()); //sets text boxs to the current creatures details for easier editing
            healthBox.setText(String.valueOf(editTar.getMaxHP()));

        }
        else
            editTar = new Creature("",0); //set up for add Creature
    }

    @Override
    public void onBackPressed() {//override back button to allow for quicker save and exit and hinder accidental back button presses
        if (nameBox.getText().toString() == null && healthBox.getText().toString() == null)//allows backing out without changes if text boxes havent changed
            finish();

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked;
                        saveBtn(null);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        canBtn(null);
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Save Changes?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    public void saveBtn(@Nullable View view) { //saves the new Creature or altered one
        if (nameBox.getText().toString() == null && healthBox.getText().toString() == null)
            finish();
        editTar.setCreatureName(nameBox.getText().toString());
        editTar.setMaxHP(Integer.parseInt(healthBox.getText().toString()));
        if (editTar.getPk() > 0)//greater than 0   == edit
            db.editCreature(editTar, null);
        else
            db.newCreature(editTar);
        finish();
    }

    public void canBtn(@Nullable View view) {
        finish();
    }//added redundancy with hardware back button
}
