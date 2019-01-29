package com.example.liamkelly.patient_android.studies;

import java.io.Serializable;

public class Study implements Serializable {

    private final String mName;
    private final String mResearcher;
    private final String mInstitution;

    public Study(String name, String researcher, String institution) {
        mName = name;
        mResearcher = researcher;
        mInstitution = institution;
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
}
