package com.mvp.base.view;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mvp.base.presenter.BasePresenter;

import java.util.ArrayList;

/**
 * 有Fragment的Activity继承BaseFragmentActivity进行编写
 *
 * @param <T>
 */
public abstract class BaseFragmentActivity<T extends BasePresenter> extends MvpActivity<T> {
    protected FragmentManager mFragmentManager = getSupportFragmentManager();
    protected ArrayList<MvpFragment> mFragments = getFragments();
    protected Fragment mCurrFragment;

    /**
     * @return 返回所有需要显示的Fragment集合
     */
    protected abstract ArrayList<MvpFragment> getFragments();

    /**
     * @return 返回显示Fragment的布局
     */
    protected abstract int getContainerViewId();

    /**
     * 调用此方法前，需要先正确的复写-getFragments()与-getContainerViewId()两个方法
     *
     * @param targetFragment 需要显示的Fragment
     * @param tag            每个Fragment都需要有个tag去标识它，可用类名作为tag
     */
    protected void setCurrFragment(Fragment targetFragment, String tag) {
        setCurrFragment(getContainerViewId(), targetFragment, tag);
    }

    /**
     * Note that: Fragments in the same type and with same container ID will not work on right,event with different
     * tags,if you has to use same type Fragments which only distinguished by Arguments,you can use ViewPager
     * instead.
     */
    protected void setCurrFragment(int containerViewId, Fragment targetFragment, String tag) {
        if (mCurrFragment == targetFragment) {
            return;
        }
        if (targetFragment.isAdded()) {
            showFragment(targetFragment);
        } else {
            addFragment(containerViewId, targetFragment, tag);
        }
        mCurrFragment = targetFragment;
    }

    /**
     * 添加Fragment
     */
    private void addFragment(int containerViewId, Fragment targetFragment, String tag) {
        FragmentTransaction commonTransaction = getCommonTransaction();
        hideOtherFragments(commonTransaction, targetFragment);
        commonTransaction.add(containerViewId, targetFragment, tag);
        commonTransaction.commitAllowingStateLoss();
    }

    /**
     * 显示传入的Fragment
     *
     * @param targetFragment 需要显示的Fragment
     */
    private void showFragment(Fragment targetFragment) {
        FragmentTransaction commonTransaction = getCommonTransaction();
        hideOtherFragments(commonTransaction, targetFragment);
        commonTransaction.show(targetFragment);
        commonTransaction.commitAllowingStateLoss();
    }

    /**
     * 隐藏其他的Fragment
     */
    private void hideOtherFragments(FragmentTransaction commonTransaction, Fragment targetFragment) {
        if (null != mFragments && mFragments.size() > 0) {
            for (Fragment fragment : mFragments) {
                if (fragment.isAdded() && fragment != targetFragment) {
                    commonTransaction.hide(fragment);
                }
            }
        }
    }

    /**
     * 获取FragmentTransaction
     */
    @SuppressLint("CommitTransaction")
    protected FragmentTransaction getCommonTransaction() {
//        return mFragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim
//                .slide_out_left);
        return mFragmentManager.beginTransaction();
        // 需要切换动画就取消注释
//        setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public Fragment getCurrFragment() {
        return mCurrFragment;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // 防止内存回收导致的Fragment重叠
    }

}
