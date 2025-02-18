package com.hf.hf_smartcloud.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.hf.hf_smartcloud.R;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class NewClock2 extends View {

    private Context context;
    private final String TAG = getClass().getSimpleName();

    //    圆形和刻度的画笔、指针的画笔、数字的画笔,扇形画笔
    private Paint mCirclePaint,mPointerPaint,mNumPaint,mFanPaint;

    //    时钟的外环宽度、时钟的半径、默认刻度的宽度、默认刻度的长度
//    特殊刻度的宽度、特殊刻度的长度、时针的宽度、分针的宽度、秒针的宽度
    private float mClockRingWidth,mClockRadius,mDefaultWidth,mDefaultLength,
            mSpecialWidth,mSpecialLength,mHWidth,mMWidth,mSWidth;

    //    圆形和刻度的颜色，时针的颜色，分针的颜色，秒针的颜色，数字的颜色
    private int mCircleColor,mHColor,mMColor,mSColor,mNumColor;

    //    宽，高
    private int mWidth, mHeight, mCenterX, mCenterY;

    //    当前时间 时，分，秒
    private float mH,mM,mS;

    private PathEffect mEffect;
    /**
     * 定时器
     */
    private Timer mTimer=new Timer();
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (mS == 360) {
                mS = 0;
            }
            if (mM == 360){
                mM = 0;
            }
            if (mH == 360){
                mH = 0;
            }
            //具体计算
            mS = mS + 6;
            mM = mM + 0.1f;
            mH = mH + 1.0f/120;
            //子线程用postInvalidate
            postInvalidate();
        }
    };
    /**
     *开启定时器
     */
    public void start() {
        mTimer.schedule(task,0,1000);
    }

    public void setTime(int h, int m, int s) {
        if (h >= 24 || h < 0 || m >= 60 || m < 0 || s >= 60 || s < 0) {
            Toast.makeText(getContext(), "时间不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        //需要以12点为准，所以统一减去180度
        if (h >= 12) {
            mH = (h + m * 1.0f/60f + s * 1.0f/3600f - 12)*30f-180;
        } else {
            mH = (h + m * 1.0f/60f + s * 1.0f/3600f)*30f-180;
        }
        mM = (m + s * 1.0f/60f) *6f-180;
        mS = s * 6f-180;
    }


    public NewClock2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context,attrs);
        mEffect = new DashPathEffect(new float[]{2,2}, 0);    // float[]{ 虚线的厚度, 虚线的间距,虚线的厚度, 虚线的间距 ......}
        initPaint();
        Calendar mCalendar= Calendar.getInstance();
        //获取当前小时数
        int hours = mCalendar.get(Calendar.HOUR);
        //获取当前分钟数
        int minutes = mCalendar.get(Calendar.MINUTE);
        //获取当前秒数
        int seconds=mCalendar.get(Calendar.SECOND);
        setTime(hours,minutes,seconds);
        //开启定时
        start();
    }

    /**
     * 初始化自定义参数
     */
    private void init(Context context,AttributeSet attributeSet){
        TypedArray ta = context.obtainStyledAttributes(attributeSet, R.styleable.Clock);
        mClockRingWidth=ta.getDimension(R.styleable.Clock_mClockRingWidth, SizeUtils.dp2px(context,4));//时钟的外环宽度
        mDefaultWidth=ta.getDimension(R.styleable.Clock_mDefaultWidth, SizeUtils.dp2px(context,4));//默认刻度的宽度
        mDefaultLength=ta.getDimension(R.styleable.Clock_mDefaultLength, SizeUtils.dp2px(context,8));//默认刻度的长度
        mSpecialWidth=ta.getDimension(R.styleable.Clock_mSpecialWidth, SizeUtils.dp2px(context,4));//特殊刻度的宽度
        mSpecialLength=ta.getDimension(R.styleable.Clock_mSpecialLength, SizeUtils.dp2px(context,30));//特殊刻度的长度
        mHWidth=ta.getDimension(R.styleable.Clock_mHWidth, SizeUtils.dp2px(context,1));//时针的宽度
        mMWidth=ta.getDimension(R.styleable.Clock_mMWidth, SizeUtils.dp2px(context,4));//分针的宽度
        mSWidth=ta.getDimension(R.styleable.Clock_mSWidth, SizeUtils.dp2px(context,2));//秒针的宽度
        //颜色
        mCircleColor=ta.getColor(R.styleable.Clock_mCircleColor, Color.GRAY);//圆形和刻度的颜色
        mHColor=ta.getColor(R.styleable.Clock_mHColor, Color.GRAY);//时针的颜色
//        mMColor=ta.getColor(R.styleable.Clock_mMColor, Color.GRAY);
//        mSColor=ta.getColor(R.styleable.Clock_mSColor, Color.GRAY);
        mNumColor=ta.getColor(R.styleable.Clock_mNumColor, Color.GRAY);//数字的颜色
        //记得释放
        ta.recycle();

    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        //时钟的画笔
        mCirclePaint=new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setPathEffect(mEffect);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        //指针的画笔
        mPointerPaint=new Paint();
        Path mTriangle = new Path();
        mTriangle.moveTo(190, 110);// 此点为多边形的顶点【三角形（指针）】
        mPointerPaint.setAntiAlias(true);
        mPointerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPointerPaint.setStrokeCap(Paint.Cap.ROUND);
        //数字的画笔
        mNumPaint=new Paint();
        mNumPaint.setStyle(Paint.Style.FILL);
        mNumPaint.setTextSize(20);
        mNumPaint.setColor(this.getResources().getColor(R.color.grey_600));
        //扇形画笔
        mFanPaint=new Paint();
        mFanPaint.setStyle(Paint.Style.FILL);
        mFanPaint.setAntiAlias(true);
        mFanPaint.setPathEffect(mEffect);
        mFanPaint.setStyle(Paint.Style.STROKE);
        mFanPaint.setColor(this.getResources().getColor(R.color.grey_600));
        //透明度
        mFanPaint.setAlpha(80);
        mFanPaint.setStrokeWidth(2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getMeasureSize(true, widthMeasureSpec);
        int height = getMeasureSize(false, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;
        mCenterX=w/2;
        mCenterY=h/2;
        mClockRadius= (float) ((float) (w/2)*0.6);
    }

    /**
     * 获取View尺寸
     *
     * @param isWidth 是否是width，不是的话，是height
     */
    private int getMeasureSize(boolean isWidth, int measureSpec) {

        int result = 0;

        int specSize = MeasureSpec.getSize(measureSpec);
        int specMode = MeasureSpec.getMode(measureSpec);

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                if (isWidth) {
                    result = getSuggestedMinimumWidth();
                } else {
                    result = getSuggestedMinimumHeight();
                }
                break;
            case MeasureSpec.AT_MOST:
                if (isWidth)
                    result = Math.min(specSize, mWidth);
                else
                    result = Math.min(specSize, mHeight);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //先设置画布为view的中心点
        canvas.translate(mCenterX,mCenterY);
        //绘制外圆和刻度
        drawCircle(canvas);
        //绘制数字
        drawNums(canvas);
        //绘制时针，分针，秒针和中间小圆点
        drawPointer(canvas);
        //绘制扇形
        drawFan(canvas);
    }

    /**
     * 绘制外圆和刻度
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        mCirclePaint.setStrokeWidth(mClockRingWidth);
        mCirclePaint.setColor(this.getResources().getColor(R.color.grey_600));
        //先画出圆环
        canvas.drawCircle(0,0,mClockRadius,mCirclePaint);
        for (int i = 0; i < 8; i++) {
            if (i==0){//特殊的刻度
                mCirclePaint.setStrokeWidth(mSpecialWidth);
                mCirclePaint.setColor(this.getResources().getColor(R.color.grey_600));
                canvas.drawLine(0,-mClockRadius+mClockRingWidth/2,0,-mClockRadius+mSpecialLength,mCirclePaint);
            }else {//普通刻度
                mCirclePaint.setStrokeWidth(mDefaultWidth);
                mCirclePaint.setColor(this.getResources().getColor(R.color.grey_600));
                canvas.drawLine(0,-mClockRadius+mClockRingWidth/2,0,-mClockRadius+mDefaultLength,mCirclePaint);
            }
//            通过旋转画布的方式快速设置刻度
            canvas.rotate(45);
        }
    }

    /**
     * 绘制文字
     * @param canvas
     */
    private void drawNums(Canvas canvas) {
        for (int i = 1; i < 9; i++) {
            canvas.save();
            if (i==1){ //绘制12点的数字
                Rect textBound = new Rect();
                canvas.translate(0, 60);
                String text="1";
                mNumPaint.getTextBounds(text, 0, text.length(), textBound);
                canvas.drawText(text, -textBound.width()/2,textBound.height() / 2, mNumPaint);
            }else { //绘制其他数字
                Rect textBound = new Rect();
                canvas.translate(0, 60);
                String text=i+"";
                mNumPaint.getTextBounds(text, 0, text.length(), textBound);
                canvas.rotate(-i*45); //因画布被旋转了，所以要把画布正过来再绘制数字
                canvas.drawText(text, -textBound.width()/2,textBound.height() / 2, mNumPaint);
            }
            canvas.restore();
            canvas.rotate(45);
        }
    }



    /**
     * 绘制扇形
     * @param canvas
     */
    private void drawFan(Canvas canvas) {
        float x = getMeasuredWidth();
        RectF oval = new RectF(0, 0, getRight(),x);
        canvas.drawArc(oval,0,90,true,mFanPaint);
    }

    /**
     * 绘制指针，每次绘制完恢复画布状态，使用 save 和 restore 方法
     * 指针长短根据半径长度进行计算
     * @param canvas
     */
    private void drawPointer(Canvas canvas) {
//        //时针
//        canvas.save();
//        mPointerPaint.setColor(R.color.grey_600);
//        mPointerPaint.setStrokeWidth(mHWidth);
//        canvas.rotate(mH, 0, 0);
//        canvas.drawLine(0, -20, 0,
//                (float) (mClockRadius*0.45), mPointerPaint);
//        canvas.restore();

        //新时针
        canvas.save();
        mPointerPaint.setColor(this.getResources().getColor(R.color.grey_600));
        mPointerPaint.setStrokeWidth(SizeUtils.dp2px(context,2));
        canvas.rotate(mH, 0, 0);
        canvas.drawLine(0, 8, 0,
                (float) (mClockRadius*0.75), mPointerPaint);
        canvas.restore();

////        分针
//        canvas.save();
//        mPointerPaint.setColor(mMColor);
//        mPointerPaint.setStrokeWidth(mMWidth);
//        canvas.rotate(mM, 0, 0);
//        canvas.drawLine(0, -20, 0,
//                (float) (mClockRadius*0.6), mPointerPaint);
//        canvas.restore();
//
//        //秒针
//        canvas.save();
//        mPointerPaint.setColor(mSColor);
//        mPointerPaint.setStrokeWidth(mSWidth);
//        canvas.rotate(mS, 0, 0);
//        canvas.drawLine(0, -40, 0,
//                (float) (mClockRadius*0.75), mPointerPaint);
//        canvas.restore();
        //最后绘制一个小圆点，要不然没效果
        mPointerPaint.setColor(mSColor);
        canvas.drawCircle(0,0,mHWidth/2,mPointerPaint);

    }
    /**
     * 尺寸转换工具类
     */
    private static class SizeUtils {
        static int dp2px(Context context, float dp) {
            final float density = context.getResources().getDisplayMetrics().density;
            return (int) (dp * density + 0.5);
        }

        static int px2dp(Context context, float px) {
            final float density = context.getResources().getDisplayMetrics().density;
            return (int) (px / density + 0.5);
        }
    }

}
