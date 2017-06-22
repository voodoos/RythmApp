package fr.u31.rythm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

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


        // Populating the difficulty menu :
        Spinner spinner = (Spinner) findViewById(R.id.difficulty_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.difficulty_levels, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setSelection(2);

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
