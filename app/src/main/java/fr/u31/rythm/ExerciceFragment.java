package fr.u31.rythm;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ulysse on 30/06/2017.
 */

public class ExerciceFragment extends Fragment {
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
    }

    @Override
    public View onCreateView(LayoutInflater li, ViewGroup container, Bundle bundle) {
        if(getExerciseId() >= 0)
            return ((MainActivity) getActivity()).getExercises().getExercise(getExerciseId()).getLayout(li, container);
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
