package com.hf.hf_smartcloud.ui.activity.facility;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.bean.Date31Bean;
import com.hf.hf_smartcloud.bean.RecordBean;
import com.hf.hf_smartcloud.ui.fragment.FragmentTwoSun3;
import com.hf.hf_smartcloud.utils.ChartView;
import com.hf.hf_smartcloud.utils.DynamicLineChartManager;
import com.hf.hf_smartcloud.utils.ScatterChartView;
import com.hf.hf_smartcloud.utils.StackedHorizontalProgressBar;
import com.hf.hf_smartcloud.utils.barchart.BarChartActivityOnlyUtils;
import com.hf.hf_smartcloud.utils.bluetooth.ResultUtils;
import com.hf.hf_smartcloud.utils.bluetooth.Utils;
import com.hf.hf_smartcloud.weigets.CircleGradientProgressView;
import com.hf.hf_smartcloud.weigets.chart.WaveView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class DevicesPortableDetailsActivity extends BaseActivity implements View.OnClickListener {
    static String resultValue = null;
    static List<RecordBean> resultListRecordBean21 = new ArrayList<>();
    static List<Float> y_value = new ArrayList<>();
    static List<String> dateresultList21 = new ArrayList<>();
    static Float Realcurve;
    static int secondary_pts = 0;
    static int primary_pts = 0;
    static int secondary_three = 0;
    static Date31Bean dateBean = new Date31Bean();
    private static String str1, str2, gastype;
    /**
     * ==========生命周期=======================================
     */
    public LocationClient mLocationClient;
    public BDLocationListener myListener = new MyLocationListener();
    List<Float> x_number = new ArrayList<>();
    BarChartActivityOnlyUtils mymbarchartonlyutils;
    //=================================================
    ChartView chartView;
    ScatterChartView scatterChartView;
    TextView time1, time2, time3, tv_unit, tv_baojing;
    LineChart mChart1;
    //    BarChart mBarChart;
    StackedHorizontalProgressBar stackedHorizontalProgressBar;
    TextView txt_primary1, txt_secondary1, txt_primary2, txt_secondary2, txt_primary3, txt_secondary3;
    ArrayList<String> valueresultList21 = new ArrayList<>();
    Float onexs, nongdu;
    TextView chucdate, nowdate;
    String unit,range,jingdu;
    private int[] mShaderColors = new int[]{0xFF4FEAAC, 0xFFA8DD51, 0xFFE8D30F, 0xFFA8DD51, 0xFF4FEAAC};
    private WaveView waveview;
    private CircleGradientProgressView circlegradientprogressview;
    //=================================================
    private View view1, view2, view3;
    private List<View> viewList = new ArrayList<View>();
    private ViewPager mViewPage;
    //=================================================
    private DynamicLineChartManager dynamicLineChartManager1;
    private List<String> names = new ArrayList<>(); //折线名字集合
    private List<Integer> colour = new ArrayList<>();//折线颜色集合
    //x轴坐标对应的数据
    private List<String> xValue = new ArrayList<>();
    //y轴坐标对应的数据
    private List<Integer> yValue = new ArrayList<>();
    //折线对应的数据
    private Map<String, Integer> value = new HashMap<>();
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LatLng latLng;
    private boolean isFirstLoc = true; // 是否首次定位
    private String one,two;//一二级报警
    //控件初始化
    private TextView tv_item1, tv_item2, tv_item3, tv_item4;
    private int position;
    private String pos;
    //x轴坐标对应的数据
    private List<String> xview1Value = new ArrayList<>();
    //y轴坐标对应的数据
    private List<Integer> yview1Value = new ArrayList<>();
    //折线对应的数据
    private List<Integer> view1value = new ArrayList<>();

    /**
     * 接受返回来的数据
     * 获取蓝牙指令回掉
     */
    public static synchronized void char6_display(final String str, byte[] data, String uuid) {
        Log.i("char6_display21", "char6_display str = " + str);
        if (uuid.equals(FragmentTwoSun3.UUID_CHAR6)) {  //  的串口透传  uuid是否等于UUID_CHAR6
                    if (str.startsWith("#") && !str.endsWith("\r\n")) { //21寄存器报警记录中的值
                        str1 = str;
                    } else if (!str.startsWith("#") && str.endsWith("\r\n")) {
                        str2 = str;
                        resultValue = str1 + str2;
                        resultListRecordBean21.add(ResultUtils.register21(resultValue));
                } else if (str.startsWith("#") && str.endsWith("\r\n") && str.length() == 17) {//获取实时浓度
                    Realcurve = ResultUtils.register0F(str).getNongdu2();
                } else if (str.startsWith("#") && str.endsWith("\r\n") && str.length() == 19) {//生命周期的时间
                    Log.i("str:::", "str:::" + str);
                    dateBean = ResultUtils.register31(str);
                    primary_pts = dateBean.getIntone();
                    secondary_pts = dateBean.getInttwo();
                    secondary_three = dateBean.getIntthree();
                }
        }

        Log.i("resultListRecordBean21:", "resultListRecordBean21:::" + resultListRecordBean21.size());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devicesportabledetails);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(flag, flag);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xb4a4F7);
        }

        initTitle();//初始化标题
        initView();//初始化控件
        initBundle();//接收bundle传过来的值
        initTheWaves();//水波球的值
        initPage();//初始化page
        send21();//发送请求报警记录的指令
//
        initHistoryData();//初始化历史纪录
        initMap();//初始化地图
        send31();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(5 * 1000);
                    SwitchMachine();
                    initLifeCycle();//初始化生命周期
                    setBarchartData();//柱状图数据
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    /**
     * 接收bundle传过来的值
     */
    private void initBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            position = bundle.getInt("position");
            gastype = bundle.getString("gastype");
            range = bundle.getString("range");
            unit = bundle.getString("unit");
            one = bundle.getString("one");
            two = bundle.getString("two");
            onexs = bundle.getFloat("onexs");
            tv_item1.setText(bundle.getString("gastype"));
            tv_item2.setText(range+unit);
            tv_item3.setText(one);
            tv_item4.setText(two);
            unit = bundle.getString("unit");
            nongdu = bundle.getFloat("nongdu");
            jingdu = bundle.getString("jingdu");

        }
    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        ImageView btn_sancode = findviewByid(R.id.btn_sancode);
        ImageView btn_back = findviewByid(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tv_title = findviewByid(R.id.tv_title);
        tv_title.setText("传感器详情");
        TextView title_mac = findviewByid(R.id.title_mac);
        title_mac.setText("");
        btn_sancode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("gastype",gastype);
                bundle.putString("range",range);
                bundle.putString("one",one);
                bundle.putString("two",two);
                bundle.putString("unit",unit);
                if (jingdu.equals("00")){
                    bundle.putString("jingdu","1");
                }else if(jingdu.equals("01")){
                    bundle.putString("jingdu","0.1");
                }else if(jingdu.equals("10")){
                    bundle.putString("jingdu","0.01");
                }

                bundle.putString("chuchangdate",dateBean.getChuchangDate());
                bundle.putString("xiacibd",dateBean.getStrtwo());
                bundle.putString("shouming",dateBean.getStrthree());
                openActivity(GasSettingActivity.class,bundle);
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        tv_item1 = findviewByid(R.id.tv_item1);
        tv_item2 = findviewByid(R.id.tv_item2);
        tv_item3 = findviewByid(R.id.tv_item3);
        tv_item4 = findviewByid(R.id.tv_item4);

        mMapView = findviewByid(R.id.bmapView);
        waveview = findViewById(R.id.waveview);
        circlegradientprogressview = findViewById(R.id.circlegradientprogressview);
        tv_unit = findviewByid(R.id.tv_unit);
        tv_baojing = findviewByid(R.id.tv_baojing);
    }

    /**
     * 水波球
     */
    private void initTheWaves() {
        circlegradientprogressview.setShowTick(false);
        waveview.setProgressWithAnim(nongdu / 100);
        tv_unit.setText(unit);
        circlegradientprogressview.setProgressColor(mShaderColors);
        circlegradientprogressview.showAnimation(30, 3000);
    }

    /**
     * 切换page
     */
    private void initPage() {
        //对Viewpage
        mViewPage = findViewById(R.id.viewpage);
        LayoutInflater mInflater = getLayoutInflater();
        view1 = mInflater.inflate(R.layout.viewpage_layout_one, null);
        view2 = mInflater.inflate(R.layout.viewpage_layout_two, null);
        view3 = mInflater.inflate(R.layout.viewpage_layout_three, null);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        PagerAdapter pagerAdapter = new PagerAdapter() {
            //返回要滑动的VIew的个数
            @Override
            public int getCount() {
                return viewList.size();
            }

            //视图View和键对象 o是否一致
            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            //从当前container中删除指定位置（position）的View;
            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(viewList.get(position));
            }

            //初始化item,做了两件事，第一：将当前视图添加到container中，第二：返回当前View作为键
            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
        };
        mViewPage.setAdapter(pagerAdapter);

        scatterChartView = view1.findViewById(R.id.scatter_chartone);
        mChart1 = view2.findViewById(R.id.dynamic_charttwo);
        mymbarchartonlyutils = view3.findViewById(R.id.mymbarchartonlyutils);
        time1 = view1.findViewById(R.id.time1);
        time2 = view2.findViewById(R.id.time2);
        time3 = view3.findViewById(R.id.time3);

//        RealtimeCurve();//实时曲线
        scatterChartView.setOnTouchListener(new View.OnTouchListener() {
            float lastY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    lastY = event.getY();//获得起始纵坐标
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    //执行这一步时view会发生Y轴上的抖动并且抖动程度会随着ACTION_MOVE的执行次数而加剧
                    scatterChartView.setTranslationY(event.getY() - lastY);
                    //这一步用来探明抖动的原因来自于event.getY()异常
                    Log.i("Y", event.getY() + "");
                }
                return false;
            }
        });

        /*图标顶部时间的线程*/
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    while (true) {
                        Thread.sleep(1000);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
                        String str = sdf.format(new Date());
                        time1.setText(str);
                        time2.setText(str);
                        time3.setText(str);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 开关机状态记录
     */
    private void SwitchMachine() {
        if (xview1Value.size() == 0 && yview1Value.size() == 0 && view1value.size() == 0) {

            if (yview1Value.size() == 0) {
                for (int j = 0; j < 4; j++) {
                    yview1Value.add(j * 60);
                }
            }
            if (resultListRecordBean21.size() != 0) {
                for (int i = 0; i < resultListRecordBean21.size(); i++) {
                    if (resultListRecordBean21.get(i).getRecordtype() != null) {
                        Log.i("开关机类型：", "开关机类型：" + resultListRecordBean21.get(i).getRecordtype()+"resultListRecordBean21:"+resultListRecordBean21.size());
                        boolean status = resultListRecordBean21.get(i).getRecordtype().contains("开机");
                        boolean status2 = resultListRecordBean21.get(i).getRecordtype().contains("关机");

                        if (status) {
                            view1value.add(120);
                            Log.i("==gxl-data","开机");
                            xview1Value.add(resultListRecordBean21.get(i).getDate());
                        } else if (status2) {
                            view1value.add(60);
                            Log.i("==gxl-data","关机");
                            xview1Value.add(resultListRecordBean21.get(i).getDate());

                        }
                    }
                }
                Log.i("==gxl-data","view1value长度:"+view1value.size()+"xview1Value长度:"+xview1Value.size());
            }
        }
        if (xview1Value.size() != 0 && yview1Value.size() != 0 && view1value.size() != 0) {
            scatterChartView.setValue(view1value, xview1Value, yview1Value);
        }
    }

    /**
     * 历史记录
     */
    private void initHistoryData() {
        chartView = findViewById(R.id.chartview);
        for (int i = 0; i < 12; i++) {
            xValue.add((i + 1) + "月");
            value.put((i + 1) + "月", (int) (Math.random() * 181 + 60));//60--240
        }

        for (int i = 0; i < 6; i++) {
            yValue.add(i * 60);
        }
        chartView.setValue(value, xValue, yValue);
    }

    /**
     * 实时曲线
     */
    private void RealtimeCurve() {
        //========实时曲线=======================================================

        //折线名字
        names.add("浓度");
        //折线颜色
        colour.add(this.getResources().getColor(R.color.mainzise));
        colour.add(Color.GREEN);
        colour.add(Color.RED);
        //去掉纵向网格线和顶部边线
        //设置左边y轴的样式
        YAxis yAxisLeft = mChart1.getAxisLeft();
        yAxisLeft.setAxisLineColor(Color.parseColor("#66CDAA"));
        yAxisLeft.setAxisLineWidth(5);
        yAxisLeft.setDrawGridLines(false);
        //设置右边y轴的样式
        yAxisLeft.setEnabled(true);


        mChart1.setBackgroundResource(R.color.white);
        mChart1.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // 让x轴在下面
        mChart1.getXAxis().setGridColor(getResources().getColor(R.color.transparent));
        //去掉左右边线：
        mChart1.getAxisLeft().setDrawAxisLine(false);
        mChart1.getAxisRight().setDrawAxisLine(false);
        mChart1.setDrawGridBackground(false);
        dynamicLineChartManager1 = new DynamicLineChartManager(mChart1, names.get(0), colour.get(0));
        dynamicLineChartManager1.setYAxis(100, 0, 10);
        //死循环添加数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //TODO  实时随机数据
                            send0F();
                            if (Realcurve != null) {
                                dynamicLineChartManager1.addEntry(Realcurve);
//                                waveview.setProgressWithAnim(Realcurve / 100);
                            }
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * 柱状报警记录
     */
    private void setBarchartData() {
        if (x_number.size() != 0 && y_value.size() != 0 && dateresultList21.size() != 0) {
            x_number.clear();
            y_value.clear();
            dateresultList21.clear();
//            mymbarchartonlyutils.init(DevicesPortableDetailsActivity.this, mymbarchartonlyutils, x_number, y_value, "mymbarchartonlyutils", dateresultList21);
        } else {
            if (resultListRecordBean21.size() != 0) {
                for (int i = 0; i < resultListRecordBean21.size(); i++) {
                    if (resultListRecordBean21.get(i).getShowvalue() != null && resultListRecordBean21.get(i).getGastype() != null) {
                        Log.i("柱状图气体类型：", "柱状图气体类型：" + resultListRecordBean21.get(i).getGastype());
                        if (resultListRecordBean21.get(i).getGastype().equals(gastype)) {
                            y_value.add(Float.parseFloat(resultListRecordBean21.get(i).getShowvalue()));
                            dateresultList21.add(resultListRecordBean21.get(i).getDate());
                            x_number.add((float) i);
                        }
                    }
                }
                Log.i("dateresultList21:::", "dateresultList21:::" + dateresultList21.size());
                mymbarchartonlyutils.init(DevicesPortableDetailsActivity.this, mymbarchartonlyutils, x_number, y_value, "mymbarchartonlyutils", dateresultList21);
            }
        }
    }
    /**
     * 发送21寄存器的指令【获取报警记录】
     */
    private void send21() {
        if (String.valueOf(position).length() == 1) {
            pos = "0" + position;
        }
        String lrcstr = Utils.makeChecksum(pos + "0321").toUpperCase();
        if (lrcstr != null) {
            String zhiLstr = ":" + pos + "0321" + lrcstr + "\r\n";
            FragmentTwoSun3.writeChar6(zhiLstr);
        }
    }

    /**
     * 发送0F寄存器的指令【获取报警记录】
     */
    private void send0F() {
        if (String.valueOf(position).length() == 1) {
            pos = "0" + position;
        }
        String lrcstr = Utils.makeChecksum(pos + "030F").toUpperCase();
        if (lrcstr != null) {
            String zhiLstr = ":" + pos + "030F" + lrcstr + "\r\n";
            FragmentTwoSun3.writeChar6(zhiLstr);
        }
    }

    /**
     * 发送31寄存器的指令【获取生命周期】
     */
    private void send31() {
        if (String.valueOf(position).length() == 1) {
            pos = "0" + position;
        }
        String lrcstr31 = Utils.makeChecksum(pos + "0331").toUpperCase();
        if (lrcstr31 != null) {
            FragmentTwoSun3.writeChar6(":" + pos + "0331" + lrcstr31 + "\r\n");
        }
    }

    @Override
    public void onClick(View v) {
    }

    /**
     * =========百度地图   START================================================================================
     */
    private void initMap() {
        //获取地图控件引用
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);

        //默认显示普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //开启交通图
        //mBaiduMap.setTrafficEnabled(true);
        //开启热力图
        //mBaiduMap.setBaiduHeatMapEnabled(true);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        //配置定位SDK参数
        initLocation();
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        //开启定位
        mLocationClient.start();
        //图片点击事件，回到定位点
        mLocationClient.requestLocation();
    }

    /**
     * 生命周期
     */
    private void initLifeCycle() {
        send31();
        stackedHorizontalProgressBar = findViewById(R.id.stackedhorizontalprogressbar);
        int maxd = dateBean.getIntone() + dateBean.getInttwo() + dateBean.getIntthree();
        Log.i("maxd:::", "maxd:::" + maxd);
        stackedHorizontalProgressBar.setMax(maxd);

        chucdate = findViewById(R.id.chucdate);
        chucdate.setText(dateBean.getChuchangDate());
        nowdate = findViewById(R.id.nowdate);
        nowdate.setText(ResultUtils.systemDate());
        txt_primary1 = findViewById(R.id.txt_primary1);
        txt_secondary1 = findViewById(R.id.txt_secondary1);
        txt_primary2 = findViewById(R.id.txt_primary2);
        txt_secondary2 = findViewById(R.id.txt_secondary2);
        txt_primary3 = findViewById(R.id.txt_primary3);
        txt_secondary3 = findViewById(R.id.txt_secondary3);


        stackedHorizontalProgressBar.setProgress(primary_pts);
        txt_primary1.setText("设备使用");
        txt_secondary1.setText("" + dateBean.getStrone());

        stackedHorizontalProgressBar.setSecondaryProgress(secondary_pts);
        txt_primary2.setText("下一次标定");
        txt_secondary2.setText("" + dateBean.getStrtwo());

        txt_primary3.setText("寿命");
        txt_secondary3.setText(dateBean.getStrthree());
    }

    //配置定位SDK参数
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation
        // .getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        option.setOpenGps(true); // 打开gps

        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
    // =========百度地图  END======================================================================================

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause (),实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();

        x_number.clear();
        y_value.clear();
        valueresultList21.clear();
        dateresultList21.clear();
        resultListRecordBean21.clear();
        xview1Value.clear();
        yview1Value.clear();
        view1value.clear();
    }

    //实现BDLocationListener接口,BDLocationListener为结果监听接口，异步获取定位结果
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            // 当不需要定位图层时关闭定位图层
            //mBaiduMap.setMyLocationEnabled(false);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

                if (location.getLocType() == BDLocation.TypeGpsLocation) {
                    // GPS定位结果
                    Toast.makeText(DevicesPortableDetailsActivity.this, location.getAddrStr(), Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                    // 网络定位结果
                    Toast.makeText(DevicesPortableDetailsActivity.this, location.getAddrStr(), Toast.LENGTH_SHORT).show();

                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                    // 离线定位结果
                    Toast.makeText(DevicesPortableDetailsActivity.this, location.getAddrStr(), Toast.LENGTH_SHORT).show();

                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    Toast.makeText(DevicesPortableDetailsActivity.this, "服务器错误，请检查", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    Toast.makeText(DevicesPortableDetailsActivity.this, "网络错误，请检查", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    Toast.makeText(DevicesPortableDetailsActivity.this, "手机模式错误，请检查是否飞行", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
