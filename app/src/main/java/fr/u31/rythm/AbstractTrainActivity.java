package fr.u31.rythm;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * The type Train activity.
 */
public abstract class AbstractTrainActivity extends AppCompatActivity implements View.OnTouchListener {
    private static final String TAG = "AbstractTrainAct";

    protected SharedPreferences prefs;
    private FrameLayout exercise_layout;
    private ProgressBar progressBar;

    protected boolean dualHanded;

    protected Score score;
    protected AbstractMetronome m_left, m_right;
    protected Exercise ex;
    protected Rythm r_left, r_right;
    protected ArrayList<ImageView> rightNotesViews, leftNotesViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        // Adding listener to tapping surfaces :
        findViewById(R.id.RightTap).setOnTouchListener(this);

        // The intent (used to get parameters from parent view) :
        Intent intent = getIntent();

        // Which exercise ?
        ex = Exercises.getInstance().getExercise(intent.getIntExtra("exercise", 0));
        ex.reset();
        dualHanded = ex.isDualHanded();

        // We have preferences :
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Adding the ActionBar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the BACK button
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        // Creating some Score :
        int d = Integer.parseInt(prefs.getString("pref_difficulty", "2"));

        score = new Score(Difficulty.which(d), getResources());

        //Getting pointer to the progressbar :
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Getting pointer to the exercise layout :
        exercise_layout = (FrameLayout) findViewById(R.id.exercise);

        // Drawing the exercise :
        RelativeLayout rlex = ex.getLayout(this, exercise_layout);
        rlex.findViewById(R.id.start).setVisibility(View.GONE); // Don't show buttons !
        rlex.findViewById(R.id.ear).setVisibility(View.GONE); // Don't show buttons !
        exercise_layout.addView(rlex);

        // Dual Handed ? Let's show the tap !
        if (dualHanded) {
            if (BuildConfig.DEBUG) Log.v(TAG, "Dualhanded, we show the tap");
            findViewById(R.id.LeftTap).setVisibility(View.VISIBLE);
        }

        // Starting the metronomes (metronomes are more than metronomes, they are the time keepers)
        newMetronomes();
    }

    @Override
    protected void onPause() {
        if (BuildConfig.DEBUG) Log.v(TAG, "TrainAct pausing");
        super.onPause();
    }

    @Override
    protected void onDestroy(){
        if (BuildConfig.DEBUG) Log.v(TAG, "TrainAct destroying");
        destroyMetronomes();
        super.onDestroy();
    }

    /**
     * The Metronome is more than a ticking device, it's the time master
     */
    protected abstract void newMetronomes();

    protected abstract void destroyMetronomes();

    /**
     * Set view counter.
     *
     * @param t the text to show
     */
    protected void setViewCounter(final String t){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView)findViewById(R.id.counter)).setText(t);
            }
        });
    }

    /**
     * ReSet the late display.
     *
     */
    protected void resetLateEarly(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView)findViewById(R.id.early)).setText("");
                ((TextView)findViewById(R.id.late)).setText("");
            }
        });
    }

    /**
     * Set the late display.
     *
     * @param i the lateness from 1 to infinity
     */
    protected void setLate(final int i){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView)findViewById(R.id.early)).setText("");
                if(i > 2) ((TextView)findViewById(R.id.late)).setText(">>>");
                else if(i > 1) ((TextView)findViewById(R.id.late)).setText(">>");
                else if(i > 0) ((TextView)findViewById(R.id.late)).setText(">");
            }
        });
    }

    /**
     * Set the early display.
     *
     * @param i the lateness from 1 to infinity
     */
    protected void setEarly(final int i){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView)findViewById(R.id.late)).setText("");
                if(i > 2) ((TextView)findViewById(R.id.early)).setText("<<<");
                else if(i > 1) ((TextView)findViewById(R.id.early)).setText("<<");
                else if(i > 0) ((TextView)findViewById(R.id.early)).setText("<");
            }
        });
    }


    /**
     * Set view note color.
     *
     * @param id    the id of the image
     * @param color the new color
     */
    protected void setViewNoteColor(final ImageView id, final int color){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                id.setColorFilter(color);
            }
        });
    }

    /**
     * Set progressbar.
     *
     * @param progress the current progress
     */
    protected void moveProgress(final double progress){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(progress < 0) {
                    progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#D41C1C"), PorterDuff.Mode.SRC_IN);
                }
                else {
                    progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#20B2AA"), PorterDuff.Mode.SRC_IN);
                }

                int newProgress = (int)(Math.max(((double)progressBar.getProgress()) + progress, 0));

                // will update the "progress" propriety of seekbar until it reaches progress
                ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", newProgress);
                animation.setDuration(100); // 0.5 second
                animation.setInterpolator(new DecelerateInterpolator());
                animation.start();

                //progressBar.setProgress((int)(Math.max(((double)progressBar.getProgress()) + progress, 0)));
            }
        });
    }

    /**
     * Red note.
     *
     * @param id the id
     */
    public void redNote(int id) {
        boolean right = true;

        // Getting the scroller to center played note:
        HorizontalScrollView hsv = exercise_layout.findViewById(R.id.rythm_scroll);

        int noteNbr;

        LinearLayout notesContainer;
        LinearLayout rContainer = (LinearLayout) exercise_layout.findViewById(R.id.rythm_container);

        if(right) {
            notesContainer = (LinearLayout) rContainer.getChildAt(0);
        }
        else {
            notesContainer = (LinearLayout) rContainer.getChildAt(1);
        }

        noteNbr = notesContainer.getChildCount();

        //Resetting all notes to black :
        for (int i = 0; i < noteNbr; i++) {
            setViewNoteColor((ImageView) notesContainer.getChildAt(i), Color.parseColor("#000000"));
        }

        // Actual note in red :
        if (BuildConfig.DEBUG) Log.v(TAG, "id: " + id + "Noteid: " + id);
        setViewNoteColor((ImageView) notesContainer.getChildAt(id), Color.parseColor("#D41C1C"));

        Rect rect = new Rect();
        notesContainer.getChildAt(id).getGlobalVisibleRect(rect);

        hsv.smoothScrollTo((int) (((double) id / (double) noteNbr) * (double) hsv.getMaxScrollAmount ()), 0);
    }

    /* onCLick actions */

    /**
     * Tap action
     *
     * @param sv the sv
     */
    public boolean onTouch(View sv, MotionEvent me) {
        // Which hand ?
        boolean rightTap = sv.getId() == R.id.RightTap;

        if(me.getActionMasked() == MotionEvent.ACTION_DOWN)
            tapAction(rightTap);


        return true;
    }

    protected abstract void tapAction(boolean right);
}
