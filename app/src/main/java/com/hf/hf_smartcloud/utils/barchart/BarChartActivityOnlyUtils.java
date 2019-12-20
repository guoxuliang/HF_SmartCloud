package com.hf.hf_smartcloud.utils.barchart;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class BarChartActivityOnlyUtils extends BarChart{
    public BarChartActivityOnlyUtils(Context context) {
        super(context);
    }
    public BarChartActivityOnlyUtils(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public BarChartActivityOnlyUtils(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    /**
     * @param context
     * @param xAxisValues     x_number
     * @param yAxisValues     y_value
     * @param label           标题
     * @param values          x_value
     */
    public void init(Context context, BarChart mChart , List<Float> xAxisValues, List<Float> yAxisValues, String label, List<String> values){

        mChart.setDrawValueAboveBar(true);//设置柱状图上数值
        mChart.getDescription().setEnabled(false); //不显示描述
        mChart.setMaxVisibleValueCount(60);//设置最大显示数量
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);//是否显示表格颜色
        //设置是否可以缩放
//        mChart.setScaleEnabled(false);
//        //设置是否可以触摸
//        mChart.setTouchEnabled(false);
//        //设置是否可以拖拽
//        mChart.setDragEnabled(false);
        //自定义x轴显示
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setLabelRotationAngle(80);//柱的下面描述文字  旋转90度
        MyXFormatter formatter = new MyXFormatter(values);
        xAxis.setValueFormatter(formatter);
        xAxis.setLabelCount(xAxisValues.size() - 1, false);
        IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(0, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(10, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);


        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
//        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
        for (int i = 0; i < yAxisValues.size(); i++) {
            yVals1.add(new BarEntry(i, yAxisValues.get(i)));
//            yVals2.add(new BarEntry(i, yAxisValues.get(i)+20));
        }

        BarDataSet set1;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);

            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {

            set1 = new BarDataSet(yVals1, "A");
            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            BarData data = new BarData(set1);
            data.setValueFormatter(new LargeValueFormatter());
            if (values.size()<10){
                data.setBarWidth((float) 0.3f);
                mChart.setData(data);
            }else {
                mChart.setData(data);
                setChartData(values.size(),mChart);
            }

        }
    }

    /*固定宽*/
    private void setChartData(int size,BarChart mChart){
        Matrix m = new Matrix();
        m.postScale(scaleNum(size), 1f);//两个参数分别是x,y轴的缩放比例。例如：将x轴的数据放大为之前的1.5倍
        mChart.getViewPortHandler().refresh(m, mChart, false);//将图表动画显示之前进行缩放
    }
    //    30个横坐标时，缩放4f是正好的。
    private float scalePercent = 3f/30f;

    private float scaleNum(int xCount){
        return xCount * scalePercent;
    }
}

