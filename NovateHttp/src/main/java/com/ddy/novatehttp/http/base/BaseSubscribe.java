package com.ddy.novatehttp.http.base;


import android.accounts.NetworkErrorException;
import android.content.Context;
import android.net.ParseException;

import com.alibaba.fastjson.JSONException;
import com.ddy.novatehttp.LLog;
import com.ddy.novatehttp.ProgressDialog;
import com.ddy.novatehttp.entity.BaseEntity;
import com.ddy.novatehttp.http.IException;
import com.google.gson.JsonParseException;


import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * 观察者
 */
public abstract class BaseSubscribe<T> implements Observer<BaseEntity<T>> {
    private Context mContext;
    private int what;

    public BaseSubscribe(int what) {
        this.what = what;
    }

    public BaseSubscribe(int what, Context cxt) {
        this.mContext = cxt;
        this.what = what;
    }


    @Override
    public void onSubscribe(Disposable d) {
        showProgressDialog();
    }

    @Override
    public void onNext(BaseEntity<T> tBaseEntity) {
        closeProgressDialog();
        if (tBaseEntity.isSuccess()) {
            try {
                onSuccess(what, tBaseEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        closeProgressDialog();
        try {
            if (e instanceof HttpException
                    || e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(what, e,"网络异常", true);
            } else if (e instanceof JsonParseException
                    || e instanceof JSONException
                    || e instanceof ParseException) {
                onFailure(what, e,"数据解析错误", true);
            }else if (e instanceof IException) {
                IException ie = (IException) e;
                if (!ie.isIExceptionCode()) {
                    onCodeError(what,ie.getErrorCode(), e.getMessage());
                } else {
                    onFailure(what, e,"自定义异常，其他错误", false);
                }
            } else {
                onFailure(what, e,"未知错误", false);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            LLog.d("onError: " + "catch");
        }
    }

    @Override
    public void onComplete() {

    }

    /**
     * 返回成功
     *
     * @param what 区分接口
     * @param t    异常
     */
    public abstract void onSuccess(int what, BaseEntity<T> t) throws Exception;

    /**
     * 返回成功了,但是code错误
     *
     * @param what         区分接口
     * @param code         错误码
     * @param errorMessage 请求成功 错误码不为0 的消息提示
     */
    public abstract void onCodeError(int what, int code, String errorMessage) throws Exception;

    /**
     * 返回失败
     *
     * @param what           区分接口
     * @param e              异常
     * @param isNetWorkError 是否是网络错误
     */
    public abstract void onFailure(int what, Throwable e,String error, boolean isNetWorkError) throws Exception;


    private void showProgressDialog() {
        ProgressDialog.show(mContext, false, "请稍后");
    }

    private void closeProgressDialog() {
        ProgressDialog.cancle();
    }

}