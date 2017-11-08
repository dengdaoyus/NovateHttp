package com.ddy.novatehttp.novatehttpdemo.http;

import com.alibaba.fastjson.JSONObject;
import com.ddy.novatehttp.entity.BaseEntity;
import com.ddy.novatehttp.novatehttpdemo.bean.RegisterBean;
import com.ddy.novatehttp.novatehttpdemo.bean.StringBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public interface ServiceApi {

    /**
     * 获取验证码
     *
     * @param object
     * @return
     */
    @POST("sms/captcha/send")
    Observable<BaseEntity<StringBean>> sms_verificationCode_send(@Body JSONObject object);


    /**
     * 注册 ---mousns
     *
     * @return
     */
    @POST("user/add")
    Observable<BaseEntity<RegisterBean>> registerMousns(@Body JSONObject object);
}
