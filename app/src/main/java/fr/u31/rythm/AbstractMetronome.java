package fr.u31.rythm;

import android.media.MediaPlayer;

/**
 * Created by ulysse on 16/06/2017.
 */
abstract class AbstractMetronome {
    AbstractTrainActivity activity;
    MediaPlayer mp;
    protected Rythm r;

    AbstractMetronome(Rythm r, AbstractTrainActivity a) {
        this.r = r;
        this.activity = a;
        mp = MediaPlayer.create(a, R.raw.click);
    }

    public void onDestroy() {
        mp.stop();
        mp.reset();
        mp.release();
    }

    public abstract double tick();
}
