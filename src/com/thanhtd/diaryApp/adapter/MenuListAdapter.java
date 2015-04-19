package com.thanhtd.diaryApp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.thanhtd.diaryApp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a on 02/03/2015.
 */
public class MenuListAdapter extends BaseExpandableListAdapter
{
    private List<MenuItem> groups = new ArrayList<MenuItem>();
    private Context context;

    public MenuListAdapter(List<MenuItem> groups, Context context)
    {
        this.groups = groups;
        this.context = context;
    }

    @Override
    public int getGroupCount()
    {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return groups.get(groupPosition).getChild().size();
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return groups.get(groupPosition).getChild().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return 0;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        if (((MenuItem) getGroup(groupPosition)).getIcon() == -1)
        {
            ((TextView) convertView.findViewById(R.id.title)).setText(((MenuItem) getGroup(groupPosition)).getTitle());
            ((TextView) convertView.findViewById(R.id.title)).setTextColor(context.getResources().getColor(R.color.gray));
        }
        else
        {
            convertView.findViewById(R.id.icon).setBackgroundResource(((MenuItem) getGroup(groupPosition)).getIcon());
            ((TextView) convertView.findViewById(R.id.title)).setText(((MenuItem) getGroup(groupPosition)).getTitle());
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        if (getChild(groupPosition, childPosition) != null)
        {
            if (convertView == null)
            {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_item_child, null);
            }
            if (((MenuItem) getChild(groupPosition, childPosition)).getIcon() == -1)
            {
                ((TextView) convertView.findViewById(R.id.child_title)).setText(((MenuItem) getChild(groupPosition, childPosition)).getTitle());
                ((TextView) convertView.findViewById(R.id.child_title)).setTextColor(context.getResources().getColor(R.color.red));
            }
            else
            {
                convertView.findViewById(R.id.child_icon).setBackgroundResource(((MenuItem) getChild(groupPosition, childPosition)).getIcon());
                ((TextView) convertView.findViewById(R.id.child_title)).setText(((MenuItem) getChild(groupPosition, childPosition)).getTitle());
            }
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }
}
