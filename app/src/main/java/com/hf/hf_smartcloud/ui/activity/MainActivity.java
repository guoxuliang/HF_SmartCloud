package com.hf.hf_smartcloud.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hf.hf_smartcloud.Manifest;
import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.adapter.myFragmentPagerAdapter;
import com.hf.hf_smartcloud.base.BaseActivity;
import com.hf.hf_smartcloud.ui.fragment.Fragment1;
import com.hf.hf_smartcloud.ui.fragment.Fragment2;
import com.hf.hf_smartcloud.ui.fragment.Fragment3;
import com.hf.hf_smartcloud.ui.fragment.Fragment4;
import com.hf.hf_smartcloud.ui.jpush.ExampleUtil;
import com.hf.hf_smartcloud.ui.jpush.LocalBroadcastManager;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;
import rx.Observer;

public class MainActivity extends BaseActivity {
  private Button btn_clean;
    private RadioButton rbMain,rbEquipment,rbTimer,rbMe;
    public Fragment1 fragment1;
    public Fragment2 fragment2;
    public Fragment3 fragment3;
    public Fragment4 fragment4;
//    private FragmentManager mFm;
//    private String[] mFragmentTagList = {"Fragment1","Fragment2","Fragment3","Fragment4"};
    private ArrayList<Fragment> fragmentList;
    private ViewPager mPager;
    private RadioGroup mGroup;

    public static boolean isForeground = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //初始化ViewPager
        initViewPager();
        init();
        jpush();
        init();
//        initgetPermissions();

    }

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void init(){
        JPushInterface.init(getApplicationContext());
    }

    private void  jpush() {
        JPushInterface.init(getApplicationContext());
        JPushInterface.getRegistrationID(getApplicationContext());
        JPushInterface.resumePush(getApplicationContext());
//        JPushInterface.stopPush(getApplicationContext());
        registerMessageReceiver(); // used for receive msg
    }
    private void initView(){
        mPager=(ViewPager)findViewById(R.id.viewPager);
        mGroup=(RadioGroup)findViewById(R.id.radiogroup);
        rbMain=(RadioButton)findViewById(R.id.rbMain);
        rbEquipment=(RadioButton)findViewById(R.id.rbEquipment);
        rbTimer=(RadioButton)findViewById(R.id.rbTimer);
        rbMe=(RadioButton)findViewById(R.id.rbMe);
        //RadioGroup选中状态改变监听
        mGroup.setOnCheckedChangeListener(new myCheckChangeListener());
        mPager .setOffscreenPageLimit(4);
    }
    private void initViewPager(){
        fragment1=new Fragment1();
        fragment2=new Fragment2();
        fragment3=new Fragment3();
        fragment4=new Fragment4();
        fragmentList=new ArrayList<Fragment>();
        fragmentList.add(0,fragment1);
        fragmentList.add(1,fragment2);
        fragmentList.add(2,fragment3);
        fragmentList.add(3,fragment4);
        //ViewPager设置适配器
        //ViewPager显示第一个Fragment
        mPager.setCurrentItem(0);
        //ViewPager页面切换监听
        mPager.setOnPageChangeListener(new myOnPageChangeListener());
        mPager.setAdapter(new myFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
    }


    /**
     *RadioButton切换Fragment
     */
    private class myCheckChangeListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){

                case R.id.rbMain:
                    //ViewPager显示第一个Fragment且关闭页面切换动画效果
                    mPager.setCurrentItem(0);
                    rbMain.setChecked(true);
                    rbEquipment.setChecked(false);
                    rbTimer.setChecked(false);
                    rbMe.setChecked(false);
                    break;
                case R.id.rbEquipment:
                    mPager.setCurrentItem(1);
                    rbMain.setChecked(false);
                    rbEquipment.setChecked(true);
                    rbTimer.setChecked(false);
                    rbMe.setChecked(false);
                    break;
                case R.id.rbTimer:
                    mPager.setCurrentItem(2);
                    rbMain.setChecked(false);
                    rbEquipment.setChecked(false);
                    rbTimer.setChecked(true);
                    rbMe.setChecked(false);
                    break;
                case R.id.rbMe:
                    mPager.setCurrentItem(3);
                    rbMain.setChecked(false);
                    rbEquipment.setChecked(false);
                    rbTimer.setChecked(false);
                    rbMe.setChecked(true);
                    break;


            }
        }
    }

    /**
     *ViewPager切换Fragment,RadioGroup做相应变化
     */
    private class myOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        private RadioButton rbMain,rbEquipment,rbTimer,rbMe;
        @Override
        public void onPageSelected(int position) {
            switch (position){
                case 0:
                    mGroup.check(R.id.rbMain);
                    break;
                case 1:
                    mGroup.check(R.id.rbEquipment);
                    break;
                case 2:
                    mGroup.check(R.id.rbTimer);
                    break;
                case 3:
                    mGroup.check(R.id.rbMe);
                    break;

            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.hf.hf_smartcloud.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e){
            }
        }
    }
    private void setCostomMsg(String msg){
//        if (null != msgText) {
//            msgText.setText(msg);
//            msgText.setVisibility(View.VISIBLE);
//        }
    }





    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    //声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //========================================================================================
    protected String getVersionName(){
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }



//    private void initgetPermissions() {
//        RxPermissions rxPermissions = new RxPermissions(this);
//        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.READ_PHONE_STATE)
//                .subscribe(new Observer<Boolean>() {
//                    @Override
//                    public void onNext(Boolean aBoolean) {
//                    }
//
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                });
//    }
}
