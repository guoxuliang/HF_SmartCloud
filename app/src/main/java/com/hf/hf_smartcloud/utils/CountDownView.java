package com.hf.hf_smartcloud.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.hf.hf_smartcloud.R;

public class CountDownView extends View {
    //圆环颜色
    private int mRingColor;
    //圆环宽度
    private float mRingWidth;
    //圆环进度值文本大小
    private int mRingProgessTextSize;
    //宽度
    private int mWidth;
    //高度
    private int mHeight;
    private Paint mPaint;
    //圆环的矩形区域
    private RectF mRectF;
    //
    private int mProgessTextColor;
    private int mCountdownTime;
    private float mCurrentProgress;
    ValueAnimator valueAnimator;
    private String text;

    /**
     * 监听事件
     */
    private OnCountDownListener mListener;

    public CountDownView(Context context) {
        this(context, null);

    }

    public CountDownView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);


    }

    public CountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // init();

        /**
         * 获取相关属性值
         */
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CountDownView);
        mRingColor = typedArray.getColor(R.styleable.CountDownView_ringColor, context.getResources().getColor(R.color.colorPrimary));
        mRingWidth = typedArray.getFloat(R.styleable.CountDownView_ringWidth, 60);
        mRingProgessTextSize = typedArray.getDimensionPixelSize(R.styleable.CountDownView_progressTextSize, 30);
        mProgessTextColor = typedArray.getColor(R.styleable.CountDownView_progressTextColor, context.getResources().getColor(R.color.colorPrimary));
        mCountdownTime = typedArray.getInteger(R.styleable.CountDownView_countdownTime, 60);
        typedArray.recycle();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        this.setWillNotDraw(false);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mRectF = new RectF(0 + mRingWidth / 2, 0 + mRingWidth / 2,
                mWidth - mRingWidth / 2, mHeight - mRingWidth / 2);
    }


    /**
     * 设置倒计时间 单位秒
     * @param mCountdownTime
     */
    public void setCountdownTime(int mCountdownTime) {
        this.mCountdownTime = mCountdownTime;
        invalidate();

    }
    /**
     * 动画
     * @param countdownTime
     * @return
     */
    private ValueAnimator getValueAnimator(long countdownTime) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 100);
        valueAnimator.setDuration(countdownTime);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(0);
        return valueAnimator;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         *圆环
         */
        //颜色
        mPaint.setColor(mRingColor);
        //空心
        mPaint.setStyle(Paint.Style.STROKE);
        //宽度
        mPaint.setStrokeWidth(mRingWidth);
        canvas.drawArc(mRectF, -90, mCurrentProgress - 360, false, mPaint);
        //绘制文本
        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
         text = mCountdownTime - (int) (mCurrentProgress / 360f * mCountdownTime) + "";
        textPaint.setTextSize(mRingProgessTextSize);
        textPaint.setColor(mProgessTextColor);

        //文字居中显示
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (int) ((mRectF.bottom + mRectF.top - fontMetrics.bottom - fontMetrics.top) / 2);
        canvas.drawText(text, mRectF.centerX(), baseline, textPaint);
        if (mListener != null) {
            mListener.countDownFinished(text);
        }
    }


    /**
     * 开始倒计时
     */
    public void startCountDown() {
        valueAnimator = getValueAnimator(mCountdownTime * 1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float i = Float.valueOf(String.valueOf(animation.getAnimatedValue()));
                mCurrentProgress = (int) (360 * (i / 100f));
                invalidate();
            }
        });
        valueAnimator.start();
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //倒计时结束回调
//                if (mListener != null) {
//                    mListener.countDownFinished();
//                }
            }
        });
    }


    /**
     * 停止倒计时
     */
    public void stopCountDdwn(){

        valueAnimator.cancel();


    }
    public void setOnCountDownListener(OnCountDownListener mListener) {
        this.mListener = mListener;
    }

    /**
     * 倒计时监听接口
     */
    public interface OnCountDownListener {
        void countDownFinished(String text);
    }
}
