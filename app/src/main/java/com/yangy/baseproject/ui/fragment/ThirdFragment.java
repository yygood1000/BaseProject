package com.yangy.baseproject.ui.fragment;

import android.view.View;

import com.mvp.base.view.MvpFragment;
import com.yangy.baseproject.R;
import com.yangy.baseproject.ui.dialog.LoadingDialog;

import butterknife.OnClick;

public class ThirdFragment extends MvpFragment {
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_third;
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

    @OnClick(R.id.tv)
    public void onViewClicked() {
        LoadingDialog dialog = new LoadingDialog(mActivity,R.style.Dialog_Tran);
        dialog.show();

    }
}
