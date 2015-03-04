package com.thanhtd.diaryApp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.thanhtd.diaryApp.adapter.Item;
import com.thanhtd.diaryApp.data.DatabaseHelper;
import com.thanhtd.diaryApp.data.SingletonHolder;
import com.thanhtd.diaryApp.data.model.ItemModel;
import com.zenkun.datetimepicker.time.RadialPickerLayout;
import com.zenkun.datetimepicker.time.TimePickerDialog;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * Created by a on 11/02/2015.
 */
public class AddDiaryLog extends FragmentActivity
{
    TextView tvTime;
    TextView tvDate;
    Button btAdd;
    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_diary_log);
        final NumberPicker npSystolic = (NumberPicker) findViewById(R.id.add_diary_log_np1);
        final NumberPicker npDiastolic = (NumberPicker) findViewById(R.id.add_diary_log_np2);
        final NumberPicker npPulse = (NumberPicker) findViewById(R.id.add_diary_log_np3);
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

        String[] data = getResources().getStringArray(R.array.place_array);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.simple_spinner_item, R.id.style_spinner, data);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        setNumberPickerTextColor(npSystolic, getResources().getColor(R.color.black));
        setNumberPickerTextColor(npDiastolic, getResources().getColor(R.color.black));
        setNumberPickerTextColor(npPulse, getResources().getColor(R.color.black));

        Spinner spinner2 = (Spinner) findViewById(R.id.add_diary_log_spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.position_array, R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        tvTime = (TextView) findViewById(R.id.add_diary_log_tvTime);
        final Calendar calendar = Calendar.getInstance();
        tvTime.setText(calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + (calendar.get(Calendar.AM_PM) == 1 ? " AM" : " PM"));

        tvTime.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TimePickerDialog.newInstance(timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false)
                        .show(getSupportFragmentManager(), "");
            }
        });
        tvDate = (TextView) findViewById(R.id.add_diary_log_tvDate);
        tvDate.setText(calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.YEAR));

        tvDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new DatePickerDialog(AddDiaryLog.this, datePickerListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final CheckBox cbCardiac = (CheckBox) findViewById(R.id.add_diary_log_cbCardiac);

        btAdd = (Button) findViewById(R.id.add_diary_log_btAdd);
        btAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), DiaryApp.class);
                Item item = new Item(String.valueOf(npSystolic.getValue()), String.valueOf(npDiastolic.getValue()),
                        String.valueOf(npPulse.getValue()), tvTime.getText().toString(), tvDate.getText().toString(), cbCardiac.isChecked());
                try
                {
                    ItemModel itemModel = new ItemModel(item);
                    SingletonHolder.getInstance().get(DatabaseHelper.class).getDaoItem().create(itemModel);
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });
    }

    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener()
    {
        @Override
        public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute)
        {
            tvTime.setText(hourOfDay + ":" + minute + " " + (view.getIsCurrentlyAmOrPm() == 0 ? "AM" : "PM"));
        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener()
    {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay)
        {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
//            String nameOfMonth = AddDiaryLog.this.getResources().getStringArray(R.array.month_names)[month];
            tvDate.setText(new StringBuilder().append(month + 1)
                    .append("/").append(day).append("/").append(year)
                    .append(" "));
        }
    };

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color)
    {
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++)
        {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText)
            {
                try
                {
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText) child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                }
                catch (NoSuchFieldException e)
                {
                    Log.w("setNumberPickerTextColor", e);
                }
                catch (IllegalAccessException e)
                {
                    Log.w("setNumberPickerTextColor", e);
                }
                catch (IllegalArgumentException e)
                {
                    Log.w("setNumberPickerTextColor", e);
                }
            }
        }
        return false;
    }
}
