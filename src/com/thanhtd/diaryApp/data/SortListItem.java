package com.thanhtd.diaryApp.data;

import com.thanhtd.diaryApp.data.model.ItemModel;

import java.util.Comparator;

/**
 * Created by a on 09/03/2015.
 */
public class SortListItem implements Comparator<ItemModel>
{

    @Override
    public int compare(ItemModel lhs, ItemModel rhs)
    {
        return lhs.getDate().compareTo(rhs.getDate());
    }
}
