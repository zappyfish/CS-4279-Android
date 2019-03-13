package com.example.liamkelly.patient_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.liamkelly.patient_android.api.APIManager;
import com.example.liamkelly.patient_android.api.RecordsAPIRequest;
import com.example.liamkelly.patient_android.user.CurrentUser;
import com.example.liamkelly.patient_android.user.data.Matches;
import com.example.liamkelly.patient_android.user.data.PatientData;
import com.example.liamkelly.patient_android.user.data.StudyRequirements;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String MATCHES = "true";
    private static final String DOES_NOT_MATCH = "false";
    private static final PatientData TEST_PATIENT = PatientData.getTestPatient();

    private final RecordsAPIRequest.Callback mSessionStartCallback = new RecordsAPIRequest.Callback() {
        @Override
        public void onRequestSuccess(JSONObject response) {
            try {
                String key = response.getString("key");
                queryStudiesThenStartActivity(key, null); // kick off query process
            } catch (JSONException e) {

            }
        }

        @Override
        public void onRequestFailure(VolleyError error) {

        }
    };

    private final RecordsAPIRequest.Callback mQueryCallback = new RecordsAPIRequest.Callback() {
        @Override
        public void onRequestSuccess(JSONObject response) {
            try {
                boolean done = response.getBoolean("done");
                if (!done) {
                    String key = response.getString("key");
                    JSONObject queries = response.getJSONObject("query");
                    queryStudiesThenStartActivity(key, getMatchNumbers(queries));
                } else {
                    JSONObject matches = response.getJSONObject("matches");
                    Matches.setMatches(matches);
                    Intent i = new Intent(MainActivity.this, WelcomeActivity.class);
                    startActivity(i);
                }
            } catch (JSONException e) {

            }
        }

        @Override
        public void onRequestFailure(VolleyError error) {

        }
    };

    private List<Integer> getMatchNumbers(JSONObject queries) {
        List<Integer> matchNumbers = new LinkedList<>();
        Iterator<String> keys = queries.keys();
        while (keys.hasNext()) {
            String number = keys.next();
            try {
                JSONObject query = queries.getJSONObject(number);
                StudyRequirements requirements = new StudyRequirements(query);
                if (requirements.patientIsEligible(TEST_PATIENT)) {
                    matchNumbers.add(Integer.parseInt(number));
                }
            } catch (JSONException e) {

            }
        }
        return matchNumbers;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initButtons();
    }

    private void initButton(int btnID, View.OnClickListener listener) {
        Button btn = (Button)findViewById(btnID);
        btn.setOnClickListener(listener);
    }

    private void initButtons() {
        initButton(R.id.sign_up, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Insert sign up here", Toast.LENGTH_SHORT).show();
            }
        });

        initButton(R.id.sign_in, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                dialogBuilder.setTitle("Incredibly Secure, Sophisticated Sign On");

                final EditText input = new EditText(MainActivity.this);
                input.setHint("enter your name");
                dialogBuilder.setView(input);
                dialogBuilder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String entered = input.getText().toString();
                        if (entered.length() > 0) {
                            CurrentUser.getInstance().setName(input.getText().toString());
                            startQuerySesssion();
                        } else {
                            Toast.makeText(MainActivity.this, "Please enter your login credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialogBuilder.show();
            }
        });
    }

    private void startQuerySesssion() {
        RecordsAPIRequest request = new RecordsAPIRequest.Builder()
                .setMethod(RecordsAPIRequest.GET)
                .setPath("/session/start")
                .setCallback(mSessionStartCallback)
                .build();
        APIManager.getInstance(this).makeRequest(request);
    }

    private void queryStudiesThenStartActivity(String sessionKey, @Nullable List<Integer> matchNumbers) {
        RecordsAPIRequest.Builder builder = new RecordsAPIRequest.Builder()
                .setMethod(RecordsAPIRequest.GET)
                .setPath("/session/check")
                .setCallback(mQueryCallback)
                .setParam("key", sessionKey);

        if (matchNumbers != null) {
            StringBuilder matches = new StringBuilder();
            if (matchNumbers.size() > 0) {
                matches.append(matchNumbers.get(0));
            }

            for (int i = 1; i < matchNumbers.size(); i++) {
                matches.append(",");
                matches.append(matchNumbers.get(i));
            }
            builder.setParam("matches", matches.toString());
        }
        APIManager.getInstance(this).makeRequest(builder.build());
    }
}
