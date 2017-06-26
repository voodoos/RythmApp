package fr.u31.rythm;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.annotation.XmlRes;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ulysse on 26/06/2017.
 *
 * This helper class is in control on the Exercises database (a bunch of xml files)
 *
 */

public class Exercises {
    private static final String TAG = "Exercises";
    private static Exercises instance = new Exercises();

    private HashMap<Integer, Rythm> rythms = new HashMap<>();
    private HashMap<Integer, Exercise> exercises = new HashMap<>();

    private Exercises(){
    }

    static Exercises getInstance() { return instance; }
    HashMap<Integer, Exercise> getExercises() { return exercises; }

    void loadExercises(Context ctx) {
        //First we parse the Rythms file :
        loadRythms(ctx);

        // Then the exercises file :
        loadExercisesAux(ctx);
    }

    private void loadExercisesAux(Context ctx) {
        XmlResourceParser xpp = ctx.getResources().getXml(R.xml.data_exercises);
        try {
            int id = -1, bpm = -1, r_left = -1, r_right = -1;
            while (xpp.next() != XmlResourceParser.END_DOCUMENT) {
                int xppet = xpp.getEventType();
                if (xppet != XmlResourceParser.START_TAG) {
                    //if (BuildConfig.DEBUG) Log.v(TAG, "NONSTART: " + xpp.getName());
                    if (xppet == XmlResourceParser.END_TAG && xpp.getName().equals("Exercise")) {
                        if (BuildConfig.DEBUG)
                            Log.v(TAG, "NEW EXERCISE id: " + id + " rleft:"+ r_left+" rright:"+r_right);
                        // We've got an Exercise :
                        //String name = ctx.getResources().getString()
                        try {
                            if(r_left >= 0)
                                exercises.put(id, new DualHandedExercise("toto", bpm, rythms.get(r_right), rythms.get(r_left)));
                            else exercises.put(id, new Exercise("toto", bpm, rythms.get(r_right)));
                        } catch (BadExerciseException e) {
                            if (BuildConfig.DEBUG) Log.v(TAG, "Arg, apparently this is not a valid exercise");
                            e.printStackTrace();
                        }

                        //Reset !
                        id = -1; bpm = -1; r_left = -1; r_right = -1;
                    }

                    // If not starting a node, we continue parsing without the switch
                    continue;
                }
                switch (xpp.getName()) {
                    case "Exercises":
                        if (BuildConfig.DEBUG) Log.v(TAG, "XML EXERCISES");
                        break;
                    case "Exercise":
                        // Getting the template_signature :
                        id = Integer.parseInt(xpp.getAttributeValue(null, "id"));
                        bpm = Integer.parseInt(xpp.getAttributeValue(null, "bpm"));

                        if (BuildConfig.DEBUG)
                            Log.v(TAG, "XML EXERCISE id: " + id + " bpm:" + bpm);
                        break;
                    case "rythm":
                        String nt = xpp.nextText();
                        if(r_right >= 0)
                            r_left = Integer.parseInt(nt);
                        else r_right = Integer.parseInt(nt);

                        if (BuildConfig.DEBUG) Log.v(TAG, "XML RYTHMID" + nt);
                        break;
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadRythms(Context ctx) {
        if (BuildConfig.DEBUG) Log.v(TAG, "Loading exercises");

        //First we parse the Rythms file :
        XmlResourceParser xpp = ctx.getResources().getXml(R.xml.data_rythms);
        try {
            ArrayList<Integer> intervals = null;
            int id = 0, beats = 0, unit = 0;

            while (xpp.next() != XmlResourceParser.END_DOCUMENT) {
                int xppet = xpp.getEventType();
                if (xppet != XmlResourceParser.START_TAG) {
                    if (BuildConfig.DEBUG) Log.v(TAG, "NONSTART: "+xpp.getName());
                    if(xppet == XmlResourceParser.END_TAG && xpp.getName().equals("Rythm")) {
                        if (BuildConfig.DEBUG) Log.v(TAG, "NEW RYTHM id: "+id+" beats:"+beats+" unit:"+unit+" intervals:"+intervals.toString());
                        // We've got Rythm :
                        try {
                            rythms.put(id, new Rythm(beats, unit, intervals));
                        } catch (BadRythmException e) {
                            e.printStackTrace();

                            if (BuildConfig.DEBUG) Log.v(TAG, "Arg, apparently this is not a valid rythm");
                        }
                    }

                    // If not starting a node, we continue parsing without the switch
                    continue;
                }
                switch (xpp.getName()) {
                    case "Rythms":
                        if (BuildConfig.DEBUG) Log.v(TAG, "XML RYTHMS");
                        break;
                    case "Rythm":
                        // Emptying the intervalles, before reading the notes :
                        intervals = new ArrayList<>();
                        // Getting the template_signature :
                        id = Integer.parseInt(xpp.getAttributeValue(null, "id"));
                        beats = Integer.parseInt(xpp.getAttributeValue(null, "beats"));
                        unit = Integer.parseInt(xpp.getAttributeValue(null, "unit"));

                        if (BuildConfig.DEBUG) Log.v(TAG, "XML RYTHM id: "+id+" beats:"+beats+" unit:"+unit);
                        break;
                    case "note":
                        // Adding each note to the interval list :
                        String nt = xpp.nextText();
                        intervals.add(Integer.parseInt(nt));
                        if (BuildConfig.DEBUG) Log.v(TAG, "XML NOTE"+nt);
                        break;
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
