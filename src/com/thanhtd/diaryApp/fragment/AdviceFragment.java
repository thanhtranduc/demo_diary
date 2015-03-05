package com.thanhtd.diaryApp.fragment;

import android.content.Intent;
import android.net.Uri;
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
        view.findViewById(R.id.learn_more_instruction).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://en.m.wikipedia.org/wiki/Blood_pressure"));
                startActivity(browserIntent);
            }
        });
        view.findViewById(R.id.learn_more_smoke).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://en.m.wikipedia.org/wiki/Smoking"));
                startActivity(browserIntent);
            }
        });
        view.findViewById(R.id.learn_more_weight).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://en.m.wikipedia.org/wiki/Dieting"));
                startActivity(browserIntent);
            }
        });
        view.findViewById(R.id.learn_more_eat).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://en.m.wikipedia.org/wiki/Meal"));
                startActivity(browserIntent);
            }
        });
        return view;
    }
}
