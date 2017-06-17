package fr.u31.rythm;

/**
 * Created by ulysse on 16/06/2017.
 */

public class HumanMetronome extends AbstractMetronome {

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
        // tack
    }
}
