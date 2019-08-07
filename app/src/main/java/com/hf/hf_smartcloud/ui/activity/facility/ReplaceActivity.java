package com.hf.hf_smartcloud.ui.activity.facility;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.ui.activity.me.AddaddressActivity;

public class ReplaceActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replace);
        initTitle();
    }

    private void initTitle() {
        ImageView btn_back = findviewByid(R.id.btn_back);
        ImageView btn_add =  findviewByid(R.id.btn_add);
        TextView tv_title= findviewByid(R.id.tv_title);
        tv_title.setText("更换设备");
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
}
