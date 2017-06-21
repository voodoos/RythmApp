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

    public HumanMetronome(Rythm r, AbstractTrainActivity a) {

        super(r, a);

        // We average the tempo other 5 samples :
        previousTempo = new Average(5);
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

            if(previousTempo.getNbrOfSamples() > 0) {
                double deltaTempo = Math.abs(tempo - previousTempo.getAverage());
                int progress;
                boolean late = tempo - previousTempo.getAverage() > 0;

                if (BuildConfig.DEBUG) Log.v(TAG, "Tempo : " + tempo + "Delta : " + deltaTempo);

                if (deltaTempo < 100) {
                    activity.setViewCounter("GOOD");
                    progress = 10;
                    activity.resetLateEarly();
                } else if (deltaTempo < 300) {
                    activity.setViewCounter("BOF");
                    progress = 5;
                    if (late) activity.setLate(1);
                    if (!late) activity.setEarly(1);
                } else {
                    activity.setViewCounter("BAD");
                    progress = -10;
                    if (late) activity.setLate(2);
                    if (!late) activity.setEarly(2);
                }

                activity.moveProgress(progress);
            }

            previousTempo.addSample(tempo);
        }



        activity.redNote(r.index());

        previousNote =  r.currentAndForward();
        previousTime = tapTime;

    }
}
