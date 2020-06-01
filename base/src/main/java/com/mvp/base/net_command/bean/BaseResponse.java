package com.mvp.base.net_command.bean;


import java.io.Serializable;

/**
 * 服务端返回的最外层数据基类
 * Created by tsj004 on 2017/7/31.
 *
 * @param <T> Result的
 */

public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 3455624598684006501L;

    private String time_stamp;
    private String session_id;
    private String system_code;
    private String system_msg;
    private Result<T> result;

    public long getTimeStamp() {
        return Long.parseLong(time_stamp);
    }

    public void setTimeStamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getSystemCode() {
        return system_code;
    }

    public void setSystemCode(String system_code) {
        this.system_code = system_code;
    }

    public String getSystemMsg() {
        return system_msg;
    }

    public void setSystemMsg(String system_msg) {
        this.system_msg = system_msg;
    }

    public String getSessionId() {
        return session_id;
    }

    public void setSessionId(String session_id) {
        this.session_id = session_id;
    }

    public long getLongDate() {
        return Long.parseLong(time_stamp);
    }

    public Result<T> getResult() {
        return result;
    }

    public void setResult(Result<T> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "time_stamp='" + time_stamp + '\'' +
                ", session_id='" + session_id + '\'' +
                ", system_code='" + system_code + '\'' +
                ", system_msg='" + system_msg + '\'' +
                ", result=" + result +
                '}';
    }
}
