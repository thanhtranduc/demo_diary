package com.thanhtd.diaryApp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.thanhtd.diaryApp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by a on 09/02/2015.
 */
public class ListAdapter extends BaseAdapter
{
    private List<Item> groups = new ArrayList<Item>();
    private Context context;

    private HashMap<Integer, Boolean> mSelection = new HashMap<Integer, Boolean>();

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
            viewHolder.imStatus = (ImageView) convertView.findViewById(R.id.diary_item_imStatus);
            viewHolder.ivCardiac = (ImageView) convertView.findViewById(R.id.diary_item_ivCardiac);
            viewHolder.rlCardiac = (RelativeLayout) convertView.findViewById(R.id.diary_item_rlCardiac);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        Item item = (Item) getItem(position);
        viewHolder.tvSystol.setText(item.getSystol());
        viewHolder.tvDiastol.setText(item.getDiasol());
        viewHolder.tvPulse.setText(item.getPulse());
        viewHolder.tvTitleTime.setText(item.getDate());
        viewHolder.tvValueTime.setText(item.getTime());
        viewHolder.rlCardiac.setVisibility(item.isCardiac() ? View.VISIBLE : View.INVISIBLE);
        if (item.getSystol() != null && item.getDiasol() != null)
        {
            viewHolder.imStatus.setBackgroundResource(convertToColor(item.getSystol(), item.getDiasol()));
        }

        return convertView;
    }

    private class ViewHolder
    {
        TextView tvSystol;
        TextView tvDiastol;
        ImageView imStatus;
        TextView tvPulse;
        TextView tvTitleTime;
        TextView tvValueTime;
        ImageView ivCardiac;
        RelativeLayout rlCardiac;
    }

    private int convertToColor(String systol, String diastol)
    {
        int systol_ = Integer.valueOf(systol);
        int diastol_ = Integer.valueOf(diastol);
        if (systol_ > 180 || diastol_ > 110)
        {
            return R.drawable.ic_heart_red;
        }
        else if ((systol_ > 160 && systol_ <= 180) || (diastol_ > 100 && diastol_ <= 110))
        {
            return R.drawable.ic_heart_orange;
        }
        else if ((systol_ > 140 && systol_ <= 160) || (diastol_ > 90 && diastol_ <= 100))
        {
            return R.drawable.ic_heart_gray;
        }
        else if ((systol_ > 120 && systol_ <= 140) || (diastol_ > 80 && diastol_ <= 90))
        {
            return R.drawable.ic_heart_yellow;
        }
        else if ((systol_ > 90 && systol_ <= 120) || (diastol_ > 60 && diastol_ <= 80))
        {
            return R.drawable.ic_heart_green;
        }
        else if (systol_ <= 90 || diastol_ <= 60)
        {
            return R.drawable.ic_heart_blue;
        }
        return -1;
    }

    public List<Item> getGroups()
    {
        return groups;
    }

    public void setGroups(List<Item> groups)
    {
        this.groups = groups;
    }


    //----------------------------
    public void setNewSelection(int position, boolean value)
    {
        mSelection.put(position, value);
        notifyDataSetChanged();
    }

    public boolean isPositionChecked(int position)
    {
        Boolean result = mSelection.get(position);
        return result == null ? false : result;
    }

    public Set<Integer> getCurrentCheckedPosition()
    {
        return mSelection.keySet();
    }

    public void removeSelection(int position)
    {
        mSelection.remove(position);
        notifyDataSetChanged();
    }

    public void clearSelection()
    {
        mSelection = new HashMap<Integer, Boolean>();
        notifyDataSetChanged();
    }
}
