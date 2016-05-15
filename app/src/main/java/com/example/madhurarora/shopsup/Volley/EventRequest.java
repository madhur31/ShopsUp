package com.example.madhurarora.shopsup.Volley;

import com.android.volley.Response;
import com.example.madhurarora.shopsup.Response.EventResponse;
import com.google.gson.reflect.TypeToken;

/**
 * Created by madhur.arora on 15/05/16.
 */
public class EventRequest extends GsonRequest<EventResponse> {

    public EventRequest(String url, Response.Listener<EventResponse> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, new TypeToken<EventResponse>() {}.getType(), listener, errorListener);
    }
}
