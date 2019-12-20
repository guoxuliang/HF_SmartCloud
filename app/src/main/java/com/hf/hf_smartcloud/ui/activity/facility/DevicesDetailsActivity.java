package com.hf.hf_smartcloud.ui.activity.facility;

import android.os.Bundle;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;

//
//import android.graphics.Color;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.Toast;
//
//import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.charts.LineChart;
//import com.github.mikephil.charting.components.AxisBase;
//import com.github.mikephil.charting.components.Description;
//import com.github.mikephil.charting.components.Legend;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.components.YAxis;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.formatter.IAxisValueFormatter;
//import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
//import com.hf.hf_smartcloud.R;
//import com.hf.hf_smartcloud.adapter.PageRecycleViewAdapter;
//import com.hf.hf_smartcloud.adapter.RecycleViewAdapter;
//import com.hf.hf_smartcloud.adapter.ViewpageAdapter;
//import com.hf.hf_smartcloud.base.BaseActivity;
//import com.hf.hf_smartcloud.utils.ChartView;
//import com.hf.hf_smartcloud.utils.DynamicLineChartManager;
//import com.hf.hf_smartcloud.utils.FullyLinearLayoutManager;
//import com.hf.hf_smartcloud.weigets.CircleGradientProgressView;
//import com.hf.hf_smartcloud.weigets.chart.WaveView;
//import com.hf.hf_smartcloud.wrapper.LoadMoreWrapper;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
public class DevicesDetailsActivity extends BaseActivity/* implements View.OnClickListener*/{
//    private int[] mShaderColors = new int[]{0xFF4FEAAC,0xFFA8DD51,0xFFE8D30F,0xFFA8DD51,0xFF4FEAAC};
//    private WaveView waveview;
//    private CircleGradientProgressView circlegradientprogressview;
//    private RecyclerView recyclerView;
//    private LoadMoreWrapper loadMoreWrapper;
//    private List<String> dataList = new ArrayList<>();
//    RecycleViewAdapter recycleViewAdapter;
//    //=================================================
//    PageRecycleViewAdapter pageRecycleViewAdapter;
//    private View view1,view2,view3;
//    private List<View> viewList = new ArrayList<View>();
//    private ViewPager mViewPage;
//    private ViewpageAdapter adpter;
//    //=================================================
//    private DynamicLineChartManager dynamicLineChartManager1;
//    private List<Integer> list = new ArrayList<>(); //数据集合
//    private List<String> names = new ArrayList<>(); //折线名字集合
//    private List<Integer> colour = new ArrayList<>();//折线颜色集合
//    //=================================================
//    ChartView chartView;
//    //x轴坐标对应的数据
//    private List<String> xValue = new ArrayList<>();
//    //y轴坐标对应的数据
//    private List<Integer> yValue = new ArrayList<>();
//    //折线对应的数据
//    private Map<String, Integer> value = new HashMap<>();
//
//    BarChart mBarChart;
//    ArrayList<String> mlist=new ArrayList<String>();
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devicesdetails);
//        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
//        //设置当前窗体为全屏显示
//        getWindow().setFlags(flag, flag);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(0xb4a4F7);
        }
//        initView();
//        initPage();
//        initHistoryData();
//        getData();
//        setAdapterData();
//        initListener();//點擊事件
//
    }
//
//    private void initBarChart() {
//
//        mBarChart.setDrawBarShadow(false);     //表不要阴影
//        mBarChart.setDrawValueAboveBar(true);
//        Description description=new Description();
//        description.setText("通行民族");
//        mBarChart.setDescription(description);  //表的描述信息
//
//        mBarChart.setPinchZoom(false);
//        mBarChart.setMaxVisibleValueCount(60); //最大显示的个数。超过60个将不再显示
//        mBarChart.setScaleEnabled(false);     //禁止缩放
//        mBarChart.setDragEnabled(true);// 是否可以拖拽
//        mBarChart.setHighlightPerDragEnabled(true);// 拖拽超过图标绘制画布时高亮显示
//        mBarChart.setDrawGridBackground(false);//
//        /*  mBarChart.setAutoScaleMinMaxEnabled(true);*/
//        /* mBarChart.animateX(500);//数据显示动画，从左往右依次显示*/
//        /* mBarChart.getAxisRight().setEnabled(false);*/
//        /*mBarChart.setDragDecelerationEnabled(true);*///拖拽滚动时，手放开是否会持续滚动，默认是true（false是拖到哪是哪，true拖拽之后还会有缓冲）
//        mBarChart.zoom(2.5f,1f,0,0);//显示的时候 是 按照多大的比率缩放显示   1f表示不放大缩小
//        //我默认手机屏幕上显示10  剩下的滑动直方图 然后显示。。假如要显示25个 那么除以10 就是放大2.5f。。同理
//        // 56个民族   那么放大5.6f
//        draw();
//    }
//
//    private void initHistoryData() {
//        chartView = (ChartView) findViewById(R.id.chartview);
//        for (int i = 0; i < 12; i++) {
//            xValue.add((i + 1) + "月");
//            value.put((i + 1) + "月", (int) (Math.random() * 181 + 60));//60--240
//        }
//
//        for (int i = 0; i < 6; i++) {
//            yValue.add(i * 60);
//        }
//        chartView.setValue(value, xValue, yValue);
//    }
//
//
//    private void initPage() {
//        //对Viewpage的学习
//        mViewPage = findViewById(R.id.viewpage);
//        LayoutInflater mInflater = getLayoutInflater();
//        view1 = mInflater.inflate(R.layout.viewpage_layout_one,null);
//        view2 = mInflater.inflate(R.layout.viewpage_layout_two,null);
//        view3 = mInflater.inflate(R.layout.viewpage_layout_three,null);
//        viewList.add(view1);
//        viewList.add(view2);
//        viewList.add(view3);
//        RecyclerView rv = (RecyclerView)view1.findViewById(R.id.recycler_view_pageone);
////        rv.setLayoutManager(new LinearLayoutManager(this));
//        // 设置刷新控件颜色
//        FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(this);
//        layoutManager.setOrientation(FullyLinearLayoutManager.VERTICAL);
//        rv.setLayoutManager(layoutManager);
//        pageRecycleViewAdapter = new PageRecycleViewAdapter(dataList);
//        loadMoreWrapper = new LoadMoreWrapper(pageRecycleViewAdapter);
//        rv.setAdapter(loadMoreWrapper);
//        //==========================================================================================
//        LineChart mChart1 = (LineChart)view2.findViewById(R.id.dynamic_charttwo);
//
//        //折线名字
//        names.add("浓度");
//        names.add("压强");
//        names.add("其他");
//        //折线颜色
//        colour.add(this.getResources().getColor(R.color.mainzise));
//        colour.add(Color.GREEN);
//        colour.add(Color.RED);
//        //去掉纵向网格线和顶部边线
//        //设置左边y轴的样式
//        YAxis yAxisLeft = mChart1.getAxisLeft();
//        yAxisLeft.setAxisLineColor(Color.parseColor("#66CDAA"));
//        yAxisLeft.setAxisLineWidth(5);
//        yAxisLeft.setDrawGridLines(false);
//        //设置右边y轴的样式
////        YAxis yAxisRight = mChart1.getAxisRight();
//        yAxisLeft.setEnabled(true);
//
//
//        mChart1.setBackgroundResource(R.color.white);
//        mChart1.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // 让x轴在下面
//        mChart1.getXAxis().setGridColor(getResources().getColor(R.color.transparent));
//        //去掉左右边线：
//        mChart1.getAxisLeft().setDrawAxisLine(false);
//        mChart1.getAxisRight().setDrawAxisLine(false);
//        mChart1.setDrawGridBackground(false);
//        dynamicLineChartManager1 = new DynamicLineChartManager(mChart1, names.get(0), colour.get(0));
//        dynamicLineChartManager1.setYAxis(100, 0, 10);
//        //死循环添加数据
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            //TODO  实时随机数据
//                            dynamicLineChartManager1.addEntry((int) (Math.random() * 100));
//                        }
//                    });
//                }
//            }
//        }).start();
//
//
//
//        PagerAdapter pagerAdapter = new PagerAdapter() {
//            //返回要滑动的VIew的个数
//            @Override
//            public int getCount() {
//                return viewList.size();
//            }
//            //视图View和键对象 o是否一致
//            @Override
//            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
//                return view==o;
//            }
//            //从当前container中删除指定位置（position）的View;
//            @Override
//            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//                container.removeView(viewList.get(position));
//            }
//            //初始化item,做了两件事，第一：将当前视图添加到container中，第二：返回当前View作为键
//            @NonNull
//            @Override
//            public Object instantiateItem(@NonNull ViewGroup container, int position) {
//                container.addView(viewList.get(position));
//                return viewList.get(position);
//            }
//        };
//        mViewPage.setAdapter(pagerAdapter);
//
//        mBarChart = (BarChart)view3.findViewById(R.id.mBarChart);
//        initBarChart();
//    }
//    private void initView() {
//        waveview = findViewById(R.id.waveview);
//        circlegradientprogressview = findViewById(R.id.circlegradientprogressview);
//        circlegradientprogressview.setShowTick(false);
//        waveview.setProgressWithAnim(Float.valueOf("0.3"));
//        circlegradientprogressview.setProgressColor(mShaderColors);
//        circlegradientprogressview.showAnimation(30, 3000);
//
////        setAdapterData();
////        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
////            @Override
////            public void onItemClick(View view, int position) {
////                //TODO 点击
////                Toast.makeText(RecycleViewScollViewActivity.this,"您点击了"+position+"项",Toast.LENGTH_LONG).show();
////                recycleViewAdapter.setThisPosition(position);
////                recycleViewAdapter.notifyDataSetChanged();
////            }
////
////            @Override
////            public void onLongClick(View view, int posotion) {
////                //TODO 点击
////                Toast.makeText(RecycleViewScollViewActivity.this,"您长按了第"+posotion+"项",Toast.LENGTH_LONG).show();
////            }
////        }));
//    }
//    private void getData() {
//        char letter = 'A';
//        for (int i = 0; i < 26; i++) {
//            dataList.add(String.valueOf(letter));
//            letter++;
//        }
//    }
//    private void setAdapterData(){
//        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
////    recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        // 设置刷新控件颜色
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(layoutManager);
//        // 模拟获取数据
//
//        recycleViewAdapter = new RecycleViewAdapter(this,dataList);
////    loadMoreWrapper = new LoadMoreWrapper(recycleViewAdapter);
//        recyclerView.setAdapter(recycleViewAdapter);
//        recycleViewAdapter.notifyDataSetChanged();
//    }
//    @Override
//    public void onClick(View v) {
//    }
//
//    /**
//     * 點擊事件
//     */
//    private void initListener() {
//        /**
//         * 使用  適配器我們剛寫好的  暴露的接口
//         */
//        recycleViewAdapter.setOnRecyclerViewItemClickListener(new RecycleViewAdapter.OnItemClickListener() {
//            //帶出來的【點擊】重寫方法
//            @Override
//            public void onClick(int position) {
//                Toast.makeText(DevicesDetailsActivity.this, "点击了" + (position + 1), Toast.LENGTH_SHORT).show();
//                //拿适配器调用适配器内部自定义好的setThisPosition方法（参数写点击事件的参数的position）
//                recycleViewAdapter.setThisPosition(position);
//                //嫑忘记刷新适配器
//                recycleViewAdapter.notifyDataSetChanged();
//            }
//            @Override
//            public void onLongClick(int position) {
//                Log.e("tag", "獲取到的左側長按點擊值為：" + (position + 1));
//            }
//        });
//    }
//
//    /**
//     * BarChart===========================================================================================
//     */
//    public void draw(){
//        //X轴 样式
//        final XAxis xAxis = mBarChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setLabelRotationAngle(90);//柱的下面描述文字  旋转90度
//        xAxis.setDrawLabels(true);
//        xAxis.setDrawGridLines(false);
////        xAxis.setTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));//字体的相关的设置
//        xAxis.setGranularity(1f);//设置最小间隔，防止当放大时，出现重复标签。
//        xAxis.setCenterAxisLabels(true);//字体下面的标签 显示在每个直方图的中间
//        xAxis.setLabelCount(11,true);//一个界面显示10个Lable。那么这里要设置11个
//        xAxis.setTextSize(10f);
//
//
//        //Y轴样式
//        YAxis leftAxis = mBarChart.getAxisLeft();
//        leftAxis.setLabelCount(10);
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setSpaceTop(10f);
////        leftAxis.setStartAtZero(false);
//        leftAxis.setYOffset(10f);
//
//
//        //这个替换setStartAtZero(true)
////        leftAxis.setAxisMaxValue(100f);
////        leftAxis.setAxisMinValue(0f);
////        leftAxis.setDrawGridLines(true);//背景线
////        leftAxis.setAxisLineColor(getResources().getColor(R.color.colorPrimaryDark));
//
//
//        //.设置比例图标的显示隐藏
//        Legend l = mBarChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        //样式
//        l.setForm(Legend.LegendForm.CIRCLE);
//        //字体
//        l.setFormSize(10f);
//        //大小
//        l.setTextSize(13f);
//        l.setFormToTextSpace(10f);
//        l.setXEntrySpace(10f);
//
//
//
//        //模拟数据
//        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
//        yVals1.add(new BarEntry(1,23));
//        yVals1.add(new BarEntry(2, 20));
//        yVals1.add(new BarEntry(3, 30));
//        yVals1.add(new BarEntry(4, 10));
//        yVals1.add(new BarEntry(5, 45));
//        yVals1.add(new BarEntry(6, 50));
//        yVals1.add(new BarEntry(7, 35));
//        yVals1.add(new BarEntry(8, 26));
//        yVals1.add(new BarEntry(9, 14));
//        yVals1.add(new BarEntry(10, 20));
//        yVals1.add(new BarEntry(11, 33));
//        yVals1.add(new BarEntry(12, 44));
//        yVals1.add(new BarEntry(13, 42));
//        yVals1.add(new BarEntry(14, 41));
//        yVals1.add(new BarEntry(15, 12));
//        yVals1.add(new BarEntry(16, 31));
//        yVals1.add(new BarEntry(17, 21));
//        yVals1.add(new BarEntry(18, 20));
//        yVals1.add(new BarEntry(19, 44));
//        yVals1.add(new BarEntry(20, 42));
//        yVals1.add(new BarEntry(21, 41));
//        yVals1.add(new BarEntry(22, 12));
//        yVals1.add(new BarEntry(23, 31));
//        yVals1.add(new BarEntry(24, 21));
//        yVals1.add(new BarEntry(25, 20));
//        setData(yVals1);
//    }
//    private void setData(ArrayList yVals1) {
//        for(int i=1;i<=yVals1.size();i++) {
//            mlist.add(""+i);
//        }
//        IAxisValueFormatter ix=new MyXAxisValueFormatter(mlist);
//        mBarChart.getXAxis().setValueFormatter(ix);
//
//        BarDataSet set1;
//        if (mBarChart.getData() != null && mBarChart.getData().getDataSetCount() > 0) {
//            set1 = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
//            set1.setValues(yVals1);
//
//            mBarChart.getData().notifyDataChanged();
//            mBarChart.notifyDataSetChanged();
//        } else {
//            set1 = new BarDataSet(yVals1, "The year 2017");
////            set1.setColors(ColorTemplate.MATERIAL_COLORS);//条状柱状图的颜色
//            set1.setColors(getResources().getColor(R.color.zitizise));
//            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
//            dataSets.add(set1);
//            BarData data = new BarData(dataSets);
//            data.setValueTextSize(10f);
//            data.setBarWidth(0.5f);
//            mBarChart.setData(data);
//        }
//    }
//
//    public class MyXAxisValueFormatter implements IAxisValueFormatter {
//
//        private List<String> mValues;
//
//        public MyXAxisValueFormatter(List<String> values) {
//            this.mValues = values;
//        }
//
//        @Override
//        public String getFormattedValue(float value, AxisBase axis) {
//            int x=(int)(value);
//            if (x<0)
//                x=0;
//            if (x>=mValues.size())
//                x=mValues.size()-1;
//            return mValues.get(x);
//        }
//    }
//}
