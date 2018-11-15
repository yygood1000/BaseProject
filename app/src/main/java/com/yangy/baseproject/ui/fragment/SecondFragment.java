package com.yangy.baseproject.ui.fragment;

import android.app.AlertDialog;
import android.view.View;

import com.mvp.base.view.MvpFragment;
import com.yangy.baseproject.R;

import butterknife.OnClick;

public class SecondFragment extends MvpFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_second;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView(View v) {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void finish() {

    }

    @OnClick(R.id.click)
    public void onViewClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("我是AlertDialog")
                .setMessage("Dialog内容")
                .setIcon(R.mipmap.ic_launcher)
                .create()
                .show();
    }
}
