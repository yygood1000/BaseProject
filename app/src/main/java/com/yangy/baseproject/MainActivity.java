package com.yangy.baseproject;

import android.content.Intent;
import android.widget.TextView;

import com.mvp.base.view.MvpActivity;
import com.yangy.baseproject.bean.extra.SecondActivityExtra;
import com.yangy.baseproject.bean.extra.SimpleExtra;

import butterknife.BindView;
import butterknife.OnClick;
import utils.ActivityUtils;
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
        Logger.d("oye", "click");
        mPresenter.click();
        ActivityUtils.turnToActivityResult(this, SecondActivity.class, 1,
                new SecondActivityExtra("TAG_YY", new SimpleExtra(1000)));

    }

    @Override
    public void changeTextContext() {
        mTvTest.setText("修改了文本");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mTvTest.setText("返回该页面了");
        }
    }
}
