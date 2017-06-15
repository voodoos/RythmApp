package fr.u31.rythm;

import android.app.Activity;
import android.util.Log;
import android.view.ViewDebug;
import android.widget.TextView;

import java.util.Timer;

/**
 * Created by ulysse on 13/06/2017.
 */

public class Metronome {
    private static final String TAG = "MetronomeC";
    private int t;
    private Timer timer;
    private TrainActivity activity;
    private Rythm r;
    private double tempo = 90;

    public Metronome(Rythm r, TrainActivity a) {
        super();

        zero();
        this.activity = a;
        this.r = r;

        timer = new Timer();
        tick();

    }

    public void zero() {
        t = 0;
    }
    public void nextTick(double delay){
        Log.v(TAG, "delay: "+String.valueOf(delay));
        timer.schedule(new TickTask(this), (long)(delay*tempo*100));
    }

    public void stop(){
        timer.cancel();
    }

    public void tick() {
        t++;

        int duration = r.next();

        Log.v(TAG, "Duration: "+String.valueOf(duration));

        activity.setCounter(String.valueOf(duration));

        Log.v(TAG, "delay: "+String.valueOf(1./(double)duration));
        nextTick(1./(double)duration);
    }
}
