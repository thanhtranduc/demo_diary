package com.thanhtd.diaryApp.adapter;

import android.os.Parcel;
import android.os.Parcelable;
import com.thanhtd.diaryApp.data.model.ItemModel;

/**
 * Created by a on 09/02/2015.
 */
public class Item implements Parcelable
{
    private String systol;
    private String diasol;
    private String level;
    private String pulse;
    private String time;

    public Item(ItemModel itemModel)
    {
        this.systol = itemModel.getSystol();
        this.diasol = itemModel.getDiasol();
        this.level = itemModel.getLevel();
        this.pulse = itemModel.getPulse();
        this.time = itemModel.getTime();
    }

    public Item(String systol, String diasol, String level, String pulse, String time)
    {
        this.systol = systol;
        this.diasol = diasol;
        this.level = level;
        this.pulse = pulse;
        this.time = time;
    }

    public Item(Parcel parcel)
    {
        systol = parcel.readString();
        diasol = parcel.readString();
        level = parcel.readString();
        pulse = parcel.readString();
        time = parcel.readString();
    }

    public String getSystol()
    {
        return systol;
    }

    public void setSystol(String systol)
    {
        this.systol = systol;
    }

    public String getDiasol()
    {
        return diasol;
    }

    public void setDiasol(String diasol)
    {
        this.diasol = diasol;
    }

    public String getLevel()
    {
        return level;
    }

    public void setLevel(String level)
    {
        this.level = level;
    }

    public String getPulse()
    {
        return pulse;
    }

    public void setPulse(String pulse)
    {
        this.pulse = pulse;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(systol);
        dest.writeString(diasol);
        dest.writeString(level);
        dest.writeString(pulse);
        dest.writeString(time);
    }

    public static final Creator<Item> CREATOR = new Creator<Item>()
    {
        @Override
        public Item createFromParcel(Parcel source)
        {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size)
        {
            return new Item[0];
        }
    };
}
