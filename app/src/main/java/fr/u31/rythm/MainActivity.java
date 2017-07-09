package fr.u31.rythm;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainAct";

    protected Toolbar myToolbar;
    protected SharedPreferences prefs;
    protected FragmentManager fragm;
    protected List<ExerciceFragment> exfrags;

    private FrameLayout settings_fragment = null;
    private ImageView tune_setting_button_action_view = null;
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

        // We need the fragment manager for showing exercises :
        fragm = getFragmentManager();

        settings_fragment = findViewById(R.id.settingsFragmentFrame);

        // Getting pointer to the list of exercises :
        lv = findViewById(R.id.exercises_list);

        final MainActivity that = this;

        // We want settings to close when touching outside of them
        // Done : Not working properly for now
        lv.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent me) {
                if (BuildConfig.DEBUG) Log.v(TAG, "Touch list");

                hideSettings();
                return false;
            }
        });

        // Loading exercises :
        exs = Exercises.getInstance();
        exs.loadExercises(this);

        // One big transaction for all the exercises :
        FragmentTransaction fragmentTransaction = fragm.beginTransaction();

        // Populating !
        for (final Exercise ex: exs.getExercises().values()) {
            // We need a new instance of ou exercise fragment :
            ExerciceFragment ef = ExerciceFragment.newInstance(ex, ExerciceFragment.SHOW_CONTROLS);

            // We add it :
            fragmentTransaction.add(lv.getId(), ef);
        }

        // ENd of the transaction :
        fragmentTransaction.commit();


    }

    @Override
    public void onPause() {
        super.onPause();
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem mi = menu.findItem(R.id.action_tune);
        tune_setting_button_action_view = (ImageView) mi.getActionView();

        if(tune_setting_button_action_view != null) {
            tune_setting_button_action_view.setImageResource(R.drawable.ic_tune_black_24dp);


            tune_setting_button_action_view.setOnClickListener(new View.OnClickListener() {
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
                // We show the difficulty setting if they're not, else wehide them :
                hideShowSettings();
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

    /**
     * @param ev
     * @return
     *
     *  Intercepting touch events to analyse which ones are outside of the setitng menu and appbar.
     *  If setting menu is open and touch event is outside, closing the setting menu.
     *
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Rect viewRect = new Rect(), barRect = new Rect();
        settings_fragment.getGlobalVisibleRect(viewRect);
        myToolbar.getGlobalVisibleRect(barRect);
        if (!viewRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
            if (!barRect.contains((int) ev.getRawX(), (int) ev.getRawY()))
            hideSettings();
        }
        super.dispatchTouchEvent(ev);
        return true;
    }

    private void hideShowSettings() {
        if(!settings) showSettings();
        else hideSettings();
    }

    private void showSettings() {
        if(!settings) {
            if(tune_setting_button_action_view != null) {
                Animation rot = AnimationUtils.loadAnimation(this, R.anim.rotate_settings_out);
                rot.setFillAfter(true);
                tune_setting_button_action_view.startAnimation(rot);
            }
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.setings_in);
            anim.setFillAfter(true);
            settings_fragment.startAnimation(anim);
            settings = true;
        }
    }

    private void hideSettings() {
        if(settings) {
            if(tune_setting_button_action_view != null) {
                Animation rot = AnimationUtils.loadAnimation(this, R.anim.rotate_settings_in);
                rot.setFillAfter(true);
                tune_setting_button_action_view.startAnimation(rot);
            }
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.setings_out);
            settings_fragment.startAnimation(anim);
            settings = false;
        }
    }
}
