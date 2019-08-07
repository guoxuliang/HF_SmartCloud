package com.hf.hf_smartcloud.ui.activity.me;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.utils.CountDownTimerUtils;

public class ZfPwdActivity extends BaseActivity {
    private TextView daojs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zfpwd);
        initViews();
    }

    private void initViews() {
        daojs = findviewByid(R.id.daojs);
        daojs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(daojs, 60000, 1000); //倒计时1分钟
                mCountDownTimerUtils.start();
            }
        });
    }
}
