package com.hf.hf_smartcloud.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.adapter.myFragmentPagerAdapter;
import com.hf.hf_smartcloud.base.BaseFragment;
import com.hf.hf_smartcloud.bean.UserEvent;
import com.hf.hf_smartcloud.ui.activity.facility.SearchDeviceActivity;
import com.hf.hf_smartcloud.ui.activity.facility.SelectorLinkTypeActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class Fragment2 extends BaseFragment implements View.OnClickListener {
    public FragmentTwoSun1 fg1;
    public FragmentTwoSun2 fg2;
    public FragmentTwoSun3 fg3;
    public FragmentTwoSun4 fg4;
    View view, inflate;
    RadioGroup fgradiogroup;
    private Toolbar toolbar;
    //POPwindow===================================
    private RadioButton fgrbMain, fgrbEquipment, fgrbTimer, fgrbMe;
    private ArrayList<Fragment> fragmentSunList;
    private ViewPager mPagerSun;
    //======================
    private ImageView imageView;
    private TextView one;
    private TextView two;
    private TextView three;
    private PopupWindow popupWindow;
    private int flag = 0;

    String dev_name,mac_addr,char_uuid;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.fragment2, container, false);
        inflate = LayoutInflater.from(getActivity()).inflate(R.layout.pop, null, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initVeiws();
        initPopWindow();
        //初始化ViewPager
        initfgViewPager();
    }

    private void initPopWindow() {
        popupWindow = new PopupWindow(inflate, 200, 258, true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.a));
        popupWindow.setFocusable(false);
        one = inflate.findViewById(R.id.one);
        two = inflate.findViewById(R.id.two);
        three = inflate.findViewById(R.id.three);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);

    }

    private void initVeiws() {
        mPagerSun = view.findViewById(R.id.fgviewPager);
        fgradiogroup = view.findViewById(R.id.fgradiogroup);
        fgrbMain = view.findViewById(R.id.fgrbMain);
        fgrbEquipment = view.findViewById(R.id.fgrbEquipment);
        fgrbTimer = view.findViewById(R.id.fgrbTimer);
        fgrbMe = view.findViewById(R.id.fgrbMe);
        //RadioGroup选中状态改变监听
        fgradiogroup.setOnCheckedChangeListener(new frCheckChangeListener());
        mPagerSun.setOffscreenPageLimit(4);

        imageView = view.findViewById(R.id.toolbar_titleAdd_dev);
        imageView.setOnClickListener(this);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("早上好,钢镚儿");
        // 使用Toolbar替换ActionBars
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void initfgViewPager() {
        fg1 = new FragmentTwoSun1();
        fg2 = new FragmentTwoSun2();
        fg3 = new FragmentTwoSun3();
        fg4 = new FragmentTwoSun4();
        fragmentSunList = new ArrayList<Fragment>();
        fragmentSunList.add(0, fg1);
        fragmentSunList.add(1, fg2);
        fragmentSunList.add(2, fg3);
        fragmentSunList.add(3, fg4);
        mPagerSun.setCurrentItem(0);
        //ViewPager页面切换监听
        mPagerSun.setOnPageChangeListener(new myOnPageChangeListener());
        mPagerSun.setAdapter(new myFragmentPagerAdapter(getChildFragmentManager(), fragmentSunList));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.layout_menu, menu);
    }

    public MenuInflater getMenuInflater() {
        return new MenuInflater(getActivity());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.one:
                one.setTextColor(Color.parseColor("#52b9a8"));
                two.setTextColor(Color.parseColor("#222222"));
                three.setTextColor(Color.parseColor("#222222"));
                popupWindow.dismiss();
                flag = 0;
                break;
            case R.id.two:
                one.setTextColor(Color.parseColor("#222222"));
                two.setTextColor(Color.parseColor("#52b9a8"));
                three.setTextColor(Color.parseColor("#222222"));
                popupWindow.dismiss();
                flag = 0;
                break;
            case R.id.three:
                one.setTextColor(Color.parseColor("#222222"));
                two.setTextColor(Color.parseColor("#222222"));
                three.setTextColor(Color.parseColor("#52b9a8"));
                popupWindow.dismiss();
                flag = 0;
                Intent intent = new Intent(getActivity(), SearchDeviceActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.toolbar_titleAdd_dev:
//                if (flag == 0) {
//                    popupWindow.showAsDropDown(v, -5, 0);
//                    flag = 1;
//                } else {
//                    popupWindow.dismiss();
//                    flag = 0;
//
//                }
                Intent intentlink = new Intent(getActivity(), SelectorLinkTypeActivity.class);
//                startActivityForResult(intentlink, 1);
                startActivity(intentlink);
                break;
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //此处可以根据两个Code进行判断，本页面和结果页面跳过来的值
//        if (requestCode == 1 && resultCode == 3) {
//            /*dev_name = data.getStringExtra("dev_name");
//            mac_addr = data.getStringExtra("mac_addr");
//            char_uuid = data.getStringExtra("char_uuid");
//            UserEvent userEvent=new UserEvent();
//            data.getStringExtra(userEvent.getDev_name());
//            //发送事件
//            EventBus.getDefault().post(new UserEvent(dev_name,mac_addr,char_uuid));*/
//            //方法二：
//            String jsonList = data.getStringExtra("jsonList");
//            EventBus.getDefault().post(new UserEvent(jsonList));
//        }
//    }
    /**
     * RadioButton切换Fragment
     */
    private class frCheckChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {

                case R.id.fgrbMain:
                    //TODO 控制柜
                    mPagerSun.setCurrentItem(0);
                    fgrbMain.setChecked(true);
                    fgrbEquipment.setChecked(false);
                    fgrbTimer.setChecked(false);
                    fgrbMe.setChecked(false);
                    break;
                case R.id.fgrbEquipment:
                    //TODO 壁挂式
                    mPagerSun.setCurrentItem(1);
                    fgrbMain.setChecked(false);
                    fgrbEquipment.setChecked(true);
                    fgrbTimer.setChecked(false);
                    fgrbMe.setChecked(false);
                    break;
                case R.id.fgrbTimer:
                    //TODO 便携
                    mPagerSun.setCurrentItem(2);
                    fgrbMain.setChecked(false);
                    fgrbEquipment.setChecked(false);
                    fgrbTimer.setChecked(true);
                    fgrbMe.setChecked(false);
                    break;
                case R.id.fgrbMe:
                    //TODO 分组
                    mPagerSun.setCurrentItem(3);
                    fgrbMain.setChecked(false);
                    fgrbEquipment.setChecked(false);
                    fgrbTimer.setChecked(false);
                    fgrbMe.setChecked(true);
                    break;
            }
        }
    }

    /**
     * ViewPager切换Fragment,RadioGroup做相应变化
     */
    private class myOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    fgradiogroup.check(R.id.fgrbMain);
                    break;
                case 1:
                    fgradiogroup.check(R.id.fgrbEquipment);
                    break;
                case 2:
                    fgradiogroup.check(R.id.fgrbTimer);
                    break;
                case 3:
                    fgradiogroup.check(R.id.fgrbMe);
                    break;
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
