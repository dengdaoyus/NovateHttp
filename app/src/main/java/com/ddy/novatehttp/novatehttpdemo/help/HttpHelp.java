package com.ddy.novatehttp.novatehttpdemo.help;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.ddy.novatehttp.http.InterceptorUtil;
import com.ddy.novatehttp.http.config.HttpConfig;
import com.ddy.novatehttp.http.gsoncover.CustomGsonConverterFactory;
import com.ddy.novatehttp.novatehttpdemo.app.App;
import com.ddy.novatehttp.novatehttpdemo.http.ServiceApi;
import com.ddy.novatehttp.novatehttpdemo.utils.MD5;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * Created by Administrator on 2017/6/22 0022.
 */

public class HttpHelp {
    private static HttpHelp mHttpHelp;
    private static ServiceApi mServiceApi;
    public static final String APP_CONNECT_PWD = "GycSuB^ATh7#4BL#NbE0Ks5F%Bbj2Z$AJ9U";
    public HttpHelp() {


        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()

                .addInterceptor(InterceptorUtil.LogInterceptor())//添加日志拦截器
                .addInterceptor(addHead())
                .retryOnConnectionFailure(true)
                .connectTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .build();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(HttpConfig.BASE_URL)
                .addConverterFactory(CustomGsonConverterFactory.create())//添加gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava转换器
                .client(mOkHttpClient)
                .build();
        mServiceApi = mRetrofit.create(ServiceApi.class);

    }

    //头部
    public Interceptor addHead() {
       return  new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                long timestamp = System.currentTimeMillis();
                Request response = chain.request().newBuilder().
                        addHeader("timestamp", timestamp + "").
                        addHeader("User-Agent", "1_" + getVersion()).
                        addHeader("key" , MD5.getMD5Code(APP_CONNECT_PWD + timestamp))
                        //获取设备号
                        .addHeader("deviceNumber", ((TelephonyManager) App.getInstance().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId()).build();
                return chain.proceed(response);
            }
        };

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
