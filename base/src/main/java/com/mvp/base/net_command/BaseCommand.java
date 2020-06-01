package com.mvp.base.net_command;

import com.mvp.base.net_command.bean.BaseResponse;
import com.mvp.base.net_command.bean.CommonParams;
import com.mvp.base.view.MvpActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * creator: yangy
 * describe: 请求的基类
 */

public abstract class BaseCommand<T> {
    public T mApiService;
    private MvpActivity mActivity;

    /**
     * @param c Api 接口
     */
    public BaseCommand(Class<T> c) {
        initApi(c);
    }

    public BaseCommand(Class<T> c, MvpActivity mActivity) {
        initApi(c);
        this.mActivity = mActivity;
    }

    /**
     * 初始化api相关操作
     */
    private void initApi(Class<T> c) {
        // 对ApiService添加接口代理，在代理中处理Session
        mApiService = RetrofitUtil.getInstance().getApiService(c);
    }


    /*========================================对外提供的方法========================================*/


    /**
     * 处理Retrofit返回的Observeble
     *
     * @param observable 接口返回的被观察者
     * @param listener   最终的回调处理对象，只回调onResult
     * @param <V>        返回体中data实体类
     */
    public <V> void handleOnResultObserver(Observable<BaseResponse<V>> observable,
                                           ObserverOnResultListener<V> listener) {
        processObservable(observable, mActivity).subscribe(new BackgroundObserver<>(listener));
    }

    /**
     * 获取请求体
     *
     * @param destination 目标请求
     * @param params      请求参数
     * @param <C>         参数泛型
     * @return 返回需要的请求参数对象
     */
    public <C> CommonParams<C> getParams(String destination, C params) {
        CommonParams<C> commonParams = new CommonParams<>(destination);
        commonParams.setParameter(params);
        return commonParams;
    }

    /**
     * 获取请求体
     * 只需要destination的接口
     *
     * @param destination 目标请求
     * @param <C>         参数泛型
     * @return 返回需要的请求参数对象
     */
    public <C> CommonParams<C> getParams(String destination) {
        return new CommonParams<>(destination);
    }

    /**
     * 处理网络请求生成的被观察者
     *
     * @param observable 需要处理的被观察者
     * @param <R>        result 实体类
     * @return 处理后的被观察者
     */
    private <R> Observable<R> processObservable(Observable<BaseResponse<R>> observable, RxAppCompatActivity activity) {
        return observable
                .compose(activity.<BaseResponse<R>>bindUntilEvent(ActivityEvent.DESTROY))// 绑定生命周期，避免内存泄漏
                .compose(RxHelper.<BaseResponse<R>>rxSchedulerHelper())// 切换线程
                .compose(this.<R>handleResult());    // 从BaseResponse中得到具体的实例,返回一个泛型为result 实体类的Observable
    }

    /**
     * 统一返回结果处理.这里是借口返回的第一步处理位置
     * 1.返回的data，会在onNext中接收到
     * 2.抛出ApiException，会在onError中接收到
     */
    public <R> ObservableTransformer<BaseResponse<R>, R> handleResult() {   //compose判断结果
        return new ObservableTransformer<BaseResponse<R>, R>() {
            @Override
            public ObservableSource<R> apply(Observable<BaseResponse<R>> observable) {
                return observable.map(new Function<BaseResponse<R>, R>() {
                    @Override
                    public R apply(BaseResponse<R> tBaseResponse) throws Exception {
                        return tBaseResponse.getResult().getData();
                    }
                });
            }
        };
    }

}
