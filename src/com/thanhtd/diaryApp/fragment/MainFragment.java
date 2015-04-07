package com.thanhtd.diaryApp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import com.thanhtd.diaryApp.AddDiaryLog;
import com.thanhtd.diaryApp.EditDiaryDialogFragment;
import com.thanhtd.diaryApp.R;
import com.thanhtd.diaryApp.adapter.Item;
import com.thanhtd.diaryApp.adapter.ListAdapter;
import com.thanhtd.diaryApp.data.DatabaseHelper;
import com.thanhtd.diaryApp.data.SingletonHolder;
import com.thanhtd.diaryApp.data.model.ItemModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by a on 09/03/2015.
 */
public class MainFragment extends Fragment
{
    private ListView lvDiary;

    public ListAdapter adapter;
    List<ItemModel> list;
    List<Item> groups;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        final DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(), "diary.db");
        SingletonHolder.getInstance().add(databaseHelper);
        adapter = new ListAdapter(getActivity());
        lvDiary = (ListView) view.findViewById(R.id.main_lvDiary);

        try
        {
            list = databaseHelper.getDaoItem().queryForAll();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        if (list != null && list.size() > 0)
        {
            getActivity().findViewById(R.id.main_tvHintAdd).setVisibility(View.GONE);
        }
        groups = new ArrayList<Item>();
        for (ItemModel itemModel : list)
        {
            groups.add(new Item(itemModel));
        }
        adapter.setGroups(groups);
        lvDiary.setAdapter(adapter);

        lvDiary.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                return false;
            }
        });
        lvDiary.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (adapter.getCurrentCheckedPosition().size() != 0)
                {
                    lvDiary.setItemChecked(position, !adapter.isPositionChecked(position));
                }
                else
                {
                    //todo
                    EditDiaryDialogFragment editDiaryDialogFragment = new EditDiaryDialogFragment();
                    Bundle bundle = new Bundle();
                    Item item = (Item) adapter.getItem(position);
                    bundle.putLong("id", item.getId());
                    bundle.putInt("position", position);
                    editDiaryDialogFragment.setArguments(bundle);
                    editDiaryDialogFragment.show(getActivity().getFragmentManager(), "show");
                }
            }
        });

        lvDiary.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lvDiary.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener()
        {
            private int nr = 0;
            Menu menu;

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked)
            {
                if (checked)
                {
                    nr++;
                    adapter.setNewSelection(position, checked);
                }
                else
                {
                    nr--;
                    adapter.removeSelection(position);
                }
                if (nr == 1 && menu != null)
                {
                    menu.findItem(R.id.edit_entry).setVisible(true);
                }
                else
                {
                    menu.findItem(R.id.edit_entry).setVisible(false);

                }
                mode.setTitle(nr + " rows selected");
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu)
            {
                MenuInflater inflater = getActivity().getMenuInflater();
                inflater.inflate(R.menu.cabselection_menu, menu);
                this.menu = menu;
                menu.findItem(R.id.edit_entry).setOnMenuItemClickListener(new android.view.MenuItem.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(android.view.MenuItem item)
                    {
                        Intent intent = new Intent(getActivity().getApplicationContext(), AddDiaryLog.class);
                        Set<Integer> integers = adapter.getCurrentCheckedPosition();
                        Integer integer = null;
                        for (Integer position : integers)
                        {
                            integer = position;
                        }
                        Item item1 = (Item) adapter.getItem(integer);
                        intent.putExtra("id", item1.getId());
                        startActivity(intent);
                        return true;
                    }
                });
                menu.findItem(R.id.delete_entry).setOnMenuItemClickListener(new android.view.MenuItem.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(android.view.MenuItem item)
                    {
                        Set<Integer> positions = adapter.getCurrentCheckedPosition();
                        for (int position : positions)
                        {
                            try
                            {
                                databaseHelper.getDaoItem().deleteById(adapter.getGroups().get(position).getId());
                            }
                            catch (SQLException e)
                            {
                                e.printStackTrace();
                            }

                        }
                        try
                        {
                            list = databaseHelper.getDaoItem().queryForAll();
                            groups.clear();
                            for (ItemModel itemModel : list)
                            {
                                groups.add(new Item(itemModel));
                            }
                        }
                        catch (SQLException e)
                        {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                        adapter.clearSelection();
                        nr = 0;
                        return true;
                    }
                });
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu)
            {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, android.view.MenuItem item)
            {
                StringBuilder sb = new StringBuilder();
                Set<Integer> positions = adapter.getCurrentCheckedPosition();
                for (Integer pos : positions)
                {
                    sb.append(" " + pos + ",");
                }
                switch (item.getItemId())
                {
                    case R.id.edit_entry:

                        break;
                    case R.id.delete_entry:
                        for (Integer position : positions)
                        {
                            adapter.getGroups().remove(position);
                            try
                            {
                                databaseHelper.getDaoItem().deleteById(adapter.getGroups().get(position).getId());
                            }
                            catch (SQLException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        getActivity().runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                adapter.notifyDataSetChanged();
                            }
                        });
                        break;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode)
            {
                nr = 0;
                adapter.clearSelection();
            }

        });

        return view;
    }
}
