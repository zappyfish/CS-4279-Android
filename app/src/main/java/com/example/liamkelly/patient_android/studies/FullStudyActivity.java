package com.example.liamkelly.patient_android.studies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.liamkelly.patient_android.R;

public class FullStudyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_study);

        Intent studyIntent = getIntent();
        Study selectedStudy = (Study)studyIntent.getSerializableExtra("study");
        displayStudyInfo(selectedStudy);
    }

    private void displayStudyInfo(Study study) {
        TextView txt = (TextView)findViewById(R.id.study_info);
        txt.setText(study.getName() + "\nLorum Ipsum blah blah blah blah blah");
    }
}
