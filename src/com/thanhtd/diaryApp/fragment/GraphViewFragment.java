package com.thanhtd.diaryApp.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.thanhtd.diaryApp.R;
import com.thanhtd.diaryApp.data.DatabaseHelper;
import com.thanhtd.diaryApp.data.SortListItem;
import com.thanhtd.diaryApp.data.model.ItemModel;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by a on 09/03/2015.
 */
public class GraphViewFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.graph_view_fragment, container, false);
        final DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(), "diary.db");
        List<ItemModel> itemModels = null;
        try
        {
            itemModels = databaseHelper.getDaoItem().queryForAll();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>();
        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<DataPoint>();

        if (itemModels != null)
        {
            Collections.sort(itemModels, new SortListItem());
            for (ItemModel itemModel : itemModels)
            {
                DataPoint dataPointDiasol = new DataPoint(itemModel.getDate(), Double.valueOf(itemModel.getDiasol()));
                series.appendData(dataPointDiasol, false, 1000);
                DataPoint dataPointSystol = new DataPoint(itemModel.getDate(), Double.valueOf(itemModel.getSystol()));
                series2.appendData(dataPointSystol, false, 1000);
                DataPoint dataPointPulse = new DataPoint(itemModel.getDate(), Double.valueOf(itemModel.getPulse()));
                series3.appendData(dataPointPulse, false, 1000);
            }
        }

        GraphView graph = (GraphView) view.findViewById(R.id.graph);
        GraphView graphPulse = (GraphView) view.findViewById(R.id.graph_pulse);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter()
        {
            @Override
            public String formatLabel(double value, boolean isValueX)
            {
                if (isValueX)
                {
                    final Date date = new Date(new Double(value).longValue());
                    SimpleDateFormat df2 = new SimpleDateFormat("MM/dd");
                    return df2.format(date);
                }
                else
                {
                    return super.formatLabel(value, isValueX);
                }
            }
        });
        graphPulse.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter()
        {
            @Override
            public String formatLabel(double value, boolean isValueX)
            {
                if (isValueX)
                {
                    final Date date = new Date(new Double(value).longValue());
                    SimpleDateFormat df2 = new SimpleDateFormat("MM/dd");
                    return df2.format(date);
                }
                else
                {
                    return super.formatLabel(value, isValueX);
                }
            }
        });
        setupGraghView(graph);
        setupGraghView(graphPulse);

        series.setColor(Color.RED);
        series.setTitle("Diastolic");
        series2.setColor(Color.BLUE);
        series2.setTitle("Systolic");
        series3.setColor(Color.GREEN);
        series3.setTitle("Pulse");
        graph.addSeries(series);
        graph.addSeries(series2);
        graphPulse.addSeries(series3);
        return view;
    }

    private void setupGraghView(GraphView graph)
    {
        graph.getLegendRenderer().setVisible(true);

        graph.getGridLabelRenderer().setVerticalLabelsVisible(true);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(true);

        graph.getGridLabelRenderer().setGridColor(Color.BLACK);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.BLACK);
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.BLACK);
    }
}
