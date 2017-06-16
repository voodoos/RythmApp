package fr.u31.rythm;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * The type Train activity.
 */
public abstract class AbstractTrainActivity extends AppCompatActivity {
    private static final String TAG = "TrainAct";

    private RelativeLayout l_left, l_right;

    protected boolean dualHanded;

    protected AbstractMetronome m_left, m_right;
    protected Rythm r_left, r_right;
    protected ArrayList<ImageView> rightNotesViews, leftNotesViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        Intent intent = getIntent();
        dualHanded = intent.getBooleanExtra("dualHanded", false);

        // Adding the ActionBar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        // Getting pointer to the rythm layout :
        l_right = (RelativeLayout) findViewById(R.id.rl_rythm);
        rightNotesViews = new ArrayList<>();
        leftNotesViews = new ArrayList<>();

        ArrayList<Integer> test = new ArrayList<>();
        //test.add(4);
        //test.add(4);
        test.add(4);
        test.add(8);
        test.add(8);
        r_right = new Rythm(new Pair<>(2, 4), test);
        if (dualHanded) {}

        drawRythm(r_right, rightNotesViews, l_right);
        if (dualHanded) drawRythm(r_left, leftNotesViews, l_left);

        newMetronomes();
    }


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
     * That function draw a Rythm and store notes views in the corresponding NotesViews array
     */
    private void drawRythm(Rythm r, ArrayList<ImageView> notesViews, RelativeLayout layout) {
        r.restart();
        notesViews.clear();

        ImageView previousNote = null;
        int i = 0;

        while(r.hasNext()) {
            // Adding one note :
            int n = r.next();

            // Creating the layout params to link the note to the previous one
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(50, RelativeLayout.LayoutParams.MATCH_PARENT);
            if (previousNote != null) lp.addRule(RelativeLayout.RIGHT_OF, previousNote.getId());

            //Creating the Imageview
            ImageView note = new ImageView(this);
            note.setLayoutParams(lp);
            note.setId(++i);

            if(n == 4) note.setImageResource(R.drawable.ic_noire);
            else if (n == 8) note.setImageResource(R.drawable.ic_croche);

            layout.addView(note);
            previousNote = note;
            notesViews.add(note);
        }
    }

    /**
     * Red note.
     *
     * @param id the id
     */
    public void redNote(int id) {
        //Resetting all notes to black :
        for (ImageView i: rightNotesViews) {
            setViewNoteColor(i, Color.parseColor("#000000"));
        }

        // Actual note in red :
        if (BuildConfig.DEBUG) Log.v(TAG, "id: " + id + "Viewid: " + rightNotesViews.get(id));
        setViewNoteColor(rightNotesViews.get(id), Color.parseColor("#D41C1C"));
    }

    /* onCLick actions */

    /**
     * Tap action
     *
     * @param sv the sv
     */
    public void tap(View sv) {
        // Which hand ?
        boolean rightTap = sv.getId() == R.id.RightTap;

        // Getting time
        long current = System.currentTimeMillis();

        tapAction(rightTap, current);
    }

    protected abstract void tapAction(boolean right, long time);
}
