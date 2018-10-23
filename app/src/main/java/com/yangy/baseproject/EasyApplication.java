package com.yangy.baseproject;

import android.app.Application;

import utils.Utils;


/**
 * Created by yangy
 */
public class EasyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
