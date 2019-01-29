package com.example.liamkelly.patient_android;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liamkelly.patient_android.studies.Study;
import com.example.liamkelly.patient_android.studies.StudyViewerFragment;
import com.example.liamkelly.patient_android.user.CurrentUser;

import java.util.LinkedList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity implements StudyViewerFragment.OnFragmentInteractionListener {

    Handler mAnimationHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        getWelcome();
        initInteractions();
        setTestSudies();
    }

    private void initInteractions() {
        LinearLayout help = (LinearLayout)findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WelcomeActivity.this, "HAHA! No help available (currently)", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getWelcome() {
        final TextView welcomeText = (TextView)findViewById(R.id.welcome_name);
        final float alpha = 0f;
        welcomeText.setAlpha(alpha);
        welcomeText.setText("Welcome, " + CurrentUser.getInstance().getName() + ".");
        setWelcomeAlpha(alpha, 0.01f, 40);
    }

    private void setWelcomeAlpha(final float alpha, final float stepSize, final int delay) {
        final TextView welcomeText = (TextView)findViewById(R.id.welcome_name);
        welcomeText.setAlpha(alpha);
        if (alpha < 1) {
            mAnimationHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setWelcomeAlpha(alpha + stepSize, stepSize, delay);
                }
            }, delay);
        }
    }

    private void setTestSudies() {
        Study test1 = new Study("Effects of X on Y", "Dr. Anna", "Vanderbilt University");
        Study test2 = new Study("Is Smoking Bad?", "Dr. Seuss", "Rhyme College");
        Study test3 = new Study("The Extent of Laziness", "(not complete)", "(not complete)");

        StudyViewerFragment fragment = (StudyViewerFragment)getSupportFragmentManager().findFragmentById(R.id.study_preview_fragment);
        List<Study> studies = new LinkedList<>();
        studies.add(test1);
        studies.add(test2);
        studies.add(test3);
        fragment.addStudies(studies);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // TODO: anything?
    }
}