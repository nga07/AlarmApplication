package com.ngadt.demo4;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ngadt.demo4.databinding.ActivityRingBinding;
import com.ngadt.demo4.model.Time;

public class RingActivity extends AppCompatActivity {
    ActivityRingBinding binding;
    SQLiteHelper db;
    Time time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("click", "Ring: ");
        db = new SQLiteHelper(getBaseContext());
        binding = ActivityRingBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        // Bundle bundle = getIntent().getBundleExtra("BundleAlarmObject");
        time = (Time) getIntent().getSerializableExtra("AlarmObject");
        Log.d("click", "timeRing: " + time.toString());
        binding.activityRingDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAlarm();
            }
        });
    }

    private void dismissAlarm() {
        // Bundle bundle = new Bundle();
        //   bundle = getIntent().getBundleExtra("BundleAlarmObject");
        //   Time time = (Time) bundle.getSerializable("AlarmObject");
//        Bundle bundle = getIntent().getBundleExtra("BundleAlarmObjec");
//        Time time = (Time) bundle.getSerializable("AlarmObject");
        time.setChecked(0);
        //cancel
        AlarmManager alarmManager = (AlarmManager) getBaseContext().getSystemService(Context.ALARM_SERVICE);
        Intent intentBroad = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, intentBroad, 0);
        alarmManager.cancel(alarmPendingIntent);
        //cap nhat du lieu
        db.updateTime(time);
        Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
        getApplicationContext().stopService(intentService);
        finish();
    }
}
