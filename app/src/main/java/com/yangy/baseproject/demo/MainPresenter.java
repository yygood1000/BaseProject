package com.yangy.baseproject.demo;

import android.content.Context;

import com.mvp.base.presenter.BasePresenter;

import utils.Logger;

public class MainPresenter extends BasePresenter<MainView> {

    MainPresenter(MainView mView) {
        super(mView);
    }

    public void click() {
        Logger.d("oye", "MainPresenter click");
        mView.changeTextContext();
    }
}
