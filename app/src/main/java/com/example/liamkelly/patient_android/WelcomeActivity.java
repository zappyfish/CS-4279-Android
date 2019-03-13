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

import com.android.volley.VolleyError;
import com.example.liamkelly.patient_android.api.APIManager;
import com.example.liamkelly.patient_android.api.RecordsAPIRequest;
import com.example.liamkelly.patient_android.studies.Study;
import com.example.liamkelly.patient_android.studies.StudyViewerFragment;
import com.example.liamkelly.patient_android.user.CurrentUser;
import com.example.liamkelly.patient_android.user.data.Matches;
import com.example.liamkelly.patient_android.user.data.criteria.Criterion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
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
        // setTestSudies();
        setMatchedStudies();
    }

    private void setMatchedStudies() {
        final StudyViewerFragment fragment = (StudyViewerFragment)getSupportFragmentManager().findFragmentById(R.id.study_preview_fragment);
        fragment.addStudies(Matches.getMatchedStudies());
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
        final StudyViewerFragment fragment = (StudyViewerFragment)getSupportFragmentManager().findFragmentById(R.id.study_preview_fragment);
        RecordsAPIRequest testStudyRequest = new RecordsAPIRequest.Builder()
                .setMethod(RecordsAPIRequest.GET)
                .setPath("/studies/check")
                .setParam("token", "1234")
                .setParam("key", "1234")
                .setCallback(new RecordsAPIRequest.Callback() {
                    @Override
                    public void onRequestSuccess(JSONObject response) {
                        List<Study> studies = new LinkedList<>();
                        Iterator<String> keys = response.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            try {
                                JSONObject studyData = response.getJSONObject(key);
                                String name = studyData.getString("name");
                                String researcher = studyData.getString("researcher");
                                String description = studyData.getString("description");
                                String institution = studyData.getString("institution");

                                studies.add(new Study(name, researcher, institution, description, new LinkedList<Criterion>()));
                            } catch (JSONException e) {
                                // ?
                            }
                        }
                        fragment.addStudies(studies);
                    }

                    @Override
                    public void onRequestFailure(VolleyError error) {

                    }
                })
                .build();
        APIManager.getInstance(this).makeRequest(testStudyRequest);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // TODO: anything?
    }
}
