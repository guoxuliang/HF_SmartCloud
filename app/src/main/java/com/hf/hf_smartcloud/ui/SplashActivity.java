package com.hf.hf_smartcloud.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.ui.activity.GuideActivity;
import com.hf.hf_smartcloud.ui.activity.LoginActivity;
import com.hf.hf_smartcloud.ui.activity.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout daojishi;
    private int recLen = 5;//跳过倒计时提示5秒
    private TextView tv;
    Timer timer = new Timer();
    private Handler handler;
    private Runnable runnable;
    //是否是第一次使用
    private boolean isFirstUse;
    private SharedPreferences preferences;
    private boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(flag, flag);
        setContentView(R.layout.activity_splash);
        preferences = getSharedPreferences("isFirstUse", MODE_PRIVATE);
        isFirstUse = preferences.getBoolean("isFirstUse", true);
        isLogin = getBooleanSharePreferences("isLogin", "isLogin");
        Log.i("===gxl==isLogin=","===gxl==isLogin="+isLogin);
        initView();
        timer.schedule(task, 1000, 1000);//等待时间一秒，停顿时间一秒
        /**
         * 正常情况下不点击跳过
         */
        handler = new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                //从闪屏界面跳转到首界面
                if (isFirstUse) {
                    openActivity(GuideActivity.class);
                } else if(isLogin==true){
                    openActivity( MainActivity.class);
                }else {
                    openActivity(LoginActivity.class);
                }
                finish();
                //实例化Editor对象
                SharedPreferences.Editor editor = preferences.edit();
                //存入数据
                editor.putBoolean("isFirstUse", false);
                //提交修改
                editor.commit();
            }
        }, 5000);//延迟5S后发送handler信息

    }


    private void initView() {
        tv = findViewById(R.id.tv);//跳过
        tv.setOnClickListener(this);//跳过监听
        daojishi = findviewByid(R.id.daojishi);
        daojishi.setOnClickListener(this);//跳过监听
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    recLen--;
                    tv.setText("" + recLen);
//                    tv.setText("跳过 " + recLen);
                    if (recLen < 0) {
                        timer.cancel();
                        tv.setVisibility(View.GONE);//倒计时到0隐藏字体
                    }
                }
            });
        }
    };

    /**
     * 点击跳过
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.tv:
//                //从闪屏界面跳转到首界面
//                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//                if (runnable != null) {
//                    handler.removeCallbacks(runnable);
//                }
//                break;
            case R.id.daojishi:
                //从闪屏界面跳转到首界面
                if (isLogin == true) {
                    openActivity(MainActivity.class);
                } else {
                    openActivity(LoginActivity.class);
                }
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                break;
            default:
                break;
        }
    }
}