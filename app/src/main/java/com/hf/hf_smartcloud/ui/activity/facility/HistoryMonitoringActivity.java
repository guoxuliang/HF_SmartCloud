package com.hf.hf_smartcloud.ui.activity.facility;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.ui.activity.me.AddaddressActivity;
import com.veken.chartview.bean.ChartBean;
import com.veken.chartview.bean.PieChartBean;
import com.veken.chartview.drawtype.DrawBgType;
import com.veken.chartview.drawtype.DrawConnectLineType;
import com.veken.chartview.drawtype.DrawLineType;
import com.veken.chartview.view.BarChartView;
import com.veken.chartview.view.LineChartView;
import com.veken.chartview.view.PieChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HistoryMonitoringActivity extends BaseActivity {
    private TextView tv_hp, tv_sp;
    /**
     * 柱状图
     */
    private BarChartView barChartView;
    private ArrayList<ChartBean> barChartBeanList;
    /**
     * 折线图
     */
    private LineChartView lineChartView;
    private ArrayList<ChartBean> lineChartBeanList;
    /**
     * 饼图
     */
    private PieChartView pieChartView;
    private PieChartView pieChartViewt;
    private List<PieChartBean> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historymon);
        initTitle();
        initViews();
        initData();
        initDataLine();
        init();
    }

    private void initTitle() {
        ImageView btn_back = findviewByid(R.id.btn_back);
        ImageView btn_add = findviewByid(R.id.btn_add);
        TextView tv_title = findviewByid(R.id.tv_title);
        tv_title.setText("历史监测");
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(AddaddressActivity.class);
            }
        });
    }

    private void initViews() {
        barChartView = findViewById(R.id.bar_chart_view);//柱状图
        lineChartView = findViewById(R.id.line_chart_view);//折线图
        pieChartView = findViewById(R.id.piechart_view);//饼图
        pieChartViewt = findViewById(R.id.piechart_viewt);//饼图2
        tv_hp = findViewById(R.id.tv_hp);
        tv_sp = findViewById(R.id.tv_sp);
        tv_hp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                tv_hp.setVisibility(View.GONE);
                tv_sp.setVisibility(View.VISIBLE);
            }
        });
        tv_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                tv_hp.setVisibility(View.VISIBLE);
                tv_sp.setVisibility(View.GONE);
            }
        });
    }
    private void initDataLine() {
        if(barChartBeanList == null){
            barChartBeanList = new ArrayList<>();
        }
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            ChartBean bean = new ChartBean();
            bean.setDate(i+"");
            bean.setValue(100*random.nextInt(10)+"");
            barChartBeanList.add(bean);
        }
        barChartView.setNeedBg(true);
        barChartView.setData(barChartBeanList);
        barChartView.setyLableText("柱状图");
        barChartView.setNeedDrawConnectYDataLine(true);
        barChartView.setNeedDrawYScale(true);
        barChartView.setDrawConnectLineType(DrawConnectLineType.DrawDottedLine);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        String message = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ? "屏幕设置为：横屏" : "屏幕设置为：竖屏";

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void initData() {
        if(lineChartBeanList ==null){
            lineChartBeanList = new ArrayList<>();
        }
        lineChartView.setDefaultTextSize(24);
        Random random = new Random();
        for(int i=0;i<12;i++){
            ChartBean lineChartBean = new ChartBean();
            lineChartBean.setValue(String.valueOf(random.nextInt(10000)));
            lineChartBean.setDate(String.valueOf(i));
            lineChartBeanList.add(lineChartBean);
        }
        lineChartView.setData(lineChartBeanList);
        lineChartView.setyLableText("折线图");
        lineChartView.setDrawBgType(DrawBgType.DrawBitmap);
        lineChartView.setShowPicResource(R.mipmap.click_icon);
        lineChartView.setDrawConnectLineType(DrawConnectLineType.DrawDottedLine);
        lineChartView.setClickable(true);
        lineChartView.setNeedDrawConnectYDataLine(true);
        lineChartView.setConnectLineColor(getResources().getColor(R.color.default_color));
        lineChartView.setNeedBg(true);
        lineChartView.setDrawLineType(DrawLineType.Draw_Curve);
    }
    private void init() {
        if(mList==null){
            mList = new ArrayList<>();
        }
        int[] colors = new int[]{getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.default_color),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.endColor)};
        Random random = new Random();
        for(int i = 0;i<4;i++){
            PieChartBean pieChartBean = new PieChartBean();
            pieChartBean.setValue(random.nextInt(10)+i);
            pieChartBean.setColor(colors[i]);
            mList.add(pieChartBean);
        }

        pieChartView.setData(mList);
        pieChartView.setInsideText("Hello");
        pieChartView.setIsNeedAnimation(true,5000);
        pieChartView.setNeedInside(false);
        pieChartViewt.setData(mList);
        pieChartViewt.setInsideText("Hello");
        pieChartViewt.setIsNeedAnimation(true,5000);
        pieChartViewt.setNeedInside(false);
    }
    @Override
    public void onPause() {
        super.onPause();
        if(lineChartBeanList!=null&&lineChartBeanList.size()>0){
            lineChartBeanList.clear();
        }
        if(barChartBeanList!=null&&barChartBeanList.size()>0){
            barChartBeanList.clear();
        }
        if(mList!=null&&mList.size()>0){
            mList.clear();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        lineChartView.recycleBitmap();
    }
}