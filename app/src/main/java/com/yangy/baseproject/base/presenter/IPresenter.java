package com.yangy.baseproject.base.presenter;

/**
 * Created by yy on 2016/11/16.
 * <p>
 * Presenter 基础接口
 */

public interface IPresenter {
    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onCreate();

}
