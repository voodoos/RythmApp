package fr.u31.rythm;

/**
 * Created by ulysse on 16/06/2017.
 */
abstract class AbstractMetronome {
    AbstractTrainActivity activity;
    protected Rythm r;

    AbstractMetronome(Rythm r, AbstractTrainActivity a) {
        this.r = r;
        this.activity = a;
    }

    public abstract void onDestroy();

    public abstract void tick();
}
