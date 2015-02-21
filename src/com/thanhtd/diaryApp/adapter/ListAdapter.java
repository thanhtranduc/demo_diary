package com.thanhtd.diaryApp.adapter;

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
 * Created by a on 09/02/2015.
 */
public class ListAdapter extends BaseAdapter
{
    private List<Item> groups = new ArrayList<Item>();
    private Context context;

    public ListAdapter(Context context)
    {
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
        ViewHolder viewHolder;
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.diary_item, null);
            viewHolder.tvSystol = (TextView) convertView.findViewById(R.id.diary_item_tvSystol);
            viewHolder.tvDiastol = (TextView) convertView.findViewById(R.id.diary_item_tvDiasol);
            viewHolder.tvPulse = (TextView) convertView.findViewById(R.id.diary_item_tvPulse);
            viewHolder.tvTitleTime = (TextView) convertView.findViewById(R.id.diary_item_tvTitleTime);
            viewHolder.tvValueTime = (TextView) convertView.findViewById(R.id.diary_item_tvValueTime);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        Item item = (Item) getItem(position);
        viewHolder.tvSystol.setText(item.getSystol());
        viewHolder.tvDiastol.setText(item.getDiasol());
        viewHolder.tvPulse.setText(item.getPulse());
        return convertView;
    }

    private class ViewHolder
    {
        TextView tvSystol;
        TextView tvDiastol;
        TextView tvPulse;
        TextView tvTitleTime;
        TextView tvValueTime;
    }

    public List<Item> getGroups()
    {
        return groups;
    }

    public void setGroups(List<Item> groups)
    {
        this.groups = groups;
    }
}
