package com.hf.hf_smartcloud.ui.activity.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;

public class AboutActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_cheakversion, ll_help;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initViews();
    }

    private void initViews() {
        ll_cheakversion = findViewById(R.id.ll_cheakversion);
        ll_help = findViewById(R.id.ll_help);
        ll_cheakversion.setOnClickListener(this);
        ll_help.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_cheakversion:
                //TODO 检查更新
                break;
            case R.id.ll_help:
                //TODO 使用帮助
                openActivity(HelpActivity.class);
                break;
        }
    }
}
