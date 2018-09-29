package com.yangy.baseproject.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yangy.baseproject.R;
import com.yangy.baseproject.base.view.MvpActivity;
import com.yangy.baseproject.utils.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MvpActivity<MainPresenter> implements MainView {

    @BindView(R.id.tv_test)
    TextView mTvTest;

    @Override
    protected void initPresenter() {
        mPresenter = new MainPresenter(this, this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
    }

    @Override
    public void testToast() {
        showToast("测试");
    }

    @OnClick(R.id.tv_test)
    public void onViewClicked() {
        Logger.i("oye","click");
        mPresenter.requestTest();
    }
}
