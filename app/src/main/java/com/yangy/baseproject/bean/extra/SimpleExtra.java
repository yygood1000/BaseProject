package com.yangy.baseproject.bean.extra;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 一个基础的Activity数据交互类，若有拓展字段，请另行添加
 */
public class SimpleExtra implements Parcelable {
    private boolean simpleBoolean;
    private String simpleStr = null;
    private Integer simpleInt = null;

    public SimpleExtra(boolean simpleBoolean, String simpleStr, Integer simpleInt) {
        this.simpleBoolean = simpleBoolean;
        this.simpleStr = simpleStr;
        this.simpleInt = simpleInt;
    }

    public SimpleExtra(Integer simpleInt) {
        this.simpleInt = simpleInt;
    }

    public SimpleExtra(String simpleStr) {
        this.simpleStr = simpleStr;
    }

    public SimpleExtra(boolean simpleBoolean) {
        this.simpleBoolean = simpleBoolean;
    }

    public boolean isSimpleBoolean() {
        return simpleBoolean;
    }

    public void setSimpleBoolean(boolean simpleBoolean) {
        this.simpleBoolean = simpleBoolean;
    }

    public String getSimpleStr() {
        return simpleStr;
    }

    public void setSimpleStr(String simpleStr) {
        this.simpleStr = simpleStr;
    }

    public Integer getSimpleInt() {
        return simpleInt;
    }

    public void setSimpleInt(Integer simpleInt) {
        this.simpleInt = simpleInt;
    }

    /**
     * 当前对象的内容描述,一般返回0即可
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 将当前对象写入序列化结构中
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.simpleBoolean ? (byte) 1 : (byte) 0);
        dest.writeString(this.simpleStr);
        dest.writeValue(this.simpleInt);
    }

    public SimpleExtra() {
    }

    /**
     * 从序列化后的对象中创建原始对象
     */
    private SimpleExtra(Parcel in) {
        this.simpleBoolean = in.readByte() != 0;
        this.simpleStr = in.readString();
        this.simpleInt = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    /**
     * public static final一个都不能少，内部对象CREATOR的名称也不能改变，必须全部大写。
     * 重写接口中的两个方法：
     * <p>
     * createFromParcel(Parcel in) 实现从Parcel容器中读取传递数据值,封装成Parcelable对象返回逻辑层
     * <p>
     * newArray(int size) 创建一个类型为T，长度为size的数组，供外部类反序列化本类数组使用。
     */
    public static final Creator<SimpleExtra> CREATOR = new Creator<SimpleExtra>() {
        /**
         * 从序列化后的对象中创建原始对象
         */
        @Override
        public SimpleExtra createFromParcel(Parcel source) {
            return new SimpleExtra(source);
        }

        /**
         * 创建指定长度的原始对象数组
         */
        @Override
        public SimpleExtra[] newArray(int size) {
            return new SimpleExtra[size];
        }
    };
}
