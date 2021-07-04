package com.example.muse.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.muse.MainActivity;
import com.example.muse.R;
import com.example.muse.model.InsightDataModel;
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

        // fixed line chart for now
        int[] xAxis_value =new int[HomeFragment.dataModels.size()];
        int[] yAxis_value = new int[HomeFragment.dataModels.size()];


        for (int i=0;i<HomeFragment.dataModels.size();i++){
            xAxis_value[i]=i;
            yAxis_value[i]=HomeFragment.dataModels.get(i).getValue();
        }

        lineChart = view.findViewById(R.id.FDay_chart);
        lineChart.setData(DataCharts.drawLineChart(getContext(), xAxis_value, yAxis_value));
        setupLineChart();
    }

    public void setupLineChart() {
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.setAutoScaleMinMaxEnabled(true);
        lineChart.animateY(1000);
        lineChart.setDescription(null);
        lineChart.fitScreen();
        lineChart.zoomIn();
        lineChart.zoomOut();
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setVisibleXRangeMaximum(7);

        // x axis edit
        String[] xValues = new String[HomeFragment.dataModels.size()];
        for (int i=0;i<HomeFragment.dataModels.size();i++){
            xValues[i]=HomeFragment.dataModels.get(i).getTime();
        }

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextColor(MainActivity.colorPrimaryVariant);
        xAxis.setGranularity(1f);
        xAxis.setDrawLimitLinesBehindData(true);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));

        // y axis edit
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setLabelCount(5);
        yAxis.setTextColor(MainActivity.colorPrimaryVariant);
        yAxis.setDrawLimitLinesBehindData(true);
        yAxis.setDrawGridLines(false);
        yAxis.setValueFormatter(new DataCharts.AxisFormat());
    }
}