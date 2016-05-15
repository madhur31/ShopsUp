package com.example.madhurarora.shopsup.DataHandler;

import com.example.madhurarora.shopsup.Response.EventResponse;
import com.example.madhurarora.shopsup.Volley.EventRequest;
import com.example.madhurarora.shopsup.app.AppController;
import com.google.gson.reflect.TypeToken;

/**
 * Created by madhur.arora on 15/05/16.
 */
abstract public class EventDataHandler extends BaseDataHandler<EventResponse> {

    private String URL;

    public EventDataHandler(String url) {
        this.URL = url;
        this.ctype = new TypeToken<EventResponse>(){}.getType();
    }

    public void fetchData() {
        EventRequest eventRequest = new EventRequest(URL, listner, errorListener);
        this.request = eventRequest;
        AppController.getInstance().addToRequestQueue(request);
    }

    @Override
    public void resultReceived(EventResponse response, boolean fromDB) {
        if(response != null)
            resultReceivedEventInfo(200, "", response);
        else
            resultReceivedEventInfo(999, "Oops ! Something wrong happened.", null);
    }

    @Override
    public void errorReceived(int responseCode, int errorCode, String errorMessage) {
        resultReceivedEventInfo(errorCode, errorMessage, null);
    }

    abstract public void resultReceivedEventInfo(int resultCode, String errorMessage, EventResponse response);
}
