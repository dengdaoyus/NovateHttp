package com.ddy.novatehttp.novatehttpdemo.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.ddy.novatehttp.LLog;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 管理activity
 * Created by bailiangjin on 16/4/11.
 */
public abstract class AbsSuperApplication extends Application {
    protected static Context context;
    protected static String appName;
    public static List<Activity> mListActivity = Collections.synchronizedList(new LinkedList<Activity>());

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        appName = getAppNameFromSub();
        registerActivityListener();
    }

    public static String getAppName() {
        return appName;
    }

    public static Context getContext() {
        return context;
    }

    protected abstract String getAppNameFromSub();

    /**
     * @param activity 作用说明 ：添加一个activity到管理里
     */
    public void addActivity(Activity activity) {
        mListActivity.add(activity);
        Log.i("activityList:size======", mListActivity.size() + "");
    }

    /**
     * @param activity 作用说明 ：删除一个activity在管理里
     */
    public void removeActivity(Activity activity) {
        mListActivity.remove(activity);
        Log.i("activityList:size======", "" + mListActivity.size());
    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public static Activity currentActivity() {
        if (mListActivity == null || mListActivity.isEmpty()) {
            return null;
        }
        Activity activity = mListActivity.get(mListActivity.size() - 1);
        return activity;
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public void finishCurrentActivity() {
        if (mListActivity == null || mListActivity.isEmpty()) {
            return;
        }
        Activity activity = mListActivity.get(mListActivity.size() - 1);
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (mListActivity == null || mListActivity.isEmpty()) {
            return;
        }
        if (activity != null) {
            mListActivity.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (mListActivity == null || mListActivity.isEmpty()) {
            return;
        }
        for (Activity activity : mListActivity) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 按照指定类名找到activity
     *
     * @param cls
     * @return
     */
    public static Activity findActivity(Class<?> cls) {
        Activity targetActivity = null;
        if (mListActivity != null) {
            for (Activity activity : mListActivity) {
                if (activity.getClass().equals(cls)) {
                    targetActivity = activity;
                    break;
                }
            }
        }
        return targetActivity;
    }

    /**
     * @return 作用说明 ：获取当前最顶部activity的实例
     */
    public Activity getTopActivity() {
        Activity mBaseActivity = null;
        synchronized (mListActivity) {
            final int size = mListActivity.size() - 1;
            if (size < 0) {
                return null;
            }
            mBaseActivity = mListActivity.get(size);
        }
        return mBaseActivity;

    }

    /**
     * @return 作用说明 ：获取当前最顶部的acitivity 名字
     */
    public String getTopActivityName() {
        Activity mBaseActivity = null;
        synchronized (mListActivity) {
            final int size = mListActivity.size() - 1;
            if (size < 0) {
                return null;
            }
            mBaseActivity = mListActivity.get(size);
        }
        return mBaseActivity.getClass().getName();
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (mListActivity == null) {
            return;
        }
        for (Activity activity : mListActivity) {
            activity.finish();
        }
        mListActivity.clear();
    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            finishAllActivity();
        } catch (Exception e) {
            LLog.d(e.getMessage());
        }
    }

    private void registerActivityListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    /**
                     *  监听到 Activity创建事件 将该 Activity 加入list
                     */
                    addActivity(activity);

                }

                @Override
                public void onActivityStarted(Activity activity) {

                }

                @Override
                public void onActivityResumed(Activity activity) {

                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    assert mListActivity != null;
                    if (mListActivity.contains(activity)) {
                        /**
                         *  监听到 Activity销毁事件 将该Activity 从list中移除
                         */
                        removeActivity(activity);
                    }
                }
            });
        }
    }
}

