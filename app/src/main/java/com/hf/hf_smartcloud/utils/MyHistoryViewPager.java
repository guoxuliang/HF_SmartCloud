package com.hf.hf_smartcloud.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Content：ScrollView与viewPager之间的滑动冲突
 * Actor：gxl ヾ(✿ﾟ▽ﾟ)ノ
 * Time:  2019/8/1 13:48
 * Update:
 * Time:
 */
public class MyHistoryViewPager extends ViewPager {
    public MyHistoryViewPager(Context context) {
        super(context);
    }


    public MyHistoryViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height)
                height = h;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        return super.dispatchTouchEvent(ev);
//    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if(v.getClass().getName().equals("com.hf.hf_smartcloud.utils.ScatterChartView")){
            Log.i("MyHistoryViewPager:::","MyHistoryViewPager:::"+v.getClass().getName());
            return true;
        }
        return false;
    }
}
