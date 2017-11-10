package com.ddy.novatehttp.novatehttpdemo.help;


import android.util.Log;

import com.ddy.novatehttp.http.InterceptorUtil;
import com.ddy.novatehttp.http.config.HttpConfig;
import com.ddy.novatehttp.http.gsoncover.CustomGsonConverterFactory;
import com.ddy.novatehttp.novatehttpdemo.http.ServiceApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * Created by Administrator on 2017/6/22 0022.
 */

public class HttpHelp {
    private static HttpHelp mHttpHelp;
    private static ServiceApi mServiceApi;

    public HttpHelp() {


        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()

                .connectTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .addInterceptor(InterceptorUtil.LogInterceptor())//添加日志拦截器
                .build();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(HttpConfig.BASE_URL)
                .addConverterFactory(CustomGsonConverterFactory.create())//添加gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava转换器
                .client(mOkHttpClient)
                .build();
        mServiceApi = mRetrofit.create(ServiceApi.class);

    }

    public static HttpHelp getInstance() {
        if (mHttpHelp == null) {
            synchronized (HttpHelp.class) {
                if (mHttpHelp == null)
                    mHttpHelp = new HttpHelp();
            }

        }
        return mHttpHelp;
    }

    public ServiceApi getApi() {
        return mServiceApi;
    }

}
