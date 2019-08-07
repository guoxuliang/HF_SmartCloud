package com.hf.hf_smartcloud.adapter;

/**
 * Created by Administrator on 2018/6/29 0029.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/6/24.
 */
public class myFragmentPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> list;
    public myFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list){
        super(fm);
        this.list=list;
    }
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
