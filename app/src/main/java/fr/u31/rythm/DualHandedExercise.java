package fr.u31.rythm;

/**
 * Created by ulysse on 26/06/2017.
 */

public class DualHandedExercise extends Exercise {
    private static final String TAG = "DualHandedExercise";

    private Rythm r_left;

    DualHandedExercise(String name, int bpm, Rythm r_right, Rythm r_left) throws BadExerciseException {
        super(name, bpm, r_right);

        // Checking that Rythms' signatures are coherent :
        if((r_left.getBeats() != r_right.getBeats()) || (r_left.getUnit() != r_right.getUnit()))
            throw new BadExerciseException();

        this.r_left = r_left;
    }

    Rythm getLeftRythm() {
        return r_left;
    }
}
