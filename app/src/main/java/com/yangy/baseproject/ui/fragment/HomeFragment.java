package com.yangy.baseproject.ui.fragment;

import android.view.View;

import com.mvp.base.view.MvpFragment;
import com.yangy.baseproject.R;
import com.yangy.baseproject.bean.extra.SecondActivityExtra;
import com.yangy.baseproject.bean.extra.SimpleExtra;
import com.yangy.baseproject.ui.activity.SecondActivity;

import butterknife.OnClick;
import butterknife.Unbinder;
import utils.ActivityUtils;

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
        ActivityUtils.turnToActivityResult(mActivity, SecondActivity.class, 1,
                new SecondActivityExtra("TAG_YY", new SimpleExtra(1000)));
    }
}
