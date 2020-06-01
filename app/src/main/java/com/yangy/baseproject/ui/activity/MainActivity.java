package com.yangy.baseproject.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mvp.base.view.MvpActivity;
import com.yangy.baseproject.R;
import com.yangy.baseproject.bean.extra.SecondActivityExtra;
import com.yangy.baseproject.bean.extra.SimpleExtra;
import com.yangy.baseproject.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.OnClick;
import utils.ActivityUtils;
import utils.Logger;

public class MainActivity extends MvpActivity<MainPresenter> implements MainView {
    @BindView(R.id.btn_jump)
    Button mBtnJump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleDeepLink(getIntent());
        // ATTENTION: This was auto-generated to handle app links.

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleDeepLink(intent);
    }

    private void handleDeepLink(Intent intent) {
        String appLinkAction = intent.getAction();
        //接受数据
        if (null != intent.getData()) {
            Uri uri = intent.getData();
            Log.d("qw", uri.toString());
            String pageTarget = uri.getQueryParameter("page");
            String pageText = uri.getQueryParameter("text");
            if (TextUtils.isEmpty(pageTarget))
                pageTarget = "";
            if (TextUtils.isEmpty(pageText))
                pageText = "";

            if (pageTarget.equals("2")) {
                ActivityUtils.turnToActivity(this, PageActivity.class);
            }
        }
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MainPresenter(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
    }

    @Override
    public void changeTextContext() {
        mBtnJump.setText("修改了文本");
    }

    @OnClick({R.id.btn_jump, R.id.btn_fragment_activity,R.id.btn_matrix_activity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_jump:
                mPresenter.click();
                ActivityUtils.turnToActivityResult(this, SecondActivity.class, 1,
                        new SecondActivityExtra("TAG_YY", new SimpleExtra(1000)));
                break;
            case R.id.btn_fragment_activity:
                ActivityUtils.turnToActivity(this, PageActivity.class);
                break;
            case R.id.btn_matrix_activity:
                ActivityUtils.turnToActivity(this, MatrixImageActivity.class);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mBtnJump.setText("返回该页面了");
        }
    }
}
