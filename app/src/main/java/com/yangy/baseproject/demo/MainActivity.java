package com.yangy.baseproject.demo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.mvp.base.view.MvpActivity;
import com.yangy.baseproject.R;
import com.yangy.baseproject.demo.bean.extra.SecondActivityExtra;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.Logger;

public class MainActivity extends MvpActivity<MainPresenter> implements MainView {
    @BindView(R.id.tv_test)
    TextView mTvTest;

    @Override
    protected void initPresenter() {
        mPresenter = new MainPresenter(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
    }

    @OnClick(R.id.tv_test)
    public void onViewClicked() {
        Logger.d("oye","click");
        mPresenter.click();
        turnToActivity(SecondActivity.class, new SecondActivityExtra());
    }

    @Override
    public void changeTextContext() {
        mTvTest.setText("修改了文本");
    }
}
