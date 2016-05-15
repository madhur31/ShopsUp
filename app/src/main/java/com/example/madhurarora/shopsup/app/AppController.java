package com.example.madhurarora.shopsup.app;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.madhurarora.shopsup.Utils.LruBitmapCache;
import com.example.madhurarora.shopsup.Utils.SSL;
import com.google.gson.Gson;

/**
 * Created by madhur.arora on 15/05/16.
 */
public class AppController extends Application {
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context context;
    private static AppController mInstance;
    private static Gson gson;
    private String TAG = "Volley";

    @Override
    public void onCreate() {
        super.onCreate();
        AppController.context = getApplicationContext();
        mInstance = this;
        SSL.nuke();
        initGson();
    }

    private void initGson() {
        gson = new Gson();
    }

    public static Context getAppContext() {
        return AppController.context;
    }

    public static Gson getGsonInstance() {
        return AppController.gson;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null)
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        return mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
        //getRequestQueue().start();
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }
}
