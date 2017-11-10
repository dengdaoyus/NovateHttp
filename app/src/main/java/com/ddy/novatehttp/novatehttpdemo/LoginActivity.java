package com.ddy.novatehttp.novatehttpdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ddy.novatehttp.entity.BaseEntity;
import com.ddy.novatehttp.novatehttpdemo.base.BaseActivity;
import com.ddy.novatehttp.novatehttpdemo.bean.DiaryMode;
import com.ddy.novatehttp.novatehttpdemo.bean.FileMultiBean;
import com.ddy.novatehttp.novatehttpdemo.bean.LoginBean;
import com.ddy.novatehttp.novatehttpdemo.bean.TalkRequestModel;
import com.ddy.novatehttp.novatehttpdemo.data.LoginData;
import com.ddy.novatehttp.novatehttpdemo.upload.GetMultiToken;
import com.ddy.novatehttp.novatehttpdemo.upload.UpLoadDynamic;
import com.ddy.novatehttp.novatehttpdemo.upload.UploadUtils;
import com.ddy.novatehttp.novatehttpdemo.utils.GenerateUtils;
import com.ddy.novatehttp.novatehttpdemo.utils.GlideImageLoader;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity {


    @Bind(R.id.textView4)
    TextView tvAccount;//帐号

    @Bind(R.id.textView5)
    TextView tvPath;//图片地址

    @Bind(R.id.login)
    TextView tvLogin;//是否登录成功

    @Bind(R.id.tv_delete)
    TextView tv_delete;//删除


    private LoginData loginData;
    private GetMultiToken getMultiToken;

    private UpLoadDynamic upLoadDynamic;
    int phoneCount = 0, userId = 0;
    private String account, imgPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initImagePicker();
        loginData = new LoginData();
        getMultiToken = new GetMultiToken();
        upLoadDynamic = new UpLoadDynamic();
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }


    private String keyType;
    private BaseEntity<List<FileMultiBean>> t;
    private int dynamicId = 0;
    private int secretId = 0;
    private int articleId = 0;

    @OnClick({R.id.button4, R.id.button8, R.id.button5, R.id.button6, R.id.delete, R.id.bt_secret, R.id.bt_delete_secret, R.id.bt_article, R.id.bt_delete_article})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button4://选择帐号
                account = GenerateUtils.generatePhone(phoneCount);
                tvAccount.setText(account);
                break;
            case R.id.button8://登录
                loginData.userLogin(LoginActivity.this, account, new LoginData.LoginImp() {
                    @Override
                    public void loginSuccess(LoginBean loginBean) {
                        tvLogin.setText("true");
                        userId = loginBean.getUserId();
                    }
                });
                break;
            case R.id.button5://选择图片
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, 101);
                break;
            case R.id.button6://开始上传 说说
                getTokenTalk();
                break;
            case R.id.delete:
                deleteTalk();
                break;

            case R.id.bt_article://上传文章
                getTokenArticle();
                break;
            case R.id.bt_delete_article://删除文章
                deleteArticle();
                break;
            case R.id.bt_secret://上传秘密
                upSecret();
                break;
            case R.id.bt_delete_secret://删除秘密
                deleteSecret();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 101) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                imgPath = images.get(0).path;
                tvPath.setText(imgPath);
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getTokenTalk() {
        if (TextUtils.isEmpty(imgPath) || TextUtils.isEmpty(String.valueOf(userId))) return;
        //获取token
        getMultiToken.getMultiToken(LoginActivity.this, UploadUtils.FILE_TYPE_TALK, new GetMultiToken.MultiTokenImp() {
            @Override
            public void multiTokenSuccess(String key, BaseEntity<List<FileMultiBean>> ts) {
                keyType = key;
                t = ts;
                upTalkqiniu();
            }
        });
    }

    private void upTalkqiniu() {
        if (t == null || keyType == null) return;
        //上传七牛
        upLoadDynamic.uploadTalk(userId, imgPath, "这只小猫猫在观赏花园", keyType, t, new UpLoadDynamic.UpLoadQiNiuTalkImp() {
            @Override
            public void upLoadQiniuSuccess(final TalkRequestModel mode) {
                insertTalk(mode);
            }
        });
    }

    private void insertTalk(final TalkRequestModel mode) {
        upLoadDynamic.releaseTalk(LoginActivity.this, mode, new UpLoadDynamic.ReleaseSuccessImp() {
            @Override
            public void onReleaseSuccess(boolean b, final int dynamicsId) {
                dynamicId = dynamicsId;
                if (b) {
                    getGold("talk");
                }
            }
        });
    }

    private void getGold(String earnType) {
        if (dynamicId == 0 || userId == 0) return;
        upLoadDynamic.updateActivityEarnTwice(LoginActivity.this, dynamicId, userId, earnType, new UpLoadDynamic.UpdateActivityEarnTwiceImp() {
            @Override
            public void onEarnTwicSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("login", "获取钱钱成功");
                    }
                });
            }
        });
    }

    private void deleteTalk() {
        if (LoginActivity.this.dynamicId == 0) return;
        upLoadDynamic.deleteDynamicDetails(LoginActivity.this, dynamicId, new UpLoadDynamic.onDeleteTalkImp() {
            @Override
            public void deleteSuccess() {
                Log.e("login", "删除成功");
                tv_delete.setText("删除成功");
            }
        });
    }

    private void upSecret() {
        upLoadDynamic.releaseSecret(LoginActivity.this, userId, "我心中的小秘密", LoginActivity.this.getString(R.string.secret_content), new UpLoadDynamic.ReleaseSuccessImp() {
            @Override
            public void onReleaseSuccess(boolean b, int id) {
                secretId = id;
//                deleteSecret();
            }
        });
    }

    private void deleteSecret() {
        if (secretId == 0) return;
        upLoadDynamic.deleteSecret(LoginActivity.this, userId, secretId, new UpLoadDynamic.onDeleteTalkImp() {
            @Override
            public void deleteSuccess() {

            }
        });
    }

    private void getTokenArticle() {
        if (TextUtils.isEmpty(imgPath) || TextUtils.isEmpty(String.valueOf(userId))) return;
        //获取token
        getMultiToken.getMultiToken(LoginActivity.this, UploadUtils.DIARY_PIC, new GetMultiToken.MultiTokenImp() {
            @Override
            public void multiTokenSuccess(String key, BaseEntity<List<FileMultiBean>> ts) {
                keyType = key;
                t = ts;
                upArticleqiniu();
            }
        });
    }

    private void upArticleqiniu() {
        if (t == null || keyType == null) return;
        //上传七牛
        upLoadDynamic.releaseArticle(userId, imgPath, "这只小猫猫在观赏花园", keyType, t, new UpLoadDynamic.UpLoadQiNiuArtilceImp() {
            @Override
            public void upLoadQiniuSuccess(final DiaryMode mode) {
                insertArticle(mode);
            }
        });
    }

    private void insertArticle(final DiaryMode mode) {
        upLoadDynamic.releaseArticle(LoginActivity.this, mode, new UpLoadDynamic.ReleaseSuccessImp() {
            @Override
            public void onReleaseSuccess(boolean b, final int id) {
                articleId = id;
                if (b) {
                    getGold("diary");
                }

            }
        });
    }

    private void deleteArticle() {
        if (articleId == 0) return;
        upLoadDynamic.deleteArticle(LoginActivity.this, userId, articleId, new UpLoadDynamic.onDeleteTalkImp() {
            @Override
            public void deleteSuccess() {

            }
        });
    }
}
