package com.yangy.baseproject.utils;

/**
 * Created by yy on 2017/9/16.
 * <p>
 * 内存工具类
 */

public class MemoryUtils {

    public static double getMemoryPercent( ) {
//        ActivityManager activityManager = (ActivityManager) Utils.getApp().getSystemService(ACTIVITY_SERVICE);

        //最大分配内存
//        int memory = activityManager.getMemoryClass();

        //最大分配内存获取方法2
        float maxMemory = (float) (Runtime.getRuntime().maxMemory() * 1.0 / (1024 * 1024));
        //当前分配的总内存
        double totalMemory = (float) (Runtime.getRuntime().totalMemory() * 1.0 / (1024 * 1024));
        //剩余内存
        double freeMemory = (float) (Runtime.getRuntime().freeMemory() * 1.0 / (1024 * 1024));

        return freeMemory / totalMemory;
//        NumberFormat nt = NumberFormat.getPercentInstance();
//        nt.setMinimumFractionDigits(2);
//        nt.format(percent);

//        System.out.println("totalMemory: " + totalMemory);
//        System.out.println("freeMemory: " + freeMemory);
//        System.out.println("present: " + percent);
//        System.out.println("maxMemory: " + maxMemory);
    }

}
