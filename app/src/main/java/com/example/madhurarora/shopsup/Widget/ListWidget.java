package com.example.madhurarora.shopsup.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.madhurarora.shopsup.DataHandler.EventDataHandler;
import com.example.madhurarora.shopsup.R;
import com.example.madhurarora.shopsup.Response.EventResponse;
import com.example.madhurarora.shopsup.Response.Response;
import com.example.madhurarora.shopsup.Services.ListProvider;
import com.example.madhurarora.shopsup.Services.WidgetService;
import com.example.madhurarora.shopsup.app.AppController;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class ListWidget extends AppWidgetProvider {

    public static ArrayList<Response> eventResponse;


    @Override
    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
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

    private void getData(final Context context, final int widgetId) {
        String url = "https://www.hackerearth.com/api/events/upcoming/?format=json";
        final EventDataHandler eventDataHandler = new EventDataHandler(url) {
            @Override
            public void resultReceivedEventInfo(int resultCode, String errorMessage, EventResponse response) {
                Log.d("Widget", "widget");
                if(resultCode == 200 || response != null) {
                    eventResponse = response.getResponse();
                    RemoteViews remoteViews = updateWidgetListView(context, widgetId);

                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    appWidgetManager.updateAppWidget(widgetId, remoteViews);

                    appWidgetManager.notifyAppWidgetViewDataChanged(widgetId, R.id.listWidget);
                }
                else {
                    eventResponse = new ArrayList<>();
                    RemoteViews remoteViews = updateWidgetListView(context, widgetId);

                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    appWidgetManager.updateAppWidget(widgetId, remoteViews);
                }
            }
        };
        eventDataHandler.fetchData();

      //  RemoteViews remoteViews = updateWidgetListView(AppController.getAppContext(), widgetId);

      //  AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(AppController.getAppContext());
      //  appWidgetManager.updateAppWidget(widgetId, remoteViews);
    }

    private RemoteViews updateWidgetListView(Context context, int appWidgetId) {
        Log.d("Here", "HERE");

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.list_widget);

        Intent reload = new Intent(context, ListWidget.class);
        reload.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        reload.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[] {appWidgetId});
        PendingIntent pendingReload = PendingIntent.getBroadcast(context, 0, reload, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.updateButton, pendingReload);

        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        //intent.putExtra("list", bundle);
        //intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        remoteViews.setRemoteAdapter(R.id.listWidget, intent);
        remoteViews.setEmptyView(R.id.listWidget, R.id.emptyView);
        return remoteViews;
    }
}

