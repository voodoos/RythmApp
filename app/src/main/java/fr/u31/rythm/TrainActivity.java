package fr.u31.rythm;

import android.content.Intent;
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

public class TrainActivity extends AppCompatActivity {
    private static final String TAG = "TrainAct";
    private RelativeLayout layout;
    private Metronome m;
    private Rythm r;
    long prev = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        Intent intent = getIntent();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        // Getting pointer to the rythm layout :
        layout = (RelativeLayout) findViewById(R.id.rl_rythm);

        ArrayList<Integer> test = new ArrayList<Integer>();
        //test.add(4);
        //test.add(4);
        test.add(4);
        test.add(8);
        test.add(8);
        r = new Rythm(new Pair<>(2, 4), test);

        m = new Metronome(r, this);

        drawRythm();
    }

    protected void onDestroy(){
        super.onDestroy();
        Log.v(TAG, "TrainAct destroyed");

        m.stop();
    }

    public void setCounter(final String t){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView)findViewById(R.id.counter)).setText(t);
            }
        });
    }

    public void drawRythm() {
        r.restart();

        ImageView previousNote = null;
        int i = 0;

        while(r.hasNext()) {
            // Adding one note :
            int n = r.next();

            // Creating de layout params
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(50, RelativeLayout.LayoutParams.MATCH_PARENT);
            if (previousNote != null) lp.addRule(RelativeLayout.RIGHT_OF, previousNote.getId());

            ImageView note = new ImageView(this);
            note.setLayoutParams(lp);
            note.setId(++i);

            if(n == 4) note.setImageResource(R.drawable.ic_noire);
            else if (n == 8) note.setImageResource(R.drawable.ic_croche);

            layout.addView(note);
            previousNote = note;
        }
    }

    /* onCLick actions */

    public void tap(View sv) {
        // Which hand ?
        boolean rightTap = sv.getId() == R.id.RightTap;

        long current = System.currentTimeMillis();
        long delta = current - prev;
        prev = current;

        // Getting time
        Log.v(TAG, String.valueOf(delta));

    }
}
