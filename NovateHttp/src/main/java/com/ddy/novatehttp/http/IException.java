package com.ddy.novatehttp.http;


import com.ddy.novatehttp.LLog;
import com.ddy.novatehttp.http.config.HttpConfig;

/**
 * 返回成功了 code 异常
 * Created by Administrator on 2017/6/23 0023.
 */

public class IException extends RuntimeException {
    private int mErrorCode;




    public IException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.mErrorCode = errorCode;
        LLog.d("返回成功了,但是code不等于1,错误码：:"+mErrorCode+"   错误信息："+getMessage());
    }

    /**
     * 判断是否是token失效
     *
     * @return 失效返回true, 否则返回false;
     */
    public boolean isIExceptionCode() {
        return mErrorCode == HttpConfig.SUCCESS_CODE;
    }

    public int getErrorCode() {
        return mErrorCode;
    }
}