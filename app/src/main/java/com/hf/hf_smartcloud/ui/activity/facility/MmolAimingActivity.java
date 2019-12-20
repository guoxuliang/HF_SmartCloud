package com.hf.hf_smartcloud.ui.activity.facility;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import com.hf.hf_smartcloud.utils.MoneyInputFilter;
import com.hf.hf_smartcloud.utils.MultiLineRadioGroup;
import com.hf.hf_smartcloud.utils.music.PlayVoice;
import com.hf.hf_smartcloud.utils.bluetooth.Utils;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MmolAimingActivity extends Activity implements View.OnClickListener {
    static String resultLrc;
    CountDownView countDownView_mmol;
private CheckBox cbox_mmol;
    private boolean state = false;//倒计时监听状态
    static boolean resultstate = false;//接受返回的参数是否成功
    boolean isRdbtn = false;
    boolean isCkd = false;
    int i = 1;
    boolean resultListb;
    boolean resultList09b;
    boolean resultList09datab;
    boolean resultList0fdatab;
    ArrayList<String> listof;
    ArrayList<String> listo9;
    ArrayList<BleO9Bean> resultList09Data;
    ArrayList<BleOFBean> resultList0FData;
    private LinearLayout pop_layout_concentration;
    private TextView btn_go1mic, btn_cancelmic;
    private ImageView colse_concentration;
    private CustomStatusView pic_mic;
    private TextView pageNomic, name_concentration, msg_mic;
    private LinearLayout mmol_mmol, cgq_concentration, param_concentration, prepare_concentration, calibrationin, isSuccessful_mic;
    private GifImageView gifImageView_concentration;//gif动画
    private GifDrawable gifDrawable;//gif动画
    private MultiLineRadioGroup rgp_concentration_bq;//气体选择Group
    private RadioButton concentration_bq1, concentration_bq2, concentration_bq3, concentration_bq4, concentration_bq5, concentration_bq6;//气体选择
    private EditText et_con_param;//标气参数输入
    private TextView zero, current, realtime_police, full;//零点标定值，当前浓度，实时标定值，满量程标定值
    private String realtime, gatt;
    private int location;
    private String newlocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmolaiming);
        initViews();
        /*原始指令数据*/
        resultListb = Hawk.contains("resultList");
        resultList09b = Hawk.contains("resultList09");
        /*解析后的bean值*/
        resultList09datab = Hawk.contains("resultListData09");
        resultList0fdatab = Hawk.contains("resultListData");
        /*原始指令数据*/
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

        zero = findViewById(R.id.zero);
        current = findViewById(R.id.current);
        realtime_police = findViewById(R.id.realtime_police);
        full = findViewById(R.id.full);

        gifImageView_concentration = findViewById(R.id.gifImageView_concentration);//gif控件
        gifDrawable = (GifDrawable) gifImageView_concentration.getDrawable();
        countDownView_mmol = findViewById(R.id.countDownView_mmol);//倒计时
        pop_layout_concentration = findViewById(R.id.pop_layout_concentration);
        mmol_mmol = findViewById(R.id.mmol_mmol);
        cgq_concentration = findViewById(R.id.cgq_concentration_bq);
        param_concentration = findViewById(R.id.param_concentration);
        prepare_concentration = findViewById(R.id.prepare_concentration);
        isSuccessful_mic = findViewById(R.id.isSuccessful_mic);
        calibrationin = findViewById(R.id.calibrationin);
        pic_mic = findViewById(R.id.pic_mic);
        pic_mic.loadLoading();
        msg_mic = findViewById(R.id.msg_mic);
        btn_go1mic = findViewById(R.id.btn_go1mic);
        btn_cancelmic = findViewById(R.id.btn_cancelmic);
        colse_concentration = findViewById(R.id.colse_concentration);
        pageNomic = findViewById(R.id.pageNomic);
        name_concentration = findViewById(R.id.name_concentration);
        rgp_concentration_bq = findViewById(R.id.rgp_concentration_bq);
        concentration_bq1 = findViewById(R.id.rb1_concentration_bq1);
        concentration_bq2 = findViewById(R.id.rb2_concentration_bq2);
        concentration_bq3 = findViewById(R.id.rb3_concentration_bq3);
        concentration_bq4 = findViewById(R.id.rb4_concentration_bq4);
        concentration_bq5 = findViewById(R.id.rb5_concentration_bq5);
        concentration_bq6 = findViewById(R.id.rb6_concentration_bq6);
        rgp_concentration_bq.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MultiLineRadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb1_concentration_bq1:
                        Toast.makeText(MmolAimingActivity.this, "可燃气", Toast.LENGTH_SHORT).show();
                        realtime = listo9.get(0);
                        gatt = realtime.substring(26, 30);
                        location = 1;
                        isRdbtn = true;
                        sensorSelector();
                        break;
                    case R.id.rb2_concentration_bq2:
                        Toast.makeText(MmolAimingActivity.this, "氨气", Toast.LENGTH_SHORT).show();
                        realtime = listo9.get(1);
                        gatt = realtime.substring(26, 30);
                        location = 2;
                        isRdbtn = true;
                        sensorSelector();
                        break;
                    case R.id.rb3_concentration_bq3:
                        Toast.makeText(MmolAimingActivity.this, "氧气", Toast.LENGTH_SHORT).show();
                        realtime = listo9.get(2);
                        gatt = realtime.substring(26, 30);
                        location = 3;
                        isRdbtn = true;
                        sensorSelector();
                        break;
                    case R.id.rb4_concentration_bq4:
                        Toast.makeText(MmolAimingActivity.this, "二氧化硫", Toast.LENGTH_SHORT).show();
                        realtime = listo9.get(3);
                        gatt = realtime.substring(26, 30);
                        location = 4;
                        isRdbtn = true;
                        sensorSelector();
                        break;
                    case R.id.rb5_concentration_bq5:
                        Toast.makeText(MmolAimingActivity.this, "一氧化碳", Toast.LENGTH_SHORT).show();
                        realtime = listo9.get(4);
                        gatt = realtime.substring(26, 30);
                        location = 5;
                        isRdbtn = true;
                        sensorSelector();
                        break;
                    case R.id.rb6_concentration_bq6:
                        Toast.makeText(MmolAimingActivity.this, "硫化氢", Toast.LENGTH_SHORT).show();
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
        et_con_param = findViewById(R.id.et_con_param);
        et_con_param.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Toast.makeText(MmolAimingActivity.this,"输入结束了",Toast.LENGTH_LONG).show();
              if(editable!=null && !editable.equals("")){
                      standardInput();
                      sensorSelector();
                  Toast.makeText(MmolAimingActivity.this,"标气参数不为空",Toast.LENGTH_LONG).show();
              }else {
                  Toast.makeText(MmolAimingActivity.this,"请输入标气参数",Toast.LENGTH_LONG).show();
                  sensorSelectorNo();
              }
            }
        });
//        et_con_param.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                if(!textView.equals("")){
//                    standardInput();
//                      sensorSelector();
//                    Toast.makeText(MmolAimingActivity.this,"标气参数不为空",Toast.LENGTH_LONG).show();
//                }else {
//                    Toast.makeText(MmolAimingActivity.this,"请输入标气参数",Toast.LENGTH_LONG).show();
//                  sensorSelectorNo();
//                }
//                return false;
//            }
//        });
        btn_go1mic.setOnClickListener(this);
        btn_cancelmic.setOnClickListener(this);
        colse_concentration.setOnClickListener(this);
        pageNomic.setOnClickListener(this);
        windowColor();
        //添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
        pop_layout_concentration.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！", Toast.LENGTH_SHORT).show();
            }
        });

        cbox_mmol = findViewById(R.id.cbox_mmol);
        cbox_mmol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
            case R.id.btn_go1mic:
                i++;
                if (btn_go1mic.getText().toString().trim().equals("关闭")) {
                    finish();
                    PlayVoice.stopVoice();
                    return;
                }
                if (i >= 1 && i <= 5) {
                    pageNomic.setText(i + "/5");
                    if (i == 1) {
                        name_concentration.setText("零点标定");
                        mmol_mmol.setVisibility(View.VISIBLE);
                        cgq_concentration.setVisibility(View.GONE);
                        param_concentration.setVisibility(View.GONE);
                        prepare_concentration.setVisibility(View.GONE);
                        calibrationin.setVisibility(View.GONE);
                        isSuccessful_mic.setVisibility(View.GONE);
                        btn_cancelmic.setEnabled(false);
                        btn_go1mic.setBackgroundResource(R.drawable.icon_no_quxiao);
                        btn_go1mic.setEnabled(true);
                        btn_go1mic.setBackgroundResource(R.drawable.icon_yes_goon);
                        i = 1;
                    } else if (i == 2) {
                        name_concentration.setText("选择传感器");
                        mmol_mmol.setVisibility(View.GONE);
                        cgq_concentration.setVisibility(View.VISIBLE);
                        param_concentration.setVisibility(View.GONE);
                        prepare_concentration.setVisibility(View.GONE);
                        calibrationin.setVisibility(View.GONE);
                        isSuccessful_mic.setVisibility(View.GONE);
                        if (isRdbtn == true) {
                            sensorSelector();
                        } else {
                            sensorSelectorNo();
                        }

                    } else if (i == 3) {
                        name_concentration.setText("标气参数");
                        mmol_mmol.setVisibility(View.GONE);
                        cgq_concentration.setVisibility(View.GONE);
                        param_concentration.setVisibility(View.VISIBLE);
                        prepare_concentration.setVisibility(View.GONE);
                        calibrationin.setVisibility(View.GONE);
                        isSuccessful_mic.setVisibility(View.GONE);
                        sensorSelectorNo();
                        isRdbtn = false;
                        /**标气参数函数
                         * 1.小数点
                         * 2.精度
                         * 3.量程
                         * */
//                        if(!et_con_param.getText().toString().trim().equals("")){
//                            standardInput();
//                            sensorSelector();
//                        }else {
//                            Toast.makeText(MmolAimingActivity.this,"请输入标气参数",Toast.LENGTH_LONG).show();
//                            sensorSelectorNo();
//                        }

                    } else if (i == 4) {
                        name_concentration.setText("准备");
                        mmol_mmol.setVisibility(View.GONE);
                        cgq_concentration.setVisibility(View.GONE);
                        param_concentration.setVisibility(View.GONE);
                        prepare_concentration.setVisibility(View.VISIBLE);
                        calibrationin.setVisibility(View.GONE);
                        isSuccessful_mic.setVisibility(View.GONE);
                        gifDrawable.start(); //开始播放
                        gifDrawable.setLoopCount(3); //设置播放的次数，播放完了就自动停止
                        if (isCkd == true) {
                            sensorSelector();
                        } else {
                           sensorSelectorNo();
                        }
                        state = false;
                    } else if (i == 5) {
                        isCkd = false;
                        PlayVoice.playVoice(this,2);
                        name_concentration.setText("标定中...");
                        mmol_mmol.setVisibility(View.GONE);
                        cgq_concentration.setVisibility(View.GONE);
                        param_concentration.setVisibility(View.GONE);
                        prepare_concentration.setVisibility(View.GONE);
                        calibrationin.setVisibility(View.VISIBLE);
                        isSuccessful_mic.setVisibility(View.GONE);
                        countDownView_mmol.setCountdownTime(15);
                        countDownView_mmol.startCountDown();
                        gifDrawable.stop(); //停止播放
                        btn_cancelmic.setText("重标");
                        btn_go1mic.setText("关闭");
                        sensorSelector();
                        setVaulePage();
                        /**发送指令函数
                         * 1.a获取满量程、b当前浓度【获取输入的值】、c零点AD、d实时AD、e零点AD
                         * 2.套用函数
                         * 3.lrc计算
                         * 4.拼接指令【:+地址+03or06+寄存器+值+LRC+"\r\n"】
                         * 5.发送指令
                         * 6.接收并判断指令
                         * */

                        countDownView_mmol.setOnCountDownListener(new CountDownView.OnCountDownListener() {
                            @Override
                            public void countDownFinished(String text) {
                                if (text.equals("10") && state == false) {
                                    state = true;
                                    sendCommand();
                                    if (resultstate == true) {
                                        name_concentration.setText("标定状态");
                                        mmol_mmol.setVisibility(View.GONE);
                                        cgq_concentration.setVisibility(View.GONE);
                                        param_concentration.setVisibility(View.GONE);
                                        prepare_concentration.setVisibility(View.GONE);
                                        calibrationin.setVisibility(View.GONE);
                                        isSuccessful_mic.setVisibility(View.VISIBLE);
                                        pic_mic.loadSuccess();
                                        msg_mic.setText("零点标定成功");
                                        PlayVoice.stopVoice();

                                    } else {
                                        name_concentration.setText("标定状态");
                                        mmol_mmol.setVisibility(View.GONE);
                                        cgq_concentration.setVisibility(View.GONE);
                                        param_concentration.setVisibility(View.GONE);
                                        prepare_concentration.setVisibility(View.GONE);
                                        calibrationin.setVisibility(View.GONE);
                                        isSuccessful_mic.setVisibility(View.VISIBLE);
                                        pic_mic.loadFailure();
                                        msg_mic.setText("零点标定失败");
                                        PlayVoice.stopVoice();
                                    }

                                }
                            }
                        });
                    }
                }
                break;
            case R.id.btn_cancelmic:
                i--;
                if (btn_cancelmic.getText().toString().trim().equals("重标")) {
                    PlayVoice.playVoice(this,2);
                    state = false;
                    name_concentration.setText("标定中...");
                    mmol_mmol.setVisibility(View.GONE);
                    cgq_concentration.setVisibility(View.GONE);
                    param_concentration.setVisibility(View.GONE);
                    prepare_concentration.setVisibility(View.GONE);
                    calibrationin.setVisibility(View.VISIBLE);
                    isSuccessful_mic.setVisibility(View.GONE);
                    countDownView_mmol.setCountdownTime(15);
                    countDownView_mmol.startCountDown();
                    gifDrawable.stop(); //停止播放
                    setVaulePage();
                    /**发送指令函数
                     * 1.a获取满量程、b当前浓度【获取输入的值】、c零点AD、d实时AD、e零点AD
                     * 2.套用函数
                     * 3.lrc计算
                     * 4.拼接指令【:+地址+03or06+寄存器+值+LRC+"\r\n"】
                     * 5.发送指令
                     * 6.接收并判断指令
                     * */

                    countDownView_mmol.setOnCountDownListener(new CountDownView.OnCountDownListener() {
                        @Override
                        public void countDownFinished(String text) {
                            if (text.equals("10") && state == false) {
                                state = true;
                                sendCommand();
                                if (resultstate == true) {
                                    name_concentration.setText("标定状态");
                                    mmol_mmol.setVisibility(View.GONE);
                                    cgq_concentration.setVisibility(View.GONE);
                                    param_concentration.setVisibility(View.GONE);
                                    prepare_concentration.setVisibility(View.GONE);
                                    calibrationin.setVisibility(View.GONE);
                                    isSuccessful_mic.setVisibility(View.VISIBLE);
                                    pic_mic.loadSuccess();
                                    msg_mic.setText("零点标定成功");
                                    PlayVoice.stopVoice();

                                } else {
                                    name_concentration.setText("标定状态");
                                    mmol_mmol.setVisibility(View.GONE);
                                    cgq_concentration.setVisibility(View.GONE);
                                    param_concentration.setVisibility(View.GONE);
                                    prepare_concentration.setVisibility(View.GONE);
                                    calibrationin.setVisibility(View.GONE);
                                    isSuccessful_mic.setVisibility(View.VISIBLE);
                                    pic_mic.loadFailure();
                                    msg_mic.setText("零点标定失败");
                                    PlayVoice.stopVoice();
                                }

                            }
                        }
                    });
                    return;
                }
                if (i >= 1 && i <= 5) {
                    pageNomic.setText(i + "/5");
                    if (i == 1) {
                        name_concentration.setText("零点标定");
                        mmol_mmol.setVisibility(View.VISIBLE);
                        cgq_concentration.setVisibility(View.GONE);
                        param_concentration.setVisibility(View.GONE);
                        prepare_concentration.setVisibility(View.GONE);
                        calibrationin.setVisibility(View.GONE);
                        isSuccessful_mic.setVisibility(View.GONE);
                        btn_go1mic.setEnabled(true);
                        btn_cancelmic.setEnabled(false);
                        btn_go1mic.setBackgroundResource(R.drawable.icon_yes_goon);
                    } else if (i == 2) {
                        name_concentration.setText("选择传感器");
                        mmol_mmol.setVisibility(View.GONE);
                        cgq_concentration.setVisibility(View.VISIBLE);
                        param_concentration.setVisibility(View.GONE);
                        prepare_concentration.setVisibility(View.GONE);
                        calibrationin.setVisibility(View.GONE);
                        isSuccessful_mic.setVisibility(View.GONE);
                        gifDrawable.stop(); //停止播放
                        if (isRdbtn == true) {
                            sensorSelector();
                        } else {
                            sensorSelectorNo();
                        }
                    } else if (i == 3) {
                        name_concentration.setText("标气参数");
                        mmol_mmol.setVisibility(View.GONE);
                        cgq_concentration.setVisibility(View.GONE);
                        param_concentration.setVisibility(View.VISIBLE);
                        prepare_concentration.setVisibility(View.GONE);
                        calibrationin.setVisibility(View.GONE);
                        isSuccessful_mic.setVisibility(View.GONE);
                        gifDrawable.stop(); //停止播放
                        if(!et_con_param.getText().toString().trim().equals("")){
                            standardInput();
                            sensorSelector();
                        }else {
                            Toast.makeText(MmolAimingActivity.this,"请输入标气参数",Toast.LENGTH_LONG).show();
                            sensorSelectorNo();
                        }

                    } else if (i == 4) {
                        name_concentration.setText("准备");
                        mmol_mmol.setVisibility(View.GONE);
                        cgq_concentration.setVisibility(View.GONE);
                        param_concentration.setVisibility(View.GONE);
                        prepare_concentration.setVisibility(View.VISIBLE);
                        calibrationin.setVisibility(View.GONE);
                        isSuccessful_mic.setVisibility(View.GONE);
                        gifDrawable.start(); //开始播放
                        gifDrawable.setLoopCount(3); //设置播放的次数，播放完了就自动停止
                        sensorSelector();
                    } else if (i == 5) {
                        name_concentration.setText("标定中...");
                        mmol_mmol.setVisibility(View.GONE);
                        cgq_concentration.setVisibility(View.GONE);
                        param_concentration.setVisibility(View.GONE);
                        prepare_concentration.setVisibility(View.GONE);
                        calibrationin.setVisibility(View.VISIBLE);
                        isSuccessful_mic.setVisibility(View.GONE);
                        countDownView_mmol.setCountdownTime(60);
                        countDownView_mmol.startCountDown();
                        PlayVoice.playVoice(this,2);

                    }
                }
                break;
            case R.id.colse_concentration:
                finish();
                break;
            default:
                break;
        }
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

    public void standardInput() {
        /**标气参数函数
         * 1.小数点
         * 2.精度
         * 3.量程
         * */
        MoneyInputFilter filter = new MoneyInputFilter();
        String range = resultList09Data.get(location - 1).getRange();//获取量程
        String jingdu = resultList0FData.get(location - 1).getJingdu();//精度
        filter.setMaxValue(Double.parseDouble(range));// 最多可输入1万元
        if (jingdu.equals("00")) {
            filter.setDecimalLength(0);//保留小数点后0位
        } else if (jingdu.equals("01")) {
            filter.setDecimalLength(1);//保留小数点后1位
        } else if (jingdu.equals("10")) {
            filter.setDecimalLength(2);//保留小数点后2位
        }
        InputFilter[] filters = {filter};
        et_con_param.setFilters(filters);
    }

    public void sendCommand() {
        /**发送指令函数
         * 1.a获取满量程、b当前浓度【获取输入的值】、c零点AD、d实时AD、e零点AD
         * 2.套用函数
         * 3.lrc计算
         * 4.拼接指令【:+地址+03or06+寄存器+值+LRC+"\r\n"】
         * 5.发送指令
         * 6.接收并判断指令
         * */
        String full = resultList09Data.get(location - 1).getFull();//满量程  a
        String current = et_con_param.getText().toString().trim();//当前浓度  b
        String zero = resultList09Data.get(location - 1).getZero();//零点AD  c  e
        String realtime = resultList09Data.get(location - 1).getRealtime();//实时AD值  d
        String funVaule = String.valueOf(Utils.concentration(Double.parseDouble(full), Double.parseDouble(current), Double.parseDouble(zero), Double.parseDouble(realtime), Double.parseDouble(zero)));
        String funVaulelrc = String.valueOf(Double.valueOf(funVaule).intValue());//转换为Int类型

        String vue = Utils.To10_16(funVaulelrc);
        String vues = null;
        if (vue.length() == 3) {
            vues = "0" + vue;
        } else if (vue.length() == 2) {
            vues = "00" + vue;
        } else if (vue.length() == 1) {
            vues = "000" + vue;
        }
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
        Log.i("0608", "0608:" + newlocation + "0608" + vues);
        String lrc = Utils.makeChecksum(newlocation + "0608" + vues);

        String gattall = newlocation + "0608" + vues + lrc.toUpperCase() + "\r\n";
        Log.i("gattall", "gattall:" + gattall);
        FragmentTwoSun3.writeChar6(":" + gattall);
    }

    public void setVaulePage() {
        if (resultList09Data.size() != 0) {
            zero.setText("" + resultList09Data.get(location - 1).getZero());
            current.setText("" + resultList0FData.get(location - 1).getNongdu());
            realtime_police.setText("" + resultList09Data.get(location - 1).getRealtime());
            full.setText("" + resultList09Data.get(location - 1).getFull());
        }
    }

    /**
     * 传感器选择状态
     */
    private void sensorSelector() {
        btn_cancelmic.setEnabled(true);
        btn_cancelmic.setBackgroundResource(R.drawable.icon_yes_quxiao);
        btn_go1mic.setEnabled(true);
        btn_go1mic.setBackgroundResource(R.drawable.icon_yes_goon);
    }
    private void sensorSelectorNo() {
        btn_cancelmic.setEnabled(true);
        btn_cancelmic.setBackgroundResource(R.drawable.icon_yes_quxiao);
        btn_go1mic.setEnabled(false);
        btn_go1mic.setBackgroundResource(R.drawable.icon_no_goon);
    }

    /**
     * 接受返回来的数据
     */
    public static synchronized void char6_displayMmolAiming(final String str, byte[] data, String uuid) {
        Log.i("char6_display08", "char6_display str = " + str);
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
    protected void onDestroy() {
        super.onDestroy();
        isRdbtn = false;
        isCkd = false;
        state = false;
    }
}
