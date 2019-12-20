package com.hf.hf_smartcloud.utils.barchart;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

public class MyXFormatter implements IAxisValueFormatter {

        private List<String> mValues;

        public MyXFormatter(List<String> values) {
            this.mValues = values;
        }
        private static final String TAG = "MyXFormatter";

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            Log.d(TAG, "----->getFormattedValue: "+value);
            return mValues.get((int) value % mValues.size());
        }
}
