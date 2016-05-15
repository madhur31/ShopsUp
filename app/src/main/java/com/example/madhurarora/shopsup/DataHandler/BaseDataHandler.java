package com.example.madhurarora.shopsup.DataHandler;

import android.util.Log;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import java.lang.reflect.Type;

/**
 * Created by madhur.arora on 15/05/16.
 */
abstract public class BaseDataHandler<T> {
    protected Response.ErrorListener errorListener;
    protected Response.Listener<T> listner;

    protected Request request;

    Type ctype;

    public BaseDataHandler() {
        try {
            errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error", "Error");
                    if (error instanceof TimeoutError) {
                        errorReceived(504, -1, "Request Timeout!");
                    } else if (error instanceof NoConnectionError) {
                        errorReceived(-1, -1, "Internet Connection not available");
                    }
                    if (error.networkResponse != null) {
                        if (error.networkResponse.statusCode == 500) {
                            errorReceived(500, -1, "Oops ! Something wrong happened.");
                        } else if (error.networkResponse.statusCode == 415) {
                            errorReceived(415, -1, "Oops ! Something wrong happened.");
                        } else if (error.networkResponse.statusCode == 405) {
                            errorReceived(405, -1, "Oops ! Something wrong happened.");
                        } else {
                            errorReceived(999, -1, "Oops ! Something wrong happened.");
                        }
                    }
                }
            };
            listner = new Response.Listener<T>() {
                @Override
                public void onResponse(T response) {
                    Log.d("Response", "response");
                    resultReceived(response, false);
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    abstract public void resultReceived(T response, boolean fromDB);

    abstract public void errorReceived(int responseCode, int errorCode, String errorMessage);
}
