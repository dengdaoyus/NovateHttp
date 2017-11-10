package com.ddy.novatehttp.novatehttpdemo.upload;

import android.content.Context;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ddy.novatehttp.entity.BaseEntity;
import com.ddy.novatehttp.http.base.BaseSubscribe;
import com.ddy.novatehttp.novatehttpdemo.base.BaseActivity;
import com.ddy.novatehttp.novatehttpdemo.bean.FileMultiBean;
import com.ddy.novatehttp.novatehttpdemo.help.HttpHelp;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.ddy.novatehttp.LLog.e;


public class GetMultiToken {




    //获取上传token
    public  void getMultiToken(final Context context, final String type, final MultiTokenImp multiTokenImp) {
        JSONArray jsonArray = new JSONArray();
        String keyType = type + java.util.UUID.randomUUID().toString() + ".jpg";
        JSONObject object = new JSONObject();
        object.put("bucket", "image");
        object.put("key", keyType);
        object.put("order", String.valueOf(1));
        jsonArray.add(object);
        HttpHelp httpHelp=new HttpHelp();
        httpHelp.getApi()
                .getMultiToken(jsonArray)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(((BaseActivity) context).<BaseEntity<List<FileMultiBean>>>bindToLifecycle())
                .subscribe(new BaseSubscribe<List<FileMultiBean>>(1, context) {

                    @Override
                    public void onSuccess(int what, final BaseEntity<List<FileMultiBean>> t) throws Exception {
                        e("onSuccess", "getMultiToken   "+t.getData().get(0).getToken());
                      if(multiTokenImp!=null){
                          multiTokenImp.multiTokenSuccess(t);
                      }
                    }

                    @Override
                    public void onCodeError(int what, int code, final String errorMessage) throws Exception {
                        e("onCodeError :" + code + " " + errorMessage);
                        ((BaseActivity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((BaseActivity) context).Toast(errorMessage);
                            }
                        });
                    }

                    @Override
                    public void onFailure(int what, Throwable e, String error, boolean isNetWorkError) throws Exception {
                        e("onFailure :" + e + "      " + error);
                    }

                });
    }
    public interface  MultiTokenImp{
        void multiTokenSuccess(BaseEntity<List<FileMultiBean>> t);
    }
}
