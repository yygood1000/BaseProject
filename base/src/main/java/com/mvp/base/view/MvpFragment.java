package com.mvp.base.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mvp.base.presenter.IFragmentPresenter;
import com.trello.rxlifecycle2.components.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import utils.Toaster;

/**
 * Created by yy on 2017.8.1
 * <p>
 * Fragment MVP模式基类
 */

public abstract class MvpFragment<T extends IFragmentPresenter> extends RxFragment implements IView {
    public String TAG;
    public T mPresenter;
    public MvpActivity mActivity;
    private Unbinder unbinder;

    //初始化presenter
    protected abstract void initPresenter();

    //获取LayoutId
    protected abstract int getLayoutResId();

    protected abstract void initView(View v);

    protected abstract void initData();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (MvpActivity) activity;
        TAG = getClass().toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        unbinder = ButterKnife.bind(this, view);

        initPresenter();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    @Override
    public Context getContext() {
        return mActivity;
    }

    //获取presenter
    protected T getPresenter() {
        if (mPresenter == null) {
            throw new NullPointerException("Presenter 未被初始化，请先在initPresenter中初始化Presenter");
        }
        return mPresenter;
    }

    // toast提示
    public void showToast(String msg) {
        Toaster.showShort(msg);
    }

    /**
     * 展示/收起加载框Dialog
     */
    public void showLoadingDialog() {
        mActivity.showLoading();
    }

    /**
     * 关闭加载框
     */
    public void cancelShowLoadingDialog() {
        mActivity.dismissLoading();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != unbinder) {
            unbinder.unbind();
        }
    }
}
