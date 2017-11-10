package com.ddy.novatehttp.novatehttpdemo.data;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import com.alibaba.fastjson.JSONObject;
import com.ddy.novatehttp.entity.BaseEntity;
import com.ddy.novatehttp.http.base.BaseSubscribe;
import com.ddy.novatehttp.novatehttpdemo.app.App;
import com.ddy.novatehttp.novatehttpdemo.base.BaseActivity;
import com.ddy.novatehttp.novatehttpdemo.bean.LoginBean;
import com.ddy.novatehttp.novatehttpdemo.config.CityConfig;
import com.ddy.novatehttp.novatehttpdemo.help.HttpHelp;
import com.ddy.novatehttp.novatehttpdemo.utils.MD5;

import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.TELEPHONY_SERVICE;
import static com.ddy.novatehttp.LLog.e;


public class LoginData {




    public void  userLogin(final Context context, String account,final LoginImp registerImp){

        HttpHelp.getInstance().getApi()
                .userLogin(loginJSONObject(account))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(((BaseActivity)context).<BaseEntity<LoginBean>>bindToLifecycle())
                .subscribe(new BaseSubscribe<LoginBean>(1,context) {

                    @Override
                    public void onSuccess(int what, BaseEntity<LoginBean> t) throws Exception {
                        e("onSuccess"," userLogin ");
                        if(registerImp!=null){
                            registerImp.loginSuccess(t.getData());
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

   public interface  LoginImp{
        void loginSuccess(LoginBean loginBean);
    }


    private JSONObject loginJSONObject(String account) {

        JSONObject object = new JSONObject();
        object.put("regType", 1);
        object.put("account", account);
        object.put("password", MD5.encrypt("123456"));
        object.put("accountType", 1);
        object.put("deviceNumber", getAppId());
        object.put("modelType", android.os.Build.MODEL);
        object.put("versionNumber", getVersion());
        generateCity();
        object.put("longitude",generateLo());
        object.put("latitude",generateLa());
        return object;
    }



    private static int cityPosition = 0;

    public static String generateCity() {
        cityPosition = (new Random().nextInt(CityConfig.city.length));
        return CityConfig.city[cityPosition];
    }

    public static String generateLo() {
        return CityConfig.lo[cityPosition];
    }

    public static String generateLa() {
        return CityConfig.la[cityPosition];
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
