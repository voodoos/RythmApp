package fr.u31.rythm;

import android.os.Bundle;

/**
 * Created by ulysse on 16/06/2017.
 */

public class TrainActivity extends AbstractTrainActivity {

    @Override
    protected void newMetronomes() {
        m_right = new Metronome(r_right, this);
        if(dualHanded) m_left = new Metronome(r_left, this);

    }

    @Override
    protected void destroyMetronomes() {
        m_right.onDestroy();
        if(dualHanded) m_left.onDestroy();

    }

    @Override
    protected void tapAction(boolean right, long time) {

    }


}
