package com.thanhtd.diaryApp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.*;
import com.example.material.MaterialMenuDrawable;
import com.example.material.MaterialMenuView;
import com.thanhtd.diaryApp.adapter.MenuItem;
import com.thanhtd.diaryApp.adapter.MenuListAdapter;
import com.thanhtd.diaryApp.fragment.AdviceFragment;
import com.thanhtd.diaryApp.fragment.GraphViewFragment;
import com.thanhtd.diaryApp.fragment.MainFragment;
import com.thanhtd.diaryApp.fragment.ReminderAlarmDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class DiaryApp extends FragmentActivity
{
    private MaterialMenuView materialMenuView;
    private ListView materialMenu;
    private DrawerLayout drawerLayout;
    public MainFragment mainFragment;
    TextView tvTitle;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tvTitle = (TextView) findViewById(R.id.main_tvTitle);
        ImageView ivGraph = (ImageView) findViewById(R.id.main_ivGraph);
        ivGraph.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new GraphViewFragment()).commit();
                getSupportFragmentManager().executePendingTransactions();
            }
        });
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
                    tvTitle.setText("BP");
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new MainFragment()).commit();
                    getSupportFragmentManager().executePendingTransactions();
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
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.facebook.katana"));
                                    startActivity(browserIntent);
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
//                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new ReminderAlarmDialogFragment()).commit();
//                    getSupportFragmentManager().executePendingTransactions();
                    ReminderAlarmDialogFragment reminderAlarmDialogFragment = new ReminderAlarmDialogFragment();
                    reminderAlarmDialogFragment.show(getFragmentManager(), "reminder");
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
        btAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), AddDiaryLog.class);
                startActivity(intent);
            }
        });

        mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, mainFragment, "main_fragment").commit();
        getSupportFragmentManager().executePendingTransactions();
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
