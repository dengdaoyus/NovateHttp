package com.ddy.novatehttp.novatehttpdemo.http;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ddy.novatehttp.entity.BaseEntity;
import com.ddy.novatehttp.novatehttpdemo.bean.DiaryMode;
import com.ddy.novatehttp.novatehttpdemo.bean.EarnTwiceMoneyBean;
import com.ddy.novatehttp.novatehttpdemo.bean.FileMultiBean;
import com.ddy.novatehttp.novatehttpdemo.bean.LoginBean;
import com.ddy.novatehttp.novatehttpdemo.bean.RegisterBean;
import com.ddy.novatehttp.novatehttpdemo.bean.SecretMode;
import com.ddy.novatehttp.novatehttpdemo.bean.TalkRequestModel;
import com.ddy.novatehttp.novatehttpdemo.bean.UpLoadTalkSuccess;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceApi {

    /**
     * 获取验证码
     *
     * @param object
     * @return
     */
    @POST("sms/captcha/send")
    Observable<BaseEntity<String>> sms_verificationCode_send(@Body JSONObject object);


    /**
     * 注册 ---mousns
     *
     * @return
     */
    @POST("user/add")
    Observable<BaseEntity<RegisterBean>> registerMousns(@Body JSONObject object);


    /**
     * 上传文件资源（多张）
     */
    @POST("upload/tokens")
    Observable<BaseEntity<List<FileMultiBean>>> getMultiToken(@Body JSONArray object);

    /**
     * 发布说说
     *
     * @param model
     * @return
     */
    @POST("talk/insertTalk")
    Observable<BaseEntity<UpLoadTalkSuccess>> releaseTalk(@Body TalkRequestModel model);

    @POST("rule/updateActivityEarnTwice")
    Observable<BaseEntity<EarnTwiceMoneyBean>> updateActivityEarnTwice(@Body JSONObject object);

    /**
     * 删除说说！
     *
     * @return
     */
    @POST("talk/updateTalkState")
    Observable<BaseEntity<String>>  deleteDynamicDetails(@Body JSONObject object);

    //登录
    @POST("user/checkV360")
    Observable<BaseEntity<LoginBean>> userLogin(@Body JSONObject jsonObject);

    /**
     * 发布秘密
     */
    @POST("secret/insert")
    Observable<BaseEntity<UpLoadTalkSuccess>> secret_insert(@Body SecretMode mode);


    /**
     * 删除秘密
     */
    @POST("secret/updateSecretState")
    Observable<BaseEntity<String>> secret_updateSecretState(@Body JSONObject object);



    /**
     * 发布日记
     */
    @POST("diary/insert")
    Observable<BaseEntity<UpLoadTalkSuccess>> diary_insert(@Body DiaryMode mode);

    /**
     * 删除文章
     */
    @POST("life/updateDiaryState")
    Observable<BaseEntity<String>> life_updateDiaryState(@Body JSONObject object);
}
