package com.yangy.baseproject.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;

import java.lang.reflect.Method;

import utils.Logger;

public class BaseWebView extends WebView {
    private Context mContext;
    private WebsiteChangeListener mWebsiteChangeListener;


    public BaseWebView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public BaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // AppRTC requires third party cookies to work
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true);
        }
        setScrollBarStyle(ScrollView.SCROLLBARS_INSIDE_OVERLAY);
        // 移除系统自带的JS接口
        removeJavascriptInterface("searchBoxJavaBridge_");
        removeJavascriptInterface("accessibility");
        removeJavascriptInterface("accessibilityTraversal");

        setHorizontalScrollBarEnabled(false);
        WebSettings mWebSettings = getSettings();

        mWebSettings.setBuiltInZoomControls(false);
        mWebSettings.setSupportZoom(false);
        mWebSettings.setSaveFormData(false);
        mWebSettings.setSavePassword(false);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setLoadsImagesAutomatically(true);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportMultipleWindows(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setGeolocationEnabled(true);

        mWebSettings.setBlockNetworkImage(false); // 解决图片不显示

        // 启动应用缓存
        mWebSettings.setAppCacheEnabled(true);
        // 设置缓存模式
        mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebSettings.setPluginState(WebSettings.PluginState.ON);
        Class<?> clazz = mWebSettings.getClass();
        Method method;
        try {
            method = clazz.getMethod("setAllowUniversalAccessFromFileURLs", boolean.class);
            if (method != null) {
                method.invoke(mWebSettings, true);
            }
        } catch (Exception e) {
            Logger.e("WebView Error", "method invoke error.", e);
        }
        try {
            mWebSettings.setMediaPlaybackRequiresUserGesture(false);
        } catch (Exception e) {
            Logger.e("WebView Error", "setMediaPlaybackRequiresUserGesture invoke error.", e);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        setWebChromeClient(webChromeClient);
        setWebViewClient(webViewClient);
    }

    @Override
    protected void onSizeChanged(int w, int h, int ow, int oh) {
        super.onSizeChanged(w, h, ow, oh);
        Logger.d("详情页的高度:--->" + h);
    }

    /**
     * 多窗口的问题
     */
    private void newWin(WebSettings mWebSettings) {
        //html中的_bank标签就是新建窗口打开，有时会打不开，需要加以下
        //然后 复写 WebChromeClient的onCreateWindow方法
        mWebSettings.setSupportMultipleWindows(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    }

    /**
     * HTML5数据存储
     */
    private void saveData(WebSettings mWebSettings) {
        //有时候网页需要自己保存一些关键数据,Android WebView 需要自己设置
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setAppCacheEnabled(true);
        String appCachePath = mContext.getCacheDir().getAbsolutePath();
        mWebSettings.setAppCachePath(appCachePath);
    }

    WebChromeClient webChromeClient = new WebChromeClient() {

        //=========HTML5定位==========================================================
        //需要先加入权限
        //<uses-permission android:name="android.permission.INTERNET"/>
        //<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
        //<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }

        @Override
        public void onGeolocationPermissionsHidePrompt() {
            super.onGeolocationPermissionsHidePrompt();
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(final String origin,
                                                       final GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);//注意个函数，第二个参数就是是否同意定位权限，第三个是是否希望内核记住
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
        //=========HTML5定位==========================================================


        //=========多窗口的问题==========================================================
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            HitTestResult result = view.getHitTestResult();
            String data = result.getExtra();
            view.loadUrl(data);
            return true;
        }
        //=========多窗口的问题==========================================================

        //=========顶部进度条的进度更新===============================
        @Override
        public void onProgressChanged(WebView v, int newProgress) {
            super.onProgressChanged(v, newProgress);
            if (mWebsiteChangeListener != null) {
                mWebsiteChangeListener.onProgress(newProgress);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (mWebsiteChangeListener != null) {
                mWebsiteChangeListener.onWebsiteChange(title);
            }
        }

        /**
         * 播放网络视频时全屏会被调用的方法
         */
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            if (mWebsiteChangeListener != null) {
                mWebsiteChangeListener.openFullScreenVideo(view, callback);
            }
        }

        @Override
        public void onHideCustomView() {
            if (mWebsiteChangeListener != null) {
                mWebsiteChangeListener.closeFullScreenVideo();
            }
        }
    };

    WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onReceivedError(WebView v, int errorCode, String description, String failingUrl) {
            super.onReceivedError(v, errorCode, description, failingUrl);
            if (mWebsiteChangeListener != null) {
                mWebsiteChangeListener.showProgressBar(false);
            }
        }

        @Override
        public void onPageStarted(WebView view, final String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (mWebsiteChangeListener != null) {
                mWebsiteChangeListener.showProgressBar(true);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (mWebsiteChangeListener != null) {
                if (mWebsiteChangeListener.isOpenActivity(url)) {
                    return true;
                }
            }
            if (!url.startsWith("http://") && !url.startsWith("https://")
                    && !url.startsWith("file:///")) {
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mWebsiteChangeListener != null) {
                        mWebsiteChangeListener.onPageFinished();
                    }
                }
            }, 500);
        }
    };

    public interface WebsiteChangeListener {
        void onWebsiteChange(String title);

        void onProgress(int progress);

        void showProgressBar(boolean visible);

        void openFullScreenVideo(View view, WebChromeClient.CustomViewCallback callback);

        void closeFullScreenVideo();

        boolean isOpenActivity(String url);

        void onPageFinished();
    }

    public void setWebsiteChangeListener(WebsiteChangeListener websiteChangeListener) {
        this.mWebsiteChangeListener = websiteChangeListener;

    }
}