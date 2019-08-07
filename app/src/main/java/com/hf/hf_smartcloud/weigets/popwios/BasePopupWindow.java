package com.hf.hf_smartcloud.weigets.popwios;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.hf.hf_smartcloud.R;

/**
 * Created by wujn on 2018/10/29.
 * Version : v1.0
 * Function: base popuwindow
 */
public abstract class BasePopupWindow extends PopupWindow implements View.OnClickListener{

    /**
     * 上下文
     */
    protected Context context;
    /**
     * 最上边的背景视图
     */
    private View vBgBasePicker;
    /**
     * 内容viewgroup
     */
    private LinearLayout llBaseContentPicker;

    public BasePopupWindow(Context context) {
        super(context);
        this.context = context;
        View parentView = View.inflate(context, R.layout.base_popup_window_picker, null);
        vBgBasePicker = parentView.findViewById(R.id.v_bg_base_picker);
        llBaseContentPicker = (LinearLayout) parentView.findViewById(R.id.ll_base_content_picker);
        /***
         * 添加布局到界面中
         */
        llBaseContentPicker.addView(View.inflate(context, bindLayout(), null));
        setContentView(parentView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //在PopupWindow里面就加上下面代码，让键盘弹出时，不会挡住pop窗口。
        this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setFocusable(true);//设置获取焦点
        setTouchable(true);//设置可以触摸
        setOutsideTouchable(true);//设置外边可以点击
        ColorDrawable dw = new ColorDrawable(0xffffff);
        setBackgroundDrawable(dw);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.BottomDialogWindowAnim);
        initView(parentView);
        initData();
        initListener();
        vBgBasePicker.setOnClickListener(this);
        //是否需要屏幕半透明
        setBackgroundHalfTransition(isNeedBackgroundHalfTransition());
    }

    /**
     * 初始化布局
     *
     * @return
     */
    protected abstract int bindLayout();

    /**
     * 初始化view
     *
     * @param parentView
     */
    protected abstract void initView(View parentView);

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化监听
     */
    protected abstract void initListener();





    /**
     * 为了适配7.0系统以上显示问题(显示在控件的底部)
     *
     * @param anchor
     */
    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
        if(isNeedBgHalfTrans){
            backgroundAlpha(0.5f);
        }
    }

    /**
     * 展示在屏幕的底部
     *
     * @param layoutid rootview
     */
    public void showAtLocation(@LayoutRes int layoutid) {
        showAtLocation(LayoutInflater.from(context).inflate(layoutid, null),
                Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        if(isNeedBgHalfTrans){
            backgroundAlpha(0.5f);
        }
    }


    /**
     * 最上边视图的点击事件的监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.v_bg_base_picker:
                dismiss();
                break;
        }
    }

    /**
     * 是否设置背景半透明
     * */
    public boolean isNeedBackgroundHalfTransition(){
        return false;
    }

    private boolean isNeedBgHalfTrans = false;
    private void setBackgroundHalfTransition(boolean isNeed){
        isNeedBgHalfTrans = isNeed;
        if(isNeedBgHalfTrans){
            this.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1f);
                }
            });
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity)context).getWindow().setAttributes(lp);
    }


}
