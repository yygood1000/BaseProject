package com.yangy.wedget;

import android.graphics.Color;
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
    //    @BindView(R.id.tablayout)
//    TabLayout mTabLayout;
//    @BindView(R.id.viewPager)
//    ViewPager mViewPager;
    private String mTitles[] = {
            "上海", "头条推荐", "生活", "娱乐八卦"};
//    , "体育",
//            "段子", "美食", "电影", "科技", "搞笑",
//            "社会", "财经", "时尚", "汽车", "军事",
//            "小说", "育儿", "职场", "萌宠", "游戏",
//            "健康", "动漫", "互联网"};

    private List<Fragment> mFragments;
    private TabFragmentAdapter mTabFragmentAdapter;
    private CustomTabLayout mTabLayout;

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
        mTabLayout = findViewById(R.id.tablayout);
        ViewPager mViewPager = findViewById(R.id.viewPager);

        for (int i = 0; i < mTitles.length; i++) {
            mTabLayout.addTab(mTitles[i]);
        }
        for (int i = 0; i < mTitles.length; i++) {
            MyFragment fragment = new MyFragment();
            Bundle bundle = new Bundle();
            bundle.putString("text", mTitles[i]);
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }

        TabFragmentAdapter adapter = new TabFragmentAdapter(mFragments, mTitles, getSupportFragmentManager(), this);
        mViewPager.setAdapter(adapter);
        // 一下两句代码实现ViewPager与TabLayout联动
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout.getTabLayout()));
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中了tab的逻辑
                Logger.i("======我选中了====");

                View view = tab.getCustomView();
                TextView textView = view.findViewById(R.id.tab_item_text);
                textView.setTextColor(Color.RED);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //未选中tab的逻辑
                Logger.i("======我未被选中====");
                View view = tab.getCustomView();
                TextView textView = view.findViewById(R.id.tab_item_text);
                textView.setTextColor(Color.BLUE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //再次选中tab的逻辑
                Logger.i("======我再次被选中====");
            }
        });

        mTabLayout.setUnReadVisibility(0, true);
    }
}
