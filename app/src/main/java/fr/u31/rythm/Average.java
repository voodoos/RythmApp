package fr.u31.rythm;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ulysse on 21/06/2017.
 */

class Average {
    private static final String TAG = "Average";

    private ArrayList<Double> samples;
    private int index = 0;
    private int nbrOfSamples = 0;
    private int maxSamples;

    Average(int numberOfSamples) {
        maxSamples = numberOfSamples;
        samples = new ArrayList<>(maxSamples);
        for(int i = 0; i < maxSamples; i++) samples.add(0D);
    }

    int getNbrOfSamples() {
        return nbrOfSamples;
    }

    void addSample(double s) {
        samples.set(index, s);

        if(index == maxSamples - 1) index = 0;
        else {
            index++;
            nbrOfSamples = Math.min(nbrOfSamples + 1, maxSamples);
        }
    }

    double getAverage () {
        double sum = 0;
        for (double s: samples) {
            sum += s;
        }
        if (BuildConfig.DEBUG) Log.v(TAG, "Sum : "+sum+" NbrSamples : "+nbrOfSamples);
        return sum / nbrOfSamples;
    }
}
