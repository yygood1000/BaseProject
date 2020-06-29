package com.yangy.wedget;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.mvp.base.view.MvpFragment;

public class MyFragment extends MvpFragment {
    TextView mTvName;

    private String mName;

    @Override
    protected int getLayoutResId() {
        return R.layout.my_fragment;
    }

    @Override
    protected void initPresenter() {
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString("text");
        }
        mTvName = view.findViewById(R.id.tv_name);
        mTvName.setText(mName);
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
}