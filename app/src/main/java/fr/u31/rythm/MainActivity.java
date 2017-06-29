package fr.u31.rythm;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainAct";

    protected Toolbar myToolbar;
    protected SharedPreferences prefs;
    private LinearLayout lv;

    private Exercises exs;

    boolean settings = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        // We have preferences :
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false); // This sets the default value once and for all (not at everylaunch)
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Getting pointer to the list of exercises :
        lv = (LinearLayout) findViewById(R.id.exercises_list);

        // Loading exercises :
        exs = Exercises.getInstance();
        exs.loadExercises(this);

        // Populating !
        for (final Exercise ex: exs.getExercises().values()) {
            RelativeLayout exl = ex.getLayout(this, lv);

            // Linking the Ear button
            exl.findViewById(R.id.ear).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (BuildConfig.DEBUG) Log.v(TAG, "Click Ear");

                    //start(ex.getId());
                }
            });

            // Linking the Start button
            exl.findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (BuildConfig.DEBUG) Log.v(TAG, "Click");

                    start(ex.getId());
                }
            });

            lv.addView(exl);
        }


    }
    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem mi = menu.findItem(R.id.action_tune);
        ImageView v = (ImageView) mi.getActionView();

        if(v != null) {
            v.setImageResource(R.drawable.ic_tune_black_24dp);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onOptionsItemSelected(mi);
                }
            });
        }
        return true;
    }

    // React tu button press in actionbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_tune:
                ImageView v = (ImageView) item.getActionView();

                // We show the difficulty setting if they're not, else wehide them :
                if(settings) {
                    if(v != null) {
                        Animation rot = AnimationUtils.loadAnimation(this, R.anim.rotate_settings_in);
                        rot.setFillAfter(true);
                        v.startAnimation(rot);
                    }
                    Animation anim = AnimationUtils.loadAnimation(this, R.anim.setings_out);
                    findViewById(R.id.settingsFragmentFrame).startAnimation(anim);
                    settings = false;
                } else {
                    if(v != null) {
                        Animation rot = AnimationUtils.loadAnimation(this, R.anim.rotate_settings_out);
                        rot.setFillAfter(true);
                        v.startAnimation(rot);
                    }
                    Animation anim = AnimationUtils.loadAnimation(this, R.anim.setings_in);
                    anim.setFillAfter(true);
                    findViewById(R.id.settingsFragmentFrame).startAnimation(anim);
                    settings = true;
                }
                return true;

            case R.id.action_settings:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;


            case R.id.home:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                this.finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void start(final int exid) {
        Intent intent;

        if(prefs.getBoolean("pref_rythm", false))
            intent = new Intent(this, TrainActivity.class);
        else intent = new Intent(this, HumanTrainActivity.class);

        intent.putExtra("exercise", exid);
        startActivity(intent);
    }
}
