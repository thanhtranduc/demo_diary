package com.thanhtd.diaryApp.fragment;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.TextView;
import com.thanhtd.diaryApp.AddDiaryLog;
import com.thanhtd.diaryApp.DiaryApp;
import com.thanhtd.diaryApp.R;
import com.thanhtd.diaryApp.data.DatabaseHelper;
import com.thanhtd.diaryApp.data.model.ItemModel;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by a on 08/03/2015.
 */
public class EditDiaryDialogFragment extends DialogFragment
{
    ItemModel itemModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.edit_diaray_dialog, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        TextView tvDelete = (TextView) rootView.findViewById(R.id.edit_dialog_delete);
        TextView tvEdit = (TextView) rootView.findViewById(R.id.edit_dialog_edit);
        TextView tvOk = (TextView) rootView.findViewById(R.id.edit_dialog_ok);
        final DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(), "diary.db");
        final Long id = (Long) getArguments().get("id");
        getActivity().findViewById(R.id.main_tvHintAdd).setVisibility(View.GONE);
        try
        {
            itemModel = databaseHelper.getDaoItem().queryForId(id);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        final int position = (Integer) getArguments().get("position");
        tvDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    databaseHelper.getDaoItem().deleteById(id);
                    ((DiaryApp) getActivity()).mainFragment.adapter.getGroups().remove(position);
                    ((DiaryApp) getActivity()).mainFragment.adapter.notifyDataSetChanged();
                    dismiss();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        });

        tvEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), AddDiaryLog.class);
                Long id = (Long) getArguments().get("id");
                intent.putExtra("id", id);
                startActivity(intent);
                dismiss();
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((DiaryApp) getActivity()).mainFragment.adapter.getGroups()
                        .get(position).setCardiac(((CheckBox) rootView.findViewById(R.id.cbCardiac)).isChecked());
                itemModel.setIsCardiac(((CheckBox) rootView.findViewById(R.id.cbCardiac)).isChecked());
                try
                {
                    databaseHelper.getDaoItem().update(itemModel);
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
                ((DiaryApp) getActivity()).mainFragment.adapter.notifyDataSetChanged();
                dismiss();
            }
        });


        ((TextView) rootView.findViewById(R.id.systol)).setText(itemModel.getSystol());
        ((TextView) rootView.findViewById(R.id.diasol)).setText(itemModel.getDiasol());
        ((TextView) rootView.findViewById(R.id.pulse)).setText(itemModel.getPulse());
        ((CheckBox) rootView.findViewById(R.id.cbCardiac)).setChecked(itemModel.getIsCardiac());
        if (itemModel.getComment() != null)
        {
            ((TextView) rootView.findViewById(R.id.comment)).setText(itemModel.getComment());
        }

        final Date time = new Date(itemModel.getTime());
        DateFormat formatter = new SimpleDateFormat("hh:mm a");
        ((TextView) rootView.findViewById(R.id.time)).setText(formatter.format(time));


        final Date date = new Date(itemModel.getDate());
        SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
        ((TextView) rootView.findViewById(R.id.date)).setText(df2.format(date));


        return rootView;
    }
}

