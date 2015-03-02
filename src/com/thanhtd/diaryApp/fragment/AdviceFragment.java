package com.thanhtd.diaryApp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.thanhtd.diaryApp.R;

/**
 * Created by a on 03/03/2015.
 */
public class AdviceFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.advice_layout, container, false);
        return view;
    }
}
