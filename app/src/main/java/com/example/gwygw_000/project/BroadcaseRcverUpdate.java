package com.example.gwygw_000.project;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by HuijunLin on 4/29/16.
 */
public class BroadcaseRcverUpdate extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        Intent i[] = new Intent[1];
        i[0] = new Intent(context, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivities(context, 1, i, 0);


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Break News")
                        .setContentText("Let's check it out")
                        .setContentIntent(pendingIntent);


        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());


    }
}
