package fr.u31.rythm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainAct";

    protected SharedPreferences prefs;
    private LinearLayout lv;

    private Exercises exs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
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
        return true;
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
