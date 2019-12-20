package com.hf.hf_smartcloud.ui.activity.facility;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;

public class GasSettingActivity extends BaseActivity {
    String name, type, liangcheng, one, two, unit, jingdu, chuchangdate, xiacibd, shouming;
    private ImageView btn_back, btn_add, btn_sancode;
    private TextView tv_title;
    private TextView tv_name, tv_type, tv_liangcheng, tv_one, tv_two, tv_unit, tv_jingdu, tv_chuchangdate, tv_xiacibd, tv_shouming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gassetting);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(flag, flag);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0xb4a4F7);
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name = bundle.getString("gastype");
            liangcheng = bundle.getString("range");
            one = bundle.getString("one");
            two = bundle.getString("two");
            unit = bundle.getString("unit");
            jingdu = bundle.getString("jingdu");
            chuchangdate = bundle.getString("chuchangdate");
            xiacibd = bundle.getString("xiacibd");
            shouming = bundle.getString("shouming");

        }
        initTitle();
        initViews();
    }


    private void initTitle() {
        tv_title = findviewByid(R.id.tv_title);
        tv_title.setText("蓝牙传感器详情");
        btn_add = findviewByid(R.id.btn_add);
        btn_add.setVisibility(View.GONE);
        btn_sancode = findviewByid(R.id.btn_sancode);
        btn_sancode.setVisibility(View.GONE);
        btn_back = findviewByid(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GasSettingActivity.this.finish();
            }
        });

    }

    private void initViews() {
        tv_name = findViewById(R.id.tv_name);
        tv_type = findViewById(R.id.tv_type);
        tv_liangcheng = findViewById(R.id.tv_liangcheng);
        tv_one = findViewById(R.id.tv_one);
        tv_two = findViewById(R.id.tv_two);
        tv_unit = findViewById(R.id.tv_unit);
        tv_jingdu = findViewById(R.id.tv_jingdu);
        tv_chuchangdate = findViewById(R.id.tv_chuchangdate);
        tv_xiacibd = findViewById(R.id.tv_xiacibd);
        tv_shouming = findViewById(R.id.tv_shouming);
        tv_name.setText(name);
        tv_type.setText(name);
        tv_liangcheng.setText(liangcheng);
        tv_one.setText(one);
        tv_two.setText(two);
        tv_unit.setText(unit);
        tv_jingdu.setText(jingdu);
        tv_chuchangdate.setText(chuchangdate);
        tv_xiacibd.setText(xiacibd);
        tv_shouming.setText(shouming);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
