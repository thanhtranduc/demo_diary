package com.thanhtd.diaryApp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

/**
 * Created by a on 11/02/2015.
 */
public class AddDiaryLog extends Activity
{
    TextView tvTime;
    private int year;
    private int month;
    private int day;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_diary_log);
        NumberPicker npSystolic = (NumberPicker) findViewById(R.id.add_diary_log_np1);
        NumberPicker npDiastolic = (NumberPicker) findViewById(R.id.add_diary_log_np2);
        NumberPicker npPulse = (NumberPicker) findViewById(R.id.add_diary_log_np3);
        npSystolic.setMinValue(0);
        npSystolic.setMaxValue(300);
        npSystolic.setWrapSelectorWheel(false);
        npSystolic.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {

            }
        });

        npDiastolic.setMinValue(0);
        npDiastolic.setMaxValue(300);
        npDiastolic.setWrapSelectorWheel(false);
        npDiastolic.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {

            }
        });

        npPulse.setMinValue(0);
        npPulse.setMaxValue(300);
        npPulse.setWrapSelectorWheel(false);
        npPulse.setOnValueChangedListener(new NumberPicker.OnValueChangeListener()
        {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal)
            {

            }
        });

        final Spinner spinner1 = (Spinner) findViewById(R.id.add_diary_log_spinner1);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.place_array, R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);


        Spinner spinner2 = (Spinner) findViewById(R.id.add_diary_log_spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.position_array, R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        tvTime = (TextView) findViewById(R.id.add_diary_log_tvTime);
        tvTime.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new DatePickerDialog(getApplication(), datePickerListener, year, month, day).show();
            }
        });
    }


    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            tvTime.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

            // set selected date into datepicker also

        }
    };
}
