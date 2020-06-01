package com.mvp.base.net_command;

public interface ObserverOnNextListener<T> {
    void onNext(T t);

    void onError(String errorCode, String msg);
}
