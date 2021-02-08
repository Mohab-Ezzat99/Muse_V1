package com.example.muse;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import java.util.ArrayList;

public class XAxisFormat extends ValueFormatter {
    String[] mValues;

    public XAxisFormat(String[] values) {
        this.mValues = values;
    }

    @Override
    public String getFormattedValue(float value) {
        return mValues[(int) value];
    }

    public static LineData drawLineChart(Context context) {
        // y axis values
        ArrayList<Entry> yValues = new ArrayList<>();
        yValues.add(new Entry(0, 0f));
        yValues.add(new Entry(1, 2f));
        yValues.add(new Entry(2, 4f));
        yValues.add(new Entry(3, 2f));
        yValues.add(new Entry(4, 5f));
        yValues.add(new Entry(5, 6f));
        yValues.add(new Entry(6, 10f));

        //collect values in a set
        LineDataSet lineDataSet = new LineDataSet(yValues, null);
        lineDataSet.setForm(Legend.LegendForm.NONE);
        lineDataSet.setFillAlpha(180);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setLineWidth(1.5f);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setCircleRadius(2f);
        lineDataSet.setCircleColor(ContextCompat.getColor(context,R.color.red));
        lineDataSet.setValueTextColor(ContextCompat.getColor(context,R.color.red));
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        //list of data sets which everyone makes a line
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        //assign all data sets to line chart
        return new LineData(dataSets);
    }
}
