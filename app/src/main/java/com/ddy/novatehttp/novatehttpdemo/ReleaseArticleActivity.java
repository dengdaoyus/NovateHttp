package com.ddy.novatehttp.novatehttpdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ddy.novatehttp.entity.BaseEntity;
import com.ddy.novatehttp.novatehttpdemo.base.BaseActivity;
import com.ddy.novatehttp.novatehttpdemo.bean.DiaryMode;
import com.ddy.novatehttp.novatehttpdemo.bean.FileMultiBean;
import com.ddy.novatehttp.novatehttpdemo.bean.LoginBean;
import com.ddy.novatehttp.novatehttpdemo.bean.TalkRequestModel;
import com.ddy.novatehttp.novatehttpdemo.config.AvatarConfig;
import com.ddy.novatehttp.novatehttpdemo.config.PhoneConfig;
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
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ddy.novatehttp.novatehttpdemo.R.id.login;
import static com.ddy.novatehttp.novatehttpdemo.R.id.tv_delete;

/**
 * release secret
 * Created by Administrator on 2017/11/15 0015.
 */

public class ReleaseArticleActivity extends BaseActivity {


    @Bind(R.id.success)
    TextView success;
    @Bind(R.id.tv_pic_size)
    TextView tv_pic_size;


    private LoginData loginData;
    private GetMultiToken getMultiToken;
    private UpLoadDynamic upLoadDynamic;
    private int count = 0, articleId = 0, userId;
    private String keyType, imgPath;
    private BaseEntity<List<FileMultiBean>> listBaseEntity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);
        initImagePicker();
        loginData = new LoginData();
        getMultiToken = new GetMultiToken();
        upLoadDynamic = new UpLoadDynamic();
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        login(count);
    }

    @OnClick(R.id.select_pic)
    public void onViewClickedSelectPic() {
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, 101);
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(3);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }


    ArrayList<ImageItem> images;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 101) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                tv_pic_size.setText("张数：" + images.size());
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void login(int count) {
        loginData.userLogin(ReleaseArticleActivity.this, GenerateUtils.generatePhone(count), new LoginData.LoginImp() {
            @Override
            public void loginSuccess(LoginBean loginBean) {
                userId = loginBean.getUserId();
                getTokenTalk();
            }
        });
    }

    private void getTokenTalk() {
        if (images == null || TextUtils.isEmpty(String.valueOf(userId))) return;
        //获取token
        getMultiToken.getMultiToken(ReleaseArticleActivity.this, UploadUtils.DIARY_PIC, new GetMultiToken.MultiTokenImp() {
            @Override
            public void multiTokenSuccess(String key, BaseEntity<List<FileMultiBean>> ts) {
                keyType = key;
                listBaseEntity = ts;
                imgPath = images.get(new Random().nextInt(images.size())).path;
                upArticleqiniu();
            }
        });
    }

    private void upArticleqiniu() {
        if (listBaseEntity == null || keyType == null || imgPath == null) return;
        //上传七牛
        upLoadDynamic.releaseArticle(userId, imgPath, "这只小猫猫在观赏花园", keyType, listBaseEntity, new UpLoadDynamic.UpLoadQiNiuArtilceImp() {
            @Override
            public void upLoadQiniuSuccess(final DiaryMode mode) {
                insertArticle(mode);
            }
        });

    }

    private void insertArticle(final DiaryMode mode) {
        upLoadDynamic.releaseArticle(ReleaseArticleActivity.this, mode, new UpLoadDynamic.ReleaseSuccessImp() {
            @Override
            public void onReleaseSuccess(boolean b, final int id) {
                articleId = id;
                if (b) {
                    getGold(articleId, "diary");
                } else {
                    deleteArticle(articleId);
                }

            }
        });
    }


    private void getGold(final int articleId, String earnType) {
        if (articleId == 0 || userId == 0) return;
        upLoadDynamic.updateActivityEarnTwice(ReleaseArticleActivity.this, articleId, userId, earnType, new UpLoadDynamic.UpdateActivityEarnTwiceImp() {
            @Override
            public void onEarnTwicSuccess() {
                deleteArticle(articleId);
            }
        });
    }

    private void deleteArticle(int articleId) {
        if (articleId == 0) return;
        upLoadDynamic.deleteArticle(ReleaseArticleActivity.this, userId, articleId, new UpLoadDynamic.onDeleteTalkImp() {
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
