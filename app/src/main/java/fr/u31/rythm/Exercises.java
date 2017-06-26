package fr.u31.rythm;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by ulysse on 26/06/2017.
 *
 * This helper class is in control on the Exercises database (a bunch of xml files)
 *
 */

public class Exercises {
    private static final String TAG = "Exercises";
    private static Exercises instance = new Exercises();

    private Exercises(){
    }

    public static Exercises getInstance() { return instance; }

    void loadExercises(Context ctx) {
        if (BuildConfig.DEBUG) Log.v(TAG, "Loading exercises");

        //First we parse the Rythms file :
        XmlResourceParser xpp = ctx.getResources().getXml(R.xml.data_rythms);
        try {
            xpp.next();
            xpp.next();

            xpp.next();
            xpp.next();
            if (BuildConfig.DEBUG) Log.v(TAG, "IDXML " + xpp.getName());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
