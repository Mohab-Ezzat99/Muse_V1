package com.example.musev1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.musev1.MainActivity;
import com.example.musev1.utility.DataCharts;
import com.example.musev1.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

public class ChartWeekFragment extends Fragment {

    private BarChart barChart;

    public ChartWeekFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart_week, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // fixed line chart for now
        int[] xAxis_value =new int[7];
        int[] yAxis_value ={100,118,104,120,125,114,105};

        for (int i=0;i<xAxis_value.length;i++){
            xAxis_value[i]=i;
        }

        barChart = view.findViewById(R.id.FWeak_chart);
        barChart.setData(DataCharts.drawBarChart(getContext(), xAxis_value, yAxis_value));
        setupBarChart();
    }

    public void setupBarChart() {
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.getAxisRight().setEnabled(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.setAutoScaleMinMaxEnabled(true);
        barChart.setFitBars(true);
        barChart.animateY(1000);
        barChart.setDescription(null);
        barChart.getLegend().setEnabled(false);
        barChart.setVisibleXRangeMaximum(7);

        // x axis edit
        String[] values = new String[]{"Sat", "Sun", "Mon", "Tue", "Wed", "Thu","Fri"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setTextColor(MainActivity.colorPrimaryVariant);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(values));
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);

        // y axis edit
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setTextColor(MainActivity.colorPrimaryVariant);
        yAxis.setAxisMinimum(0f);
        yAxis.setLabelCount(6, true);
        yAxis.setAxisMaximum(yAxis.getAxisMaximum()+10);
        yAxis.setDrawLimitLinesBehindData(true);
        yAxis.setDrawGridLines(false);
        yAxis.setValueFormatter(new DataCharts.AxisFormat());
    }
}