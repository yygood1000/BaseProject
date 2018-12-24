package com.yy.recoder;

import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceView;

import com.mvp.base.view.MvpActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class RecorderActivity extends MvpActivity {
    private Camera camera;
    @BindView(R.id.surface_view)
    SurfaceView mSurfaceView;

    @Override
    protected void initPresenter() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_recorder;
    }

    @Override
    protected void initView() {
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    @OnClick(R.id.main_press_control)
    public void onViewClicked() {
        releaseCamera();
        camera = Camera.open(0);
        try {
            camera.setPreviewDisplay(mSurfaceView.getHolder());
            camera.startPreview();
        } catch (IOException e) {
            Log.e(TAG, "Error while trying to display the camera preview", e);
        }
    }
}
