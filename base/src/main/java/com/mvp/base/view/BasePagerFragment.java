package com.mvp.base.view;

import android.os.Bundle;

import com.mvp.base.presenter.IFragmentPresenter;


/**
 * 配合viewPager使用，监听fragment可见状态懒加载
 */
public abstract class BasePagerFragment<T extends IFragmentPresenter> extends MvpFragment<T> {
    private boolean isFirstResume = true;
    private boolean isFirstVisible = true;
    private boolean isPrepared;

    private void initPrepare() {
        if (isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onUserVisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        }
    }

    /**
     * 页面对用户可见且不是首次
     */
    protected abstract void onUserVisible();

    /**
     * 页面首次对用户可见的回调，可执行数据初始化等操作
     */
    protected abstract void onFirstUserVisible();

}
