package com.example.madhurarora.shopsup.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.bumptech.glide.Glide;
import com.example.madhurarora.shopsup.DataHandler.EventDataHandler;
import com.example.madhurarora.shopsup.R;
import com.example.madhurarora.shopsup.Response.EventResponse;
import com.example.madhurarora.shopsup.Response.Response;
import com.example.madhurarora.shopsup.app.AppController;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class EventWidget extends AppWidgetProvider {

    static int index = 0;
    static ArrayList<Response> listItems;

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        int id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        if (intent != null && listItems != null) {
            if (intent.getAction().equalsIgnoreCase("NEXT")) {
                int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                    id = appWidgetId;
                    if (index < listItems.size() - 1)
                        index++;
                    updateView(context, appWidgetId);
            }

            if (intent.getAction().equalsIgnoreCase("PREVIOUS")) {
                int appWidgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                if (appWidgetID != AppWidgetManager.INVALID_APPWIDGET_ID) {
                    id = appWidgetID;
                    if (index != 0)
                        index--;
                    updateView(context, appWidgetID);
                }
            }
        } else
            getData(context, id);
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.d("NULL", "UPDATE");
        for (final int appWidgetId : appWidgetIds) {
            getData(context, appWidgetId);
            final Handler handler = new Handler();

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    getData(context, appWidgetId);
                    handler.postDelayed(this, 30000);
                }
            };
            handler.postDelayed(runnable, 30000);
        }
    }

    public void getData(final Context context, final int widgetId) {
        String url = "https://www.hackerearth.com/api/events/upcoming/?format=json";
        EventDataHandler eventDataHandler = new EventDataHandler(url) {
            @Override
            public void resultReceivedEventInfo(int resultCode, String errorMessage, EventResponse response) {
                if (resultCode != 200 || response != null) {
                    index =0;
                    listItems = FilterList(response.getResponse());
                    updateView(context, widgetId);
                } else {
                    listItems = new ArrayList<>();
                    updateView(context, widgetId);
                }
            }
        };
        eventDataHandler.fetchData();
    }

    public void updateView(Context context, final int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.event_widget);
        if (listItems.get(index) != null) {
            Response response = listItems.get(index);
            remoteViews.setTextViewText(R.id.eventtitle, response.getTitle());
            remoteViews.setTextViewText(R.id.challageType, response.getChallenge_type());
            remoteViews.setTextViewText(R.id.onGoing, "Ongoing: " +String.valueOf(listItems.size()));
            remoteViews.setTextViewText(R.id.contectEvent, response.getDescription());
            if(response.getThumbnail() != null){
                try {
                    imageLoader(response.getThumbnail(), remoteViews, appWidgetId, context);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
                remoteViews.setImageViewResource(R.id.imageWidget, R.drawable.ic_launcher);
        }
        else {
            remoteViews.setTextViewText(R.id.eventtitle, "Loading...");
        }

        Intent next = new Intent(context, EventWidget.class);
        next.setAction("NEXT");
        next.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pendingNext = PendingIntent.getBroadcast(context, 0, next, 0);
        remoteViews.setOnClickPendingIntent(R.id.right, pendingNext);

        Intent previous = new Intent(context, EventWidget.class);
        previous.setAction("PREVIOUS");
        previous.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pendingPrevious = PendingIntent.getBroadcast(context, 0, previous, 0);
        remoteViews.setOnClickPendingIntent(R.id.left, pendingPrevious);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

    }

    private void imageLoader(String url, final RemoteViews remoteViews, final int widgetID, final Context context) {
        ImageRequest imageRequest = new ImageRequest(url, new com.android.volley.Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                remoteViews.setImageViewBitmap(R.id.imageWidget, response);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                appWidgetManager.updateAppWidget(widgetID, remoteViews);
            }
        }, 0, 0, null, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                remoteViews.setImageViewResource(R.id.imageWidget, R.drawable.ic_launcher);
            }
        });
        AppController.getInstance().addToRequestQueue(imageRequest);
    }

    private ArrayList<Response> FilterList(ArrayList<Response> responses) {
        ArrayList<Response> filterList = new ArrayList<>();
        if (responses.size() == 0)
            return filterList;

        for (Response response : responses) {
            if (response.getStatus().equalsIgnoreCase("ONGOING")) {
                filterList.add(response);
            }
        }
        return filterList;
    }

}

