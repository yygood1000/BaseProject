package com.yangy.baseproject.demo;

import android.content.Context;

import com.mvp.base.presenter.BasePresenter;

public class MainPresenter extends BasePresenter<MainView> {

    MainPresenter(MainView mView) {
        super(mView);
    }

    public void requestTest() {
        mView.testToast();
    }
}
