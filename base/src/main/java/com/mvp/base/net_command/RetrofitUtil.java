package com.mvp.base.net_command;

import android.annotation.SuppressLint;

import com.mvp.base.net.MyHttpLoggingInterceptor;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yangy
 */

public class RetrofitUtil {
    //超时时间
    private static final int DEFAULT_TIMEOUT = 5;
    private static boolean isDebug = true;

    private Retrofit mRetrofit;
    private static RetrofitUtil mInstance = new RetrofitUtil();

    /**
     * 私有构造方法
     */
    private RetrofitUtil() {
        MyHttpLoggingInterceptor logging = new MyHttpLoggingInterceptor();//网络log拦截器
        logging.setLevel(MyHttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        // 设置Log拦截器
        builder.addInterceptor(logging);
        // 添加請求加密攔截器
//        builder.addInterceptor(new MyEncryptionInterceptor());
        // 添加请求头
//        builder.addInterceptor(new MyAddHeadInterceptor());

        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl("base host")// 配置正确的Host地址。不然运行会报错
                .addConverterFactory(GsonConverterFactory.create())// 设置Gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// 设置支持RxJava2
                .build();
    }

    public static RetrofitUtil getInstance() {
        return mInstance;
    }

    public static void setDebug(boolean debug) {
        isDebug = debug;
    }

    private Retrofit getRetrofit() {
        return mRetrofit;
    }

    /**
     * 对ApiService添加接口代理，在代理中处理Session
     *
     * @param tClass 接口类
     * @param <T>    接口类泛型
     * @return 添加代理后的ApiService
     */
    public <T> T getApiService(Class<T> tClass) {
        return getRetrofit().create(tClass);
//        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class<?>[]{tClass}, new ProxyHandler(t));
    }

//    /**
//     * 处理网络请求生成的被观察者
//     *
//     * @param observable 需要处理的被观察者
//     * @param activity   所在页面
//     * @param <T>        data 实体类
//     * @return 处理后的观察者
//     */
//    public <T> Observable<T> getObservable(Observable<BaseResponse<T>> observable, RxAppCompatActivity activity) {
//        return observable
//                .compose(activity.<BaseResponse<T>>bindUntilEvent(ActivityEvent.DESTROY))
//                .compose(RxHelper.<BaseResponse<T>>rxSchedulerHelper())//切换线程
//                .compose(RetrofitUtil.<T>handleResult());    //从BaseResponse中得到具体的实例
//    }
//
//    /**
//     * 处理网络请求生成的被观察者
//     * 不需要绑定activity生命周期的处理
//     *
//     * @param observable 需要处理的被观察者
//     * @param <T>        data 实体类
//     * @return 处理后的被观察者
//     */
//    public <T> Observable<T> getObservable(Observable<BaseResponse<T>> observable) {
//        return observable
//                .compose(RxHelper.<BaseResponse<T>>rxSchedulerHelper())//切换线程
//                .compose(RetrofitUtil.<T>handleResult());    //从BaseResponse中得到具体的实例
//    }
//
//    /**
//     * 处理网络请求生成的被观察者
//     * 生命周期与Fragment绑定
//     *
//     * @param observable 需要处理的被观察者
//     * @param fragment   所在页面
//     * @param <T>        data 实体类
//     * @return 处理后的被观察者
//     */
//    public <T> Observable<T> getObservable(Observable<BaseResponse<T>> observable, RxFragment fragment) {
//        return observable
//                .compose(fragment.<BaseResponse<T>>bindUntilEvent(FragmentEvent.PAUSE))
//                .compose(RxHelper.<BaseResponse<T>>rxSchedulerHelper())//切换线程
//                .compose(RetrofitUtil.<T>handleResult());    //从BaseResponse中得到具体的实例
//    }
//
//    /**
//     * 统一返回结果处理.这里是借口返回的第一步处理位置
//     * 1.返回的data，会在onNext中接收到
//     * 2.抛出ApiException，会在onError中接收到
//     */
//    public static <T> ObservableTransformer<BaseResponse<T>, T> handleResult() {   //compose判断结果
//        return new ObservableTransformer<BaseResponse<T>, T>() {
//            @Override
//            public ObservableSource<T> apply(Observable<BaseResponse<T>> upstream) {
//                if (upstream == null) {
//                    throw new ApiException("空啦", "服务器连接失败");
//                }
//                return upstream.map(new Function<BaseResponse<T>, T>() {
//                    @Override
//                    public T apply(BaseResponse<T> tBaseResponse) throws Exception {
//                        if (tBaseResponse != null) {
//                            // 保存服务器返回的时间
//                            CPersisData.setServersDate(tBaseResponse.getTimeStamp());
//                            if (tBaseResponse.getResult() != null) {
//                                if (tBaseResponse.getResult().getData() != null) { // data
//                                    return tBaseResponse.getResult().getData();
//                                } else if (tBaseResponse.getResult().getError() != null) { // error
//                                    throw new ApiException(tBaseResponse.getResult().getError().getBusiness_code(),
//                                            tBaseResponse.getResult().getError().getBusiness_msg());
//                                } else { // data == null
//                                    throw new ApiException("空啦", tBaseResponse.getSystemMsg());
//                                }
//                            }
//                        }
//                        throw new ApiException("空啦", tBaseResponse.getSystemMsg());
//                    }
//                });
//            }
//        };
//    }


    /**
     * 默认信任所有的证书
     */
    @SuppressLint("TrulyRandom")
    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sslSocketFactory = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
        }
        return sslSocketFactory;
    }

    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }


    public SSLSocketFactory createSSLSocketFactory(InputStream... certificates) {
        SSLSocketFactory sslSocketFactory = null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            sslContext.init(
                    null,
                    trustManagerFactory.getTrustManagers(),
                    new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }
}