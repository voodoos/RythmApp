package fr.u31.rythm;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by thevo on 23/06/2017.
 *
 * The PingMachine is a metronome ticking at a regular rate.
 * In fact it's a standard metronome.
 *
 */

public class PingMachine extends AbstractMetronome {
    private int bpm;
    private Timer timer;

    PingMachine(int times, int unit, int bpm, ExerciceFragment ata) throws BadRythmException {
        super(new Exercise(-1, "", 96,constructRythm(unit)), ata);

        this.bpm = bpm;
        timer = new Timer();

        timer.scheduleAtFixedRate(new TickTask(this), 0L, (long) bpm);
    }

    private static Rythm constructRythm(int unit) throws BadRythmException {
        ArrayList<Integer> al = new ArrayList<>();
        for(int i=0; i < unit; i++) al.add(unit);
        return new Rythm(unit, unit, al);
    }
    @Override
    public double tick() {
        mp.start();
        return 0;
    }
}
