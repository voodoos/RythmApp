package fr.u31.rythm;

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
}
