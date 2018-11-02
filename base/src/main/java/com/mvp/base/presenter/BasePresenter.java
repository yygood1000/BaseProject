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
     * 为防止内存泄漏,需在页面销毁的时候解除view的绑定
     */
    private void clearMemory() {
        this.mView = null;
    }

    // TODO 统一处理网络请求的生命周期管理
}