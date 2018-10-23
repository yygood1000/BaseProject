package com.mvp.base.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mvp.base.BaseExtra;
import com.mvp.base.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.mvp.base.presenter.BasePresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import utils.MemoryUtils;
import utils.SystemUtils;
import utils.Toaster;

/**
 * 如果需要启用MVP模式开发，Activity相关的就继承该基类
 * Created by yangy
 */
public abstract class MvpActivity<T extends BasePresenter> extends RxAppCompatActivity implements IView {
    public String TAG;
    public Context mContext;
    public T mPresenter;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // App 被系统回收，App跳转到启动页重新开启
//        if (savedInstanceState != null) {
//            finish();
//            AppManager.getInstance().finishAllActivity();
//            turnToActivity(RespectiveData.getSplashActivityClassClass());
//            return;
//        }

        setContentView(getLayoutResId());

        unbinder = ButterKnife.bind(this);
        mContext = this;
        TAG = this.getClass().toString();

        initTitle();
        initPresenter();
        initView();
        initData();
        setActivityAnimation(0);
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
     * TODO 展示加载框
     */
    public void showLoadingDialog() {
    }

    /**
     * TODO 关闭加载框
     */
    public void cancelShowLoadingDialog() {
    }

    @Override
    public Context getContext() {
        return mContext;
    }
    /*
     * ============================页面跳转相关方法============================
     */

    /**
     * 跳转至下个页面
     */
    @Override
    public void turnToActivity(Class className) {
        Intent intent = new Intent(this, className);
        startActivity(intent);
    }

    /**
     * 跳转至下个页面并传参
     */
    @Override
    public void turnToActivity(Class className, Bundle bundle) {
        Intent intent = new Intent(this, className);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转至下个页面并传参
     *
     * @param extra 传递的实体类
     */
    public <E extends BaseExtra> void turnToActivity(Class className, E extra) {
        Bundle bundle = new Bundle();
        if (E.getExtraName() == null) {
            E.setExtraName(SystemUtils.nextExtraName());
        }
        bundle.putSerializable(E.getExtraName(), extra);
        turnToActivity(className, bundle);
    }

    /**
     * 跳转至下个页面并销毁
     */
    @Override
    public void turnToActivityWithFinish(Class className) {
        turnToActivity(className);
        this.finish();
    }

    /**
     * 跳转至下个页面，并传参，并销毁
     */
    @Override
    public void turnToActivityWithFinish(Class className, Bundle bundle) {
        turnToActivity(className, bundle);
        this.finish();
    }

    /**
     * 跳转至下个页面ForResult
     */
    public void turnToActivityForResult(Class className, int requestCode) {
        Intent intent = new Intent(this, className);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转至下个页面并传参ForResult
     */
    public void turnToActivityForResult(Class className, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, className);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转至下个页面并传参ForResult
     *
     * @param extra 需要传递的实体类
     */
    public <E extends BaseExtra> void turnToActivityForResult(Class className, int requestCode, E extra) {
        Bundle bundle = new Bundle();
        if (E.getExtraName() == null) {
            E.setExtraName(SystemUtils.nextExtraName());
        }
        bundle.putSerializable(E.getExtraName(), extra);
        turnToActivityForResult(className, requestCode, bundle);
    }

    /**
     * 设置返回数据 ，并销毁
     */
    public <E extends BaseExtra> void setResultAndFinish(int resultCode, E extra) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        if (E.getExtraName() == null) {
            E.setExtraName(SystemUtils.nextExtraName());
        }
        bundle.putSerializable(E.getExtraName(), extra);
        intent.putExtras(bundle);
        setResult(resultCode, intent);
        this.finish();
    }

    /**
     * 设置返回数据 ，并销毁
     */
    public void setResultAndFinish(int resultCode) {
        setResult(resultCode);
        this.finish();
    }

    /**
     * 通过extraName获取传参
     *
     * @param extraName getExtraName()方法获取
     */
    public <E extends BaseExtra> E getIntentExtra(String extraName) {
        return getIntentExtra(getIntent(), extraName);
    }

    /**
     * 在OnActivityResult()中获取Extra的方法
     *
     * @param intent    OnActivityResult方法中返回的Intent
     * @param extraName 通过Extra.getExtraName()获取
     */
    public <E extends BaseExtra> E getIntentExtra(Intent intent, String extraName) {
        if (intent != null) {
            return intent.getExtras() != null ? (E) intent.getExtras().getSerializable(extraName) : null;
        }
        return null;
    }

    @Override
    public void finish() {
        super.finish();
        setActivityAnimation(1);
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
