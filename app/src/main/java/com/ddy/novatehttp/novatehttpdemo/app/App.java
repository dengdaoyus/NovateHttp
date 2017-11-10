package com.ddy.novatehttp.novatehttpdemo.app;

import android.app.Activity;
import android.content.Context;

import com.ddy.novatehttp.novatehttpdemo.upload.UploadUtils;
import com.ddy.novatehttp.novatehttpdemo.utils.PreferenceHelper;


/**
 * Created by Administrator on 2017/6/28 0028.
 */

public class App extends AbsSuperApplication {
    public static App instance = null;



    public static App getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }


    @Override
    protected String getAppNameFromSub() {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        //初始化 七牛云上传工具
        UploadUtils.getInstance().init();
        //初始化 Preference
        PreferenceHelper.init(instance);
    }

    @Override
    public void addActivity(Activity activity) {
        super.addActivity(activity);
    }

    @Override
    public void removeActivity(Activity activity) {
        super.removeActivity(activity);
    }

    @Override
    public Activity getTopActivity() {
        return super.getTopActivity();
    }

    @Override
    public String getTopActivityName() {
        return super.getTopActivityName();
    }

    @Override
    public void finishCurrentActivity() {
        super.finishCurrentActivity();
    }

    @Override
    public void finishActivity(Activity activity) {
        super.finishActivity(activity);
    }

    @Override
    public void finishActivity(Class<?> cls) {
        if (mListActivity == null || mListActivity.isEmpty()) {
            return;
        }
        for (Activity activity : mListActivity) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                return;
            }
        }
    }
    @Override
    public void finishAllActivity() {
        super.finishAllActivity();
    }
}
