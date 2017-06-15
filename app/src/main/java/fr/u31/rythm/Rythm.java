package fr.u31.rythm;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by ulysse on 14/06/2017.
 */

public class Rythm {
    private static final String TAG = "RythmC";
    Pair<Integer, Integer> signature;
    ArrayList<Integer> intervals;
    ListIterator<Integer> cursor;

    public Rythm (int times, int unity, ArrayList<Integer> intervals) {
        signature = new Pair<>(times, unity);
        if(setIntervals(intervals))
            restart();
    }

    public Rythm (Pair<Integer, Integer> p, ArrayList<Integer> intervals) {
        this(p.first, p.second, intervals);
    }

    public void restart() {
        cursor = intervals.listIterator();
    }

    public boolean hasNext() {
        return cursor.hasNext();
    }
    public int next() {
        if(cursor.hasNext())
            return cursor.next();
        else {
            restart();
            return cursor.next();
        }
    }

    public boolean setIntervals(ArrayList<Integer> intervals) {
        //Before changing intervals, we check it's ok :
        if(Rythm.checkIntervals(signature, intervals)) {
            this.intervals = intervals;
            return true;
        }
        return false;
    }

    public static boolean checkIntervals(Pair<Integer, Integer> sig, ArrayList<Integer> inter) {
        // Ot check that a list of intervals is correct we sum the inverses and check that this is equal to the times unit multiplied by the number of times
        ListIterator<Integer> it = inter.listIterator();
        double sum = 0;
        while(it.hasNext()) {
            int n = it.next();
            sum += 1. / (double)n;//n * (sig.second/n);
        }
        Log.v(TAG, String.valueOf(sum));
        Log.v(TAG, String.valueOf((double)sig.first / (double)sig.second));

        return (double)sig.first / (double)sig.second == sum;
    }
}
