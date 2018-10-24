package com.mvp.base.presenter;

import com.mvp.base.view.IView;

public class BasePresenter<V extends IView> implements IPresenter {
    protected V mView;

    public BasePresenter(V view) {
        this.mView = view;
    }

    @Override
    public void onDetach() {
        clearMemory();
    }

    /**
     * 为防止内存泄漏,需在页面销毁的时候清除对context,activity实例的引用,并且解除view的绑定
     */
    private void clearMemory() {
        this.mView = null;
    }
}