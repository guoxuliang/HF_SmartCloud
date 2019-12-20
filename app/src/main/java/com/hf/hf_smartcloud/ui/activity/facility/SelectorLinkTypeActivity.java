package com.hf.hf_smartcloud.ui.activity.facility;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.ui.zxing.QrCodeActivity;

public class SelectorLinkTypeActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_scan, ll_ble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectlinktype);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(flag, flag);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xb4a4F7);
        }
        initView();
        initTitle();
    }
    private void initTitle() {
        TextView tvBj = findviewByid(R.id.tvBj);
        tvBj.setVisibility(View.GONE);
        TextView tv_title = findviewByid(R.id.tv_title);
        tv_title.setText("连接方式");
        ImageView btn_back = findviewByid(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initView() {
        ll_scan = findviewByid(R.id.ll_scan);
        ll_ble = findviewByid(R.id.ll_ble);
        ll_scan.setOnClickListener(this);
        ll_ble.setOnClickListener(this);


    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_scan:
                //TODO 扫码
                openActivity(QrCodeActivity.class);
                finish();
                break;
            case R.id.ll_ble:
                //TODO 蓝牙
                openActivity(BluetoothSearchActivity.class);
                finish();
                break;
        }
    }
}
