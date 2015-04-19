package com.thanhtd.diaryApp.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import com.thanhtd.diaryApp.R;
import com.thanhtd.diaryApp.fragment.MainFragment;

/**
 * Created by a on 17/01/2015.
 */
public class DisplayEnable implements Runnable
{
    Context context;
    NotificationManager notificationManager;

    public DisplayEnable(Context context)
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
        Intent intent = new Intent(context, MainFragment.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle("Volume Booster")
                .setContentText("Enable")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.icon_arrow_drop_down)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_arrow_drop_down));
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

    public void stop()
    {
        notificationManager.cancel(1);
    }
}
