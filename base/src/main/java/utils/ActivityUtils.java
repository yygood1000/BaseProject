package utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;

/**
 * 传递Parcelable序列表对象，快速生成Parcelable代码插件：Android Parcelable code generator
 * 手动下载插件网址：https://github.com/mcharmas/android-parcelable-intellij-plugin
 */
public class ActivityUtils {
    public static final String OPEN_ACTIVITY_KEY = "open_activity";//intent跳转传递传输key
    public static final String RESULT_KEY = "result_activity";//intent跳转传递传输key

    /**
     * 跳转至下个页面
     */
    public static void turnToActivity(Activity activity, Class<? extends Activity> className) {
        turnToActivity(activity, className, null);
    }

    /**
     * 跳转至下个页面并传参
     *
     * @param parcelable 可序列化的实体类
     */
    public static void turnToActivity(Activity activity, Class<? extends Activity> className, Parcelable
            parcelable) {
        Intent intent = new Intent(activity, className);
        putParcelable(intent, parcelable);
        activity.startActivity(intent);
    }

    /**
     * 跳转至下个页面ForResult
     */
    public static void turnToActivityResult(Activity activity, Class<? extends Activity> className, int requestCode) {
        turnToActivityResult(activity, className, requestCode, null);
    }

    /**
     * 跳转至下个页面并传参ForResult
     */
    public static void turnToActivityResult(Activity activity, Class<? extends Activity> className, int
            requestCode, Parcelable parcelable) {
        Intent intent = new Intent(activity, className);
        putParcelable(intent, parcelable);
        activity.startActivityForResult(intent, requestCode);
        activity = null;
    }

    /**
     * 设置返回数据 ，并销毁
     */
    public static void setResultAndFinish(Activity activity, int resultCode, Parcelable parcelable) {
        Intent intent = new Intent();
        putParcelableResult(intent, parcelable);
        activity.setResult(resultCode, intent);
        activity.finish();
        activity = null;
    }

    /**
     * 设置返回数据 ，并销毁
     */
    public static void setResultAndFinish(Activity activity, int resultCode) {
        setResultAndFinish(activity, resultCode, null);
    }

    private static void putParcelable(Intent intent, Parcelable parcelable) {
        if (parcelable == null) return;
        intent.putExtra(OPEN_ACTIVITY_KEY, parcelable);
    }

    private static void putParcelableResult(Intent intent, Parcelable parcelable) {
        if (parcelable == null) return;
        intent.putExtra(RESULT_KEY, parcelable);
    }

    /**
     * 获取上一个界面传递过来的参数
     *
     * @param <T> 泛型
     */
    public static <T> T getParcelable(Intent intent) {
        Parcelable parcelable = intent.getParcelableExtra(OPEN_ACTIVITY_KEY);
        return (T) parcelable;
    }

    /**
     * 获取上一个界面返回过来的参数
     *
     * @param <T> 泛型
     */
    public static <T> T getParcelableResult(Intent intent) {
        Parcelable parcelable = intent.getParcelableExtra(RESULT_KEY);
        return (T) parcelable;
    }

}
