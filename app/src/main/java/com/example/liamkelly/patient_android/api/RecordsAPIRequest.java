package com.example.liamkelly.patient_android.api;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RecordsAPIRequest {

    private static final String BASE_URL = "http://10.66.145.4:5000";
    public static final int GET = Request.Method.GET;
    public static final int POST = Request.Method.POST;

    private final String mPath;
    private final int mMethod;
    private final Map<String, String> mParams;
    private final Callback mRequestCallback;

    private RecordsAPIRequest(Builder builder) {
        mMethod = builder.method;
        mParams = builder.params;
        StringBuilder args = new StringBuilder();
        if (mMethod == GET && mParams != null) {
            if (!mParams.isEmpty()) {
                args.append("?");
                Iterator it = mParams.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    args.append(pair.getKey());
                    args.append("=");
                    args.append(pair.getValue());
                    if (it.hasNext()) {
                        args.append("&");
                    }
                }
            }
        }
        mPath = BASE_URL + builder.path + args.toString();
        mRequestCallback = builder.callback;
    }

    JsonObjectRequest createRequest() {
        return new JsonObjectRequest(mMethod, mPath, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (mRequestCallback != null) {
                    mRequestCallback.onRequestSuccess(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mRequestCallback != null) {
                    mRequestCallback.onRequestFailure(error);
                }
            }
        });
    }

    public static class Builder {

        private int method;
        private Map<String, String> params;
        private String path;
        private Callback callback;

        public Builder() {
            params = new HashMap<>();
        }

        public Builder setMethod(int method) {
            this.method = method;
            return this;
        }

        public Builder setParam(String key, String val) {
            this.params.put(key, val);
            return this;
        }

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public Builder setCallback(Callback callback) {
            this.callback = callback;
            return this;
        }

        public RecordsAPIRequest build() {
            return new RecordsAPIRequest(this);
        }
    }

    public interface Callback {

        void onRequestSuccess(JSONObject response);

        void onRequestFailure(VolleyError error);
    }
}
