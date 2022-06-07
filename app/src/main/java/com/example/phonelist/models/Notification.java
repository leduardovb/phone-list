package com.example.phonelist.models;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.phonelist.R;

public class Notification {

    private NotificationCompat.Builder builder;
    private NotificationManagerCompat managerCompat;
    private final Context context;
    private static Integer notificationId = 1;

    public Notification(Context context) {
        this.context = context;
    }

    private void setBuilder(String title, String contentText, Integer icon, Intent intent) {
        PendingIntent landingPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        builder = new NotificationCompat.Builder(context, context.getString(R.string.CHANNEL_ID));
        builder.setContentTitle(title);
        builder.setContentText(contentText);
        builder.setSmallIcon(icon);
        builder.setAutoCancel(true);
        builder.setContentIntent(landingPendingIntent);
        setManagerCompat();
    }

    private void setBuilder(String title, String contentText, Integer icon) {
        builder = new NotificationCompat.Builder(context, context.getString(R.string.CHANNEL_ID));
        builder.setContentTitle(title);
        builder.setContentText(contentText);
        builder.setSmallIcon(icon);
        builder.setAutoCancel(true);
        setManagerCompat();
    }

    private void setManagerCompat() {
        managerCompat = NotificationManagerCompat.from(context);
    }

    public void showNotification(String title, String contentText, Integer icon, Intent intent) {
        setBuilder(title, contentText, icon, intent);
        managerCompat.notify(notificationId, builder.build());
        notificationId = notificationId + 1;
    }

    public void showNotification(String title, String contentText, Integer icon) {
        setBuilder(title, contentText, icon);
        managerCompat.notify(notificationId, builder.build());
        notificationId = notificationId + 1;
    }

}
