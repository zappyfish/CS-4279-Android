package com.example.liamkelly.patient_android.user.data.criteria;

import com.example.liamkelly.patient_android.user.data.PatientData;

public class ConditionCriterion implements Criterion {

    private final String mCondition;
    private final boolean mShouldHaveCondition;

    public ConditionCriterion(String condition, boolean shouldHaveCondition) {
        mCondition = condition;
        mShouldHaveCondition = shouldHaveCondition;
    }

    @Override
    public boolean isMetByPatient(PatientData patientData) {
        boolean patientHasCondition = patientData.hasCondition(mCondition);
        if (mShouldHaveCondition) {
            return patientHasCondition;
        } else {
            return !patientHasCondition;
        }
    }

    @Override
    public String toString() {
        return mCondition + ": " + (mShouldHaveCondition ? "true\n" : "false\n");
    }
}
