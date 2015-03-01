package com.thanhtd.diaryApp.data.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.thanhtd.diaryApp.adapter.Item;

/**
 * Created by a on 21/02/2015.
 */
@DatabaseTable(tableName = "Item")
public class ItemModel
{
    @DatabaseField
    String systol;
    @DatabaseField
    String diasol;
    @DatabaseField
    String level;
    @DatabaseField
    String pulse;
    @DatabaseField
    String time;
    @DatabaseField
    String date;
    @DatabaseField
    Boolean isCardiac;

    public ItemModel()
    {
    }

    public ItemModel(Item item)
    {
        this.systol = item.getSystol();
        this.diasol = item.getDiasol();
        this.level = item.getLevel();
        this.pulse = item.getPulse();
        this.time = item.getTime();
        this.date = item.getDate();
        this.isCardiac = item.isCardiac();
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

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public Boolean getIsCardiac()
    {
        return isCardiac;
    }

    public void setIsCardiac(Boolean isCardiac)
    {
        this.isCardiac = isCardiac;
    }
}
