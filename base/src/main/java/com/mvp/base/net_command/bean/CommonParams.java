package com.mvp.base.net_command.bean;

import utils.SystemUtils;
import utils.Utils;

/**
 * 公共请求参数基类
 * Created by yangy
 */

public class CommonParams<T> {
    private String destination;// 接口地址
    private String imei = SystemUtils.getDeviceID(Utils.getApp());
    private String phone_model = android.os.Build.MODEL;//手机型号
    private String time_stamp = String.valueOf(System.currentTimeMillis());
    private String system_version = "Android" + android.os.Build.VERSION.RELEASE;//android系统版本
    private T parameter;

    public CommonParams() {
    }

    public T getParameter() {
        return parameter;
    }

    public void setParameter(T parameter) {
        this.parameter = parameter;
    }

    public CommonParams(String des) {
        this.destination = des;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getPhoneModel() {
        return phone_model;
    }

    public void setPhoneModel(String phone_model) {
        this.phone_model = phone_model;
    }

    public String getTimeStamp() {
        return time_stamp;
    }

    public void setTimeStamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getSystemVersion() {
        return system_version;
    }

    public void setSystemVersion(String system_version) {
        this.system_version = system_version;
    }


    @Override
    public String toString() {
        return "CommonParams{" +
                "destination='" + destination + '\'' +
                ", imei='" + imei + '\'' +
                ", phone_model='" + phone_model + '\'' +
                ", time_stamp='" + time_stamp + '\'' +
                ", system_version='" + system_version + '\'' +
                ", parameter=" + parameter +
                '}';
    }
}
