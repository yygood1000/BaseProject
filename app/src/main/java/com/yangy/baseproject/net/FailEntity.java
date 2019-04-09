package com.yangy.baseproject.net;

/**
 * created by bravin on 2018/6/13.
 */
public class FailEntity {
    private int code;
    private String message;

    public FailEntity() {
    }

    public FailEntity(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
        return "Error {" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
