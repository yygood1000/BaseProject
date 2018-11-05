package com.yangy.baseproject.presenter;

import com.mvp.base.presenter.BasePresenter;
import com.yangy.baseproject.view.MainView;

import utils.Logger;

public class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(MainView mView) {
        super(mView);
    }

    public void click() {
        Logger.d("oye", "MainPresenter click");
        mView.changeTextContext();
    }
}
