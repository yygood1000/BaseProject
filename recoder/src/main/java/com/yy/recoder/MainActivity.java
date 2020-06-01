package com.yy.recoder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;


public class MainActivity extends AppCompatActivity {

    private PermissionDialog dialog;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_recoder)
    public void onViewClicked() {
        checkPermission();
    }

    @SuppressLint("CheckResult")
    private void checkPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        disposable = rxPermissions
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .subscribe(granted -> {
                    if (granted) {
                        startActivity(new Intent(MainActivity.this, RecorderActivity.class));
                    } else {
                        showDialog();
                    }
                });
    }

    public void showDialog() {
        if (dialog == null) dialog = new PermissionDialog(this);
        dialog.init();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroy();

    }
}
