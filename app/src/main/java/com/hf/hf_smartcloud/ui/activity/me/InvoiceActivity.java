package com.hf.hf_smartcloud.ui.activity.me;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hf.hf_smartcloud.R;
import com.hf.hf_smartcloud.adapter.myFragmentPagerAdapter;
import com.hf.hf_smartcloud.ui.fragment.AllFPFragment;
import com.hf.hf_smartcloud.ui.fragment.WkFPFragment;
import com.hf.hf_smartcloud.ui.fragment.YkFPFragment;
import com.hf.hf_smartcloud.utils.DataCleanManager;

import java.util.ArrayList;

public class InvoiceActivity extends AppCompatActivity{
    private Button btn_clean;
    private DataCleanManager dataCleanManager;
    private RadioButton rbAll,rbYkfp,rbWkfp;
    public AllFPFragment allFPFragment;
    public YkFPFragment ykFPFragment;
    public WkFPFragment wkFPFragment;
    private ArrayList<Fragment> fragmentList;
    private ViewPager viewpagerfp;
    private RadioGroup radiogroupfp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        initTitle();
        initViews();
        initViewPager();
    }
    private void initTitle() {
        ImageView btn_back = findViewById(R.id.btn_back);
        ImageView btn_add = findViewById(R.id.btn_add);
        btn_add.setVisibility(View.GONE);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("发票管理");
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                openActivity(AddaddressActivity.class);
            }
        });
    }
    private void initViews() {
        viewpagerfp=(ViewPager)findViewById(R.id.viewpagerfp);
        radiogroupfp=(RadioGroup)findViewById(R.id.radiogroupfp);
        rbAll=(RadioButton)findViewById(R.id.rbAll);
        rbYkfp=(RadioButton)findViewById(R.id.rbYkfp);
        rbWkfp=(RadioButton)findViewById(R.id.rbWkfp);
        //RadioGroup选中状态改变监听
        radiogroupfp.setOnCheckedChangeListener(new InvoiceActivity.myfpCheckChangeListener());
        viewpagerfp .setOffscreenPageLimit(3);
    }

    private void initViewPager(){
        allFPFragment=new AllFPFragment();
        ykFPFragment=new YkFPFragment();
        wkFPFragment=new WkFPFragment();
        fragmentList=new ArrayList<Fragment>();
        fragmentList.add(0,allFPFragment);
        fragmentList.add(1,ykFPFragment);
        fragmentList.add(2,wkFPFragment);
        //ViewPager设置适配器
        //ViewPager显示第一个Fragment
        viewpagerfp.setCurrentItem(0);
        //ViewPager页面切换监听
        viewpagerfp.setOnPageChangeListener(new InvoiceActivity.myOnPageChangeListener());
        viewpagerfp.setAdapter(new myFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
    }


    /**
     *RadioButton切换Fragment
     */
    private class myfpCheckChangeListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){

                case R.id.rbAll:
                    //ViewPager显示第一个Fragment且关闭页面切换动画效果
                    viewpagerfp.setCurrentItem(0);
                    rbAll.setChecked(true);
                    rbYkfp.setChecked(false);
                    rbWkfp.setChecked(false);
                    break;
                case R.id.rbYkfp:
                    viewpagerfp.setCurrentItem(1);
                    rbAll.setChecked(false);
                    rbYkfp.setChecked(true);
                    rbWkfp.setChecked(false);
                    break;
                case R.id.rbWkfp:
                    viewpagerfp.setCurrentItem(2);
                    rbAll.setChecked(false);
                    rbYkfp.setChecked(false);
                    rbWkfp.setChecked(true);
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
                    radiogroupfp.check(R.id.rbAll);
                    break;
                case 1:
                    radiogroupfp.check(R.id.rbYkfp);
                    break;
                case 2:
                    radiogroupfp.check(R.id.rbWkfp);
                    break;

            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }




}

