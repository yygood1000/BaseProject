package com.yangy.baseproject.net;

import android.text.TextUtils;

import com.yangy.baseproject.BuildConfig;

import io.reactivex.observers.DefaultObserver;
import utils.Logger;
import utils.Toaster;

/**
 * 网络请求返回处理的基类
 *
 * @param <T> 请求实体类
 */
public abstract class BaseObserver<T> extends DefaultObserver<BaseEntity<T>> {
    @Override
    public void onNext(BaseEntity<T> response) {
        if (response != null && !TextUtils.isEmpty(response.getCode())) {
            // 对code进行对应处理
            onSuccess(response.getData());
        } else {
            Toaster.showShort("解析成空的啦");
        }
    }

    @Override
    public void onError(Throwable e) {
        Logger.e("oye", "onError = " + e.getMessage());
        onFail(new FailEntity(0, BuildConfig.DEBUG ? "接口报错了" : "内部服务错误"));
        onComplete();
    }

    @Override
    public void onComplete() {
    }

    public abstract void onSuccess(T t);

    /**
     * 错误回调
     *
     * @param entity 错误参数 与 错误消息
     * @return 底层是否自定义处理了错误信息
     * true ：视为已处理过，此处无需再次处理。（某些接口失败不需要提示也是返回true，可理解为业务层处理为不需要提示）
     * false：视为未处理，进行公共处理,可理解为默认返回false（当前公共处理是进行错误信息的toast提示）
     */
    public abstract boolean onFail(FailEntity entity);
}
