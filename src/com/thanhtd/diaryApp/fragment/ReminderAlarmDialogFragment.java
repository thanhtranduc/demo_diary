package com.thanhtd.diaryApp.fragment;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import com.thanhtd.diaryApp.R;
import com.thanhtd.diaryApp.alarm.AlarmReceiver;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Admin on 4/10/15.
 */
public class ReminderAlarmDialogFragment extends DialogFragment
{

    CheckBox cbSunday;
    CheckBox cbMonday;
    CheckBox cbTue;
    CheckBox cbWeds;
    CheckBox cbThu;
    CheckBox cbFriday;
    CheckBox cbSat;
    NumberPicker npHour;
    NumberPicker npMinute;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.reminder_alarm_dialog, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Date date = new Date();
        npHour = (NumberPicker) rootView.findViewById(R.id.reminder_alarmHour);
        npHour.setMaxValue(24);
        npHour.setMinValue(0);
        npHour.setValue(date.getHours());
        npHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {
                npHour.setValue(newVal);
            }
        });
        npMinute = (NumberPicker) rootView.findViewById(R.id.reminder_alarmMinute);
        npMinute.setMaxValue(60);
        npMinute.setMinValue(0);
        npMinute.setValue(date.getMinutes());
        npMinute.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {
                npMinute.setValue(newVal);
            }
        });
        cbSunday = (CheckBox) rootView.findViewById(R.id.reminder_alarmCbSunday);
        cbMonday = (CheckBox) rootView.findViewById(R.id.reminder_alarmCbMonday);
        cbTue = (CheckBox) rootView.findViewById(R.id.reminder_alarmCbTue);
        cbWeds = (CheckBox) rootView.findViewById(R.id.reminder_alarmCbWeds);
        cbThu = (CheckBox) rootView.findViewById(R.id.reminder_alarmCbThu);
        cbFriday = (CheckBox) rootView.findViewById(R.id.reminder_alarmCbFriday);
        cbSat = (CheckBox) rootView.findViewById(R.id.reminder_alarmCbSat);

        Button btCancel = (Button) rootView.findViewById(R.id.reminder_alarm_btCancel);
        Button btSave = (Button) rootView.findViewById(R.id.reminder_alarm_btSave);

        btSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(getActivity(), AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
                if (cbSunday.isChecked())
                {
                    setupAlarmForDay(1);
                }
                if (cbMonday.isChecked())
                {
                    setupAlarmForDay(2);
                }
                if (cbThu.isChecked())
                {
                    setupAlarmForDay(3);
                }
                if (cbWeds.isChecked())
                {
                    setupAlarmForDay(4);
                }
                if (cbThu.isChecked())
                {
                    setupAlarmForDay(5);
                }
                if (cbFriday.isChecked())
                {
                    setupAlarmForDay(6);
                }
                if (cbSat.isChecked())
                {
                    setupAlarmForDay(7);
                }
                dismiss();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
        return rootView;
    }

    private void setupAlarmForDay(int day)
    {
        Calendar calendarNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calendarNow.clone();
        calSet.set(Calendar.DAY_OF_WEEK, day);
        calSet.set(Calendar.HOUR_OF_DAY, npHour.getValue());
        calSet.set(Calendar.MINUTE, npMinute.getValue());

        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);
        AlarmManager alarm = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), 7 * AlarmManager.INTERVAL_DAY, pendingIntent);
//        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), 600, pendingIntent);
        AlarmReceiver.ALERT_STATUS = true;
    }
}
