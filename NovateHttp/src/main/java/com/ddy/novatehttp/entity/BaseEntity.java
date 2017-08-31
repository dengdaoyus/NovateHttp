package com.ddy.novatehttp.entity;


import com.ddy.novatehttp.http.config.HttpConfig;

/**
 * 基实体类 用于包装其他实体
 * Created by Administrator on 2017/6/22 0022.
 */

public class BaseEntity<T> {
    private int code;
    private String message;
    private T data;

    public boolean isSuccess() {
        return getCode() == HttpConfig.SUCCESS_CODE;
    }

    public int getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}