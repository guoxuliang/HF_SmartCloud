package com.hf.hf_smartcloud.ui.jpush;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.hf.hf_smartcloud.R;

import cn.jpush.android.api.JPushInterface;

public class TestActivity extends Activity {
private TextView tvtitle,tvcontent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tvtitle = findViewById(R.id.title);
        tvcontent = findViewById(R.id.content);

//        TextView tv = new TextView(this);
//        tv.setText("用户自定义打开的Activity");
        Intent intent = getIntent();
        if (null != intent) {
	        Bundle bundle = getIntent().getExtras();
            String title = null;
            String content = null;
            if(bundle!=null){
                title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                content = bundle.getString(JPushInterface.EXTRA_ALERT);
            }
//            tv.setText("Title : " + title + "  " + "Content : " + content);
            tvtitle.setText("通知");
            tvcontent.setText(content);
        }
//        addContentView(tv, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
    }

}
