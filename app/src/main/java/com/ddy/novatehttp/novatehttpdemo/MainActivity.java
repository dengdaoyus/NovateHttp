package com.ddy.novatehttp.novatehttpdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ddy.novatehttp.novatehttpdemo.base.BaseActivity;
import com.ddy.novatehttp.novatehttpdemo.config.PhoneConfig;
import com.ddy.novatehttp.novatehttpdemo.utils.GenerateUtils;

public class MainActivity extends BaseActivity implements RegisterUtils.GetCodeImp, RegisterUtils.RegisterImp {
     RegisterUtils registerUtils;
    TextView tvCode;
    TextView tvRegister;
    int count= 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       registerUtils=new RegisterUtils();
        tvCode= (TextView) findViewById(R.id.textView2);
        tvRegister= (TextView) findViewById(R.id.textView3);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    registerUtils.sms_captcha_send(MainActivity.this,count,1,MainActivity.this);
            }
        });


        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });

    }

    @Override
    public void code(final int count, final String code) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvCode.setText("获取验证码成功： "+String.valueOf(count));
                registerUtils.registerMousns(MainActivity.this,GenerateUtils.generatePhone(count),"123456",code,MainActivity.this);
            }
        });
    }

    @Override
    public void code() {
        count++;
        tvRegister.setText("注册成功： "+String.valueOf(count));
        if(count> PhoneConfig.phone.length) return;
        registerUtils.sms_captcha_send(MainActivity.this,count,1,MainActivity.this);
    }
}
