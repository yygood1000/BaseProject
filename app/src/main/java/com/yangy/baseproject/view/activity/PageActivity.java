package com.yangy.baseproject.view.activity;

import com.mvp.base.presenter.BasePresenter;
import com.mvp.base.view.BaseFragmentActivity;
import com.mvp.base.view.MvpFragment;
import com.yangy.baseproject.R;
import com.yangy.baseproject.view.fragment.FourthFragment;
import com.yangy.baseproject.view.fragment.HomeFragment;
import com.yangy.baseproject.view.fragment.SecondFragment;
import com.yangy.baseproject.view.fragment.ThirdFragment;

import java.util.ArrayList;

public class PageActivity extends BaseFragmentActivity<BasePresenter> {

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
        setCurrFragment(mFragments.get(0), "HomeFragment");
    }
}
