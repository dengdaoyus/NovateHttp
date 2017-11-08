package com.ddy.novatehttp.novatehttpdemo;

import android.os.Bundle;

import com.ddy.novatehttp.novatehttpdemo.base.BaseActivity;
import com.ddy.novatehttp.novatehttpdemo.utils.GenerateUtils;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RegisterUtils registerUtils=new RegisterUtils();
        registerUtils.sms_captcha_send(this, GenerateUtils.generatePhone(),1);
    }
}
