package com.ddy.novatehttp.novatehttpdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.ddy.novatehttp.novatehttpdemo.base.BaseActivity;
import com.ddy.novatehttp.novatehttpdemo.bean.LoginBean;
import com.ddy.novatehttp.novatehttpdemo.config.PhoneConfig;
import com.ddy.novatehttp.novatehttpdemo.data.LoginData;
import com.ddy.novatehttp.novatehttpdemo.upload.UpLoadDynamic;
import com.ddy.novatehttp.novatehttpdemo.utils.GenerateUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * release secret
 * Created by Administrator on 2017/11/15 0015.
 */

public class ReleaseSecretActivity extends BaseActivity {

    @Bind(R.id.button)
    Button button;
    @Bind(R.id.success)
    TextView success;
    @Bind(R.id.error)
    TextView error;

    private LoginData loginData;
    private UpLoadDynamic upLoadDynamic;
    private int count = 0, userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_secret);
        ButterKnife.bind(this);
        loginData = new LoginData();
        upLoadDynamic = new UpLoadDynamic();
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        login(count);
    }

    private void login(int count) {
        loginData.userLogin(ReleaseSecretActivity.this, GenerateUtils.generatePhone(count), new LoginData.LoginImp() {
            @Override
            public void loginSuccess(LoginBean loginBean) {
                userId = loginBean.getUserId();
                upSecret(userId);
            }
        });
    }


    private void upSecret(int userId) {
        upLoadDynamic.releaseSecret(ReleaseSecretActivity.this, userId, "我心中的小秘密", ReleaseSecretActivity.this.getString(R.string.secret_content), new UpLoadDynamic.ReleaseSuccessImp() {
            @Override
            public void onReleaseSuccess(boolean b, int secretId) {
                deleteSecret(secretId);
            }
        });
    }

    private void deleteSecret(int secretId) {
        upLoadDynamic.deleteSecret(ReleaseSecretActivity.this, userId, secretId, new UpLoadDynamic.onDeleteTalkImp() {
            @Override
            public void deleteSuccess() {
                success.setText(String.valueOf(count));
                if (count < PhoneConfig.phone.length) {
                    count++;
                    login(count);
                }
            }
        });
    }
}
