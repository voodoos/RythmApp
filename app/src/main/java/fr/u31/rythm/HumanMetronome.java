package fr.u31.rythm;

import android.util.Log;

/**
 * Created by ulysse on 16/06/2017.
 */

public class HumanMetronome extends AbstractMetronome {
    private static final String TAG = "HumanMetronome";

    private long previousTime = 0L;
    private int previousNote = 0;
    private Average previousTempo;

    public HumanMetronome(Exercise ex, ExerciceFragment ef) {

        super(ex, ef);

        // We average the tempo other 4 samples :
        previousTempo = new Average(4);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // nothing to do...
    }

    @Override
    public double tick() {
        double ret = 0;
        long tapTime = System.currentTimeMillis();

        double tempo;
        if(previousTime != 0 && previousNote != 0) {
            long delta = tapTime - previousTime;
            tempo = delta*previousNote;

            if(previousTempo.getNbrOfSamples() > 0) {
                double deltaTempo = tempo - previousTempo.getAverage();

                if (BuildConfig.DEBUG) Log.v(TAG, "Tempo : " + tempo + "Avg tempo : "+ previousTempo.getAverage() + "Delta : " + deltaTempo);


                ret = deltaTempo;
            }

            previousTempo.addSample(tempo);
        }


        if (BuildConfig.DEBUG) Log.v(TAG, "Redding");

        exFragment.redNote(ex.getRightRythm().index());

        previousNote =  ex.getRightRythm().currentAndForward();
        previousTime = tapTime;

        return ret;

    }
}
