package fr.u31.rythm;

/**
 * Created by thevo on 17/06/2017.
 */

public class HumanTrainActivity extends AbstractTrainActivity {
    private static final String TAG = "HumanTrainAct";
    @Override
    protected void newMetronomes() {
        m_right = new HumanMetronome(ex.getRightRythm(), this);
        if(dualHanded) m_left = new HumanMetronome(((DualHandedExercise) ex).getLeftRythm(), this);
    }

    @Override
    protected void destroyMetronomes() {
        m_right.onDestroy();
        if(dualHanded) m_left.onDestroy();
    }

    @Override
    protected void tapAction(boolean right) {
        Score.scdata scd = null;

        if (right) {
            // Tick return the delta, score evaluates it
            scd = score.evaluate(m_right.tick());
        }
        else if(dualHanded) {
            // Tick return the delta, score evaluates it
            scd = score.evaluate(m_left.tick());
        }

        if(scd != null) {
            setViewCounter(scd.message);
            moveProgress(scd.progres);

            resetLateEarly();
            if(scd.lateness > 0)
                setLate(Math.abs(scd.lateness));
            else
                setEarly(Math.abs(scd.lateness));
        }
    }
}
