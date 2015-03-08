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
    @DatabaseField(generatedId = true, columnName = "_id", allowGeneratedIdInsert = true)
    Long _id;
    @DatabaseField
    String systol;
    @DatabaseField
    String diasol;
    @DatabaseField
    String level;
    @DatabaseField
    String pulse;
    @DatabaseField
    Long time;
    @DatabaseField
    Long date;
    @DatabaseField
    Boolean isCardiac;
    @DatabaseField
    Long placeMeasurement;
    @DatabaseField
    Long positionMeasurement;
    @DatabaseField
    String comment;

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
        this.comment = item.getComment();
        this.isCardiac = item.isCardiac();
    }

    public Long get_id()
    {
        return _id;
    }

    public void set_id(Long _id)
    {
        this._id = _id;
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

    public Long getTime()
    {
        return time;
    }

    public void setTime(Long time)
    {
        this.time = time;
    }

    public Long getDate()
    {
        return date;
    }

    public void setDate(Long date)
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

    public Long getPlaceMeasurement()
    {
        return placeMeasurement;
    }

    public void setPlaceMeasurement(Long placeMeasurement)
    {
        this.placeMeasurement = placeMeasurement;
    }

    public Long getPositionMeasurement()
    {
        return positionMeasurement;
    }

    public void setPositionMeasurement(Long positionMeasurement)
    {
        this.positionMeasurement = positionMeasurement;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }
}
