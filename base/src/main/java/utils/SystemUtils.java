package utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;


import java.util.List;
import java.util.UUID;

//import com.umeng.analytics.MobclickAgent;

public class SystemUtils {
    /**
     * killing app from process level
     * 调用android.os.Process.killProcess(android.os.Process.myPid());和System.exit(0);
     * 系统判定程序异常关闭，会尝试重新启动
     * 如果退出进程前先关闭所有的activity
     * 这样系统就不会重新启动入口activity
     * 不会出现多次报错的问题
     */
    public static void killProcess(Context context) {
        finishAllActivities();

        android.os.Process.killProcess(android.os.Process.myPid());

        System.exit(0);
    }

    /**
     * Finish all of activities.
     */
    public static void finishAllActivities() {
        List<Activity> activityList = Utils.getActivityList();
        for (int i = activityList.size() - 1; i >= 0; --i) {// remove from top
            Activity activity = activityList.get(i);
            // sActivityList remove the index activity at onActivityDestroyed
            activity.finish();
        }
        activityList.clear();
    }

    public static void killProcess() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getIMSI() {
        TelephonyManager telephonyManager = (TelephonyManager) Utils.getApp().getApplicationContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSubscriberId();
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getDeviceID(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = tm.getDeviceId() + "";
        tmSerial = tm.getSimSerialNumber() + "";
        androidId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider
                .Settings.Secure.ANDROID_ID) + "";

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        return deviceUuid.toString();
    }

    /**
     * 获取APP 版本号
     */
    public static int getVersionCode(Context context) {
        PackageInfo packInfo = getPackageInfo(context);
        if (packInfo != null) {
            return packInfo.versionCode;
        } else {
            return -1;
        }
    }

    /**
     * 获取APP 版本名称
     */
    public static String getVersionName(Context context) {
        PackageInfo packInfo = getPackageInfo(context);
        if (packInfo != null) {
            return packInfo.versionName;
        } else {
            return "未知";
        }
    }

    /**
     * 获取包信息
     */
    private static PackageInfo getPackageInfo(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断app是否已安装
     */
    public static boolean isPackageExisted(String targetPackage) {
        PackageManager pm = Utils.getApp().getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    private static int sExtraNameIndex = 10086;

    public static String nextExtraName() {
        return "sExtraNameIndex_ext_" + (++sExtraNameIndex);
    }
}
