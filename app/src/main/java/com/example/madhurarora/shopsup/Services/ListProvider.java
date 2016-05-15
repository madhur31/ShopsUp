package com.example.madhurarora.shopsup.Services;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.bumptech.glide.Glide;
import com.example.madhurarora.shopsup.R;
import com.example.madhurarora.shopsup.Response.Response;
import com.example.madhurarora.shopsup.Widget.ListWidget;

import java.util.ArrayList;

import javax.xml.transform.ErrorListener;

/**
 * Created by madhur.arora on 15/05/16.
 */
public class ListProvider implements RemoteViewsFactory {

    private Context context;
    private ArrayList<Response> listItem = new ArrayList<>();
    private int appWidgetId;
    private int size;

    public ListProvider(Context context, Intent intent, ArrayList<Response> listItem) {
        Log.d("ITIT", "ITIT");
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        this.listItem = listItem;
    }

    public void setListItem(ArrayList<Response> listItem) {
        this.listItem = listItem;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        //Log.d("Size", String.valueOf(listItem.size()));
        if (listItem == null)
            return 0;
        return listItem.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.list_row);
        Response response = listItem.get(position);
        //  Log.d("Title", response.getTitle() + "  " + response.getDescription());
        remoteViews.setTextViewText(R.id.title, response.getTitle());
        remoteViews.setTextViewText(R.id.content, response.getDescription());
        if (response.thumbnail != null) {
            try {
                Bitmap myBitmap = Glide.with(context.getApplicationContext()).load(response.getThumbnail()).asBitmap().centerCrop().into(60, 60).get();
                remoteViews.setImageViewBitmap(R.id.thumbnail, myBitmap);
            } catch (Exception e) {
            }
        }else {
            remoteViews.setImageViewResource(R.id.thumbnail, R.drawable.ic_launcher);
        }
        return remoteViews;
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public void onCreate() {
        Log.d("oncreate", "Create");
    }

    @Override
    public void onDataSetChanged() {
        listItem = ListWidget.eventResponse;
    }

    @Override
    public void onDestroy() {

    }
}
