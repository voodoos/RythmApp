package fr.u31.rythm;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ulysse on 26/06/2017.
 */

public class Exercise {
    private static final String TAG = "Exercise";

    private String name;
    private int bpm, beats, unit;
    private Rythm r_right;

    Exercise(String name, int bpm, Rythm r) {
        this.name = name;
        this.bpm = bpm;
        this.beats = r.getBeats();
        this.unit = r.getUnit();
        this.r_right = r;
    }

    Rythm getRightRythm() {
        return r_right;
    }

    LinearLayout getLayout(Activity act, ViewGroup root) {
        if (BuildConfig.DEBUG) Log.v(TAG, "ExGetLayout");

        LinearLayout lin = (LinearLayout) act.getLayoutInflater().inflate(R.layout.template_exercise, root, false);

        // Setting signature :
        LinearLayout sig =  lin.findViewById(R.id.signature);//(LinearLayout) lin.getChildAt(0);
        ((TextView) sig.getChildAt(0)).setText(String.valueOf(beats));
        ((TextView) sig.getChildAt(1)).setText(String.valueOf(unit));

        //Getting rythm container :
        LinearLayout rc = lin.findViewById(R.id.rythm_container);

        // Getting first Rythm :
        LinearLayout rright = r_right.getLayout(act, rc);

        rc.addView(rright);

        return lin;
    }
}
