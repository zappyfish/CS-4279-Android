package com.example.liamkelly.patient_android.studies;

import com.example.liamkelly.patient_android.user.data.criteria.Criterion;

import java.io.Serializable;
import java.util.List;

public class Study implements Serializable {

    private final String mName;
    private final String mResearcher;
    private final String mInstitution;
    private final String mDescription;

    public Study(String name, String researcher, String institution, String description,
                 List<Criterion> criteria) {
        mName = name;
        mResearcher = researcher;
        mInstitution = institution;
        StringBuilder criteriaString = new StringBuilder();
        for (Criterion criterion : criteria) {
            criteriaString.append(criterion.toString());
        }
        mDescription = description + "\n" + criteriaString.toString();
    }

    public String getName() {
        return mName;
    }

    public String getResearcher() {
        return mResearcher;
    }

    public String getInstitution() {
        return mInstitution;
    }

    public String getDescription() {
        return mDescription;
    }
}
