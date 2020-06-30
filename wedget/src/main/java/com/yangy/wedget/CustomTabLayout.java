package com.yangy.wedget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import utils.Logger;

public class CustomTabLayout extends FrameLayout {
    private TabLayout mTabLayout;
    private List<String> mTabTextList;
    private List<View> mCustomViewList;
    private int mSelectTextColor;
    private int mUnSelectTextColor;
    private int mIndicatorHeight;
    private int mIndicatorWidth;
    private int mIndicatorMarginTop;
    private int mTabMode;
    private int mTabTextSizeDP;
    public boolean mIsSelectedBold;
    private boolean mIsShowBadge;
    private int mBadgeTextSize;

    private Drawable mDrawableUnRead;
    private int mUnReadDrawablePadding;

    public CustomTabLayout(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public CustomTabLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomTabLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomTabLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void readAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTabLayout);
        // Tab文字未选中颜色
        mUnSelectTextColor = typedArray.getColor(R.styleable.CustomTabLayout_tabTextColor, Color.parseColor("#000000"));
        // Tab文字选中颜色
        mSelectTextColor = typedArray.getColor(R.styleable.CustomTabLayout_tabSelectTextColor, Color.parseColor(
                "#000000"));
        // 指示条高度
        mIndicatorHeight = typedArray.getDimensionPixelSize(R.styleable.CustomTabLayout_tabIndicatorHeight, 1);
        // 指示条宽度
        mIndicatorWidth = typedArray.getDimensionPixelSize(R.styleable.CustomTabLayout_tabIndicatorWidth, 0);
        mIndicatorMarginTop = typedArray.getDimensionPixelSize(R.styleable.CustomTabLayout_tabIndicatorMarginTop, 5);
        // Tab文本大小
        mTabTextSizeDP = typedArray.getInteger(R.styleable.CustomTabLayout_tabTextSizeDP, 0);
        // TabLayout的展示模式，滚动或固定平铺
        mTabMode = typedArray.getInt(R.styleable.CustomTabLayout_tab_Mode, 2);
        mIsSelectedBold = typedArray.getBoolean(R.styleable.CustomTabLayout_tabSelectedBold, false);
        mIsShowBadge = typedArray.getBoolean(R.styleable.CustomTabLayout_tabIsShowBadge, false);
        mBadgeTextSize = typedArray.getInteger(R.styleable.CustomTabLayout_tabBadgeTextSize, 0);

        mDrawableUnRead = typedArray.getDrawable(R.styleable.CustomTabLayout_tabUnReadDrawable);
        mUnReadDrawablePadding =
                typedArray.getDimensionPixelSize(R.styleable.CustomTabLayout_tabUnReadDrawablePadding, 5);
        typedArray.recycle();
    }

    private void init(final Context context, AttributeSet attrs) {
        Logger.d("oye", "initStart = " + System.currentTimeMillis());
        readAttr(context, attrs);
        mTabTextList = new ArrayList<>();
        mCustomViewList = new ArrayList<>();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.custom_tablayout, this, true);
        mTabLayout = view.findViewById(R.id.tab_layout);

        // 添加属性
        mTabLayout.setTabMode(mTabMode == 1 ? TabLayout.MODE_FIXED : TabLayout.MODE_SCROLLABLE);
        // 添加默认的选择监听，因为如果TabLayout不与ViewPager连用，自定义Tab则无法响应切换时的UI变更
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                /// 注意：此回调中的切换UI修改了，记得修改ViewPagerOnTabSelectedListener中的回调。因为默认回调会在设置ViewPager联动时删除
                // Tab 选中之后，改变各个Tab的状态
                for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                    View view = mTabLayout.getTabAt(i).getCustomView();
                    if (view == null) {
                        return;
                    }
                    TextView text = view.findViewById(R.id.tab_item_text);
                    View indicator = view.findViewById(R.id.tab_item_indicator);
                    if (i == tab.getPosition()) {
                        // 选中状态
                        text.setTextColor(mSelectTextColor);
                        if (mIsSelectedBold) {
                            text.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        }
                        indicator.setBackground(AppCompatResources.getDrawable(context,
                                R.drawable.shape_indecator_red));
                        indicator.setVisibility(View.VISIBLE);
                    } else {
                        // 未选中状态
                        text.setTextColor(mUnSelectTextColor);
                        indicator.setVisibility(View.INVISIBLE);
                        if (mIsSelectedBold) {
                            text.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        }
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        Logger.d("oye", "initEnd = " + System.currentTimeMillis());
    }

    public List<View> getCustomViewList() {
        return mCustomViewList;
    }

    public void setBadgeVisibility(int position, boolean isVisible) {
        if (null != mCustomViewList && mCustomViewList.size() > 0) {
            View view = mCustomViewList.get(position);
            View badge = view.findViewById(R.id.tab_item_badge);
            badge.setVisibility(isVisible ? VISIBLE : GONE);
        }
    }

    public void setUnReadVisibility(int position, boolean isVisible) {
        if (null != mCustomViewList && mCustomViewList.size() > 0) {
            View view = mCustomViewList.get(position);
            TextView tabText = view.findViewById(R.id.tab_item_text);
            if (tabText != null) {
                if (isVisible) {
                    if (mDrawableUnRead != null) {
                        mDrawableUnRead.setBounds(0, 0, mDrawableUnRead.getMinimumWidth(),
                                mDrawableUnRead.getMinimumHeight());
                        tabText.setCompoundDrawables(null, null, mDrawableUnRead, null);
                        tabText.setCompoundDrawablePadding(mUnReadDrawablePadding);
                    }
                } else {
                    tabText.setCompoundDrawables(null, null, null, null);
                }
            }
        }
    }

    public TabLayout getTabLayout() {
        return mTabLayout;
    }

    /**
     * 添加tab
     */
    public void addTab(String tab) {
        mTabTextList.add(tab);
        View customView = getTabView(getContext(), tab);
        mCustomViewList.add(customView);
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(customView));
    }

    /**
     * 获取Tab 显示的内容
     * TODO 目前单个Tab的实现是写死的，如果后期有需要，可以将getTabView()交由页面实现
     */
    public View getTabView(Context context, String text) {
        View mTab = LayoutInflater.from(context).inflate(R.layout.tab_item_layout, null);
        TextView tabText = mTab.findViewById(R.id.tab_item_text);
        TextView tvBadge = mTab.findViewById(R.id.tab_item_badge);
        tvBadge.setVisibility(mIsShowBadge ? VISIBLE : GONE);
        tvBadge.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mBadgeTextSize);
        if (mIndicatorWidth > 0) {
            View indicator = mTab.findViewById(R.id.tab_item_indicator);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) indicator.getLayoutParams();
            layoutParams.width = mIndicatorWidth;
            layoutParams.height = mIndicatorHeight;
            layoutParams.topMargin = mIndicatorMarginTop;
            indicator.setLayoutParams(layoutParams);
        }
        tabText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mTabTextSizeDP);
        tabText.setTextColor(mUnSelectTextColor);
        tabText.setText(text);

        return mTab;
    }

    public void addOnTabSelectedListener(TabLayout.OnTabSelectedListener onTabSelectedListener) {
        mTabLayout.addOnTabSelectedListener(onTabSelectedListener);
    }

    /**
     * 与TabLayout 联动
     */
    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        mTabLayout.clearOnTabSelectedListeners();
        mTabLayout.addOnTabSelectedListener(new ViewPagerOnTabSelectedListener(viewPager, this));
    }

    public static class ViewPagerOnTabSelectedListener implements TabLayout.OnTabSelectedListener {
        private final ViewPager mViewPager;
        private final WeakReference<CustomTabLayout> mTabLayoutRef;

        public ViewPagerOnTabSelectedListener(ViewPager viewPager, CustomTabLayout CustomTabLayout) {
            mViewPager = viewPager;
            mTabLayoutRef = new WeakReference<>(CustomTabLayout);
        }

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mViewPager.setCurrentItem(tab.getPosition());
            CustomTabLayout mTabLayout = mTabLayoutRef.get();
            if (mTabLayout != null) {
                List<View> customViewList = mTabLayout.getCustomViewList();
                if (customViewList == null || customViewList.size() == 0) {
                    return;
                }
                for (int i = 0; i < customViewList.size(); i++) {
                    View view = customViewList.get(i);
                    if (view == null) {
                        return;
                    }
                    TextView text = view.findViewById(R.id.tab_item_text);
                    View indicator = view.findViewById(R.id.tab_item_indicator);
                    if (i == tab.getPosition()) {
                        // 选中状态
                        text.setTextColor(mTabLayout.mSelectTextColor);
                        indicator.setBackground(AppCompatResources.getDrawable(view.getContext(),
                                R.drawable.shape_indecator_red));
                        if (mTabLayout.mIsSelectedBold) {
                            text.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        }
                        indicator.setVisibility(View.VISIBLE);
                    } else {
                        if (mTabLayout.mIsSelectedBold) {
                            text.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        }
                        // 未选中状态
                        text.setTextColor(mTabLayout.mUnSelectTextColor);
                        indicator.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    }
}