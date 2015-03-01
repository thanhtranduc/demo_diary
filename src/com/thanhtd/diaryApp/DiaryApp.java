package com.thanhtd.diaryApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import com.example.material.MaterialMenuDrawable;
import com.example.material.MaterialMenuView;
import com.thanhtd.diaryApp.adapter.Item;
import com.thanhtd.diaryApp.adapter.ListAdapter;
import com.thanhtd.diaryApp.data.DatabaseHelper;
import com.thanhtd.diaryApp.data.SingletonHolder;
import com.thanhtd.diaryApp.data.model.ItemModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiaryApp extends Activity
{
    private MaterialMenuView materialMenuView;
    private ListView materialMenu;
    private DrawerLayout drawerLayout;
    private ListView lvDiary;
    private Boolean longClickHere = false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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

        ListAdapter adapter = new ListAdapter(this);
        DatabaseHelper databaseHelper = new DatabaseHelper(this, "diary.db");
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

            }
        });
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
