package fr.u31.rythm;

import android.os.Bundle;
import android.util.Log;

/**
 * Created by ulysse on 16/06/2017.
 */

public class TrainActivity extends AbstractTrainActivity {
    private static final String TAG = "TrainAct";

    private PingMachine pm;

    @Override
    protected void newMetronomes() {
        m_right = new Metronome(r_right, this);
        if(dualHanded) m_left = new Metronome(r_left, this);

        try {
            pm = new PingMachine(4, 4, 960, this);
        }
        catch (BadRythmException e) {
            if (BuildConfig.DEBUG) Log.v(TAG, "Arrrg. bad rythm.");
        }
    }

    @Override
    protected void destroyMetronomes() {
        m_right.onDestroy();
        if(dualHanded) m_left.onDestroy();

    }

    @Override
    protected void tapAction(boolean right) {

    }


}
