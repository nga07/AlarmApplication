package com.ngadt.demo4;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.ngadt.demo4.model.Time;

public class AlarmService extends Service {
    MediaPlayer mediaPlayer;
    public static final String CHANNEL_ID = "ALARM_SERVICE_CHANNEL";

    @Override
    public void onCreate() {
        Log.d("click", "Media created");
        mediaPlayer = MediaPlayer.create(this, R.raw.meloboom);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        //  Bundle bundle = new Bundle();
        Time time = (Time) intent.getSerializableExtra("AlarmObject");
        // Log.d("click", "timeFrag: " + time.toString());
        //bundle.putSerializable("AlarmObject", time);
        //  Bundle bundle = new Bundle();
        //  bundle = intent.getBundleExtra("BundleAlarmObject");
//        Time time = (Time) bundle.getSerializable("AlarmObject");
        // intentService.putExtra("timeService", time);
        Intent notificationIntent = new Intent(this, RingActivity.class);
        //tao channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    getString(R.string.app_name) + "Service Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
        //   intentService.putExtra("BundleAlarmObject", bundle);
        notificationIntent.putExtra("AlarmObject", time);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Ring Ring .. Ring Ring")
                .setContentText("Alarm")
                .setSmallIcon(R.drawable.ic__alaram)
                .setSound(null)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setFullScreenIntent(pendingIntent, true)
                .build();
        if (mediaPlayer.isPlaying()) {
            Log.d("click", "Media Playing");
        } else {
            Log.d("click", "Media don't playing");
        }
        Log.d("click", "Service");

        startForeground(1, notification);

        return START_STICKY;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
