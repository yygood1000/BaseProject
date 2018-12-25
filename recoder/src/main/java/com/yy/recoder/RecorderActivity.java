package com.yy.recoder;

import android.hardware.Camera;
import android.os.Environment;
import android.view.SurfaceView;

import com.mvp.base.view.MvpActivity;

import java.util.UUID;

import butterknife.BindView;

public class RecorderActivity extends MvpActivity {
    private Camera camera;
    @BindView(R.id.surface_view)
    SurfaceView mSurfaceView;

    private MediaUtils mediaUtils;

    @Override
    protected void initPresenter() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_recorder;
    }

    @Override
    protected void initView() {
        // setting
        mediaUtils = new MediaUtils(this);
        mediaUtils.setRecorderType(MediaUtils.MEDIA_VIDEO);
        mediaUtils.setTargetDir(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES));
        mediaUtils.setTargetName(UUID.randomUUID() + ".mp4");
        mediaUtils.setSurfaceView(mSurfaceView);
    }

    // 建议的实现预览
//    private void releaseCamera() {
//        if (camera != null) {
//            camera.release();
//            camera = null;
//        }
//    }
//
//    @OnClick(R.id.main_press_control)
//    public void onViewClicked() {
//        releaseCamera();
//        camera = Camera.open(0);
//        try {
//            camera.setPreviewDisplay(mSurfaceView.getHolder());
//            camera.startPreview();
//        } catch (IOException e) {
//            Log.e(TAG, "Error while trying to display the camera preview", e);
//        }
//    }
}
