package com.thanhtd.diaryApp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.*;
import com.example.material.MaterialMenuDrawable;
import com.example.material.MaterialMenuView;
import com.thanhtd.diaryApp.adapter.Item;
import com.thanhtd.diaryApp.adapter.ListAdapter;
import com.thanhtd.diaryApp.adapter.MenuItem;
import com.thanhtd.diaryApp.adapter.MenuListAdapter;
import com.thanhtd.diaryApp.data.DatabaseHelper;
import com.thanhtd.diaryApp.data.SingletonHolder;
import com.thanhtd.diaryApp.data.model.ItemModel;
import com.thanhtd.diaryApp.fragment.AdviceFragment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DiaryApp extends FragmentActivity
{
    private MaterialMenuView materialMenuView;
    private ListView materialMenu;
    private DrawerLayout drawerLayout;
    private ListView lvDiary;
    ListAdapter adapter = new ListAdapter(this);
    TextView tvTitle;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tvTitle = (TextView) findViewById(R.id.main_tvTitle);
        tvTitle.setText("BP");
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        materialMenu = (ListView) findViewById(R.id.slider_list);
        ImageButton btAdd = (ImageButton) findViewById(R.id.main_ivAddDiary);
        materialMenuView = (MaterialMenuView) findViewById(R.id.main_material_menu_button);
        materialMenuView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (drawerLayout.isDrawerOpen(materialMenu))
                {
                    drawerLayout.closeDrawer(materialMenu);
                    materialMenuView.animatePressedState(intToState(0));
                }
                else
                {
                    drawerLayout.openDrawer(materialMenu);
                    materialMenuView.animatePressedState(intToState(1));
                }
            }
        });

        setupMenu(materialMenu);
        materialMenu.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (position == 0)
                {
                    drawerLayout.closeDrawer(materialMenu);
                    materialMenuView.animatePressedState(intToState(0));
                    startActivity(new Intent(DiaryApp.this, DiaryApp.class));
                }
                else if (position == 1)
                {
                    //fragment advices;
                    tvTitle.setText("Advices β");
                    drawerLayout.closeDrawer(materialMenu);
                    materialMenuView.animatePressedState(intToState(0));
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new AdviceFragment()).commit();
                    getSupportFragmentManager().executePendingTransactions();
                }
                else if (position == 2 || position == 3)
                {
                    new AlertDialog.Builder(DiaryApp.this)
                            .setTitle("Notify")
                            .setMessage("We recommend this useful and FREE App for your Better Health!. Would you like " +
                                    "to get it?")
                            .setNegativeButton("Yes", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("No Thanks", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    dialog.dismiss();
                                }
                            }).show();
                }
                else if (position == 4)
                {
                    //reminders
                }
                else if (position == 5)
                {
                    //settings
                }
                else if (position == 6)
                {
                    //rate
                }
            }
        });

        final DatabaseHelper databaseHelper = new DatabaseHelper(this, "diary.db");
        SingletonHolder.getInstance().add(databaseHelper);

        lvDiary = (ListView) findViewById(R.id.main_lvDiary);
        List<ItemModel> list = null;
        try
        {
            list = databaseHelper.getDaoItem().queryForAll();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        List<Item> groups = new ArrayList<Item>();
        for (ItemModel itemModel : list)
        {
            groups.add(new Item(itemModel));
        }
        adapter.setGroups(groups);
        lvDiary.setAdapter(adapter);
        btAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), AddDiaryLog.class);
                startActivity(intent);
            }
        });
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
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.cabselection_menu, menu);
                this.menu = menu;
                menu.findItem(R.id.edit_entry).setOnMenuItemClickListener(new android.view.MenuItem.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(android.view.MenuItem item)
                    {
                        Intent intent = new Intent(getApplicationContext(), AddDiaryLog.class);
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
                            adapter.notifyDataSetChanged();
                        }
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
                            adapter.notifyDataSetChanged();
                        }
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
    }

    private void setupMenu(ListView materialMenu)
    {
        MenuItem BP = new MenuItem("BP", R.drawable.ic_action_heart);
        MenuItem advises = new MenuItem("Advises", R.drawable.ic_action_accept);
        MenuItem reminders = new MenuItem("Reminders", R.drawable.ic_action_alarms);
        MenuItem app1 = new MenuItem("App 1", -1);
        MenuItem app2 = new MenuItem("App 2", -1);
        MenuItem setting = new MenuItem("Settings", R.drawable.ic_action_settings);
        List<MenuItem> groups = new ArrayList<MenuItem>();
        groups.add(BP);
        groups.add(advises);
        groups.add(app1);
        groups.add(app2);
        groups.add(reminders);
        groups.add(setting);
        materialMenu.setAdapter(new MenuListAdapter(groups, DiaryApp.this));
    }

    public static MaterialMenuDrawable.IconState intToState(int state)
    {
        switch (state)
        {
            case 0:
                return MaterialMenuDrawable.IconState.BURGER;
            case 1:
                return MaterialMenuDrawable.IconState.ARROW;
            case 2:
                return MaterialMenuDrawable.IconState.X;
            case 3:
                return MaterialMenuDrawable.IconState.CHECK;
        }
        throw new IllegalArgumentException("Must be a number [0,3)");
    }
}
