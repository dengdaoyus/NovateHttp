package com.ddy.novatehttp.novatehttpdemo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import com.alibaba.fastjson.JSONObject;
import com.ddy.novatehttp.entity.BaseEntity;
import com.ddy.novatehttp.http.base.BaseSubscribe;
import com.ddy.novatehttp.novatehttpdemo.app.App;
import com.ddy.novatehttp.novatehttpdemo.base.BaseActivity;
import com.ddy.novatehttp.novatehttpdemo.bean.RegisterBean;
import com.ddy.novatehttp.novatehttpdemo.help.HttpHelp;
import com.ddy.novatehttp.novatehttpdemo.utils.GenerateUtils;
import com.ddy.novatehttp.novatehttpdemo.utils.MD5;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.TELEPHONY_SERVICE;
import static com.ddy.novatehttp.LLog.e;

 class RegisterUtils {


    /**
     * @param count
     * @param type        发送验证码类型
     *                    1.注册
     *                    2.密码修改
     *                    3.更换手机绑定号原手机号
     *                    4..更换手机绑定号新手机号
     *                    5..钱包--绑定手机号
     *                    6..钱包--忘记密码
     */
    void sms_captcha_send(final Context context, final int count, final int type, final GetCodeImp getCodeImp) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phoneNumber", GenerateUtils.generatePhone(count));
        jsonObject.put("type", type);
        HttpHelp.getInstance().getApi()
                .sms_verificationCode_send(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .compose(this.<BaseEntity<String>>setThread())
                .compose(((BaseActivity)context).<BaseEntity<String>>bindToLifecycle())
                .subscribe(new BaseSubscribe<String>(1,context) {


                    @Override
                    public void onSuccess(int what, final BaseEntity<String> t) throws Exception {
                        e("onSuccess",t.getData());
                        if(getCodeImp!=null){
                            getCodeImp.code(count,t.getData());
                        }
                    }

                    @Override
                    public void onCodeError(int what, int code, final String errorMessage) throws Exception {
                        e("onCodeError :" + code + " " + errorMessage);
                        ((BaseActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((BaseActivity)context).Toast(errorMessage);
                            }
                        });
                    }

                    @Override
                    public void onFailure(int what, Throwable e, String error, boolean isNetWorkError) throws Exception {
                        e("onFailure :" + e + "      " + error);
                    }

                });
    }

     interface GetCodeImp{
         void code(int count,String code);
     }

    public void  registerMousns(final Context context, String account, String password, String smsCaptcha, final RegisterImp registerImp){
        JSONObject object = new JSONObject();
        object.put("sig", "");
        object.put("regType", 1);
        object.put("province", "");
        object.put("gender", "0");
        object.put("accountType", 1);
        object.put("account", account);
        object.put("deviceNumber", getAppId());
        object.put("city", GenerateUtils.generateCity());
        object.put("longitude", GenerateUtils.generateLo());
        object.put("latitude",GenerateUtils.generateLa());
        object.put("avatar",GenerateUtils.generateAvatar());
        object.put("nickname",  GenerateUtils.generateUserNames());
        object.put("birthday", "2001-01-01 00:00:00");
        object.put("modelType", android.os.Build.MODEL);
        object.put("versionNumber", getVersion());
        object.put("password", MD5.encrypt(password));
        object.put("smsCaptcha", smsCaptcha);
        HttpHelp.getInstance().getApi()
                .registerMousns(object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(((BaseActivity)context).<BaseEntity<RegisterBean>>bindToLifecycle())
                .subscribe(new BaseSubscribe<RegisterBean>(1,context) {

                    @Override
                    public void onSuccess(int what, BaseEntity<RegisterBean> t) throws Exception {
                        e("onSuccess",t.getData().getNickname());
                        if(registerImp!=null){
                            registerImp.code();
                        }
                    }

                    @Override
                    public void onCodeError(int what, int code, String errorMessage) throws Exception {
                        e("onCodeError :" + code + " " + errorMessage);
                    }

                    @Override
                    public void onFailure(int what, Throwable e, String error, boolean isNetWorkError) throws Exception {
                        e("onFailure :" + e + "  " + error);
                    }

                });
    }

     interface RegisterImp{
         void code();
     }
    /**
     * 获取版本号
     *
     * @return
     */
    private static String getVersion() {
        try {
            PackageManager manager = App.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(App.getInstance().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "3.6.2";
        }
    }
    private String getAppId() {
        TelephonyManager TelephonyMgr = (TelephonyManager) App.getInstance().getSystemService(TELEPHONY_SERVICE);
        return TelephonyMgr.getDeviceId();
    }

}
