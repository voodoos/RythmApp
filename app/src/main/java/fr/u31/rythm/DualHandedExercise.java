package fr.u31.rythm;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by ulysse on 26/06/2017.
 */

public class DualHandedExercise extends Exercise {
    private static final String TAG = "DualHandedExercise";

    private Rythm r_left;

    DualHandedExercise(int id, String name, int bpm, Rythm r_right, Rythm r_left) throws BadExerciseException {
        super(id, name, bpm, r_right);

        // Checking that Rythms' signatures are coherent :
        if((r_left.getBeats() != r_right.getBeats()) || (r_left.getUnit() != r_right.getUnit()))
            throw new BadExerciseException();

        this.r_left = r_left;
    }

    Rythm getLeftRythm() {
        return r_left;
    }

    @Override
    boolean isDualHanded() { return true; }

    @Override
    void reset() {
        super.reset();
        r_left.restart();
    }

    RelativeLayout getLayout(LayoutInflater li, ViewGroup root) {
        if (BuildConfig.DEBUG) Log.v(TAG, "DHEx GetLayout");

        RelativeLayout lin = super.getLayout(li, root);

        //Getting rythm container :
        LinearLayout rc = lin.findViewById(R.id.rythm_container);

        // Getting first Rythm :
        LinearLayout rleft = r_left.getLayout(li, rc);

        rc.addView(rleft);

        return lin;
    }
}
