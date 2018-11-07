package com.yangy.baseproject.view.activity;

import android.app.ProgressDialog;

import com.mvp.base.view.MvpActivity;
import com.yangy.baseproject.R;
import com.yangy.baseproject.bean.extra.SecondActivityExtra;

import butterknife.OnClick;
import utils.ActivityUtils;

public class SecondActivity extends MvpActivity {

    @Override
    protected void initPresenter() {
        ProgressDialog progressDialog = new ProgressDialog(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_second;
    }

    @Override
    protected void initView() {
        SecondActivityExtra extra = ActivityUtils.getParcelable(getIntent());
        showToast(extra.getTAG() + "flag == " + extra.getSimpleExtra().getSimpleInt());
    }


    @OnClick(R.id.tv_back)
    public void onViewClicked() {
        ActivityUtils.setResultAndFinish(this, RESULT_OK);
    }
}
