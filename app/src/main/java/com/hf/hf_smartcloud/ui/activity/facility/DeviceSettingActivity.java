package com.hf.hf_smartcloud.ui.activity.facility;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.ui.fragment.FragmentTwoSun3;
import com.hf.hf_smartcloud.utils.CommonDialog;
import com.hf.hf_smartcloud.utils.bluetooth.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Thread.sleep;

public class DeviceSettingActivity extends BaseActivity implements View.OnClickListener {
    static TextView tv_name, tv_mac, timefactory,timedate;
    static String DeliveryTime = null;
    static int year;
    static String month = null;
    static String day = null;
    static String when = null;
    String timeYear;//年
    String timeMonth;//月
    String timeDay;//日
    String timeHour;//时
    String timeMinute;//分
    private ImageView btn_back, btn_add, btn_sancode;
    private TextView tv_title;
    private LinearLayout reset, mmol;
    private String name, mac;
    private String systemtime;
    private LinearLayout timing, clearrecord;//时间矫正,清除记录

    /**
     * 接受返回来的数据
     */
    public static synchronized void char6_displayMmolAiming(final String str, byte[] data, String uuid) {
        Log.i("char6_display23or30", "char6_display str = " + str);
        if (uuid.equals(FragmentTwoSun3.UUID_CHAR6)) {  //  的串口透传  uuid是否等于UUID_CHAR6
            if (str.length() == 15) {
                //  #0123 13 0B 07B7
//                #0123191107AB
                year = Integer.parseInt(str.substring(5, 7)) + 2000;
                month = str.substring(7, 9);
                day = str.substring(9, 11);
//                when = Utils.To16_10(str.substring(11,13));
                DeliveryTime = year + "-" + month + "-" + day;
                Log.i("DeliveryTime", "year:" + year + "month:" + month + "day:" + day);
                timefactory.setText(DeliveryTime);
            }else if(str.length() == 11){
                timedate.setText(sysTime());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devicesetting);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(flag, flag);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xb4a4F7);
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name = bundle.getString("name");
            mac = bundle.getString("mac_addr");
        }
        initTitle();
        initViews();
        systemtime = sysTime();
        FragmentTwoSun3.writeChar6(":010323D9" + "\r\n");//出场时间

    }

    /**
     * 获取系统当前时间
     */
    private static String sysTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String sysdateTime = simpleDateFormat.format(date);
        return sysdateTime;
    }

    private void initTitle() {
        tv_title = findviewByid(R.id.tv_title);
        tv_title.setText("设备设置");
        btn_add = findviewByid(R.id.btn_add);
        btn_add.setVisibility(View.GONE);
        btn_sancode = findviewByid(R.id.btn_sancode);
        btn_sancode.setVisibility(View.GONE);
        btn_back = findviewByid(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceSettingActivity.this.finish();
            }
        });

    }

    private void initViews() {
        timedate = findviewByid(R.id.timedate);
        timefactory = findviewByid(R.id.timefactory);
        tv_name = findviewByid(R.id.tv_name);
        tv_mac = findviewByid(R.id.tv_mac);
        tv_name.setText(name);
        tv_mac.setText(mac);
        reset = findviewByid(R.id.reset);
        mmol = findviewByid(R.id.mmol);
        timing = findviewByid(R.id.timing);
        clearrecord = findviewByid(R.id.clearrecord);
        reset.setOnClickListener(this);
        mmol.setOnClickListener(this);
        timing.setOnClickListener(this);
        clearrecord.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (DeliveryTime != null) {
            timefactory.setText(DeliveryTime);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset:
                // TODO 清零标定
                openActivity(ResetAimingActivity.class);
                break;
            case R.id.mmol:
                //TODO 浓度标定
                openActivity(MmolAimingActivity.class);
                break;
            case R.id.timing:
                //TODO 时间矫正
                initDialogTiming();
                break;
            case R.id.clearrecord:
                //TODO 清除记录
                initDialogClear();
                break;
        }
    }
/**
 * 发送校时指令
 * */
    private void initDialogTiming() {
        final CommonDialog dialog = new CommonDialog(DeviceSettingActivity.this);
        dialog.setMessage("请确定是否发送校时指令？")
//                .setImageResId(R.mipmap.ic_launcher)
                .setTitle("系统提示")
                .setSingle(false).setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
            @Override
            public void onPositiveClick() {
                dialog.dismiss();
                Toast.makeText(DeviceSettingActivity.this, "确定", Toast.LENGTH_SHORT).show();
                //TODO 发送校时指令
                String timtingTime = timting();
                if (!timtingTime.equals("") && timtingTime.length() >= 20) {
                    ArrayList<String> strdata = new ArrayList<>();

                    String fbtimtingTime = timtingTime.substring(0, 20);
                    String sytimtingTime = timtingTime.substring(20);
                    strdata.add(fbtimtingTime);
                    strdata.add(sytimtingTime);

                    for (int i = 0; i < strdata.size(); i++) {
                        try {
                            sleep(200);
                            FragmentTwoSun3.writeChar6(strdata.get(i));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

            @Override
            public void onNegtiveClick() {
                dialog.dismiss();
                Toast.makeText(DeviceSettingActivity.this, "取消", Toast.LENGTH_SHORT).show();
            }
        }).show();
    }

/**
 * 清除报警记录
 * */
    private void initDialogClear() {
        final CommonDialog dialog2 = new CommonDialog(DeviceSettingActivity.this);
        dialog2.setMessage("是否清除报警记录？")
//                .setImageResId(R.mipmap.ic_launcher)
                .setTitle("系统提示")
                .setPositive("确定")
                .setNegtive("取消")
                .setSingle(false).setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
            @Override
            public void onPositiveClick() {
                dialog2.dismiss();
                Toast.makeText(DeviceSettingActivity.this, "确定", Toast.LENGTH_SHORT).show();
                //TODO 发送清除记录指令
                FragmentTwoSun3.writeChar6(":01062200D7" + "\r\n");
                dialog2.setPositive("确定");

            }

            @Override
            public void onNegtiveClick() {
                dialog2.dismiss();
                Toast.makeText(DeviceSettingActivity.this, "取消", Toast.LENGTH_SHORT).show();
                dialog2.setNegtive("取消");
            }
        }).show();
    }

    /**
     * 校时指令
     */
    private String timting() {
        String timeinstr = null;//时间指令
        String lrcstr = null;//时间指令
        Calendar c = Calendar.getInstance();
        //获取年份
        timeYear = String.valueOf(c.get(Calendar.YEAR) - 2000);   //获取年份
        timeMonth = String.valueOf(c.get(Calendar.MONTH) + 1);  //获取月份
        timeDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));  //获取日期
        timeHour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));  //获取小时
        timeMinute = String.valueOf(c.get(Calendar.MINUTE)); //获取分钟

        /**年*/
        if (!String.valueOf(c.get(Calendar.YEAR)).equals("")) {
            timeYear = String.valueOf(c.get(Calendar.YEAR) - 2000);
        }
        /**月*/
        if ((c.get(Calendar.MONTH) + 1) < 10) {
            timeMonth = "0" + (c.get(Calendar.MONTH) + 1);
        }
        /**日*/
        if (c.get(Calendar.DAY_OF_MONTH) < 10) {
            timeDay = "0" + (c.get(Calendar.DAY_OF_MONTH));
        }
        /**时*/
        if (c.get(Calendar.HOUR_OF_DAY) < 10) {
            timeHour = "0" + (c.get(Calendar.HOUR_OF_DAY));
        }
        /**分*/
        if (c.get(Calendar.MINUTE) < 10) {
            timeMinute = "0" + (c.get(Calendar.MINUTE));
        }
        lrcstr = Utils.makeChecksum("010630" + timeYear + timeMonth + timeDay + timeHour + timeMinute);
        Log.i("lrcstr", "lrcstr" + lrcstr);
        timeinstr = ":010630" + timeYear + timeMonth + timeDay + timeHour + timeMinute + lrcstr + "\r\n";
        Log.i("timeinstr", "timeinstr" + timeinstr);

        return timeinstr;
    }


}
