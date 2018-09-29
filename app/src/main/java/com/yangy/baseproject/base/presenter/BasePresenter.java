package com.yangy.baseproject.base.presenter;

import android.content.Context;
import android.content.Intent;

import com.yangy.baseproject.base.view.IView;
import com.yangy.baseproject.base.view.MvpActivity;

public class BasePresenter<V extends IView> implements IPresenter {
    public V mView;
    public Context mContext;
    public MvpActivity mActivity;
    public String TAG;

    public BasePresenter(V mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
        this.mActivity = (MvpActivity) mContext;
        this.TAG = mActivity.getClass().toString();
    }

    public BasePresenter(Context mContext) {
        this.mContext = mContext;
        this.mActivity = (MvpActivity) mContext;
        this.TAG = mActivity.getClass().toString();
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        clearMemory();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    /**
     * 为防止内存泄漏,需在页面销毁的时候清除对context,activity实例的引用,并且解除view的绑定
     */
    private void clearMemory() {
        this.mView = null;
        this.mContext = null;
        this.mActivity = null;
    }
}