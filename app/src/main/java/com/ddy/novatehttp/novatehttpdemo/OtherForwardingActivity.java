package com.ddy.novatehttp.novatehttpdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ddy.novatehttp.novatehttpdemo.base.BaseActivity;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class OtherForwardingActivity  extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_forward);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

}
