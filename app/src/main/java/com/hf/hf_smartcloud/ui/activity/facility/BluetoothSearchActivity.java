package com.hf.hf_smartcloud.ui.activity.facility;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.utils.music.PlayVoice;

public class BluetoothSearchActivity extends BaseActivity {
    private TextView timetv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetoothsearch);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(flag, flag);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xb4a4F7);
        }
        initViews();
        initTitle();
        PlayVoice.playVoice(this,1);
    }
    private void initViews() {
        timetv = findViewById(R.id.timetv);
    }

    private void initTitle() {
       TextView tvBj = findviewByid(R.id.tvBj);
        tvBj.setVisibility(View.GONE);
        TextView tv_title = findviewByid(R.id.tv_title);
        tv_title.setText("搜索设备");
       ImageView btn_back = findviewByid(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    CountDownTimer timer = new CountDownTimer(5 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timetv.setText("正在进行蓝牙搜索" + millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            Toast.makeText(BluetoothSearchActivity.this,"搜索完成",Toast.LENGTH_SHORT).show();
            PlayVoice.stopVoice();
            Intent intent=new Intent(BluetoothSearchActivity.this,SearchDeviceOkActivity.class);
            startActivity(intent);

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        timer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PlayVoice.stopVoice();
    }
}
