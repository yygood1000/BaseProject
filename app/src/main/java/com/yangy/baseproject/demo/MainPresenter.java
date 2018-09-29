package com.yangy.baseproject.demo;

import android.content.Context;

import com.yangy.baseproject.base.presenter.BasePresenter;

public class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(MainView mView, Context mContext) {
        super(mView, mContext);
    }

    public void requestTest() {
        mView.testToast();
    }
}
