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
    private int index = 0;

    Rythm (int times, int unity, ArrayList<Integer> intervals) throws BadRythmException {
        signature = new Pair<>(times, unity);

        setIntervals(intervals);
        restart();
    }

    Rythm (Pair<Integer, Integer> p, ArrayList<Integer> intervals) throws BadRythmException {
        this(p.first, p.second, intervals);
    }

    int getDuree() {
        return signature.first;
    }

    int getUnite() {
        return signature.second;
    }

    void restart() {
        index = 0;
    }

    boolean isFirst() {
        return index == 0;
    }
    boolean hasNext() {
        return index < intervals.size() - 1;
    }

    int index() { return index; };

    void forward() {
        // Looping !
        index = (index + 1) % intervals.size();
    }

    void backward() {
        if(index > 0) index--;
        else index = intervals.size() - 1;
    }

    int currentAndForward() {
        int now = intervals.get(index);
        forward();
        return now;
    }

    int pickPrevious() {
        backward();
        return currentAndForward();
    }

    public boolean is_time() {
        double sum = 0;

        for (int i = 0; i < index; i++) sum += intervals.get(i);

        return (sum % 2) == 0;
    }

    private void setIntervals(ArrayList<Integer> intervals) throws BadRythmException {
        //Before changing intervals, we check it's ok :
        Rythm.checkIntervals(signature, intervals);
            this.intervals = intervals;
    }

    static void checkIntervals(Pair<Integer, Integer> sig, ArrayList<Integer> inter) throws BadRythmException {
        // Ot check that a list of intervals is correct we sum the inverses and check that this is equal to the times unit multiplied by the number of times
        ListIterator<Integer> it = inter.listIterator();
        double sum = 0;
        while(it.hasNext()) {
            int n = it.next();
            sum += 1. / (double)n;//n * (sig.second/n);
        }
        if (BuildConfig.DEBUG) Log.v(TAG, String.valueOf(sum));
        if (BuildConfig.DEBUG) Log.v(TAG, String.valueOf((double)sig.first / (double)sig.second));

        if ( !((double)sig.first / (double)sig.second == sum))  throw new BadRythmException();
    }
}
