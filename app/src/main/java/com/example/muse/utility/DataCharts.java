package com.example.muse.utility;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.example.muse.R;
import com.example.muse.StartActivity;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import java.util.ArrayList;

public class DataCharts {

    public static LineData drawLineChart(Context context, int[] xAxis_value, float[] yAxis_value) {
        // y axis values
        ArrayList<Entry> yEntries = new ArrayList<>();
        for (int i=0;i<xAxis_value.length;i++)
                yEntries.add(new Entry(xAxis_value[i], yAxis_value[i]));

        //collect values in a set
        LineDataSet lineDataSet = new LineDataSet(yEntries, null);
        lineDataSet.setForm(Legend.LegendForm.NONE);
        lineDataSet.setFillAlpha(120);
        lineDataSet.setFillDrawable(ContextCompat.getDrawable(context, R.drawable.shape_line_chart));
        lineDataSet.setDrawFilled(true);
        lineDataSet.setLineWidth(1.5f);
        lineDataSet.setValueTextSize(12f);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setCircleRadius(2f);
        lineDataSet.setCircleColor(StartActivity.colorOnSecondary);
        lineDataSet.setValueTextColor(StartActivity.colorOnSecondary);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        //list of data sets which everyone makes a line
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        //assign all data sets to line chart
        return new LineData(dataSets);
    }

    public static BarData drawBarChart(Context context, int[] xAxis_value, float[] yAxis_value) {
        // y axis values
        ArrayList<BarEntry> yEntries = new ArrayList<>();
        for (int i=0;i<xAxis_value.length;i++)
            yEntries.add(new BarEntry(xAxis_value[i], yAxis_value[i]));

        //collect values in a set
        BarDataSet barDataSet = new BarDataSet(yEntries, null);
        barDataSet.setGradientColor(StartActivity.colorOnPrimary
                ,StartActivity.colorPrimaryVariant);
        barDataSet.setValueTextSize(10f);
        barDataSet.setValueTextColor(StartActivity.colorOnSecondary);

        //assign all data sets to line chart
        return new BarData(barDataSet);
    }


    public static class AxisFormat extends ValueFormatter {

        public AxisFormat() {
        }

        @Override
        public String getFormattedValue(float value) {
            return (int)value+" W";
        }
    }
}
