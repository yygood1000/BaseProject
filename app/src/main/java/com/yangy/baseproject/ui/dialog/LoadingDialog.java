package com.yangy.baseproject.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.mvp.base.dialog.BaseDialog;
import com.yangy.baseproject.R;

public class LoadingDialog extends BaseDialog {
    public LoadingDialog(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    protected LoadingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }

    private void initView(Context context) {
        View dialogContentView = LayoutInflater.from(context).inflate(R.layout.layout_loading, null);
        setContentView(dialogContentView);
    }
}
