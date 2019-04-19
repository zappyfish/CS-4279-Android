package com.example.liamkelly.patient_android.api;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class APIManager {

    private final Context mContext;
    private final RequestQueue mRequestQueue;

    private static APIManager sInstance;

    private APIManager(Context context) {
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(mContext);
    }

    public static synchronized APIManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new APIManager(context);
        }
        return sInstance;
    }

    public void makeRequest(RecordsAPIRequest request) {
//        JsonObjectRequest req = request.createRequest();
//        req.setRetryPolicy(new DefaultRetryPolicy(
//                60000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        mRequestQueue.add(req);
        mRequestQueue.add(request.createRequest());
    }
}
