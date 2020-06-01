package com.yangy.baseproject.ui.activity;

import com.mvp.base.presenter.BasePresenter;
import com.mvp.base.view.BaseFragmentActivity;
import com.mvp.base.view.MvpFragment;
import com.yangy.baseproject.R;
import com.yangy.baseproject.ui.fragment.FourthFragment;
import com.yangy.baseproject.ui.fragment.HomeFragment;
import com.yangy.baseproject.ui.fragment.SecondFragment;
import com.yangy.baseproject.ui.fragment.ThirdFragment;
import com.yangy.baseproject.ui.wedget.BottomBar;
import com.yangy.baseproject.ui.wedget.BottomBarTab;

import java.util.ArrayList;

import butterknife.BindView;

public class PageActivity extends BaseFragmentActivity<BasePresenter> {
    public static final int FIRST_PAGE = 0;

    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;

    @Override
    protected ArrayList<MvpFragment> getFragments() {
        ArrayList<MvpFragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new SecondFragment());
        fragments.add(new ThirdFragment());
        fragments.add(new FourthFragment());
        return fragments;
    }

    @Override
    protected void initPresenter() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_page;
    }

    @Override
    protected int getContainerViewId() {
        return R.id.fl_container;
    }

    @Override
    protected void initView() {
        initBottomBar();

        setCurrFragment(mFragments.get(0), "HomeFragment");
    }

    private void initBottomBar() {
        mBottomBar
                .addItem(new BottomBarTab(this, R.mipmap.icon_home, "首页"))
                .addItem(new BottomBarTab(this, R.mipmap.icon_notificatin, "通知"))
                .addItem(new BottomBarTab(this, R.mipmap.icon_list, "订单"))
                .addItem(new BottomBarTab(this, R.mipmap.icon_personal, "我的"));

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                setCurrFragment(mFragments.get(position), mFragments.get(position).getClass().getName());
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        mBottomBar.setCurrentItem(FIRST_PAGE);
    }

}
