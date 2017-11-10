package com.ddy.novatehttp.novatehttpdemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.ddy.novatehttp.http.base.IBaseActivity;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public class BaseActivity extends IBaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void Toast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void Toast(int msg) {
        Toast(String.valueOf(msg));

    }
}
