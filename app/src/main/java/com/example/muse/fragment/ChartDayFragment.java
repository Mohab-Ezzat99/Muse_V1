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
        float[] yAxis_value = {20f,34f, 66f, 52f, 40f, 45f};
        lineChart.setData(DataCharts.drawLineChart(getContext(),xAxis_value,yAxis_value ));
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
        String[] xValues = new String[]{"4 am", "8 am", "12 pm", "4 pm", "8 pm", "12 am"};
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