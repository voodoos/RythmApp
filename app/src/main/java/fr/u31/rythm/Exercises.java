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

    private Exercises(){
    }

    public static Exercises getInstance() { return instance; }

    void loadExercises(Context ctx) {
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
                    continue;
                }
                switch (xpp.getName()) {
                    case "Rythms":
                        if (BuildConfig.DEBUG) Log.v(TAG, "XML RYTHMS");
                        break;
                    case "Rythm":
                        // Emptying the intervalles, before reading the notes :
                        intervals = new ArrayList<>();
                        // Getting the signature :
                        id = xpp.getAttributeIntValue(0, 0);
                        beats = xpp.getAttributeIntValue(1, 0);
                        unit = xpp.getAttributeIntValue(2, 0);

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
