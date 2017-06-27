package fr.u31.rythm;

import android.app.Activity;
import android.util.Log;
import android.util.Pair;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    int getBeats() {
        return signature.first;
    }

    int getUnit() {
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

    LinearLayout getLayout(Activity act, ViewGroup root) {
        LinearLayout lin = (LinearLayout) act.getLayoutInflater().inflate(R.layout.template_rythm, root, false);

        // Adding notes :
        int idx = index, i = 0;
        restart();
        do {
            // Adding one note :
            int n = currentAndForward();

            //Creating the Imageview based on the template_note
            ImageView note = (ImageView) act.getLayoutInflater().inflate(R.layout.template_note, lin, false);

            // If not the first note, set it's position relatively to the previous one :
            /*RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) note.getLayoutParams();
            if (previousNote != null) {
                lp.addRule(RelativeLayout.RIGHT_OF, previousNote.getId());
            }
            else {
                lp.addRule(RelativeLayout.RIGHT_OF, R.id.template_signature);
            }
            note.setLayoutParams(lp);*/

            note.setId(++i);

            if(n == 1) note.setImageResource(R.drawable.ic_ronde);
            else if(n == 2) note.setImageResource(R.drawable.ic_blanche);
            else if(n == 4) note.setImageResource(R.drawable.ic_noire);
            else if (n == 8) note.setImageResource(R.drawable.ic_croche);
            else if (n == 16) note.setImageResource(R.drawable.ic_double_croche);
            else if (n == 32) note.setImageResource(R.drawable.ic_triple_croche);

            if (BuildConfig.DEBUG) Log.v(TAG, "Rlayout: "+String.valueOf(n));
            lin.addView(note);
            //previousNote = note;
            //if(notesViews != null) notesViews.add(note);
        } while (!isFirst());
        index = idx;

        return lin;
    }
}
