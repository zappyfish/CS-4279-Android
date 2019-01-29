package com.example.liamkelly.patient_android.user;

public class CurrentUser {

    private String mUserName;

    private static CurrentUser sInstance;

    private CurrentUser() {}

    public static synchronized CurrentUser getInstance() {
        if (sInstance == null) {
            sInstance = new CurrentUser();
        }
        return sInstance;
    }

    public void setName(String name) {
        mUserName = name;
    }

    public String getName() {
        return mUserName;
    }
}
