package com.yangy.baseproject.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mvp.base.view.MvpActivity;
import com.yangy.baseproject.R;
import com.yangy.baseproject.demo.bean.extra.SecondActivityExtra;

import utils.Toaster;

public class SecondActivity extends MvpActivity {

    @Override
    protected void initPresenter() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_second;
    }

    @Override
    protected void initView() {
        SecondActivityExtra contactExtra = (SecondActivityExtra) getIntentExtra(SecondActivityExtra.getExtraName());
        showToast(contactExtra.TAG);
    }
}
