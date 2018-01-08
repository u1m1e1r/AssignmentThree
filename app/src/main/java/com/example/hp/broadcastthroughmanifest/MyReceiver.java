package com.example.hp.broadcastthroughmanifest;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Switch;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import static com.example.hp.broadcastthroughmanifest.R.id.parent;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        EventBus.getDefault().post(new SwitchEvent(intent,context));
    }
}
