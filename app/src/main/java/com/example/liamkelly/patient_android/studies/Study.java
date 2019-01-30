package com.example.liamkelly.patient_android.studies;

import java.io.Serializable;

public class Study implements Serializable {

    private final String mName;
    private final String mResearcher;
    private final String mInstitution;
    private final String mDescription;

    public Study(String name, String researcher, String institution, String description) {
        mName = name;
        mResearcher = researcher;
        mInstitution = institution;
        mDescription = description;
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

    public String getDescription() { return mDescription; }
}
