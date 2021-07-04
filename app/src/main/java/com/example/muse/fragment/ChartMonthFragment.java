package com.example.muse.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.muse.MainActivity;
import com.example.muse.utility.DataCharts;
import com.example.muse.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

public class ChartMonthFragment extends Fragment {

    private BarChart barChart;

    public ChartMonthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart_month, container, false);
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

        barChart = view.findViewById(R.id.FMonth_chart);
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

        // x axis edit
        String[] values = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun","Jul"};
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
        yAxis.setDrawLimitLinesBehindData(true);
        yAxis.setDrawGridLines(false);
        yAxis.setValueFormatter(new DataCharts.AxisFormat());
    }
}