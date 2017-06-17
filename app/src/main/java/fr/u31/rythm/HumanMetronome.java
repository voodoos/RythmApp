package fr.u31.rythm;

import android.util.Log;

/**
 * Created by ulysse on 16/06/2017.
 */

public class HumanMetronome extends AbstractMetronome {
    private static final String TAG = "HumanMetronome";

    private long previousTime = 0L;
    private int previousNote = 0;
    private double previousTempo = 0D;

    public HumanMetronome(Rythm r, AbstractTrainActivity a) {
        super(r, a);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // nothing to do...
    }

    @Override
    public void tick() {

        long tapTime = System.currentTimeMillis();

        double tempo = 0;
        if(previousTime != 0 && previousNote != 0) {
            long delta = tapTime - previousTime;
            tempo = delta*previousNote;

            double deltaTempo = Math.abs(tempo - previousTempo);

            if (BuildConfig.DEBUG) Log.v(TAG, "Tempo : " + tempo + "Delta : " + deltaTempo);
            if(deltaTempo < 100) activity.setViewCounter("GOOD");
            else if(deltaTempo < 300) activity.setViewCounter("BOF");
            else activity.setViewCounter("BAD");
        }



        activity.redNote(r.index());

        previousNote =  r.next();
        previousTime = tapTime;
        previousTempo = tempo;

    }
}
