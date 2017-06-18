package fr.u31.rythm;

/**
 * Created by thevo on 17/06/2017.
 */

public class HumanTrainActivity extends AbstractTrainActivity {
    @Override
    protected void newMetronomes() {
        m_right = new HumanMetronome(r_right, this);
        if(dualHanded) m_left = new HumanMetronome(r_left, this);
    }

    @Override
    protected void destroyMetronomes() {
        m_right.onDestroy();
        if(dualHanded) m_left.onDestroy();
    }

    @Override
    protected void tapAction(boolean right) {
        if (right) m_right.tick();
        else if(dualHanded) m_left.tick();
    }
}
