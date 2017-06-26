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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainAct";

    protected SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        // We have preferences :
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false); // This sets the default value once and for all (not at everylaunch)
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Loading exercises :
        Exercises exs = Exercises.getInstance();
        exs.loadExercises(this);

        // Dsplaying the list of rythms :
        ArrayList<Integer> test = new ArrayList<>();
        //test.add(4);
        //test.add(4);


        test.add(4);
        test.add(8);

        test.add(8);

        //test.add(4);
        //test.add(16);
        //test.add(8);
        //test.add(8);
        //test.add(16);
        //test.add(8);

        try {
            AbstractTrainActivity.drawRythm(new Rythm(new Pair<>(2, 4), test),null, (LinearLayout) findViewById(R.id.part1), this);
        } catch (BadRythmException e) {
            e.printStackTrace();
            if (BuildConfig.DEBUG) Log.v(TAG, "Arg bad rythm.");
        }

    }
    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void start(View v) {
        Intent intent;

        if(prefs.getBoolean("pref_rythm", false))
            intent = new Intent(this, TrainActivity.class);
        else intent = new Intent(this, HumanTrainActivity.class);

        intent.putExtra("dualHanded", false);
        startActivity(intent);
    }
}
