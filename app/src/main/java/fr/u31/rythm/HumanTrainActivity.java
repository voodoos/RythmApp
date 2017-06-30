package fr.u31.rythm;

import android.graphics.Color;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

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

        if(getProgress() >= 100) {
            KonfettiView kv = findViewById(R.id.viewKonfetti);
            kv.build()
                    .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                    .setDirection(0.0, 359.0)
                    .setSpeed(1f, 5f)
                    .setFadeOutEnabled(true)
                    .setTimeToLive(2000L)
                    .addShapes(Shape.RECT, Shape.CIRCLE)
                    .addSizes(new Size(12, 12f))
                    .setPosition(-50f, kv.getWidth() + 50f, -50f, -50f)
                    .stream(300, 5000L);
        }
    }
}
