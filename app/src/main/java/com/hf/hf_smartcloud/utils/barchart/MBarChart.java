package com.hf.hf_smartcloud.utils.barchart;
//import android.content.Context;
//import android.graphics.Color;
//import android.graphics.Matrix;
//import android.util.AttributeSet;
//import com.github.mikephil.charting.animation.Easing;
//import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.components.Legend;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
//import java.util.ArrayList;
//import java.util.List;
//public class MBarChart extends BarChart {
//    public MBarChart(Context context) {
//        super(context);
//    }
//
//    public MBarChart(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public MBarChart(Context context, AttributeSet attrs, int defStyle) {super(context, attrs, defStyle);}
//
//    private void initSetting(BarChart barChart,boolean isSlither) {
//        //绘制高亮箭头
////        barChart.setDrawHighlightArrow(false);
//        //设置值显示在柱状图的上边或者下边
//        barChart.setDrawValueAboveBar(true);
//        //设置绘制网格背景
//        barChart.setDrawGridBackground(true);
//        //设置双击缩放功能
//        barChart.setDoubleTapToZoomEnabled(false);
//        //设置规模Y启用
//        barChart.setScaleYEnabled(false);
//        //设置规模X启用
//        barChart.setScaleXEnabled(false);
//        //启用设置阻力
//        barChart.setScaleEnabled(true);
//        //设置每个拖动启用的高亮显示
//        barChart.setHighlightPerDragEnabled(false);
//        // 设置硬件加速功能
//        barChart.setHardwareAccelerationEnabled(true);
//        // 设置绘制标记视图
//        barChart.setDrawMarkerViews(true);
//        // 设置启用日志
//        barChart.setLogEnabled(true);
//        // 设置突出功能
////        barChart.setHighlightEnabled(true);
//        // 设置拖动减速功能
//        barChart.setDragDecelerationEnabled(true);
//        // 数据描述
////        barChart.setDescription("");
////        // 如果没有数据的时候，会显示这个，类似ListView的EmptyView
////        barChart.setNoDataTextDescription("没有数据了");
//        barChart.setNoDataText("O__O …");
//        // 是否显示表格颜色
//        barChart.setDrawGridBackground(false);
//        barChart.setVisibleXRangeMaximum(10);
//        barChart.setVisibleXRangeMinimum(10);
//        barChart.setMaxVisibleValueCount(600);
//
//        /**
//         * 下面这几个属性你们可以试试 挺有意思的
//         * */
//        // 设置是否可以触摸
////        barChart.setTouchEnabled(isSlither);
//        // 是否可以拖拽
////        barChart.setDragEnabled(isSlither);//放大可拖拽
//        // 是否可以缩放
//        barChart.setScaleEnabled(false);
//        // 集双指缩放
//        barChart.setPinchZoom(false);
//        barChart.setVisibleXRangeMaximum(10);
//        barChart.setVisibleXRangeMinimum(10);
//
//        // 设置背景
//        barChart.setBackgroundColor(Color.parseColor("#01000000"));
//        // 如果打开，背景矩形将出现在已经画好的绘图区域的后边。
//        barChart.setDrawGridBackground(false);
//        // 集拉杆阴影
//        barChart.setDrawBarShadow(false);
//        // 图例
//        barChart.getLegend().setEnabled(false);
//        // 设置数据
////        barChart.setData(barData);
//        // 隐藏右边的坐标轴 (就是右边的0 - 100 - 200 - 300 ... 和图表中横线)
//        barChart.getAxisRight().setEnabled(false);
//        // 隐藏左边的左边轴 (同上)
//        // 网格背景颜色
//        barChart.setGridBackgroundColor(Color.parseColor("#00000000"));
//        // 是否显示表格颜色
//        barChart.setDrawGridBackground(false);
//        // 设置边框颜色
//        barChart.setBorderColor(Color.parseColor("#00000000"));
//        // 说明颜色
////        barChart.setDescriptionColor(Color.parseColor("#00000000"));
//        // 拉杆阴影
//        barChart.setDrawBarShadow(false);
//        // 打开或关闭绘制的图表边框。（环绕图表的线）
//        barChart.setDrawBorders(false);
//
//        /*xy轴设置*/
//        //x轴设置显示位置在底部
//        mXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        mXAxis.setGranularity(1f);
//        //mXAxis.setCenterAxisLabels(true);
//
//        mXAxis.setTextSize(8f);
//        //文字倾斜展示
//        mXAxis.setLabelRotationAngle(90);
//        //保证y轴从0开始 不然会上移一点
//        mAxisLeft.setAxisMinimum(0f);
//        mAxisRight.setAxisMinimum(0f);
//        mAxisRight.enableGridDashedLine(10f,10f,10f);
//        //不显示X轴 Y轴线条
//        mXAxis.setDrawAxisLine(true);
//        mXAxis.setDrawGridLines(false);
//        mXAxis.setGridColor(Color.BLUE);
//
//        //mAxisLeft.setDrawAxisLine(false);
//        mAxisRight.setDrawAxisLine(false);
//        mAxisLeft.setDrawGridLines(false);
//        mAxisRight.setDrawGridLines(false);
//        //不显示左侧Y轴
//        mAxisRight.setEnabled(false);
//        /*折现图例 标签 设置*/
//        mLegend.setForm(Legend.LegendForm.LINE);
//        mLegend.setTextSize(0f);
//        //显示位置
//        mLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        mLegend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        /*是否绘制在表里*/
//        mLegend.setDrawInside(false);
//        //不显示右下角描述内容
//        mDescription.setEnabled(false);
//        setDescription(mDescription);
//        Legend mLegend = barChart.getLegend(); // 设置比例图标示
//        // 设置窗体样式
//        mLegend.setForm(Legend.LegendForm.CIRCLE);
//        // 字体
//        mLegend.setFormSize(4f);
//        // 字体颜色
//        mLegend.setTextColor(Color.parseColor("#00000000"));
//        XAxis xAxis = barChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setLabelRotationAngle(90);//柱的下面描述文字  旋转90度
//        xAxis.setDrawGridLines(false);
//
//
//        if (isSlither) {
//            //当为true时,放大图
//            // 为了使 柱状图成为可滑动的,将水平方向 放大 2.5倍
//            barChart.invalidate();
//            Matrix mMatrix = new Matrix();
//            mMatrix.postScale(2f, 1f);
//            barChart.getViewPortHandler().refresh(mMatrix, barChart, false);
//            barChart.animateY(1000);
//        } else {
//            //当为false时 不对图进行操作
//            barChart.animateY(1000);
//        }
//    }
//
//    public void setBarDataSet(BarChart barChart,int color, int size, final List<BarEntry> list, List<String> v,boolean isSlither) {
//        BarDataSet barDataSet=new BarDataSet(list,"");
//        barDataSet.setColor(color);
//        barDataSet.setFormLineWidth(0f);
//        barDataSet.setFormSize(0f);
//        mXAxis.setLabelCount(v.size());
////        mXAxis.setLabelCount(5);
////        barDataSet.setDrawValues(false);
//
//
//        barDataSet.setBarShadowColor(Color.parseColor("#01000000"));
//        ArrayList<BarDataSet> barDataSets = new ArrayList<>();
//        barDataSets.add(barDataSet);
//        barDataSet.setValueTextColor(Color.parseColor("#FF8F9D"));
//        // 绘制值
//        barDataSet.setDrawValues(true);
//
//
//        BarData barData=new BarData(barDataSet);
//        barData.setValueTextSize(10f);
////        mXAxis.setValueFormatter(new IndexAxisValueFormatter(v));
//        mXAxis.setValueFormatter(new CustomXValueFormatter(v));
//
//        if (size<10){
//            barData.setBarWidth((float) size/10f);
//            barChart.setData(barData);
//        }else {
//            barChart.setData(barData);
//            setChartData(size);
//        }
//        setChartEffect();
//        initSetting(barChart,isSlither);
//    }
//
//    /*设置图表效果*/
//    private void setChartEffect(){
//        //不可以手动缩放
//        setScaleXEnabled(false);
//        setScaleYEnabled(false);
//        setScaleEnabled(false);
//
//        //背景颜色
//        setBackgroundColor(Color.WHITE);
//        //不显示图表网格
//        setDrawGridBackground(false);
//        setDrawBorders(false);
//        //背景阴影
//        setDrawBarShadow(false);
//        setHighlightFullBarEnabled(false);
//        //显示柱图底层阴影
//        setDrawBarShadow(false);
//        //设置动画效果
//        animateY(1000, Easing.EasingOption.Linear);
//        animateX(1000,Easing.EasingOption.Linear);
//    }
//
//
//
//
//    /*固定宽*/
//    private void setChartData(int size){
//        Matrix m = new Matrix();
//        m.postScale(scaleNum(size), 1f);//两个参数分别是x,y轴的缩放比例。例如：将x轴的数据放大为之前的1.5倍
//        getViewPortHandler().refresh(m, this, false);//将图表动画显示之前进行缩放
//    }
////    30个横坐标时，缩放4f是正好的。
//    private float scalePercent = 3f/30f;
//
//    private float scaleNum(int xCount){
//        return xCount * scalePercent;
//    }
//}


import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.AttributeSet;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class MBarChart extends BarChart {
    public MBarChart(Context context) {
        super(context);
    }
    public MBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MBarChart(Context context, AttributeSet attrs, int defStyle) {super(context, attrs, defStyle);}

    private void initSetting(BarChart barChart,boolean isSlither) {
        //绘制高亮箭头
//        barChart.setDrawHighlightArrow(false);
        //设置值显示在柱状图的上边或者下边
        barChart.setDrawValueAboveBar(true);
        //设置绘制网格背景
        barChart.setDrawGridBackground(true);
        //设置双击缩放功能
        barChart.setDoubleTapToZoomEnabled(false);
        //设置规模Y启用
        barChart.setScaleYEnabled(false);
        //设置规模X启用
        barChart.setScaleXEnabled(false);
        //启用设置阻力
        barChart.setScaleEnabled(true);
        //设置每个拖动启用的高亮显示
        barChart.setHighlightPerDragEnabled(false);
        // 设置硬件加速功能
        barChart.setHardwareAccelerationEnabled(true);
        // 设置绘制标记视图
        barChart.setDrawMarkerViews(true);
        // 设置启用日志
        barChart.setLogEnabled(true);
        // 设置突出功能
//        barChart.setHighlightEnabled(true);
        // 设置拖动减速功能
        barChart.setDragDecelerationEnabled(true);
        // 数据描述
        // 是否显示表格颜色
        barChart.setDrawGridBackground(false);
        barChart.setVisibleXRangeMaximum(10);
        barChart.setVisibleXRangeMinimum(10);
        barChart.setMaxVisibleValueCount(600);

        /**
         * 下面这几个属性你们可以试试 挺有意思的
         * */
        // 设置是否可以触摸
//        barChart.setTouchEnabled(isSlither);
        // 是否可以拖拽
//        barChart.setDragEnabled(isSlither);//放大可拖拽
        // 是否可以缩放
        barChart.setScaleEnabled(false);
        // 集双指缩放
        barChart.setPinchZoom(false);
        barChart.setVisibleXRangeMaximum(10);
        barChart.setVisibleXRangeMinimum(10);

        // 设置背景
        barChart.setBackgroundColor(Color.parseColor("#01000000"));
        // 如果打开，背景矩形将出现在已经画好的绘图区域的后边。
        barChart.setDrawGridBackground(false);
        // 集拉杆阴影
        barChart.setDrawBarShadow(false);
        // 图例
        barChart.getLegend().setEnabled(false);
        // 设置数据
//        barChart.setData(barData);
        // 隐藏右边的坐标轴 (就是右边的0 - 100 - 200 - 300 ... 和图表中横线)
        barChart.getAxisRight().setEnabled(false);
        // 隐藏左边的左边轴 (同上)
        // 网格背景颜色
        barChart.setGridBackgroundColor(Color.parseColor("#00000000"));
        // 是否显示表格颜色
        barChart.setDrawGridBackground(false);
        // 设置边框颜色
        barChart.setBorderColor(Color.parseColor("#00000000"));
        // 说明颜色
//        barChart.setDescriptionColor(Color.parseColor("#00000000"));
        // 拉杆阴影
        barChart.setDrawBarShadow(false);
        // 打开或关闭绘制的图表边框。（环绕图表的线）
        barChart.setDrawBorders(false);

        /*xy轴设置*/
        //x轴设置显示位置在底部
        mXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        mXAxis.setGranularity(1f);
        //mXAxis.setCenterAxisLabels(true);
        mXAxis.setLabelCount(10);
        mXAxis.setTextSize(8f);
        //文字倾斜展示
        mXAxis.setLabelRotationAngle(90);
        //保证y轴从0开始 不然会上移一点
        mAxisLeft.setAxisMinimum(0f);
        mAxisRight.setAxisMinimum(0f);
        mAxisRight.enableGridDashedLine(10f,10f,10f);
        //不显示X轴 Y轴线条
        mXAxis.setDrawAxisLine(true);
        mXAxis.setDrawGridLines(false);
        mXAxis.setGridColor(Color.BLUE);

        //mAxisLeft.setDrawAxisLine(false);
        mAxisRight.setDrawAxisLine(false);
        mAxisLeft.setDrawGridLines(false);
        mAxisRight.setDrawGridLines(false);
        //不显示左侧Y轴
        mAxisRight.setEnabled(false);
        /*折现图例 标签 设置*/
        mLegend.setForm(Legend.LegendForm.LINE);
        mLegend.setTextSize(0f);
        //显示位置
        mLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        mLegend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        /*是否绘制在表里*/
        mLegend.setDrawInside(false);
        //不显示右下角描述内容
        mDescription.setEnabled(false);
        setDescription(mDescription);
        Legend mLegend = barChart.getLegend(); // 设置比例图标示
        // 设置窗体样式
        mLegend.setForm(Legend.LegendForm.CIRCLE);
        // 字体
        mLegend.setFormSize(4f);
        // 字体颜色
        mLegend.setTextColor(Color.parseColor("#00000000"));
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(90);//柱的下面描述文字  旋转90度
        xAxis.setDrawGridLines(false);
        if (isSlither) {
            //当为true时,放大图
            // 为了使 柱状图成为可滑动的,将水平方向 放大 2.5倍
            barChart.invalidate();
            Matrix mMatrix = new Matrix();
            mMatrix.postScale(2f, 1f);
            barChart.getViewPortHandler().refresh(mMatrix, barChart, false);
            barChart.animateY(1000);
        } else {
            //当为false时 不对图进行操作
            barChart.animateY(1000);
        }
    }

    public void setBarDataSet(BarChart barChart,int color, int size, final List<BarEntry> list, List<String> v,boolean isSlither) {
        BarDataSet barDataSet=new BarDataSet(list,"");
        barDataSet.setColor(color);
        barDataSet.setFormLineWidth(0f);
        barDataSet.setFormSize(0f);
        barDataSet.setDrawValues(false);


        barDataSet.setBarShadowColor(Color.parseColor("#01000000"));
        ArrayList<BarDataSet> barDataSets = new ArrayList<>();
        barDataSets.add(barDataSet);
        barDataSet.setValueTextColor(Color.parseColor("#FF8F9D"));
        // 绘制值
        barDataSet.setDrawValues(true);


        BarData barData=new BarData(barDataSet);
        barData.setValueTextSize(10f);
        mXAxis.setValueFormatter(new CustomXValueFormatter(v));

        if (size<10){
            barData.setBarWidth((float) size/10f);
            setData(barData);
        }else {
            setData(barData);
            setChartData(size);
        }
        setChartEffect();
        initSetting(barChart,isSlither);
    }

    /*设置图表效果*/
    private void setChartEffect(){
        //不可以手动缩放
        setScaleXEnabled(false);
        setScaleYEnabled(false);
        setScaleEnabled(false);

        //背景颜色
        setBackgroundColor(Color.WHITE);
        //不显示图表网格
        setDrawGridBackground(false);
        setDrawBorders(false);
        //背景阴影
        setDrawBarShadow(false);
        setHighlightFullBarEnabled(false);
        //显示柱图底层阴影
        setDrawBarShadow(false);
        //设置动画效果
        animateY(1000, Easing.EasingOption.Linear);
        animateX(1000,Easing.EasingOption.Linear);
    }

    /*固定宽*/
    private void setChartData(int size){
        Matrix m = new Matrix();
        m.postScale(scaleNum(size), 1f);//两个参数分别是x,y轴的缩放比例。例如：将x轴的数据放大为之前的1.5倍
        getViewPortHandler().refresh(m, this, false);//将图表动画显示之前进行缩放
    }
    //    30个横坐标时，缩放4f是正好的。
    private float scalePercent = 3f/30f;

    private float scaleNum(int xCount){
        return xCount * scalePercent;
    }








}

