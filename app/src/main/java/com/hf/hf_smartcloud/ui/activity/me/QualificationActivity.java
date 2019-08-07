package com.hf.hf_smartcloud.ui.activity.me;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;

public class QualificationActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualification);
        initTitle();
    }

    private void initTitle() {
        ImageView btn_back = findviewByid(R.id.btn_back);
        ImageView btn_sancode =  findviewByid(R.id.btn_sancode);
        TextView tv_title= findviewByid(R.id.tv_title);
        tv_title.setText("赠票资质");
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_sancode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 扫码  发票信息
            }
        });
    }
}
