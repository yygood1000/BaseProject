package com.yangy.baseproject.ui.fragment;

import android.view.View;

import com.mvp.base.view.MvpFragment;
import com.yangy.baseproject.R;

import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends MvpFragment {
    Unbinder unbinder;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView(View v) {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void finish() {

    }


    @OnClick(R.id.tv)
    public void onViewClicked() {
        showLoading();
    }
}
