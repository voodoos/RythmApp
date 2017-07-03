package fr.u31.rythm;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by ulysse on 30/06/2017.
 */

public class ExerciceFragment extends Fragment {
    private static final String TAG = "ExFrag";

    private Exercises exs;

    public static ExerciceFragment newInstance(Exercise ex) {
        ExerciceFragment ef = new ExerciceFragment();

        Bundle args = new Bundle();
        args.putInt("ex_id", ex.getId());

        ef.setArguments(args);

        return ef;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.exs = Exercises.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater li, ViewGroup container, Bundle bundle) {
        if(getExerciseId() >= 0) {
            RelativeLayout l =  exs.getExercise(getExerciseId()).getLayout(li, container);

            // Click listener to launch training
            l.findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (BuildConfig.DEBUG) Log.v(TAG, "Click Drum");

                    //((MainActivity) getActivity()).start(getExerciseId());


                    // Launching train activyty on click :
                    Intent intent;

                    if(PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("pref_rythm", false))
                        intent = new Intent(getActivity(), TrainActivity.class);
                    else intent = new Intent(getActivity(), HumanTrainActivity.class);

                    intent.putExtra("exercise", getExerciseId());
                    startActivity(intent);
                }
            });

            // Click listener to hear de beat :
            l.findViewById(R.id.ear).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (BuildConfig.DEBUG) Log.v(TAG, "Click ear");

                    //((MainActivity) getActivity()).start(getExerciseId());
                }
            });


            return l;
        }
        else return null;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    public int getExerciseId() {
        return getArguments().getInt("ex_id", -1);
    }
}
