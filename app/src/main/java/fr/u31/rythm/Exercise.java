package fr.u31.rythm;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by ulysse on 26/06/2017.
 */

public class Exercise {
    private static final String TAG = "Exercise";

    private String name;
    private int id, bpm, beats, unit;
    private Rythm r_right;

    Exercise(int id, String name, int bpm, Rythm r) {
        this.id = id;
        this.name = name;
        this.bpm = bpm;
        this.beats = r.getBeats();
        this.unit = r.getUnit();
        this.r_right = r;
    }

    Rythm getRightRythm() {
        return r_right;
    }

    int getId() { return id; }

    boolean isDualHanded() { return false; }

    void reset() {
        r_right.restart();
    }

    RelativeLayout getLayout(LayoutInflater li, ViewGroup root) {
        if (BuildConfig.DEBUG) Log.v(TAG, "ExGetLayout");

        RelativeLayout lin = (RelativeLayout) li.inflate(R.layout.template_exercise, root, false);

        // Setting signature :
        LinearLayout sig =  lin.findViewById(R.id.signature);//(LinearLayout) lin.getChildAt(0);
        ((TextView) sig.getChildAt(0)).setText(String.valueOf(beats));
        ((TextView) sig.getChildAt(1)).setText(String.valueOf(unit));

        //Getting rythm container :
        LinearLayout rc = lin.findViewById(R.id.rythm_container);

        // Getting first Rythm :
        LinearLayout rright = r_right.getLayout(li, rc);

        rc.addView(rright);

        return lin;
    }
}
