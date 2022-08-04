package com.ngadt.demo4;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.ngadt.demo4.model.Time;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentService = new Intent(context, AlarmService.class);
        Bundle bundle = intent.getParcelableExtra("AlarmBundle");
        Time time = (Time) bundle.getSerializable("AlarmObject");
        Log.d("click", "timeFrag2: " + time.toString());
        // intentService.putExtra("BundleAlarmObject", time);
        //  bundle.putSerializable("AlarmObject", time);

//        intentService.putExtra("timeService", time);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService);
            Log.d("click", "startFore ");
        } else {
            context.startService(intentService);
            Log.d("click", "startService ");
        }
////        Intent returnIntent = new Intent();
////        returnIntent.putExtra("time", 1);
//        context.startService(serviceIntent);
//        Log.d("click", "Broadcast");
    }
}
