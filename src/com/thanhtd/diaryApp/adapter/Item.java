package com.thanhtd.diaryApp.adapter;

/**
 * Created by a on 09/02/2015.
 */
public class Item
{
    private String systol;
    private String diasol;
    private String level;
    private String pulse;
    private String time;

    public Item(String systol, String diasol, String level, String pulse, String time)
    {
        this.systol = systol;
        this.diasol = diasol;
        this.level = level;
        this.pulse = pulse;
        this.time = time;
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
}
