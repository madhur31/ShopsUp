package com.example.madhurarora.shopsup.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by madhur.arora on 15/05/16.
 */
public class EventResponse {
    @SerializedName("response")
    public ArrayList<Response> response;

    public ArrayList<Response> getResponse() {
        return response;
    }
}
