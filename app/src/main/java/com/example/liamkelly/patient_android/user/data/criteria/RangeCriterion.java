package com.example.liamkelly.patient_android.user.data.criteria;

import com.example.liamkelly.patient_android.user.data.PatientData;

public class RangeCriterion implements Criterion {

    private final String mKey;
    private final int mMin;
    private final int mMax;

    public RangeCriterion(String key, int min, int max) {
        mKey = key;
        mMin = min;
        mMax = max;
    }

    @Override
    public boolean isMetByPatient(PatientData patientData) {
        return patientData.isInRange(mKey, mMin, mMax);
    }

    @Override
    public String toString() {
        return mKey + ": " + mMin + "-" + mMax + "\n";
    }
}
