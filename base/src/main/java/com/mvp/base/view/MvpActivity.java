package com.mvp.base.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.mvp.base.R;
import com.mvp.base.presenter.BasePresenter;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import utils.MemoryUtils;
import utils.Toaster;

/**
 * 如果需要启用MVP模式开发，Activity相关的就继承该基类
 * Created by yangy
 */
public abstract class MvpActivity<T extends BasePresenter> extends RxAppCompatActivity implements IView {
    public String TAG;
    public T mPresenter;
    public Context mContext;
    private Unbinder unbinder;
    private Dialog loadingDialog;

    /**
     * 初始化presenters,由子类实现
     */
    protected abstract void initPresenter();

    /**
     * 获取layout的id，由子类实现
     */
    protected abstract int getLayoutResId();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 初始化标题栏
     */
    protected void initTitle() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        unbinder = ButterKnife.bind(this);
        mContext = this;
        TAG = this.getClass().toString();

        initTitle();
        initPresenter();
        initView();
        initData();
//        setActivityAnimation(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) unbinder.unbind();
        if (mPresenter != null) mPresenter.onDetach();

        // 当分配内存剩余小于40%时，进行手动GC
        if (MemoryUtils.getMemoryPercent() < 0.4) {
            System.gc();
            System.runFinalization();
        }
    }

    /**
     * toast默认的展示方法，如果有特殊要求，在子类中重写该方法
     */
    @Override
    public void showToast(String msg) {
        Toaster.showShort(msg);
    }


    /**
     * 展示加载框
     */
    public void showLoading() {
        if (loadingDialog == null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("加载中...");
            progressDialog.setIndeterminate(false);
            loadingDialog = progressDialog;
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    /**
     * 关闭加载框
     */
    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing())
            loadingDialog.dismiss();
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    /**
     * 需要子类来实现，获取子类的IPresenter，一个activity有可能有多个IPresenter
     */
    protected BasePresenter getPresenter() {
        if (mPresenter == null) {
            throw new NullPointerException("Presenter 未被初始化，请先在initPresenter中初始化Presenter");
        }
        return mPresenter;
    }

    /*
     * ============================页面跳转相关方法============================
     */

    @Override
    public void finish() {
        super.finish();
//        setActivityAnimation(1);
    }

    public void setNoAnim() {
        isShowAnim = false;
    }

    /**
     * 设置activity转场动画
     */
    public boolean isShowAnim = true; // 是否显示动画

    /**
     * 设置页面动画
     *
     * @param flag 0：开启 1：关闭
     */
    public void setActivityAnimation(int flag) {
        switch (flag) {
            case 0:
                overridePendingTransition(R.anim.slide_in_right, R.anim.anim_no);
                break;
            case 1:
                overridePendingTransition(R.anim.anim_no, R.anim.slide_out_right);
                break;
        }
    }
}
