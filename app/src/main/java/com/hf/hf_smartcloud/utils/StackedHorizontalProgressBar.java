package com.hf.hf_smartcloud.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

public class StackedHorizontalProgressBar extends ProgressBar {
    int primary_progress, max_value;
    private Paint paint;

    public StackedHorizontalProgressBar(Context context) {
        super(context);
        init();
    }

    public StackedHorizontalProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    public synchronized void setMax(int max) {
        this.max_value = max;
        super.setMax(max);
    }

    @Override
    public synchronized void setProgress(int progress) {
        if (progress > max_value) {
            progress = max_value;
        }
        this.primary_progress = progress;
        super.setProgress(progress);
    }

    @Override
    public synchronized void setSecondaryProgress(int secondaryProgress) {
        if ((primary_progress + secondaryProgress) > max_value) {
            secondaryProgress = (max_value - primary_progress);
        }
        super.setSecondaryProgress(primary_progress + secondaryProgress);
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        primary_progress = 0;
        max_value = 100;
    }

}
