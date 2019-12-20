package com.hf.hf_smartcloud.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class xiaomiClock extends View {
    private Paint textPaint,paint;
    private Path mTriangle;
    private Timer mTimer;
    private float agree = 1;
    private Shader mshader;
    private PathEffect mEffect;

    public xiaomiClock(Context context) {
        super(context);
    }

    public xiaomiClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        //mEffect = new DashPathEffect(new float[]{1,2,5,10,50,20}, 0);    // float[]{ 虚线的厚度, 虚线的间距,虚线的厚度, 虚线的间距 ......}
        mEffect = new DashPathEffect(new float[]{5,5}, 0);    // float[]{ 虚线的厚度, 虚线的间距,虚线的厚度, 虚线的间距 ......}
        /**
         * cx   渲染中心点x 坐标
         * cy 	渲染中心y 点坐标
         * colors 	围绕中心渲染的颜色数组，至少要有两种颜色值
         * positions 	相对位置的颜色数组,可为null,  若为null,可为null,颜色沿渐变线均匀分布
         */
        mshader = new SweepGradient(250, 250, Color.parseColor("#303F9F"), Color.parseColor("#BBDEFB"));    //渐变遮罩样式


        textPaint = new Paint();
        textPaint.setStrokeWidth(16);
        textPaint.setColor(Color.WHITE);
        textPaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint.setAntiAlias(true);

        paint = new Paint();
        paint.setAntiAlias(true);

        mTriangle = new Path();
        mTriangle.moveTo(480, 250);// 此点为多边形的顶点【三角形（指针）】//moveTo  移动起点  移动下一次操作的起点位置
        //下面两个x 相等，表示底边的位置
        mTriangle.lineTo(500, 262);  // y：底边宽的其中一个顶点 //连接直线  添加上一个点到当前点之间的直线到Path
        mTriangle.lineTo(500, 237);  //y：底边宽的其中一个顶点
        mTriangle.close(); //闭合路径  连接第一个点连接到最后一个点，形成一个闭合区域
        setmTimer();
        System.out.println("度数" + ((float) 6.0 / 10));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘画手表盘
        drawbeauty(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //设置宽高
        setMeasuredDimension(500,500);  //画布大小
    }

    public void drawbeauty(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        //设置缓存层，因为下面要实用xfermode，使用xfemode必须使用缓存层，否则会出现黑色背景
        int layerId = canvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);

        //初始化画笔，因为上下两层做画需要的画笔属性不一样，所以只能每次重新设置一次
        paint.setStyle(Paint.Style.STROKE);     //设置画笔为不填充模式
        paint.setPathEffect(mEffect);           //设置笔画样式，这里设置的是虚线样式
        paint.setStrokeWidth(25);               //设置笔画宽度
        canvas.drawCircle(250, 250, 210, paint);    //画一个纯色表盘，虚线，空心圆形 【表盘位置和大小】

        //设置画笔属性，SRC_IN属性，让第二个图案只能花在第一个图案上面，也就是只能画在上面所说那个纯色表盘里面
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //把画笔虚线属性去掉，因为我要的是一个实心圆形，然后让这个实心但是颜色不一样圆形画在上面所说表盘上面，因为设置了xfermode，所以显示的一样会是虚线圆形表盘，但是颜色会变成你现在的颜色
        paint.setPathEffect(null);

        //设置画笔shader属性，这里设置的是SweepGradient模式，可以让颜色过渡泾渭分明，以圆形为中心开始变化
        paint.setShader(mshader);
        paint.setStyle(Paint.Style.FILL);

        canvas.save();      //保存画布

        //旋转画布，然后你就会发现时钟表盘开始动了
        canvas.rotate(agree, 250, 250);    //画布旋转的中心点
        canvas.drawRect(10, 10, 500, 510, paint);   //渐变矩形绘制
        canvas.drawPath(mTriangle, textPaint);    //绘制小三角形
        canvas.restore();

        //最后将画笔去除Xfermode
        paint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

    private void setmTimer() {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                agree = agree + 0.022f;//数值越大，旋转速度越快
                if (agree > 360)
                    agree = 1;
                postInvalidate();
            }
        }, 1000, 3);  //延时/周期
    }
}
