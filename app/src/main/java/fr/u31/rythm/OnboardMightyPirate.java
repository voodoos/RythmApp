package fr.u31.rythm;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;

import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;

import java.util.ArrayList;

/**
 * Created by ulysse on 30/06/2017.
 */

public class OnboardMightyPirate extends Activity {
    @Override
    public void onCreate(Bundle savedinstance) {
        super.onCreate(savedinstance);
        //setContentView(R.layout.activity_onboard);


        PaperOnboardingPage scr1 = new PaperOnboardingPage("Hotels",
                "All hotels and hostels are sorted by hospitality rating",
                Color.parseColor("#678FB4"), R.drawable.ic_ronde, R.drawable.ic_tune_black_24dp);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr1);

        PaperOnboardingFragment onBoardingFragment = PaperOnboardingFragment.newInstance(elements);


        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        //fragmentTransaction.add(R.id.onboard_container, onBoardingFragment);
        fragmentTransaction.commit();
    }
}
