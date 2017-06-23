package fr.u31.rythm;

import android.util.Log;

import java.util.Timer;

/**
 * Created by ulysse on 13/06/2017.
 *
 * Metronome can be human or not :
 * - Non-human metronome = classical metronome
 * - Human metronome allows one to use it's own rythm
 */

class Metronome extends AbstractMetronome {
    private static final String TAG = "MetronomeC";
    private int t;
    private Timer timer;
    private double tempo = 90;

    Metronome(Rythm r, AbstractTrainActivity a) {
        super(r, a);
        zero();
        this.activity = a;
        this.r = r;

        timer = new Timer();
        tick();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // We need to cancel timer when destroying !
        if (BuildConfig.DEBUG) Log.v(TAG, "Stopping timer");
        stop();
    }


    private void zero() {
        t = 0;
    }
    private void nextTick(double delay){
        if (BuildConfig.DEBUG) Log.v(TAG, "delay: "+String.valueOf(delay));
        timer.schedule(new TickTask(this), (long)(delay*tempo*50));
    }

    private void stop(){
        timer.cancel();
    }

    public double tick() {
        t++;

        // Fancy sound
        //if(r.is_time()) mp.start();

        int duration = r.currentAndForward();

        if (BuildConfig.DEBUG) Log.v(TAG, "Duration: "+String.valueOf(duration));

        activity.setViewCounter(String.valueOf(duration));
        activity.redNote(r.index());


        if (BuildConfig.DEBUG) Log.v(TAG, "delay: "+String.valueOf(1./(double)duration));
        nextTick(1./(double)duration);

        return 0D;
    }
}
