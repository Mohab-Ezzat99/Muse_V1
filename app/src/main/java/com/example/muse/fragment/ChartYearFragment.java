package com.example.muse.fragment;

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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

public class ChartYearFragment extends Fragment {

    private BarChart barChart;

    public ChartYearFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart_year, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        barChart=view.findViewById(R.id.FYear_chart);
        setupBarChart();

        // fixed line chart for now
        int[] xAxis_value = {0, 1, 2, 3, 4, 5, 6};
        float[] yAxis_value = {1f, 7f, 2f, 4f, 3f, 6f, 8f};
        barChart.setData(DataCharts.drawBarChart(getContext(), xAxis_value, yAxis_value));
    }

    public void setupBarChart() {
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.getAxisRight().setEnabled(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.setAutoScaleMinMaxEnabled(true);
        barChart.setFitBars(true);
        barChart.animateY(1000);

        // x axis edit
        String[] values = new String[]{"2014", "2015", "2016", "2017", "2018", "2019", "2020"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setTextColor(StartActivity.colorPrimaryVarient);
        xAxis.setValueFormatter(new DataCharts.XAxisFormat(values));
        xAxis.setGranularity(1f);

        // y axis edit
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setTextColor(StartActivity.colorPrimaryVarient);
        yAxis.setAxisMaximum(10f);
        yAxis.setAxisMinimum(0f);
        yAxis.setLabelCount(6, true);
        yAxis.setDrawLimitLinesBehindData(true);
    }
}