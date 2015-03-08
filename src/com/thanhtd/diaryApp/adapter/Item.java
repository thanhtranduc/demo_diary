package com.thanhtd.diaryApp.adapter;

import com.thanhtd.diaryApp.data.model.ItemModel;

/**
 * Created by a on 09/02/2015.
 */
public class Item
{
    private Long id;
    private String systol;
    private String diasol;
    private String level;
    private String pulse;
    private String timeView;
    private Long time;
    private String dateView;
    private Long date;
    private boolean isCardiac = false;
    private String comment;

    public Item(ItemModel itemModel)
    {
        this.id = itemModel.get_id();
        this.systol = itemModel.getSystol();
        this.diasol = itemModel.getDiasol();
        this.level = itemModel.getLevel();
        this.pulse = itemModel.getPulse();
        this.time = itemModel.getTime();
        this.date = itemModel.getDate();
        this.isCardiac = itemModel.getIsCardiac();
    }

    public Item(String systol, String diasol, String pulse, String time, String date, String comment, Boolean isCardiac)
    {
        this.systol = systol;
        this.diasol = diasol;
        this.pulse = pulse;
        this.timeView = time;
        this.dateView = date;
        if (comment != null)
        {
            this.comment = comment;
        }
        this.isCardiac = isCardiac;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
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

    public String getTimeView()
    {
        return timeView;
    }

    public void setTimeView(String timeView)
    {
        this.timeView = timeView;
    }

    public String getDateView()
    {
        return dateView;
    }

    public void setDateView(String dateView)
    {
        this.dateView = dateView;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public boolean isCardiac()
    {
        return isCardiac;
    }

    public void setCardiac(boolean isCardiac)
    {
        this.isCardiac = isCardiac;
    }
}
