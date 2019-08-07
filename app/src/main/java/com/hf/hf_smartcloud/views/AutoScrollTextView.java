package com.hf.hf_smartcloud.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 *
 * @author yeyu
 *
 * 创建日期：2018/9/5
 *
 * 描述：重写textview，是focus属性为true
 *
 */
public class AutoScrollTextView extends android.support.v7.widget.AppCompatTextView {
    public AutoScrollTextView(Context context) {
        super(context);
    }

    public AutoScrollTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoScrollTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //如果想让所有的TextView都有跑马灯效果,则让所有的TextView focuse属性为true
    @Override
    public boolean isFocused() {
        return true;
    }
}
