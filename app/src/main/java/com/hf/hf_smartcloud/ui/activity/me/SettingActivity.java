package com.hf.hf_smartcloud.ui.activity.me;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.utils.DataCleanManager;
import com.hf.hf_smartcloud.views.MyClickButton;
import com.hf.hf_smartcloud.views.SwitchButton;

import cn.jpush.android.api.JPushInterface;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_clean,ll_settingabout;
    private DataCleanManager dataCleanManager;
    private MyClickButton sb;
    private boolean isRightb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initTitle();
        initViews();
    }

    private void initViews() {
        ll_clean = findviewByid(R.id.ll_clean);
        ll_settingabout = findviewByid(R.id.ll_settingabout);
        sb = (MyClickButton) findViewById(R.id.wiperSwitch1);
        ll_clean.setOnClickListener(this);
        ll_settingabout.setOnClickListener(this);
        sb.setOnMbClickListener(new MyClickButton.OnMClickListener() {
            @Override
            public void onClick(boolean isRight) {
                if (isRight) {
                    Toast.makeText(SettingActivity.this, "打开推送", Toast.LENGTH_SHORT).show();
                    JPushInterface.resumePush(getApplicationContext());
                } else {
                    Toast.makeText(SettingActivity.this, "关闭推送", Toast.LENGTH_SHORT).show();
//                  cleanDialog(1, "消息推送", "您确定关闭消息推送?");
                }
            }
        });
    }

    private void initTitle() {
        ImageView btn_back = findviewByid(R.id.btn_back);
        ImageView btn_add = findviewByid(R.id.btn_add);
        TextView tv_title = findviewByid(R.id.tv_title);
        tv_title.setText("设置");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_clean:
                //清空缓存
                cleanDialog(0, "清空缓存", "你确定要清空缓存吗?");
                break;
            case R.id.ll_settingabout:
                //关于
                openActivity(AboutActivity.class);
                break;
        }
    }

    private void cleanDialog(int tag, String title, String msg) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(this);
        normalDialog.setIcon(R.mipmap.icon_heard);
        normalDialog.setTitle(title);
        normalDialog.setMessage(msg);
        normalDialog.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (tag == 0) {//是否清空缓存
                            dataCleanManager.cleanApplicationData(SettingActivity.this, "");//清除本应用所有的数据
                            finish();
                            System.exit(0);
                        } else if (tag == 1) {//是否关闭推送消息
                            JPushInterface.stopPush(getApplicationContext());
                        }
                    }
                });
        normalDialog.setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isRightb = true;
                        sb.setClickable(isRightb);
                    }
                });
        // 显示
        normalDialog.show();
    }
}
