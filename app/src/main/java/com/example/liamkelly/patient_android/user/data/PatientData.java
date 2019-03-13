package com.example.liamkelly.patient_android.user.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PatientData {

    private final Set<String> mConditions;
    private final Map<String, String> mEnumValues;
    private final Map<String, Integer> mRangeValues;

    public PatientData(List<String> conditions, Map<String, String> enumValues,
                       Map<String, Integer> rangeValues) {
        mConditions = new HashSet<>();
        mEnumValues = new HashMap<>();
        mRangeValues = new HashMap<>();

        mConditions.addAll(conditions);

        for (String key : enumValues.keySet()) {
            mEnumValues.put(key, enumValues.get(key));
        }

        for (String key : rangeValues.keySet()) {
            mRangeValues.put(key, rangeValues.get(key));
        }
    }

    public boolean hasCondition(String condition) {
        return mConditions.contains(condition);
    }

    public boolean meetsEnumValue(String key, String value) {
        return mEnumValues.containsKey(key) && mEnumValues.get(key).equals(value);
    }

    public boolean isInRange(String key, int min, int max) {
        if (mRangeValues.containsKey(key)) {
            int val = mRangeValues.get(key);
            return val >= min && val <= max;
        } else {
            return false;
        }
    }

    public static PatientData getTestPatient() {
        String[] testConditions = {"flu", "asthma", "diabetes"};
        List<String> conditions = Arrays.asList(testConditions);

        Map<String, String> enums = new HashMap<>();
        enums.put("hair_color", "brown");
        enums.put("relationship_status", "single");
        enums.put("eye_color", "blue");

        Map<String, Integer> ranges = new HashMap<>();
        ranges.put("age", 23);
        ranges.put("height_inches", 70);
        ranges.put("weight_pounds", 200);
        ranges.put("systolic_blood_pressure", 135);
        ranges.put("diastolic_blood_pressure", 115);

        return new PatientData(conditions, enums, ranges);
    }
}
