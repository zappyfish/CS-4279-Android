package com.example.liamkelly.patient_android.user.data.criteria;

import com.example.liamkelly.patient_android.user.data.PatientData;

public interface Criterion {

    boolean isMetByPatient(PatientData patientData);

    @Override
    String toString();
}