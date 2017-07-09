package fr.u31.rythm;

import android.util.Log;

import java.util.Timer;

/**
 * Created by ulysse on 13/06/2017.
 *
 * PlayMetronome is a simple metronome used to play a rythm.
 * It's used to play a rythm when the user clicks the "ear" icon
 *
 */

class PlayMetronome extends AbstractMetronome {
    private static final String TAG = "MetronomeC";
    private boolean isPlaying;
    private Timer timer;
    private double tempo = 90;


    PlayMetronome(Exercise ex, ExerciceFragment ef) {
        super(ex, ef);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // We need to cancel timer when destroying !
        if (BuildConfig.DEBUG) Log.v(TAG, "Stopping timer");
        stop();
    }

    private void nextTick(double delay){
        if (BuildConfig.DEBUG) Log.v(TAG, "delay: "+String.valueOf(delay));
        // TODO normalize tempo...(everywhere)
        timer.schedule(new TickTask(this), (long)(delay*tempo*50));
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void start() {
        if(!isPlaying) {
            timer = new Timer(); // We always need a timer after cancelling another one
            ex.getRightRythm().restart();
            tick();
            isPlaying = true;
        }
    }

    public void stop(){
        if(isPlaying) {
            timer.cancel();
            timer = null;
            isPlaying = false;
        }
    }

    public double tick() {
        // Fancy sound
        if(ex.getRightRythm().is_time()) mp.start();

        int current_idx = ex.getRightRythm().index();
        int duration = ex.getRightRythm().currentAndForward();

        if (BuildConfig.DEBUG) Log.v(TAG, "Duration: "+String.valueOf(duration));

        exFragment.redNote(current_idx);


        if (BuildConfig.DEBUG) Log.v(TAG, "delay: "+String.valueOf(1./(double)duration));
        nextTick(1./(double)duration);

        return 0D;
    }
}
