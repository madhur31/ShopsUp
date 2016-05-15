package com.example.madhurarora.shopsup.Response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by madhur.arora on 15/05/16.
 */
public class Response {

    @SerializedName("status")
    public String status;

    @SerializedName("challenge_type")
    public String challenge_type;

    @SerializedName("start_timestamp")
    public String start_timestamp;

    @SerializedName("description")
    public String description;

    @SerializedName("end_date")
    public String end_date;

    @SerializedName("title")
    public String title;

    @SerializedName("url")
    public String url;

    @SerializedName("end_tz")
    public String end_tz;

    @SerializedName("end_utc_tz")
    public String end_utc_tz;

    @SerializedName("subscribe")
    public String subscribe;

    @SerializedName("college")
    public String college;

    @SerializedName("end_time")
    public String end_time;

    @SerializedName("end_timestamp")
    public String end_timestamp;

    @SerializedName("thumbnail")
    public String thumbnail;

    public String getThumbnail() {
        return thumbnail;
    }

    public String getChallenge_type() {
        return challenge_type;
    }

    public String getCollege() {
        return college;
    }

    public String getDescription() {
        return description;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getEnd_timestamp() {
        return end_timestamp;
    }

    public String getEnd_tz() {
        return end_tz;
    }

    public String getEnd_utc_tz() {
        return end_utc_tz;
    }

    public String getStart_timestamp() {
        return start_timestamp;
    }

    public String getStatus() {
        return status;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
