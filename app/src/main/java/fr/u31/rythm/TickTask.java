package fr.u31.rythm;

import java.util.TimerTask;

/**
 * Created by ulysse on 13/06/2017.
 */

public class TickTask extends TimerTask {
    AbstractMetronome m;
    public TickTask(AbstractMetronome m) {
        super();
        this.m = m;
    }
    @Override
    public void run() {
        m.tick();
    }
}
