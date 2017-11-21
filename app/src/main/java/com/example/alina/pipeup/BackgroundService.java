package com.example.alina.pipeup;

/**
 * Created by alina on 11/19/17.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

public class BackgroundService extends Service {
    private NotificationManager mNM;
    Bundle b;
    public static Timer timer;
    Intent notificationIntent;
    private final IBinder mBinder = new LocalBinder();
    private String newtext;
    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;

    public class LocalBinder extends Binder {
        BackgroundService getService() {
            return BackgroundService.this;
        }
    }

    @Override
    public void onCreate() {
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        newtext = "BackGroundApp Service Running";

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(BackgroundService.this)
                .setVibrate(new long[]{0, 100, 100, 100, 100, 100})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Content Title")
                .setContentText("Content Text");
//
//        Notification notification = new Notification(R.drawable.alina, newtext,System.currentTimeMillis());
//        PendingIntent contentIntent = PendingIntent.getActivity(BackgroundService.this, 0, new Intent(BackgroundService.this, BackgroundAppExample.class), 0);
//        notification.setLatestEventInfo(BackgroundService.this,"BackgroundAppExample", newtext, contentIntent);
//        mNM.notify(R.string.local_service_started, notification);
        notificationIntent = new Intent(this, Starter.class);
        showNotification();
        timer = new Timer();
        timer.schedule(new RemindTask(this),
                0,        //initial delay
                5*1000);  //subsequent rate
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
    public void onDestroy() {
        mNM.cancel(R.string.local_service_started);
        stopSelf();
    }
    private void showNotification() {
        CharSequence text = getText(R.string.local_service_started);

        mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(BackgroundService.this)
                .setVibrate(new long[]{0, 100, 100, 100, 100, 100})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(newtext)
                .setContentText(text);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,new Intent(this, Starter.class), 0);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        mNM.notify(R.string.local_service_started, mBuilder.build());
    }
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class RemindTask extends TimerTask {
        int count = 10;
        BackgroundService bs;

        public RemindTask(BackgroundService backgroundService) {
            bs = backgroundService;
        }

        public void run() {
            if (count > 0) {
                System.out.println("Beep!");
                bs.showNotification();
                count--;
            } else {
                System.out.println("Time's up!");
                BackgroundService.timer.cancel(); // Not necessary because
            }
        }
    }
}
