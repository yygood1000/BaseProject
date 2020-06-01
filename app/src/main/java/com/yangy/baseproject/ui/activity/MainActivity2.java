package com.yangy.baseproject.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.mvp.base.view.MvpActivity;
import com.yangy.baseproject.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import utils.Logger;

/**
 * 嵌套滑动
 */
public class MainActivity2 extends MvpActivity {
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void initPresenter() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main2;
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new NormalRecyclerViewAdapter(this, getData()));
    }

    private ArrayList<String> getData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 0; i++) {
            list.add("第" + i + "项");
        }
        return list;
    }
}
