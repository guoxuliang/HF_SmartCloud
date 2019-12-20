package com.hf.hf_smartcloud.utils.barchart;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

public class CustomXValueFormatter implements IAxisValueFormatter {

    private List<String> labels;

    /**
     * @param labels 要显示的标签字符数组
     */
    public CustomXValueFormatter(List<String> labels) {
        this.labels = labels;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        Log.i("===","value:" + value + ";size:" + labels.size());
        return labels.get((int) value % labels.size());
    }
}
