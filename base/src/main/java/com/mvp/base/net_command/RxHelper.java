package com.mvp.base.net_command;

import android.annotation.SuppressLint;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yangy on 2017/8/8.
 * Rxjava中一些
 */

public class RxHelper {

    /**
     * 点击间隔时间
     */
    private static final int INTERVAL_TIME = 2;

    /**
     * RxBidding 点击事件的统一处理
     * 切换订阅者线程为主线程
     * 目前设置2秒不可重复点击
     */
    @SuppressLint("CheckResult")
    public static void rxClick(final View v, RxAppCompatActivity activity, Consumer<View> consumer) {
        RxView.clicks(v)
                .throttleFirst(INTERVAL_TIME, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .map(new Function<Object, View>() {
                    @Override
                    public View apply(Object o) throws Exception {
                        return v;
                    }
                })
                .subscribe(consumer);
    }

    /**
     * IO 线程与 主线程切换
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 通过Rxjava封装一个在io线程运行的事件
     */
    @SuppressLint("CheckResult")
    public static void runIOThread(final OnIOThreadListener onIOThreadListener) {
        Observable
                .create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(ObservableEmitter<Object> e) throws Exception {
                        if (onIOThreadListener != null) {
                            e.onNext(onIOThreadListener.onIOThread());
                            e.onComplete();
                        }
                    }
                })
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        onIOThreadListener.onMainThread(o);
                    }
                });
    }

    public interface OnIOThreadListener {
        Object onIOThread();

        void onMainThread(Object o);
    }


}
