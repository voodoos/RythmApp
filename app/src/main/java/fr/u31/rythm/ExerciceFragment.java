package fr.u31.rythm;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by ulysse on 30/06/2017.
 */

public class ExerciceFragment extends Fragment {
    private static final String TAG = "ExFrag";
    private PlayMetronome playMet = null;

    private Exercises exs;
    private RelativeLayout layout;

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
            layout =  exs.getExercise(getExerciseId()).getLayout(li, container);

            // Click listener to launch training
            layout.findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
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
            layout.findViewById(R.id.ear).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (BuildConfig.DEBUG) Log.v(TAG, "Click ear");

                    //((MainActivity) getActivity()).start(getExerciseId());
                }
            });


            return layout;
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

    /**
     * Red note.
     *
     * @param id the id
     */
    public void redNote(int id) {
        if (layout != null) {
            boolean right = true;

            // Getting the scroller to center played note:
            HorizontalScrollView hsv = layout.findViewById(R.id.rythm_scroll);

            int noteNbr;

            LinearLayout notesContainer;
            LinearLayout rContainer = (LinearLayout) layout.findViewById(R.id.rythm_container);

            if (right) {
                notesContainer = (LinearLayout) rContainer.getChildAt(0);
            } else {
                notesContainer = (LinearLayout) rContainer.getChildAt(1);
            }

            noteNbr = notesContainer.getChildCount();

            //Resetting all notes to black :
            for (int i = 0; i < noteNbr; i++) {
                setViewNoteColor((ImageView) notesContainer.getChildAt(i), Color.parseColor("#000000"));
            }

            // Actual note in red :
            if (BuildConfig.DEBUG) Log.v(TAG, "id: " + id + "Noteid: " + id);
            setViewNoteColor((ImageView) notesContainer.getChildAt(id), Color.parseColor("#D41C1C"));

            Rect rect = new Rect();
            notesContainer.getChildAt(id).getGlobalVisibleRect(rect);

            // Scrolling to the right place (proportionnal to total lenght)
            hsv.smoothScrollTo((int) (((double) id / (double) noteNbr) * (double) hsv.getMaxScrollAmount()), 0);
        }
    }


    /**
     * Set view note color.
     *
     * @param id    the id of the image
     * @param color the new color
     */
    protected void setViewNoteColor(final ImageView id, final int color){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                id.setColorFilter(color);
            }
        });
    }
}
