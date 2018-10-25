package com.mvp.base.view;

import android.content.Context;


/**
 * 基础View
 * Created by yy on 2017/6/28.
 */
public interface IView {
    Context getContext();

    /**
     * 该方法在MvpActivity中已经实现好了，P层只需要mView.showToast(string),即可展示Toast
     */
    void showToast(String msg);

    /**
     * 关闭页面
     */
    void finish();

    /**
     * 展示/收起加载框
     */
    void showLoading();

    /**
     * 关闭弹窗
     */
    void dismissLoading();

}
