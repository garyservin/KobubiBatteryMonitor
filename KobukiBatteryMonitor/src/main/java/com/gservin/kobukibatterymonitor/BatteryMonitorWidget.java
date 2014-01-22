package com.gservin.kobukibatterymonitor;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.widget.RemoteViews;

public class BatteryMonitorWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        Intent batteryIntent = context.getApplicationContext().registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String phoneCharging = "";
            int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

            // Are we charging / charged?
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean phoneIsCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setProgressBar(R.id.phone_progressBar, 100, batteryLevel, false);

            if (phoneIsCharging){
                phoneCharging = "\t|\tCharging";
            }
            views.setTextViewText(R.id.phone_charging_text, batteryLevel + "%" + phoneCharging);

            ComponentName componentName = new ComponentName(context, BatteryMonitorWidget.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(componentName, views);
        }
    };
}
