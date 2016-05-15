package com.example.madhurarora.shopsup.Volley;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.madhurarora.shopsup.Utils.ResponseUtils;
import com.example.madhurarora.shopsup.app.AppController;
import com.google.gson.Gson;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Created by madhur.arora on 15/05/16.
 */
public class GsonRequest<T> extends Request<T> {

    private final Response.Listener listener;
    private final String URL;
    private int method;
    private Type classType;
    private Gson gson;

    public GsonRequest(int method, String url, Type classType, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = listener;
        this.URL = url;
        this.classType = classType;
        this.method = method;
        gson = AppController.getGsonInstance();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        String xx = new String(networkResponse.data);
        Log.d("Parse Network", xx);

        if (networkResponse == null)
            return null;
        try{
            if(networkResponse.statusCode !=200 && networkResponse.statusCode != 304 ) {
                return Response.error(new VolleyError(networkResponse));
            }

            Reader jsonReader = ResponseUtils.getJsonReader(networkResponse);

            T response = gson.fromJson(jsonReader, classType);
            if(response == null)
                return Response.error(new VolleyError(networkResponse));
            return Response.success(response, null);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
    }
}
