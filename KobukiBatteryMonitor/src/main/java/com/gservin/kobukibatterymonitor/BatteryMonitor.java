package com.gservin.kobukibatterymonitor;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BatteryMonitor extends Activity {
    TextView phone_charging_text;
    TextView kobuki_charging_text;
    ProgressBar phone_pb;
    ProgressBar kobuki_pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phone_charging_text = (TextView) findViewById(R.id.phone_charging_text);
        kobuki_charging_text = (TextView) findViewById(R.id.kobuki_charging_text);
        phone_pb = (ProgressBar) findViewById(R.id.phone_progressBar);
        kobuki_pb = (ProgressBar) findViewById(R.id.kobuki_progressBar);
    }

    protected void onResume(){
        super.onResume();  // Always call the superclass method first
        registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    protected void onPause(){
        super.onPause();  // Always call the superclass method first
        unregisterReceiver(mBatInfoReceiver);
    }

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            String phoneCharging = "";

            // Are we charging / charged?
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean phoneIsCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;

            phone_pb.setProgress(level);
            if (phoneIsCharging){
                phoneCharging = "\t|\tCharging";
            }
            phone_charging_text.setText("Level: " + level + phoneCharging);
        }
    };

    //if (kobukiisCharging){
    //        kobuki_charging_text.setText("Charging");
    //}
}