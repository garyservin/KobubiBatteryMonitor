/*
 * Copyright (C) 2010 Sergej Shafarenka, beworx.com
 * Copyright (C) 2014 Gary Servin, gservin.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gservin.kobukibatterymonitor;

import static com.gservin.kobukibatterymonitor.BatteryMonitorWidget.TAG;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BatteryMonitorWidgetProvider extends AppWidgetProvider {
    public static final String EXT_UPDATE_WIDGETS = "updateWidgets";
    private static final String BATTERY_SERVICE_ACTION = "com.gservin.kobukibatterymonitor.BatteryMonitorService";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "provider.update");
        BatteryMonitorService.requestWidgetUpdate(context);
    }

    public void onEnabled(Context context) {
        Log.d(TAG, "provider.enabled");

        Intent intent = new Intent(BATTERY_SERVICE_ACTION);
        context.startService(intent);
    }

    public void onDisabled(Context context) {
        Log.d(TAG, "provider.disabled");

        // stop service
        Intent intent = new Intent(BATTERY_SERVICE_ACTION);
        context.stopService(intent);
    }


//    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String phoneCharging = "";
//            int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//
//            // Are we charging / charged?
//            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
//            boolean phoneIsCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
//                    status == BatteryManager.BATTERY_STATUS_FULL;
//
//            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
//            views.setProgressBar(R.id.phone_progressBar, 100, batteryLevel, false);
//
//            if (phoneIsCharging){
//                phoneCharging = "\t|\tCharging";
//            }
//            views.setTextViewText(R.id.phone_charging_text, batteryLevel + "%" + phoneCharging);
//
//            ComponentName componentName = new ComponentName(context, BatteryMonitorWidget.class);
//
//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//            appWidgetManager.updateAppWidget(componentName, views);
//        }
//    };
}
