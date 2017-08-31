package com.ddy.novatehttp;

import android.util.Log;

/**
 * 网络日志
 * Created by Administrator on 2017/6/22 0022.
 */

public class LLog {
    private static final String TAG = LLog.class.getName();

    public static void d(String d) {
        if (InitContent.isLog)
            Log.d(TAG, d);
    }
    public static void d(String tag,String d) {
        if (InitContent.isLog)
            Log.d(tag, d);
    }
    public static void e(String tag,String d) {
        if (InitContent.isLog)
            Log.e(tag, d);
    }
    public static void e(String d) {
        if (InitContent.isLog)
            Log.e(TAG, d);
    }
}
