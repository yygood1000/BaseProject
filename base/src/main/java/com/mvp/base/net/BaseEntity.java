package com.mvp.base.net;

import java.io.Serializable;

public class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = -3440061414071692254L;

    private String code;

    private String message;

//    @SerializedName("obj")
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "code=" + code +
                ", message='" + message + '\'' +

                ", data=" + data +
                '}';
    }
}
