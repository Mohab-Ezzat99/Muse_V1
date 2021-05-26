package com.example.muse.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.muse.StartActivity;
import com.example.muse.utility.DataCharts;
import com.example.muse.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

public class ChartDayFragment extends Fragment {

    private LineChart lineChart;

    public ChartDayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart_day, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lineChart = view.findViewById(R.id.FDay_chart);
        setupLineChart();

        // fixed line chart for now
        int[] xAxis_value = {0, 1, 2, 3, 4, 5, 6};
        float[] yAxis_value = {0f,2f,4f, 2f, 5f, 6f, 10f};
        lineChart.setData(DataCharts.drawLineChart(getContext(),xAxis_value,yAxis_value ));
    }

    @SuppressLint("ResourceType")
    public void setupLineChart(){
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.setAutoScaleMinMaxEnabled(true);

        // y axis edit
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMaximum(10f);
        yAxis.setAxisMinimum(0f);
        yAxis.setTextColor(StartActivity.colorPrimaryVarient);
        yAxis.setLabelCount(6, true);
        yAxis.setDrawLimitLinesBehindData(true);

        // x axis edit
        String[] values = new String[]{"", "4 am", "8 am", "12 pm", "4 pm", "8 pm", "12 am"};
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextColor(StartActivity.colorPrimaryVarient);
        xAxis.setValueFormatter(new DataCharts.XAxisFormat(values));
        xAxis.setGranularity(1f);
    }
}