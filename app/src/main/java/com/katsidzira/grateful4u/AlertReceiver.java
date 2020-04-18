package com.katsidzira.grateful4u;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlertReceiver extends BroadcastReceiver {
    public static final String CHANNEL_ID = "channel 2";
    public static final int NOTIFICATION_ID = 2;
    NotificationManagerCompat notificationManagerCompat;

    @Override
    public void onReceive(Context context, Intent intent) {

        createNotificationChannel(context);
        sendOnChannel1(context);

    }

    public void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_ID, "channel2", NotificationManager.IMPORTANCE_DEFAULT);

            channel1.setDescription("This is channel 2");

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }

    public void sendOnChannel1(Context context) {
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                .setContentTitle("Time to write!")
                .setContentText("Gather your thoughts and jot them down.")
                .build();

        notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(NOTIFICATION_ID, notification);
    }
}
