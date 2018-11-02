package com.mvp.base.presenter;

import com.mvp.base.view.IView;


/**
 * Created by yy on 2017/8/1
 * <p>
 * Fragment 的基类 Presenter
 */

public class BaseFragmentPresenter<V extends IView> implements IFragmentPresenter {
    public V mView;//fragment view

    public BaseFragmentPresenter(V mView) {
        this.mView = mView;
    }

    public void onDetach() {
        clearMemory();
    }

    /**
     * 为防止内存泄漏,需在页面销毁的时候解除view的绑定
     */
    private void clearMemory() {
        this.mView = null;
    }
}
