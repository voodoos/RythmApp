package fr.u31.rythm;

import android.media.MediaPlayer;

/**
 * Created by ulysse on 16/06/2017.
 */
abstract class AbstractMetronome {
    ExerciceFragment exFragment;
    MediaPlayer mp;
    protected Rythm r;

    AbstractMetronome(Rythm r, ExerciceFragment ef) {
        this.r = r;
        this.exFragment = ef;
        mp = MediaPlayer.create(ef.getActivity(), R.raw.click);
    }

    public void onDestroy() {
        // Closing the media player
        // TODO Check that's correct...
        mp.stop();
        mp.reset();
        mp.release();
    }

    public abstract double tick();
}
