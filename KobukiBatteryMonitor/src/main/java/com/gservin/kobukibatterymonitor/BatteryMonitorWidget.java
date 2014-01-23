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

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;


public class BatteryMonitorWidget {

    public static final String TAG = "gservin.kobukibatterymonitor";

    public static void updateWidgets(Context context, int chargeLevel, boolean chargerConnected) {

        String level = chargeLevel < 10 ? "0" + chargeLevel : String.valueOf(chargeLevel);

        // create views
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.battery_widget);

        // update level
        views.setInt(R.id.battery, "setImageLevel", chargeLevel);

        // update visible capacity
        views.setTextViewText(R.id.capacity_right_bottom, level);
        views.setViewVisibility(R.id.capacity_right_bottom, chargeLevel < 100 ? View.VISIBLE : View.GONE);

        // update lightning visibility
        views.setViewVisibility(R.id.lightning, chargerConnected ? View.VISIBLE : View.GONE);

        // update widgets
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, BatteryMonitorWidgetProvider.class);
        widgetManager.updateAppWidget(componentName, views);

        Log.d(TAG, "widgets updated");
    }
}
