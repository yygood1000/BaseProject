package com.yangy.baseproject.net;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.NetworkUtils;

public class RetrofitAgent {
    private static Retrofit retrofit;
    public static String baseUrl;

    private RetrofitAgent() {
    }

    public static <T> T create(Class<T> service) {
        if (retrofit == null) {
            baseUrl = "http://47.94.211.154:8099";
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

//            Cache cache = new Cache(new File(ToolBox.getCacheStorageDirectory(VYApplication.getInstance()),
//                    "HttpCache"),
//                    1024 * 1024 * 50);

            OkHttpClient.Builder build = new OkHttpClient.Builder()
                    .addInterceptor(createHeaderInterceptor())
                    .addInterceptor(loggingInterceptor)
//                    .cache(cache)
//                    .addInterceptor(cacheControlInterceptor)
                    .connectTimeout(7, TimeUnit.SECONDS)
                    .writeTimeout(8, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS);
            OkHttpClient httpClient = build.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return retrofit.create(service);
    }


    /**
     * 请求头拦截器
     */
    private static Interceptor createHeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("Content-Type", "application/json");
//                builder.addHeader("X-LC-Session", AccountManager.getSessionToken());
//                builder.addHeader("User-Agent", VYApplication.userAgent);
                return chain.proceed(builder.build());
            }
        };
    }


    private static final Interceptor cacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtils.isConnected()) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }

            Response originalResponse = chain.proceed(request);
            if (NetworkUtils.isConnected()) {
                // 有网络时 设置缓存为默认值
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma") // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build();
            } else {
                // 无网络时 设置超时为1周
                int maxStale = 60 * 60 * 24 * 7;
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

}
