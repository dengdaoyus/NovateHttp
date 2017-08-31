package com.ddy.novatehttp.novatehttpdemo;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.ddy.novatehttp.entity.BaseEntity;
import com.ddy.novatehttp.http.base.BaseSubscribe;
import com.ddy.novatehttp.novatehttpdemo.base.BaseActivity;
import com.ddy.novatehttp.novatehttpdemo.bean.DynamicBean;
import com.ddy.novatehttp.novatehttpdemo.help.HttpHelp;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.ddy.novatehttp.LLog.e;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getHttpData();
    }

    private void getHttpData() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", 12029);
        jsonObject.put("pageSize", 15);
        jsonObject.put("pageNumber", 1);
        jsonObject.put("v", "3.5.1");

//        HttpHelp.getInstance().getApi()
//                .life_dynamic(jsonObject)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(this.<BaseEntity<List<DynamicBean.DataBean>>>setThread())
//                .subscribe(new BaseSubscribe<List<DynamicBean.DataBean>>(1, MainActivity.this) {
//
//                    @Override
//                    public void onSuccess(int what, BaseEntity<List<DynamicBean.DataBean>> t) throws Exception {
//
//                    }
//
//                    @Override
//                    public void onCodeError(int what, int code, String errorMessage) throws Exception {
//
//                    }
//
//                    @Override
//                    public void onFailure(int what, Throwable e, String error, boolean isNetWorkError) throws Exception {
//
//                    }
//                });


        HttpHelp.getInstance().getApi()
                .life_dynamic(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<BaseEntity<List<DynamicBean.DataBean>>>bindToLifecycle())

                .subscribe(new BaseSubscribe<List<DynamicBean.DataBean>>(1, MainActivity.this) {

                    @Override
                    public void onSuccess(int what, BaseEntity<List<DynamicBean.DataBean>> t) throws Exception {
                        Toast(t.getCode());
                        ((TextView) findViewById(R.id.textView)).setText(t.getCode()+"");
                        e("onSuccess :" + t.getCode() + " " + t.getMessage());
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
}
