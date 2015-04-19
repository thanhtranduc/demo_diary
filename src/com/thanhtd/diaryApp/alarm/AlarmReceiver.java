package com.thanhtd.diaryApp.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver
{
// ------------------------------ FIELDS ------------------------------

    private static final String TAG = "AlarmReceiver";
    //TODO: temporary accept
    public static final String PACKAGE_NAME = "com.example.controlvolume";
    public static Boolean ALERT_STATUS = true;
// -------------------------- OTHER METHODS --------------------------

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d(TAG, "Alarm received. Requesting synchronize sync.....");
        Handler mHandler = new Handler();
        mHandler.post(new DisplayNotification(context));
    }
}