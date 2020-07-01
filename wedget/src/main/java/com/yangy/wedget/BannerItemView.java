package com.yangy.wedget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 可缩小成圆放大成圆角矩形的view
 */
public class BannerItemView extends View {
    private static final String TAG = BannerItemView.class.getSimpleName();
    private Paint mPaint;
    private float rectWidth;
    private RectF rectF;
    public static final int CENTER = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    private int location = CENTER;

    public BannerItemView(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectF = new RectF();
        mPaint.setColor(Color.RED);
    }

    public BannerItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectF = new RectF();
        mPaint.setColor(Color.RED);
    }

    public BannerItemView(Context context, int location) {
        this(context);
        this.location = location;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (location) {
            case CENTER:
                //中间对齐
                rectF.left = getWidth() / 2 - getHeight() / 2 - rectWidth;
                rectF.right = getHeight() / 2 + getWidth() / 2 + rectWidth;
                rectF.top = 0;
                rectF.bottom = getHeight();
                break;
            case LEFT:
                rectF.left = getWidth() - getHeight() - rectWidth;
                rectF.right = getWidth();
                rectF.top = 0;
                rectF.bottom = getHeight();
                break;
            case RIGHT:
                rectF.left = 0;
                rectF.right = getHeight() + rectWidth;
                rectF.top = 0;
                rectF.bottom = getHeight();
                break;
        }
        canvas.drawRoundRect(rectF, getHeight() / 2, getHeight() / 2, mPaint);
    }

    public float getRectWidth() {
        return rectWidth;
    }

    public void setRectWidth(float rectWidth) {
        this.rectWidth = rectWidth;
        invalidate();
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }
}