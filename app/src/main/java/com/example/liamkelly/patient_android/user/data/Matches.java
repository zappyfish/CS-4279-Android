package com.example.liamkelly.patient_android.user.data;

import com.example.liamkelly.patient_android.studies.Study;
import com.example.liamkelly.patient_android.user.data.criteria.ConditionCriterion;
import com.example.liamkelly.patient_android.user.data.criteria.Criterion;
import com.example.liamkelly.patient_android.user.data.criteria.EnumCriterion;
import com.example.liamkelly.patient_android.user.data.criteria.RangeCriterion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Matches {
    // TODO: Fix this. It's really kinda hacky, but demo tomorrow so lol

    private static final String CONDITION = "condition";
    private static final String ENUM = "enum";
    private static final String RANGE = "range";

    private static final String NAME = "name";
    private static final String VALUE = "value";
    private static final String MIN = "min";
    private static final String MAX = "max";
    private static final String STATE = "state";

    private static List<Study> sMatchedStudies = new LinkedList<>();

    public static List<Study> getMatchedStudies() {
        return sMatchedStudies;
    }

    public static void setMatches(JSONObject matchJson) {
        sMatchedStudies.clear();
        Iterator<String> keys = matchJson.keys();

        while (keys.hasNext()) {
            try {
                handleStudy(matchJson.getJSONObject(keys.next()));
            } catch (JSONException e) {

            }
        }
    }

    private static void handleStudy(JSONObject studyData) {
        try {
            String name = studyData.getString("name");
            String researcher = studyData.getString("researcher");
            String description = studyData.getString("description");
            String institution = studyData.getString("institution");

            List<Criterion> criteria = handleCriteriaPayload(studyData.getJSONObject("criteria"));

            sMatchedStudies.add(new Study(name, researcher, description, institution, criteria));
        } catch (JSONException e) {

        }
    }

    private static List<Criterion> handleCriteriaPayload(JSONObject payload) {
        Iterator<String> keys = payload.keys();
        List<Criterion> criteria = new LinkedList<>();

        while (keys.hasNext()) {
            String key = keys.next();
            try {
                JSONObject nextJson = payload.getJSONObject(key);
                String type = nextJson.getString("type");
                if (type.equals(CONDITION)) {
                    criteria.add(getConditionCriterion(key, nextJson));
                } else if (type.equals(ENUM)) {
                    criteria.add(getEnumCriterion(key, nextJson));
                } else if (type.equals(RANGE)) {
                    criteria.add(getRangeCriterion(key, nextJson));
                }
            } catch (JSONException e) {

            }
        }
        return criteria;
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

    private static RangeCriterion getRangeCriterion(String key, JSONObject rangeJson) {
        try {
            int min = rangeJson.getInt(MIN);
            int max = rangeJson.getInt(MAX);
            return new RangeCriterion(key, min, max);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
