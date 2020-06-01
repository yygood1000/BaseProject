package com.mvp.base.net_command;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 网络请求的观察者
 * 此Observer为 业务异常 的统一处理。
 */

public class BackgroundObserver<T> implements Observer<T> {
    private ObserverOnNextListener<T> mListener; // 监听请求


    public BackgroundObserver(ObserverOnResultListener<T> listener) {
        this.mListener = getListener(listener);
    }

    public BackgroundObserver(ObserverOnNextListener<T> listener) {
        this.mListener = getListener(listener);
    }

    /**
     * 当错误提示为默认提示时使用getListener
     * OnResult
     *
     * @param listener 监听
     */
    public ObserverOnNextListener<T> getListener(final ObserverOnResultListener<T> listener) {
        return new ObserverOnNextListener<T>() {
            @Override
            public void onNext(T t) {
                listener.onResult(t);
            }

            @Override
            public void onError(String errorCode, String msg) {
            }
        };
    }

    /**
     * 对网络请求做同一弹窗处理
     * OnNext
     *
     * @param listener
     * @return
     */
    public ObserverOnNextListener<T> getListener(final ObserverOnNextListener<T> listener) {
        return new ObserverOnNextListener<T>() {
            @Override
            public void onNext(T t) {
                listener.onNext(t);
            }

            @Override
            public void onError(String errorCode, String msg) {
                listener.onError(errorCode, msg);
            }
        };
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    /**
     * 对网络请求底层抛出的一些异常做统一处理（SocketTimeoutException，ConnectException）
     * 对自定义异常做特殊处理（ApiException）
     * 对异常进行下层分发（mListener.onError()）
     */
    @Override
    public void onError(Throwable e) {
//        if (e instanceof SocketTimeoutException || e instanceof ConnectException || e instanceof UnknownHostException) {
//            mListener.onError("Connect Fail", e.getMessage());
//        } else if (e instanceof HttpException) {
//            mListener.onError(ErrorConstant.APP_ERROR_HTTPEXCEPTION, ResourceUtils.getString(R.string
//                    .request_service_error));
//        } else {
//            if (StringUtils.isNotBlank(e.getMessage())) {
//                Toaster.showLongToast(e.getMessage());
//            }
//            mListener.onError(ErrorConstant.APP_ERROR_FAIL, e.getMessage());
//            e.printStackTrace();
//        }
        if (mListener != null) {
            mListener.onError("ERROR_CODE", e.getMessage());
        }
    }

    @Override
    public void onNext(T t) {
        if (mListener != null) {
            mListener.onNext(t);
        }
    }
}
