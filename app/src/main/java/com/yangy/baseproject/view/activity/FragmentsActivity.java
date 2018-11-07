package com.yangy.baseproject.view.activity;

import android.support.v4.app.Fragment;

import com.mvp.base.view.BaseFragmentActivity;

import java.util.ArrayList;

public class FragmentsActivity extends BaseFragmentActivity {
    @Override
    protected ArrayList<Fragment> getFragments() {
        return null;
    }

    @Override
    protected int getContainerViewId() {
        return 0;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void initView() {

    }
}
