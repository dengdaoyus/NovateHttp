package com.ddy.novatehttp.novatehttpdemo.upload;


import android.util.Log;

import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/11/9 0009.
 */

public class UploadUtils {

    // 说说文件
    public static final String FILE_TYPE_TALK = "talk/pic/";
    public static final String DIARY_PIC = "diary/pic/";//文章
    public static final String SECRET = "secret/";//秘密

    private static UploadUtils instance;
    private UploadManager uploadManager;

    public static synchronized UploadUtils getInstance() {
        if (instance == null) {
            instance = new UploadUtils();
        }
        return instance;
    }


    public void init() {
        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)
                .putThreshhold(512 * 1024)
                .connectTimeout(10)
                .responseTimeout(60)
                .zone(FixedZone.zone0)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
        uploadManager = new UploadManager(config);
    }

    public void upload(String data, String key, String token, final LoadCallBack loadCallBack) {
        uploadManager.put(data, key, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            if (loadCallBack != null) {
                                Log.e("上传", "上传成功，返回key= " + key);
                                String extendData = null;
                                try {
                                    extendData = res.getString("w") + "_" + res.getString("h");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e("上传", "JSONException" + e);
                                }
                                loadCallBack.onSuccess(key,extendData);
                            }
                            Log.i("qiniu", "Upload Success");
                        } else {
                            Log.i("qiniu", "Upload Fail");
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                    }
                }, null);
    }

    interface LoadCallBack {
        void onSuccess(String key, String extendData);
    }
}
