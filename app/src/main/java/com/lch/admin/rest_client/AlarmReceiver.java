package com.lch.admin.rest_client;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        // an Intent broadcast.
        NotificationManager mNM;
        mNM = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
        // Set the icon, scrolling text and timestamp
        Notification notification = new NotificationCompat.Builder(context)
        .setContentTitle("Signal Alert")
        .setContentText("Open position ratio changed")
        .setSmallIcon(R.drawable.ic_launcher)
        .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .build();

        //      System.currentTimeMillis());
        // The PendingIntent to launch our activity if the user selects this notification

        // Set the info for the views that show in the notification panel.
        //notification.setLatestEventInfo(context, context.getText(R.string.alarm_service_label), "This is a Test Alarm", contentIntent);
        // Send the notification.
        // We use a layout id because it is a unique number. We use it later to cancel.
        mNM.notify(R.string.alarm_service_label, notification);
    }
}
