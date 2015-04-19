package com.thanhtd.diaryApp.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import com.thanhtd.diaryApp.DiaryApp;
import com.thanhtd.diaryApp.R;

/**
 * Created by a on 17/01/2015.
 */
public class DisplayNotification implements Runnable
{
    Context context;
    NotificationManager notificationManager;

    public DisplayNotification(Context context)
    {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public void run()
    {
        makeNotification(context);
    }

    private void makeNotification(Context context)
    {
        Intent intent = new Intent(context, DiaryApp.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle("Measure BP")
                .setContentText("Time to measure you bp")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.showing)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.showing));
        Notification n;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            n = builder.build();
        }
        else
        {
            n = builder.getNotification();
        }

        n.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;

        notificationManager.notify(1, n);
    }
}
