package fr.u31.rythm;

import android.content.res.Resources;
import android.util.Log;

/**
 * Created by ulysse on 21/06/2017.
 */
enum Difficulty {
    DRUNK(0), BEGINNER(1), INTERMEDIATE(2), EXPERT(3), ROBOT(4);

    private final int value;

    private Difficulty(int value) {
        this.value = value;
    }

    public static Difficulty which(int i) {
        if(DRUNK.getValue() == i) return DRUNK;
        if(BEGINNER.getValue() == i) return BEGINNER;
        if(INTERMEDIATE.getValue() == i) return INTERMEDIATE;
        if(EXPERT.getValue() == i) return EXPERT;
        return ROBOT;
    }

    public int getValue() {
        return value;
    } };
//enum Lateness {VERY_EARLY, EARLY, ALLRIGHT, LATE, VERY_LATE}

public class Score {
    private static final String TAG = "Score";
    class scdata { public String message; public double progres; public int lateness; }

    int[] p, g, b;
    Difficulty d;
    double score = 0;

    Score(Difficulty d, Resources r){
        this.d = d;

        p = r.getIntArray(R.array.difficulty_levels_perfect_treshold);
        g = r.getIntArray(R.array.difficulty_levels_good_treshold);
        b = r.getIntArray(R.array.difficulty_levels_bof_treshold);

        if (BuildConfig.DEBUG) Log.v(TAG, "DIfficulty : " + String.valueOf(d));
    }

    public scdata evaluate(double delta) {

        String message;
        double progress;
        int lateness = 0;

        boolean late = delta < 0;
        delta = Math.abs(delta);

        if (delta < p[d.getValue()]) {
            message = "PERFECT";
            progress = 15;
        }
        else if (delta < g[d.getValue()]) {
            message = "GOOD";
            progress = 10;
            lateness = 1;
        } else if (delta < b[d.getValue()]) {
            message =" BOF";
            progress = 5;
            lateness = 2;
        } else {
            message = "BAD";
            progress = -10;
            lateness = 3;
        }

        if (!late) lateness *= -1;

        scdata scd = new scdata();
        scd.message = message;
        scd.progres = progress;
        scd.lateness = lateness;
        return scd;
    }
}
