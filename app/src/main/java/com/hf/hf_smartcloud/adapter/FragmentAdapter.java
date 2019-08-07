package com.hf.hf_smartcloud.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hf.hf_smartcloud.ui.fragment.AllFPFragment;
import com.hf.hf_smartcloud.ui.fragment.WkFPFragment;
import com.hf.hf_smartcloud.ui.fragment.YkFPFragment;

/**
 * 作者：${NanFeiLong}
 * 日期 2016/12/9 17:36
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            // 宝贝
            case 0:
                fragment = new AllFPFragment();
                break;
            case 1:
                fragment = new YkFPFragment();
                break;
            // 店铺
            case 2:
                fragment = new WkFPFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
