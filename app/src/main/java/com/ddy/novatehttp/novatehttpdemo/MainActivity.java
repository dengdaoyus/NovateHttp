package com.ddy.novatehttp.novatehttpdemo;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.ddy.novatehttp.entity.BaseEntity;
import com.ddy.novatehttp.http.base.BaseSubscribe;
import com.ddy.novatehttp.novatehttpdemo.base.BaseActivity;
import com.ddy.novatehttp.novatehttpdemo.bean.StringBean;
import com.ddy.novatehttp.novatehttpdemo.help.HttpHelp;
import com.ddy.novatehttp.novatehttpdemo.utils.GenerateUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.ddy.novatehttp.LLog.e;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          sms_captcha_send(MainActivity.this, GenerateUtils.generatePhone(),1);
            }
        });

    }
    /**
     * @param phoneNumber 手机号码
     * @param type        发送验证码类型
     *                    1.注册
     *                    2.密码修改
     *                    3.更换手机绑定号原手机号
     *                    4..更换手机绑定号新手机号
     *                    5..钱包--绑定手机号
     *                    6..钱包--忘记密码
     */
    public void sms_captcha_send(final Context context, final String phoneNumber, final int type) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phoneNumber", phoneNumber);
        jsonObject.put("type", type);
        HttpHelp.getInstance().getApi()
                .sms_verificationCode_send(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .compose(this.<BaseEntity<List<DynamicBean.DataBean>>>setThread())
                .compose(this.<BaseEntity<StringBean>>bindToLifecycle())
                .subscribe(new BaseSubscribe<StringBean>(1,context) {


                    @Override
                    public void onSuccess(int what, BaseEntity<StringBean> t) throws Exception {
                        e("data",t.getData().getData());
//                        registerMousns(context,phoneNumber,"123456",bean.getData().getData());
                    }

                    @Override
                    public void onCodeError(int what, int code, String errorMessage) throws Exception {
                        e("onCodeError :" + code + " " + errorMessage);
                    }

                    @Override
                    public void onFailure(int what, Throwable e, String error, boolean isNetWorkError) throws Exception {
                        e("onFailure :" + e + "      " + error);
                    }

                });
    }

}
