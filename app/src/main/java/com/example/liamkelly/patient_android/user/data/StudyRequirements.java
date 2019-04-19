package com.example.liamkelly.patient_android.user.data;

import com.example.liamkelly.patient_android.user.data.criteria.ConditionCriterion;
import com.example.liamkelly.patient_android.user.data.criteria.Criterion;
import com.example.liamkelly.patient_android.user.data.criteria.EnumCriterion;
import com.example.liamkelly.patient_android.user.data.criteria.RangeCriterion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StudyRequirements {

    private static final String CONDITION = "condition";
    private static final String ENUM = "enum";
    private static final String RANGE = "range";

    private static final String NAME = "name";
    private static final String VALUE = "value";
    private static final String MIN = "min";
    private static final String MAX = "max";
    private static final String STATE = "state";

    private final List<Criterion> mStudyCriteria;

    public StudyRequirements(JSONObject payload) {
        mStudyCriteria = new LinkedList<>();
        handlePayload(payload);
    }

    public boolean patientIsEligible(PatientData patientData) {
        for (Criterion criterion : mStudyCriteria) {
            if (!criterion.isMetByPatient(patientData)) {
                return false;
            }
        }
        return true; // All criteria are satisfied
    }

    private void handlePayload(JSONObject payload) {
        Iterator<String> keys = payload.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            try {
                String type = payload.getJSONObject(key).getString("type");
                JSONObject nextJson = payload.getJSONObject(key);
                if (type.equals(CONDITION)) {
                    mStudyCriteria.add(getConditionCriterion(key, nextJson));
                } else if (type.equals(ENUM)) {
                    mStudyCriteria.add(getEnumCriterion(key, nextJson));
                } else if (type.equals(RANGE)) {
                    mStudyCriteria.add(getRangeCriterion(key, nextJson));
                }
            } catch (JSONException e) {

            }
        }
    }

    private static ConditionCriterion getConditionCriterion(String name, JSONObject conditionJson) {
        try {
            boolean shouldHave = conditionJson.getBoolean(STATE);
            return new ConditionCriterion(name, shouldHave);
        } catch (JSONException e) {
            return null;
        }
    }

    private static EnumCriterion getEnumCriterion(String name, JSONObject enumJson) {
        try {
            String value = enumJson.getString(VALUE);
            return new EnumCriterion(name, value);
        } catch (JSONException e) {
            return null;
        }
    }

    private static RangeCriterion getRangeCriterion(String name, JSONObject rangeJson) {
        try {
            int min = rangeJson.getInt(MIN);
            int max = rangeJson.getInt(MAX);
            return new RangeCriterion(name, min, max);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
