package com.example.muse.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.muse.R;
import com.example.muse.StartActivity;
import com.example.muse.utility.DataCharts;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

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
        int[] xAxis_value = {0, 1, 2, 3, 4, 5};
        float[] yAxis_value = {12f, 16f, 20f, 11f, 24f, 18f};
//        int[] xAxis_value = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
//        float[] yAxis_value = {5f, 16f, 20f, 14f, 18f, 23f, 21f, 25f, 19f, 26f, 24f, 17f, 12f, 18f, 22f, 28f, 32f, 30f, 25f, 34f, 29f, 22f, 24f, 30f};
        lineChart.setData(DataCharts.drawLineChart(getContext(), xAxis_value, yAxis_value));
    }

    public void setupLineChart(){
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.setAutoScaleMinMaxEnabled(true);
        lineChart.animateY(1000);
        lineChart.setDescription(null);

        // x axis edit
        String[] xValues = new String[]{"6 pm", "7 pm", "8 pm", "9 pm", "10 pm", "11 pm"};
//        String[] xValues = new String[]{"12 am", "1 am", "2 am", "3 am", "4 am", "5 am","6 am","7 am","8 am","9 am","10 am","11 am"
//                ,"12 pm","1 pm","2 pm","3 pm","4 pm","5 pm","6 pm","7 pm","8 pm","9 pm","10 pm","11 pm"};
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextColor(StartActivity.colorPrimaryVariant);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);

        // y axis edit
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setLabelCount(5);
        yAxis.setTextColor(StartActivity.colorPrimaryVariant);
        yAxis.setDrawLimitLinesBehindData(true);
        yAxis.setDrawGridLines(false);
        yAxis.setValueFormatter(new DataCharts.AxisFormat());
    }
}