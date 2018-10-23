package com.mvp.base.view;

import android.content.Context;
import android.os.Bundle;

import com.mvp.base.BaseExtra;


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
     * 跳转至下个页面
     */
    void turnToActivity(Class className);

    /**
     * 跳转至下个页面并传参
     */
    void turnToActivity(Class className, Bundle bundle);

    /**
     * 跳转至下个页面并传参
     */
    <E extends BaseExtra> void turnToActivity(Class className, E extra);

    /**
     * 跳转至下个页面并销毁
     */
    void turnToActivityWithFinish(Class className);

    /**
     * 跳转至下个页面并传参，并销毁
     */
    void turnToActivityWithFinish(Class className, Bundle bundle);

    /**
     * 设置返回参数，并关闭页面
     */
    <E extends BaseExtra> void setResultAndFinish(int resultCode, E extra);

    /**
     * 关闭页面
     */
    void finish();

    /**
     * 展示/收起加载框
     */
    void showLoadingDialog();

    /**
     * 关闭弹窗
     */
    void cancelShowLoadingDialog();

}
