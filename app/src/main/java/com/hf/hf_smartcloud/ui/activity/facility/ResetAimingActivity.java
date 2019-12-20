package com.hf.hf_smartcloud.ui.activity.facility;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.bean.BleO9Bean;
import com.hf.hf_smartcloud.bean.BleOFBean;
import com.hf.hf_smartcloud.ui.fragment.FragmentTwoSun3;
import com.hf.hf_smartcloud.utils.CountDownView;
import com.hf.hf_smartcloud.utils.CustomStatusView;
import com.hf.hf_smartcloud.utils.MultiLineRadioGroup;
import com.hf.hf_smartcloud.utils.music.PlayVoice;
import com.hf.hf_smartcloud.utils.bluetooth.Utils;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class ResetAimingActivity extends Activity implements View.OnClickListener {
    static String resultLrc;
    static boolean resultstate = false;
    Drawable drawableno;
    Drawable drawable;
    Utils utils;
    CountDownView countDownView;
    int i = 1;
    boolean resultListb;
    boolean resultList09b;
    boolean resultList09datab;
    boolean resultList0fdatab;
    ArrayList<String> listof;
    ArrayList<String> listo9;
    ArrayList<BleO9Bean> resultList09Data;
    ArrayList<BleOFBean> resultList0FData;
    boolean isRdbtn = false;
    boolean isCkd = false;
    private boolean state = false;//倒计时监听状态
    private LinearLayout layout;
    private TextView btn_go1, btn_cancel,tv_success;
    private ImageView colse;
    private CheckBox cbox;
    private TextView pageNo, name;
    private TextView zero_zero, zero_current, zero_realtime_police, zero_full;//零点标定值，当前浓度，实时标定值，满量程标定值
    private LinearLayout calibration, select_cgq, prepare, demarcate, isSuccessful;
    private GifImageView gifImageView;
    private GifDrawable gifDrawable;
    private MultiLineRadioGroup mRadioGroup;
    private RadioButton rb1, rb2, rb3, rb4, rb5, rb6;
    private String realtime, gatt;
    private int location;
    private String newlocation;

    private CustomStatusView as_status;

    /**
     * 接受返回来的数据
     */
    public static synchronized void char6_display(final String str, byte[] data, String uuid) {
        Log.i("char6_display07", "char6_display str = " + str);
        if (uuid.equals(FragmentTwoSun3.UUID_CHAR6)) {  //  的串口透传  uuid是否等于UUID_CHAR6

            if (str.length() == 11) {
                Log.i("resultLrc:==str","resultLrc:==str:"+str.substring(7, 9));
                resultLrc = str.substring(7, 9);
                String resultLrcint = Utils.To10_2(Integer.parseInt(resultLrc));
                Log.i("resultLrc:","resultLrc:"+resultLrc+"resultLrcint:"+resultLrcint);
                String heightw = resultLrcint.substring(0, 1);
                Log.i("heightw:","heightw:"+heightw);
                if (heightw.equals("1")) {
                    //成功
                    resultstate = true;
                }
            } else {
                //失败
                resultstate = false;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetaiming);
        initViews();
        resultListb = Hawk.contains("resultList");
        resultList09b = Hawk.contains("resultList09");

        /*解析后的bean值*/
        resultList09datab = Hawk.contains("resultListData09");
        resultList0fdatab = Hawk.contains("resultListData");

        if (resultListb == true && resultList09b == true) {
            listof = Hawk.get("resultList");
            listo9 = Hawk.get("resultList09");
        }

        /*解析后的bean值*/
        if (resultList09datab == true && resultList0fdatab == true) {
            resultList09Data = Hawk.get("resultListData09");
            resultList0FData = Hawk.get("resultListData");
        }
    }

    private void initViews() {
        tv_success = findViewById(R.id.tv_success);
        as_status = findViewById(R.id.as_status);
        as_status.loadLoading();
        zero_zero = findViewById(R.id.zero_zero);
        zero_current = findViewById(R.id.zero_current);
        zero_realtime_police = findViewById(R.id.zero_realtime_police);
        zero_full = findViewById(R.id.zero_full);

        gifImageView = findViewById(R.id.gifImageView);
        gifDrawable = (GifDrawable) gifImageView.getDrawable();

        countDownView = findViewById(R.id.countDownView);
        layout = findViewById(R.id.pop_layout);
        calibration = findViewById(R.id.calibration);
        select_cgq = findViewById(R.id.select_cgq);
        prepare = findViewById(R.id.prepare);
        demarcate = findViewById(R.id.demarcate);
        isSuccessful = findViewById(R.id.isSuccessful);

        btn_go1 = findViewById(R.id.btn_go1);
        btn_cancel = findViewById(R.id.btn_cancel);
        colse = findViewById(R.id.colse);
        colse = findViewById(R.id.colse);
        pageNo = findViewById(R.id.pageNo);
        name = findViewById(R.id.name);
        btn_go1.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        colse.setOnClickListener(this);
        pageNo.setOnClickListener(this);
        windowColor();
        //添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
        layout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！", Toast.LENGTH_SHORT).show();
            }
        });

        mRadioGroup = findViewById(R.id.rgp);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        rb5 = findViewById(R.id.rb5);
        rb6 = findViewById(R.id.rb6);

        mRadioGroup.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MultiLineRadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb1:
                        Toast.makeText(ResetAimingActivity.this, "可燃气", Toast.LENGTH_SHORT).show();
                        realtime = listo9.get(0);
                        gatt = realtime.substring(26, 30);
                        location = 1;
                        isRdbtn = true;
                        sensorSelector();
                        break;
                    case R.id.rb2:
                        Toast.makeText(ResetAimingActivity.this, "氨气", Toast.LENGTH_SHORT).show();
                        realtime = listo9.get(1);
                        gatt = realtime.substring(26, 30);
                        location = 2;
                        isRdbtn = true;
                        sensorSelector();
                        break;
                    case R.id.rb3:
                        Toast.makeText(ResetAimingActivity.this, "氧气", Toast.LENGTH_SHORT).show();
                        realtime = listo9.get(2);
                        gatt = realtime.substring(26, 30);
                        location = 3;
                        isRdbtn = true;
                        sensorSelector();
                        break;
                    case R.id.rb4:
                        Toast.makeText(ResetAimingActivity.this, "二氧化硫", Toast.LENGTH_SHORT).show();
                        realtime = listo9.get(3);
                        gatt = realtime.substring(26, 30);
                        location = 4;
                        isRdbtn = true;
                        sensorSelector();
                        break;
                    case R.id.rb5:
                        Toast.makeText(ResetAimingActivity.this, "一氧化碳", Toast.LENGTH_SHORT).show();
                        realtime = listo9.get(4);
                        gatt = realtime.substring(26, 30);
                        location = 5;
                        isRdbtn = true;
                        sensorSelector();
                        break;
                    case R.id.rb6:
                        Toast.makeText(ResetAimingActivity.this, "硫化氢", Toast.LENGTH_SHORT).show();
                        realtime = listo9.get(5);
                        gatt = realtime.substring(26, 30);
                        location = 6;
                        isRdbtn = true;
                        sensorSelector();
                        break;
                    default:
                        break;
                }
            }
        });
        cbox = findViewById(R.id.cbox);
        cbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    isCkd = true;
                    sensorSelector();
                }
            }
        });
    }

    //实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go1:
                i++;
                if (btn_go1.getText().toString().trim().equals("关闭")) {
                    PlayVoice.stopVoice();
                    finish();
                    return;
                }
                if (i >= 1 && i <= 4) {
                    pageNo.setText(i + "/4");
                    if (i == 1) {
                        name.setText("零点标定");
                        calibration.setVisibility(View.VISIBLE);
                        select_cgq.setVisibility(View.GONE);
                        prepare.setVisibility(View.GONE);
                        demarcate.setVisibility(View.GONE);
                        isSuccessful.setVisibility(View.GONE);
                        gifDrawable.stop(); //停止播放
                        btn_cancel.setEnabled(false);
                        btn_go1.setEnabled(true);
                        btn_go1.setBackgroundResource(R.drawable.icon_yes_goon);


                    } else if (i == 2) {
                        name.setText("选择传感器");
                        calibration.setVisibility(View.GONE);
                        select_cgq.setVisibility(View.VISIBLE);
                        prepare.setVisibility(View.GONE);
                        demarcate.setVisibility(View.GONE);
                        isSuccessful.setVisibility(View.GONE);
                        gifDrawable.stop(); //停止播放
                        btn_cancel.setEnabled(true);
                        btn_cancel.setBackgroundResource(R.drawable.icon_yes_quxiao);
                        btn_go1.setEnabled(false);
                        btn_go1.setBackgroundResource(R.drawable.icon_no_goon);
                    } else if (i == 3) {
                        name.setText("准备");
                        calibration.setVisibility(View.GONE);
                        select_cgq.setVisibility(View.GONE);
                        prepare.setVisibility(View.VISIBLE);
                        demarcate.setVisibility(View.GONE);
                        isSuccessful.setVisibility(View.GONE);
                        gifDrawable.start(); //开始播放
                        gifDrawable.setLoopCount(3); //设置播放的次数，播放完了就自动停止
                        btn_cancel.setEnabled(true);
                        btn_cancel.setBackgroundResource(R.drawable.icon_yes_quxiao);
                        btn_go1.setEnabled(false);
                        btn_go1.setBackgroundResource(R.drawable.icon_no_goon);
                        state = false;
                    } else if (i == 4) {
                        PlayVoice.playVoice(this,2);
                        name.setText("标定");
                        calibration.setVisibility(View.GONE);
                        select_cgq.setVisibility(View.GONE);
                        prepare.setVisibility(View.GONE);
                        demarcate.setVisibility(View.VISIBLE);
                        isSuccessful.setVisibility(View.GONE);
                        countDownView.setCountdownTime(15);
                        countDownView.startCountDown();
                        gifDrawable.stop(); //停止播放

                        btn_cancel.setText("重标");
                        btn_go1.setText("关闭");
                        sensorSelector();
                        setVaulePage();//设置页面显示值
//                        sendZeroCommand();//发送零点标定指令
                        countDownView.setOnCountDownListener(new CountDownView.OnCountDownListener() {
                            @Override
                            public void countDownFinished(String text) {
                                if (text.equals("10") && state == false) {
                                    state = true;
                                    sendZeroCommand();//发送零点标定指令
                                    if (resultstate == true) {
                                        calibration.setVisibility(View.GONE);
                                        select_cgq.setVisibility(View.GONE);
                                        prepare.setVisibility(View.GONE);
                                        demarcate.setVisibility(View.GONE);
                                        isSuccessful.setVisibility(View.VISIBLE);
                                        as_status.loadSuccess();
                                        tv_success.setText("零点标定成功");
                                        PlayVoice.stopVoice();

                                    } else {
                                        calibration.setVisibility(View.GONE);
                                        select_cgq.setVisibility(View.GONE);
                                        prepare.setVisibility(View.GONE);
                                        demarcate.setVisibility(View.GONE);
                                        isSuccessful.setVisibility(View.VISIBLE);
                                        as_status.loadFailure();
                                        tv_success.setText("零点标定失败");
                                        PlayVoice.stopVoice();
                                    }

                                }
                            }
                        });
//
                    }
                }
                break;
            case R.id.btn_cancel:
                i--;
                if (btn_cancel.getText().toString().trim().equals("重标")) {
                    state = false;
                    name.setText("标定");
                    calibration.setVisibility(View.GONE);
                    select_cgq.setVisibility(View.GONE);
                    prepare.setVisibility(View.GONE);
                    demarcate.setVisibility(View.VISIBLE);
                    isSuccessful.setVisibility(View.GONE);
                    countDownView.setCountdownTime(15);
                    countDownView.startCountDown();
                    gifDrawable.stop(); //停止播放
                    PlayVoice.playVoice(this,2);
                    countDownView.setOnCountDownListener(new CountDownView.OnCountDownListener() {
                        @Override
                        public void countDownFinished(String text) {
                            if (text.equals("10") && state == false) {
                                state = true;
                                sendZeroCommand();//发送零点标定指令
                                if (resultstate == true) {
                                    calibration.setVisibility(View.GONE);
                                    select_cgq.setVisibility(View.GONE);
                                    prepare.setVisibility(View.GONE);
                                    demarcate.setVisibility(View.GONE);
                                    isSuccessful.setVisibility(View.VISIBLE);
                                    as_status.loadSuccess();
                                    tv_success.setText("零点标定成功");
                                    PlayVoice.stopVoice();
                                } else {
                                    calibration.setVisibility(View.GONE);
                                    select_cgq.setVisibility(View.GONE);
                                    prepare.setVisibility(View.GONE);
                                    demarcate.setVisibility(View.GONE);
                                    isSuccessful.setVisibility(View.VISIBLE);
                                    as_status.loadFailure();
                                    tv_success.setText("零点标定失败");
                                    PlayVoice.stopVoice();
                                }

                            }
                        }
                    });
                    return;
                }
                if (i >= 1 && i <= 4) {
                    pageNo.setText(i + "/4");
                    if (i == 1) {
                        name.setText("零点标定");
                        calibration.setVisibility(View.VISIBLE);
                        select_cgq.setVisibility(View.GONE);
                        prepare.setVisibility(View.GONE);
                        demarcate.setVisibility(View.GONE);
                        isSuccessful.setVisibility(View.GONE);
                        gifDrawable.stop(); //停止播放
                        btn_cancel.setEnabled(false);
                        btn_go1.setEnabled(true);
                        btn_go1.setBackgroundResource(R.drawable.icon_yes_goon);
                        i = 1;
                    } else if (i == 2) {
                        name.setText("选择传感器");
                        calibration.setVisibility(View.GONE);
                        select_cgq.setVisibility(View.VISIBLE);
                        prepare.setVisibility(View.GONE);
                        demarcate.setVisibility(View.GONE);
                        isSuccessful.setVisibility(View.GONE);
                        gifDrawable.stop(); //停止播放
                        if (isRdbtn == true) {
                            sensorSelector();
                        } else {
                            btn_cancel.setEnabled(true);
                            btn_cancel.setBackgroundResource(R.drawable.icon_yes_quxiao);
                            btn_go1.setEnabled(false);
                            btn_go1.setBackgroundResource(R.drawable.icon_no_goon);
                        }

                    } else if (i == 3) {
                        name.setText("准备");
                        calibration.setVisibility(View.GONE);
                        select_cgq.setVisibility(View.GONE);
                        prepare.setVisibility(View.VISIBLE);
                        demarcate.setVisibility(View.GONE);
                        isSuccessful.setVisibility(View.GONE);
                        gifDrawable.start(); //开始播放
                        gifDrawable.setLoopCount(3); //设置播放的次数，播放完了就自动停止
                        isRdbtn = false;
                        if (isCkd == true) {
                            sensorSelector();
                        } else {
                            btn_cancel.setEnabled(true);
                            btn_cancel.setBackgroundResource(R.drawable.icon_yes_quxiao);
                            btn_go1.setEnabled(false);
                            btn_go1.setBackgroundResource(R.drawable.icon_no_goon);
                        }
                        sensorSelector();
                    } else if (i == 4) {
                        name.setText("标定");
                        calibration.setVisibility(View.GONE);
                        select_cgq.setVisibility(View.GONE);
                        prepare.setVisibility(View.GONE);
                        demarcate.setVisibility(View.VISIBLE);
                        isSuccessful.setVisibility(View.GONE);
                        countDownView.setCountdownTime(10);
                        countDownView.startCountDown();
                        gifDrawable.stop(); //停止播放
                        btn_cancel.setEnabled(true);
                        btn_go1.setEnabled(true);
                    }
                }
                break;
            case R.id.colse:
                finish();
                break;
            default:
                break;
        }
    }

    public void setVaulePage() {
        if (resultList09Data.size() != 0) {
            zero_zero.setText("" + resultList09Data.get(location - 1).getZero());
            zero_current.setText("" + resultList0FData.get(location - 1).getNongdu());
            zero_realtime_police.setText("" + resultList09Data.get(location - 1).getRealtime());
            zero_full.setText("" + resultList09Data.get(location - 1).getFull());
        }

    }

    /**
     * 发送零点标定指令
     */
    public void sendZeroCommand() {
        if (location == 1) {
            newlocation = "01";
        } else if (location == 2) {
            newlocation = "02";
        } else if (location == 3) {
            newlocation = "03";
        } else if (location == 4) {
            newlocation = "04";
        } else if (location == 5) {
            newlocation = "05";
        } else if (location == 6) {
            newlocation = "06";
        }
        String sa = newlocation + "0607";
        String lrcValue = sa + gatt;
        String lrc = Utils.makeChecksum(lrcValue);

        String gattall = newlocation + "0607" + gatt + lrc.toUpperCase() + "\r\n";
        Log.i("==GATT:", "GATT:::" + gattall);
        Log.i("gattall", "gattall" + lrc.toUpperCase() + "==" + "==" + gattall);
        FragmentTwoSun3.writeChar6(":" + gattall);
    }

    /**
     * 传感器选择状态
     */
    private void sensorSelector() {
        btn_cancel.setEnabled(true);
        btn_cancel.setBackgroundResource(R.drawable.icon_yes_quxiao);
        btn_go1.setEnabled(true);
        btn_go1.setBackgroundResource(R.drawable.icon_yes_goon);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRdbtn = false;
        isCkd = false;
        state = false;
    }

    public void windowColor() {
        Window window = getWindow();
        //取消设置Window半透明的Flag
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏为透明/或者需要的颜色
        window.setStatusBarColor(getResources().getColor(R.color.colorAccent));

        getWindow().setGravity(Gravity.BOTTOM);//设置显示在底部 默认在中间
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;//设置宽度满屏
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);
        setFinishOnTouchOutside(true);//允许点击空白处关闭
    }
}
