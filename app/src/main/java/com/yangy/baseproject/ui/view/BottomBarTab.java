package com.yangy.baseproject.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yangy.baseproject.R;


/**
 * Created by yangy
 */
@SuppressLint("ViewConstructor")
public class BottomBarTab extends FrameLayout {
    private ImageView mIvTab;
    private TextView mTvTab;
    private Context mContext;
    private int mTabPosition = -1;

    private TextView mTvUnreadCount;

    public BottomBarTab(Context context, @DrawableRes int icon, CharSequence title) {
        this(context, null, icon, title);
    }

    public BottomBarTab(Context context, AttributeSet attrs, int icon, CharSequence title) {
        this(context, attrs, 0, icon, title);
    }

    public BottomBarTab(Context context, AttributeSet attrs, int defStyleAttr, int icon, CharSequence title) {
        super(context, attrs, defStyleAttr);
        init(context, icon, title);
    }

    private void init(Context context, int icon, CharSequence title) {
        mContext = context;
        // 点击水波纹效果
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{R.attr.selectableItemBackgroundBorderless});
        Drawable drawable = typedArray.getDrawable(0);
        setBackgroundDrawable(drawable);
        typedArray.recycle();

        // 单个Item的父容器
        LinearLayout lLContainer = new LinearLayout(context);
        lLContainer.setOrientation(LinearLayout.VERTICAL);
        lLContainer.setGravity(Gravity.CENTER);
        LayoutParams paramsContainer = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsContainer.gravity = Gravity.CENTER;
        lLContainer.setLayoutParams(paramsContainer);

        // Item的图片
        mIvTab = new ImageView(context);
        // 指定图片尺寸
//        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 27, getResources()
// .getDisplayMetrics());
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mIvTab.setImageResource(icon);
        mIvTab.setLayoutParams(params);
        mIvTab.setColorFilter(ContextCompat.getColor(context, R.color.tab_unselect));
        lLContainer.addView(mIvTab);

        // Item的文本
        mTvTab = new TextView(context);
        mTvTab.setText(title);
        LinearLayout.LayoutParams paramsTv = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTv.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources()
                .getDisplayMetrics());
        mTvTab.setTextSize(10);
        mTvTab.setTextColor(ContextCompat.getColor(context, R.color.tab_unselect));
        mTvTab.setLayoutParams(paramsTv);
        lLContainer.addView(mTvTab);

        addView(lLContainer);

        // 右上角的数字角标
        int min = dip2px(context, 20);
        int padding = dip2px(context, 5);
        mTvUnreadCount = new TextView(context);
        mTvUnreadCount.setBackgroundResource(R.drawable.shape_bg_msg_bubble);
        mTvUnreadCount.setMinWidth(min);
        mTvUnreadCount.setTextColor(Color.WHITE);
        mTvUnreadCount.setPadding(padding, 0, padding, 0);
        mTvUnreadCount.setGravity(Gravity.CENTER);
        LayoutParams tvUnReadParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, min);
        tvUnReadParams.gravity = Gravity.CENTER;
        tvUnReadParams.leftMargin = dip2px(context, 17);
        tvUnReadParams.bottomMargin = dip2px(context, 14);
        mTvUnreadCount.setLayoutParams(tvUnReadParams);
        mTvUnreadCount.setVisibility(GONE);

        addView(mTvUnreadCount);
    }

    /**
     * 覆写setSelected(),添加选中效果逻辑
     */
    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
            mIvTab.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary));
            mTvTab.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        } else {
            mIvTab.setColorFilter(ContextCompat.getColor(mContext, R.color.tab_unselect));
            mTvTab.setTextColor(ContextCompat.getColor(mContext, R.color.tab_unselect));
        }
    }

    /**
     * 设置当前Item的下标
     */
    public void setTabPosition(int position) {
        mTabPosition = position;
        if (position == 0) {
            setSelected(true);
        }
    }

    public int getTabPosition() {
        return mTabPosition;
    }

    /**
     * 设置未读数量
     */
    public void setUnreadCount(int num) {
        if (num <= 0) {
            mTvUnreadCount.setText(String.valueOf(0));
            mTvUnreadCount.setVisibility(GONE);
        } else {
            mTvUnreadCount.setVisibility(VISIBLE);
            if (num > 99) {
                mTvUnreadCount.setText("99+");
            } else {
                mTvUnreadCount.setText(String.valueOf(num));
            }
        }
    }

    /**
     * 获取当前未读数量
     */
    public int getUnreadCount() {
        int count = 0;
        if (TextUtils.isEmpty(mTvUnreadCount.getText())) {
            return count;
        }
        if (mTvUnreadCount.getText().toString().equals("99+")) {
            return 99;
        }
        try {
            count = Integer.valueOf(mTvUnreadCount.getText().toString());
        } catch (Exception ignored) {
        }
        return count;
    }

    private int dip2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources()
                .getDisplayMetrics());
    }
}
