package fr.u31.rythm;

import java.util.TimerTask;

/**
 * Created by ulysse on 13/06/2017.
 */

public class TickTask extends TimerTask {
    Metronome m;
    public TickTask(Metronome m) {
        super();
        this.m = m;
    }
    @Override
    public void run() {
        m.tick();
    }
}
