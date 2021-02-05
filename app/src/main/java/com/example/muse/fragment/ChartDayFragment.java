package com.example.muse.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.muse.XAxisFormat;
import com.example.muse.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class ChartDayFragment extends Fragment {

    private LineChart lineChart;

    public ChartDayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart_day, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lineChart=view.findViewById(R.id.FDay_chart);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.setAutoScaleMinMaxEnabled(true);

        // fixed line chart for now
        lineChart.setData(XAxisFormat.drawLineChart(getContext()));

        // y axis edit
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMaximum(10f);
        yAxis.setAxisMinimum(0f);
        yAxis.setLabelCount(6, true);
        yAxis.setDrawLimitLinesBehindData(true);

        // x axis edit
        String[] values = new String[]{"0 am", "4 am", "8 am", "12 pm", "4 pm", "8 pm"};
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new XAxisFormat(values));
        xAxis.setGranularity(1f);
    }
}