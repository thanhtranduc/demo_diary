package com.thanhtd.diaryApp.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.thanhtd.diaryApp.R;

/**
 * Created by Admin on 4/10/15.
 */
public class ReminderAlarmDialogFragment extends DialogFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.reminder_alarm_dialog, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return rootView;
    }
}
