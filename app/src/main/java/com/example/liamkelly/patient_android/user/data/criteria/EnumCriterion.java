package com.example.liamkelly.patient_android.user.data.criteria;

import com.example.liamkelly.patient_android.user.data.PatientData;

public class EnumCriterion implements Criterion {

    private final String mKey;
    private final String mValue;

    public EnumCriterion(String key, String value) {
        mKey = key;
        mValue = value;
    }

    @Override
    public boolean isMetByPatient(PatientData patientData) {
        return patientData.meetsEnumValue(mKey, mValue);
    }

    @Override
    public String toString() {
        return mKey + ":" + mValue + "\n";
    }
}
