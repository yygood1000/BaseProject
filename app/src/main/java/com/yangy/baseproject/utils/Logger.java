package com.yangy.baseproject.utils;

import android.util.Log;


public class Logger {

    public static boolean sDebug = true;

    public static String DEFAULT_TAG = Utils.getApp().getPackageName();

    //log的最大长度
    private static final int MAX_LENGTH = 3000;


    //这里对log的操作只使用一个静态String,避免多次申请内存空间，造成内存泄漏
    private static String showMsg = "";

    public static void setDebug(boolean debug) {
        sDebug = debug;
    }

    public static void v(String tag, String msg) {
        if (sDebug) {
            Log.v(tag, msg);
        }
    }

    public static void v(String msg) {
        v(DEFAULT_TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (sDebug) {
            showLogCompletion(tag, msg);
        }
    }

    /**
     * 解决msg太长，log无法完全打印的问题
     * 采用分段打印,大于最大长度则分段
     */
    public static void showLogCompletion(String tag, String msg) {
        if (msg.length() > MAX_LENGTH) {
            showMsg = msg.substring(0, MAX_LENGTH);
            Log.i(tag, showMsg + "");
            if ((msg.length() - MAX_LENGTH) > MAX_LENGTH) {//剩下的文本还是大于规定长度
                showMsg = msg.substring(MAX_LENGTH, msg.length());
                showLogCompletion(tag, showMsg);
            } else {
                showMsg = msg.substring(MAX_LENGTH, msg.length());
                Log.i(tag, showMsg + "");
            }
        } else {
            Log.i(tag, msg + "");
        }
        showMsg = null;
    }

    public static void i(String msg) {
        i(DEFAULT_TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (sDebug) {
            Log.d(tag, msg);
        }
    }

    public static void d(String msg) {
        d(DEFAULT_TAG, msg);
    }

    public static void d(String tag, boolean b) {
        d(tag, String.valueOf(b));
    }

    public static void d(String tag, char c) {
        d(tag, String.valueOf(c));
    }

    public static void d(String tag, char[] data) {
        d(tag, String.valueOf(data));
    }

    public static void d(String tag, char[] data, int offset, int count) {
        d(tag, String.valueOf(data, offset, count));
    }

    public static void d(String tag, double d) {
        d(tag, String.valueOf(d));
    }

    public static void d(String tag, float f) {
        d(tag, String.valueOf(f));
    }

    public static void d(String tag, int i) {
        d(tag, String.valueOf(i));
    }

    public static void d(String tag, long l) {
        d(tag, String.valueOf(l));
    }

    public static void d(String tag, Object obj) {
        d(tag, String.valueOf(obj));
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (sDebug) {
            Log.d(tag, msg, tr);
        }
    }

    public static void d(String msg, Throwable tr) {
        d(DEFAULT_TAG, msg, tr);
    }

    public static void w(String tag, String msg) {
        if (sDebug) {
            Log.w(tag, msg);
        }
    }

    public static void w(String msg) {
        w(DEFAULT_TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (sDebug) {
            Log.e(tag, msg);
        }
    }

    public static void e(String msg) {
        e(DEFAULT_TAG, msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (sDebug) {
            Log.e(tag, msg, tr);
        }
    }

    public static void e(String msg, Throwable tr) {
        e(DEFAULT_TAG, msg, tr);
    }

}
