package g.shuan.combattracker;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class About extends AppCompatActivity {
    private static final Uri dance = Uri.parse("android.resource://g.shuan.combattracker/" + R.drawable.dance);//uri for internally stored gif

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        iv = findViewById(R.id.dance);
        Glide.with(this).load(dance).into(iv);//allows for animated gif
    }

}
