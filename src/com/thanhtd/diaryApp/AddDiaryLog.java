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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    ItemModel itemModel;
    EditText etComment;
    Spinner spinner1;
    Spinner spinner2;

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
                npSystolic.setValue(newVal);
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
                npDiastolic.setValue(newVal);
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
                npPulse.setValue(newVal);
            }
        });

        spinner1 = (Spinner) findViewById(R.id.add_diary_log_spinner1);

        String[] data = getResources().getStringArray(R.array.place_array);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.simple_spinner_item, R.id.style_spinner, data);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        setNumberPickerTextColor(npSystolic, getResources().getColor(R.color.black));
        setNumberPickerTextColor(npDiastolic, getResources().getColor(R.color.black));
        setNumberPickerTextColor(npPulse, getResources().getColor(R.color.black));

        spinner2 = (Spinner) findViewById(R.id.add_diary_log_spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.position_array, R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        tvTime = (TextView) findViewById(R.id.add_diary_log_tvTime);
        tvDate = (TextView) findViewById(R.id.add_diary_log_tvDate);
        btAdd = (Button) findViewById(R.id.add_diary_log_btAdd);
        final CheckBox cbCardiac = (CheckBox) findViewById(R.id.add_diary_log_cbCardiac);
        etComment = (EditText) findViewById(R.id.add_diary_log_etComment);

        if (getIntent().getExtras() != null && getIntent().getExtras().get("id") != null)
        {
            //todo
            DatabaseHelper databaseHelper = new DatabaseHelper(this, "diary.db");
            try
            {
                itemModel = databaseHelper.getDaoItem().queryForId((Long) getIntent().getExtras().get("id"));
                npSystolic.setValue(Integer.parseInt(itemModel.getSystol()));
                npDiastolic.setValue(Integer.parseInt(itemModel.getDiasol()));
                npPulse.setValue(Integer.parseInt(itemModel.getPulse()));

                final Date time = new Date(itemModel.getTime());
                DateFormat formatter = new SimpleDateFormat("hh:mm a");
                tvTime.setText(formatter.format(time));


                final Date date = new Date(itemModel.getDate());
                SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
                tvDate.setText(df2.format(date));
                spinner1.setSelection(itemModel.getPlaceMeasurement().intValue());
                spinner2.setSelection(itemModel.getPositionMeasurement().intValue());
                cbCardiac.setChecked(itemModel.getIsCardiac());
                etComment.setText(itemModel.getComment());

                tvTime.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        TimePickerDialog.newInstance(timeSetListener, time.getHours(), time.getMinutes(), false)
                                .show(getSupportFragmentManager(), "");
                    }
                });

                tvDate.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        new DatePickerDialog(AddDiaryLog.this, datePickerListener, date.getYear(), date.getMonth(),
                                date.getDay()).show();
                    }
                });

                btAdd.setText("Edit Record");
                btAdd.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(getApplicationContext(), DiaryApp.class);
                        try
                        {
                            if (etComment.getText() != null)
                            {
                                itemModel.setComment(etComment.getText().toString());
                            }
                            itemModel.setIsCardiac(cbCardiac.isChecked());
                            itemModel.setSystol(String.valueOf(npSystolic.getValue()));
                            itemModel.setDiasol(String.valueOf(npDiastolic.getValue()));
                            itemModel.setPulse(String.valueOf(npPulse.getValue()));
                            itemModel.setPlaceMeasurement(spinner1.getSelectedItemId());
                            itemModel.setPositionMeasurement(spinner2.getSelectedItemId());
                            SingletonHolder.getInstance().get(DatabaseHelper.class).getDaoItem().update(itemModel);
                        }
                        catch (SQLException e)
                        {
                            e.printStackTrace();
                        }
                        startActivity(intent);
                    }
                });

            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            itemModel = new ItemModel();
            btAdd.setText("ADD");
            setupNewDiary(npSystolic, npDiastolic, npPulse, cbCardiac, itemModel);
        }
    }

    private void setupNewDiary(final NumberPicker npSystolic, final NumberPicker npDiastolic, final NumberPicker npPulse, final CheckBox cbCardiac, final ItemModel itemModel)
    {
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

        tvDate.setText(calendar.get(Calendar.MONTH) + 1 + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.YEAR));

        tvDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new DatePickerDialog(AddDiaryLog.this, datePickerListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), DiaryApp.class);
                Item item = new Item(String.valueOf(npSystolic.getValue()), String.valueOf(npDiastolic.getValue()),
                        String.valueOf(npPulse.getValue()), tvTime.getText().toString(), tvDate.getText().toString(),
                        String.valueOf(etComment.getText()), cbCardiac.isChecked());
                try
                {
                    ItemModel itemModel_ = new ItemModel(item);
                    itemModel_.setPlaceMeasurement(spinner1.getSelectedItemId());
                    itemModel_.setPositionMeasurement(spinner2.getSelectedItemId());
                    itemModel_.setTime(itemModel.getTime() != null ? itemModel.getTime() : Long.valueOf(calendar.getTimeInMillis()));
                    itemModel_.setDate(itemModel.getDate() != null ? itemModel.getDate() : Long.valueOf(calendar.getTimeInMillis()));
                    SingletonHolder.getInstance().get(DatabaseHelper.class).getDaoItem().create(itemModel_);
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
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.AM_PM, view.getIsCurrentlyAmOrPm());
            itemModel.setTime(calendar.getTimeInMillis());
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
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            itemModel.setDate(calendar.getTimeInMillis());
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
