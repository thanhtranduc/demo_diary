package com.thanhtd.diaryApp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.thanhtd.diaryApp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a on 02/03/2015.
 */
public class MenuListAdapter extends BaseAdapter
{
    private List<MenuItem> groups = new ArrayList<MenuItem>();
    private Context context;

    public MenuListAdapter(List<MenuItem> groups, Context context)
    {
        this.groups = groups;
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return groups.size();
    }

    @Override
    public Object getItem(int position)
    {
        return groups.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        if (((MenuItem) getItem(position)).getIcon() == -1)
        {
            ((TextView) convertView.findViewById(R.id.title)).setText(((MenuItem) getItem(position)).getTitle());
            ((TextView) convertView.findViewById(R.id.title)).setTextColor(context.getResources().getColor(R.color.gray));
        }
        else
        {
            convertView.findViewById(R.id.icon).setBackgroundResource(((MenuItem) getItem(position)).getIcon());
            ((TextView) convertView.findViewById(R.id.title)).setText(((MenuItem) getItem(position)).getTitle());
        }
        return convertView;
    }


}
