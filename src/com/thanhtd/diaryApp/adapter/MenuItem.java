package com.thanhtd.diaryApp.adapter;

/**
 * Created by a on 16/01/2015.
 */
public class MenuItem
{
    private String title;
    private int icon;

    public MenuItem(String title, int icon)
    {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getIcon()
    {
        return icon;
    }

    public void setIcon(int icon)
    {
        this.icon = icon;
    }
}
