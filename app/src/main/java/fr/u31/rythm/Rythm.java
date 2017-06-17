package fr.u31.rythm;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by ulysse on 14/06/2017.
 */

class Rythm {
    private static final String TAG = "RythmC";
    private Pair<Integer, Integer> signature;
    private ArrayList<Integer> intervals;
    private ListIterator<Integer> cursor;
    private int index = 0;

    Rythm (int times, int unity, ArrayList<Integer> intervals) {
        signature = new Pair<>(times, unity);
        if(setIntervals(intervals))
            restart();
    }

    Rythm (Pair<Integer, Integer> p, ArrayList<Integer> intervals) {
        this(p.first, p.second, intervals);
    }

    void restart() {
        index = 0;
        cursor = intervals.listIterator();
    }

    boolean hasNext() {
        return cursor.hasNext();
    }
    int index() {
        return index;
    }
    int next() {
        if (cursor.hasNext()){
            index++;
            return cursor.next();
    }
        else {
            restart();
            return cursor.next();
        }
    }

    public boolean is_time() {
        double sum = 0;

        for (int i = 0; i < index; i++) sum += intervals.get(i);

        return (sum % 2) == 0;
    }

    private boolean setIntervals(ArrayList<Integer> intervals) {
        //Before changing intervals, we check it's ok :
        if(Rythm.checkIntervals(signature, intervals)) {
            this.intervals = intervals;
            return true;
        }
        return false;
    }

    static boolean checkIntervals(Pair<Integer, Integer> sig, ArrayList<Integer> inter) {
        // Ot check that a list of intervals is correct we sum the inverses and check that this is equal to the times unit multiplied by the number of times
        ListIterator<Integer> it = inter.listIterator();
        double sum = 0;
        while(it.hasNext()) {
            int n = it.next();
            sum += 1. / (double)n;//n * (sig.second/n);
        }
        if (BuildConfig.DEBUG) Log.v(TAG, String.valueOf(sum));
        if (BuildConfig.DEBUG) Log.v(TAG, String.valueOf((double)sig.first / (double)sig.second));

        return (double)sig.first / (double)sig.second == sum;
    }
}
