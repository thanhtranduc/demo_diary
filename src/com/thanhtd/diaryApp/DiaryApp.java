package com.thanhtd.diaryApp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.example.material.MaterialMenu;
import com.example.material.MaterialMenuDrawable;
import com.example.material.MaterialMenuView;

import java.util.Random;

public class DiaryApp extends Activity
{
    private MaterialMenuView materialMenuView;
    private int              materialButtonState;
    private ListView materialMenu;
    private DrawerLayout drawerLayout;
    private boolean          direction;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        materialMenu = (ListView) findViewById(R.id.slider_list);
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
//        drawerLayout.setScrimColor(Color.parseColor("#66000000"));

//        drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
//
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
////                materialMenu.setTransformationOffset(
////                        MaterialMenuDrawable.AnimationState.BURGER_ARROW,
////                        direction ? 2 - slideOffset : slideOffset
//
//                );
//            }
//
//            @Override
//            public void onDrawerOpened(android.view.View drawerView) {
//                direction = true;
//            }
//
//            @Override
//            public void onDrawerClosed(android.view.View drawerView) {
//                direction = false;
//            }
//        });

//        drawerLayout.postDelayed(new Runnable() {
//            @Override public void run() {
//                drawerLayout.openDrawer(Gravity.LEFT);
//            }
//        }, 1500);
    }
//    @Override public void onClick(View v) {
//        final int id = v.getId();
//        switch (id) {
//            case R.id.animate_item_menu:
//                materialMenuView.animateState(MaterialMenuDrawable.IconState.BURGER);
//                break;
//            case R.id.animate_item_arrow:
//                materialMenuView.animateState(MaterialMenuDrawable.IconState.ARROW);
//                break;
//            case R.id.animate_item_x:
//                materialMenuView.animateState(MaterialMenuDrawable.IconState.X);
//                break;
//            case R.id.animate_item_check:
//                materialMenuView.animateState(MaterialMenuDrawable.IconState.CHECK);
//                break;
//            case R.id.switch_item_menu:
//                materialMenuView.setState(MaterialMenuDrawable.IconState.BURGER);
//                break;
//            case R.id.switch_item_arrow:
//                materialMenuView.setState(MaterialMenuDrawable.IconState.ARROW);
//                break;
//            case R.id.switch_item_x:
//                materialMenuView.setState(MaterialMenuDrawable.IconState.X);
//                break;
//            case R.id.switch_item_check:
//                materialMenuView.setState(MaterialMenuDrawable.IconState.CHECK);
//                break;
//            case R.id.material_menu_button:
//                setMainState();
//                break;
//        }
//    }
    private void setMainState() {
        materialButtonState = generateState(materialButtonState);
        materialMenuView.animatePressedState(intToState(materialButtonState));
    }
    public static int generateState(int previous) {
        int generated = new Random().nextInt(4);
        return generated != previous ? generated : generateState(previous);
    }
    public static MaterialMenuDrawable.IconState intToState(int state) {
        switch (state) {
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
