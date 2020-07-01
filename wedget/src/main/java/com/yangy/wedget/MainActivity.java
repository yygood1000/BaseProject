package com.yangy.wedget;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mvp.base.view.MvpActivity;

import java.util.ArrayList;
import java.util.List;

import utils.Logger;

public class MainActivity extends MvpActivity {
    private String mTitles1[] = {
            "推荐活动", "正在进行", "我参与的"};
    private String mTitles2[] = {"基因贴", "议事", "活动", "聊天室"};
    private String mTitles3[] = {"简介", "基因贴"};
    private String mTitles4[] = {"我的创建", "我的收藏"};
    private List<Fragment> mFragments;
    private CustomTabLayout mTabLayout1;
    private CustomTabLayout mTabLayout2;
    private CustomTabLayout mTabLayout3;
    private CustomTabLayout mTabLayout4;
    private BannerIndicator mIndicator;
    private ViewPager mViewPager;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragments = new ArrayList<>();
        mTabLayout1 = findViewById(R.id.tablayout1);
        mTabLayout2 = findViewById(R.id.tablayout2);
        mTabLayout3 = findViewById(R.id.tablayout3);
        mTabLayout4 = findViewById(R.id.tablayout4);
        mViewPager = findViewById(R.id.viewPager);
        mIndicator=findViewById(R.id.indicator);

        for (int i = 0; i < mTitles2.length; i++) {
            MyFragment fragment = new MyFragment();
            Bundle bundle = new Bundle();
            bundle.putString("text", mTitles2[i]);
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }
        TabFragmentAdapter adapter = new TabFragmentAdapter(mFragments, mTitles2, getSupportFragmentManager(), this);
        mViewPager.setAdapter(adapter);

        init1();

        init2();
        init3();
        init4();

        initPointIndicator();

    }

    private void initPointIndicator() {
        mIndicator.setUpWidthViewPager(mViewPager);
    }

    private void init1() {
        for (String s : mTitles1) {
            mTabLayout1.addTab(s);
        }

        mTabLayout1.setBadgeText(0, 211 + "");
        mTabLayout1.setBadgeText(1, 9999 + "");
        mTabLayout1.setBadgeText(2, 0 + "");

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout1.getTabLayout()));
        mTabLayout1.setupWithViewPager(mViewPager);
    }

    private void init2() {
        for (String s : mTitles2) {
            mTabLayout2.addTab(s);
        }
        // 一下两句代码实现ViewPager与TabLayout联动
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout2.getTabLayout()));
        mTabLayout2.setupWithViewPager(mViewPager);

        // 这个回调监听可以用于处理未读标记
        mTabLayout2.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中了tab的逻辑
                Logger.i("======我选中了====");
                mTabLayout2.setUnReadVisibility(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //未选中tab的逻辑
                Logger.i("======我未被选中====");
                View view = tab.getCustomView();
                TextView textView = view.findViewById(R.id.tab_item_text);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        // 设置显示未读标记
        mTabLayout2.setUnReadVisibility(2, true);
        mTabLayout2.setUnReadVisibility(3, true);
    }


    private void init3() {
        for (String s : mTitles3) {
            mTabLayout3.addTab(s);
        }
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout3.getTabLayout()));
        mTabLayout3.setupWithViewPager(mViewPager);
    }

    private void init4() {
        for (String s : mTitles4) {
            mTabLayout4.addTab(s);
        }
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout4.getTabLayout()));
        mTabLayout4.setupWithViewPager(mViewPager);
    }

}
