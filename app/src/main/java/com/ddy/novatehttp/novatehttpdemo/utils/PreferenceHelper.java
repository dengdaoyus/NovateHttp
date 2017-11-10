package com.ddy.novatehttp.novatehttpdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 对SharePreference的封装
 */
public class PreferenceHelper {
    
    /** 保存的Share */
    private static SharedPreferences mSharedPreferences = null;
    
    /** 编辑器 */
    private static SharedPreferences.Editor mEditor = null;
    
    /**
     * 初始化默认SharePreferences
     *
     * @param context
     *            上下文
     */
    public static void init(Context context) {
        if (null == mSharedPreferences) {
            String pageName = "default_preference";
            String[] strs = getVersion(context);
            if (strs != null && strs.length == 3)
                pageName = strs[0].replaceAll("\\.", "_");
            mSharedPreferences =
                               context.getSharedPreferences(pageName,
                                                            Context.MODE_PRIVATE);
        }
    }

    /**
     * 获取版本号和版本名
     *
     * @param context
     *            上下文
     * @return 版本号版本名字的数组
     */
    public static String[] getVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo =
                    pm.getPackageInfo(context.getPackageName(),
                            PackageManager.GET_CONFIGURATIONS);
            String[] strs = new String[3];
            strs[0] = packageInfo.packageName;
            strs[1] = String.valueOf(packageInfo.versionCode);
            strs[2] = packageInfo.versionName;
            return strs;
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 删除数据
     *
     * @param key
     *            键
     */
    public static void removeKey(String key) {
        mEditor = mSharedPreferences.edit();
        mEditor.remove(key);
        mEditor.apply();
    }
    
    /**
     * 删除所有数据
     */
    public static void removeAll() {
        mEditor = mSharedPreferences.edit();
        mEditor.clear();
        mEditor.apply();
    }
    
    /**
     * 提交字符串数据
     *
     * @param key
     *            键
     * @param value
     *            值
     */
    public static void commitString(String key, String value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putString(key, value);
        mEditor.apply();
    }
    
    /**
     * 获取字符串
     *
     * @param key
     *            键
     * @param defaultValue
     *            默认值
     * @return 当前键对应的值
     */
    public static String getString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }
    
    /**
     * 提交整型
     *
     * @param key
     *            键
     * @param value
     *            值
     */
    public static void commitInt(String key, int value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putInt(key, value);
        mEditor.apply();
    }
    
    /**
     * 获取整型
     *
     * @param key
     *            键
     * @param defaultValue
     *            默认值
     * @return 当前键对应的值
     */
    public static int getInt(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }
    
    /**
     * 提交长整型
     *
     * @param key
     *            键
     * @param value
     *            值
     */
    public static void commitLong(String key, long value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putLong(key, value);
        mEditor.apply();
    }
    
    /**
     * 获取整型
     *
     * @param key
     *            键
     * @param defaultValue
     *            默认值
     * @return 当前键对应的值
     */
    public static long getLong(String key, long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }
    
    /**
     * 提交长整型
     *
     * @param key
     *            键
     * @param value
     *            值
     */
    public static void commitBoolean(String key, boolean value) {
        mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(key, value);
        mEditor.apply();
    }
    
    /**
     * 获取整型
     * @param key 键
     * @param defaultValue
     *            默认值
     * @return 当前键对应的值
     */
    public static Boolean getBoolean(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }
}
