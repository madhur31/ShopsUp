package com.example.madhurarora.shopsup.Services;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViewsService;

import com.example.madhurarora.shopsup.Response.Response;
import com.example.madhurarora.shopsup.Widget.ListWidget;
import com.example.madhurarora.shopsup.app.AppController;

import java.util.ArrayList;

/**
 * Created by madhur.arora on 15/05/16.
 */
public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d("WidgetService", "Here");

        return new ListProvider(AppController.getAppContext(), intent, ListWidget.eventResponse);
    }
}
