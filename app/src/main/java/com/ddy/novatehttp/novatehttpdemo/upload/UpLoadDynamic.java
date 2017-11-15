package com.ddy.novatehttp.novatehttpdemo.upload;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.ddy.novatehttp.entity.BaseEntity;
import com.ddy.novatehttp.http.base.BaseSubscribe;
import com.ddy.novatehttp.novatehttpdemo.bean.DiaryMode;
import com.ddy.novatehttp.novatehttpdemo.bean.EarnTwiceMoneyBean;
import com.ddy.novatehttp.novatehttpdemo.bean.FileMultiBean;
import com.ddy.novatehttp.novatehttpdemo.bean.SecretMode;
import com.ddy.novatehttp.novatehttpdemo.bean.TalkRequestModel;
import com.ddy.novatehttp.novatehttpdemo.bean.UpLoadTalkSuccess;
import com.ddy.novatehttp.novatehttpdemo.help.HttpHelp;
import com.ddy.novatehttp.novatehttpdemo.utils.GenerateUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.ddy.novatehttp.LLog.e;

/**
 * Created by Administrator on 2017/11/9 0009.
 */

public class UpLoadDynamic {


    private TalkRequestModel talkRequestModel;

    private void getTalkRequestModel(String imgPath, int userId, String content) {
        talkRequestModel = new TalkRequestModel();
        talkRequestModel.setCity(GenerateUtils.generateCity());
        talkRequestModel.setLatitude(GenerateUtils.generateLa() + "");
        talkRequestModel.setLongitude(GenerateUtils.generateLo() + "");
        talkRequestModel.setContent(content);
        talkRequestModel.setMediaType("3");//说说
        talkRequestModel.setTalkResource(onAssembleData(imgPath));
        talkRequestModel.setCreatorId(String.valueOf(userId));
    }

    //组装列表数据
    private List<TalkRequestModel.TalkResourceBean> onAssembleData(String imgPath) {
        List<TalkRequestModel.TalkResourceBean> beanList = new ArrayList<>();
        TalkRequestModel.TalkResourceBean bean = new TalkRequestModel.TalkResourceBean();
        bean.setPicOrder(1 + "");
        bean.setUrl(imgPath);
        beanList.add(bean);
        return beanList;
    }


    public void uploadTalk( int userId, String imgPath, String content, String key, BaseEntity<List<FileMultiBean>> t, final UpLoadQiNiuTalkImp upLoadQiNiuImp) {
        getTalkRequestModel(imgPath, userId, content);
        for (int i = 0; i < t.getData().size(); i++) {
            UploadUtils.getInstance().upload(imgPath, key, t.getData().get(i).getToken(), new UploadUtils.LoadCallBack() {
                @Override
                public void onSuccess(String key, String extendData) {
                    if (talkRequestModel != null) {
                        talkRequestModel.getTalkResource().get(0).setUrl(key);
                        if (upLoadQiNiuImp != null) {
                            e("onSuccess", "长传七牛成功");
                            upLoadQiNiuImp.upLoadQiniuSuccess(talkRequestModel);
                        }
                    }
                }
            });
        }
    }

    public interface UpLoadQiNiuTalkImp {
        void upLoadQiniuSuccess(TalkRequestModel mode);
    }

    //上传服务器
    public void releaseTalk(final Context context, TalkRequestModel mode, final ReleaseSuccessImp releaseSuccessImp) {

        HttpHelp.getInstance().getApi()
                .releaseTalk(mode)
                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(((BaseActivity) context).<BaseEntity<UpLoadTalkSuccess>>bindToLifecycle())
                .subscribe(new BaseSubscribe<UpLoadTalkSuccess>(1, context) {

                    @Override
                    public void onSuccess(int what, final BaseEntity<UpLoadTalkSuccess> t) throws Exception {
                        e("onSuccess", "releaseTalk");
                            if (releaseSuccessImp != null) {
                                releaseSuccessImp.onReleaseSuccess((t.getData().getRewardType() == 1),t.getData().getDynamicId());
                            }

                    }

                    @Override
                    public void onCodeError(int what, int code, final String errorMessage) throws Exception {
                        e("onCodeError :" + code + " " + errorMessage);
                    }

                    @Override
                    public void onFailure(int what, Throwable e, String error, boolean isNetWorkError) throws Exception {
                        e("onFailure :" + e + "      " + error);
                    }

                });
    }


    public interface ReleaseSuccessImp {
        void onReleaseSuccess(boolean b,int dynamicId);
    }

    public void updateActivityEarnTwice(Context context, int dynamicId, int userId, String earnType, final UpdateActivityEarnTwiceImp updateActivityEarnTwiceImp) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        jsonObject.put("dynamicId", dynamicId);
        jsonObject.put("earnType", earnType);

        HttpHelp.getInstance().getApi()
                .updateActivityEarnTwice(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscribe<EarnTwiceMoneyBean>(1, context) {

                    @Override
                    public void onSuccess(int what, final BaseEntity<EarnTwiceMoneyBean> t) throws Exception {
                        e("onSuccess", "获取钱钱成功");
                        if (updateActivityEarnTwiceImp != null) {
                            updateActivityEarnTwiceImp.onEarnTwicSuccess();
                        }
                    }

                    @Override
                    public void onCodeError(int what, int code, final String errorMessage) throws Exception {
                        e("onCodeError :" + code + " " + errorMessage);
                    }

                    @Override
                    public void onFailure(int what, Throwable e, String error, boolean isNetWorkError) throws Exception {
                        e("onFailure :" + e + "      " + error);
                    }

                });
    }

    public interface UpdateActivityEarnTwiceImp {
        void onEarnTwicSuccess();
    }

    //删除
    public void deleteDynamicDetails(Context context, int dynamicId, final onDeleteTalkImp onDeleteTalkImp) {
        JSONObject object = new JSONObject();
        object.put("talkId", dynamicId);
        HttpHelp.getInstance().getApi()
                .deleteDynamicDetails(object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscribe<String>(1, context) {

                    @Override
                    public void onSuccess(int what, final BaseEntity<String> t) throws Exception {
                        e("onSuccess", "删除说说成功");
                        if (onDeleteTalkImp != null) {
                            onDeleteTalkImp.deleteSuccess();
                        }
                    }

                    @Override
                    public void onCodeError(int what, int code, final String errorMessage) throws Exception {
                        e("onCodeError :" + code + " " + errorMessage);
                    }

                    @Override
                    public void onFailure(int what, Throwable e, String error, boolean isNetWorkError) throws Exception {
                        e("onFailure :" + e + "      " + error);
                    }

                });
    }

    public interface onDeleteTalkImp {
        void deleteSuccess();
    }

    public void releaseSecret(Context context, int userId, String title, String content, final ReleaseSuccessImp successSecretImp) {
        SecretMode mode = new SecretMode();
        mode.setLocation(GenerateUtils.generateCity());
        mode.setSecretTitle(title);
        mode.setUserId(userId);
        mode.setContents(onAssembleDataSecret(content));

        HttpHelp.getInstance().getApi()
                .secret_insert(mode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscribe<UpLoadTalkSuccess>(1, context) {

                    @Override
                    public void onSuccess(int what, final BaseEntity<UpLoadTalkSuccess> t) throws Exception {
                        e("onSuccess", "发布秘密成功");
                        if (successSecretImp != null) {
                            successSecretImp.onReleaseSuccess(false,t.getData().getSercretId());
                        }
                    }

                    @Override
                    public void onCodeError(int what, int code, final String errorMessage) throws Exception {
                        e("onCodeError :" + code + " " + errorMessage);
                    }

                    @Override
                    public void onFailure(int what, Throwable e, String error, boolean isNetWorkError) throws Exception {
                        e("onFailure :" + e + "      " + error);
                    }

                });
    }


    //组装列表数据
    private List<SecretMode.SecretContentInsertModel> onAssembleDataSecret(String content) {
        List<SecretMode.SecretContentInsertModel> secretContentInsertModel = new ArrayList<>();
        secretContentInsertModel.clear();
        SecretMode.SecretContentInsertModel beanTxt = new SecretMode.SecretContentInsertModel();
        beanTxt.setTextOrPicture(content);
        beanTxt.setContentType(0);
        beanTxt.setSorting(1);
        secretContentInsertModel.add(beanTxt);
        return secretContentInsertModel;
    }


    public void deleteSecret(Context context, int userId, int secretId, final onDeleteTalkImp onDeleteTalkImp) {
        JSONObject object = new JSONObject();
        object.put("secretId", secretId);
        object.put("userId", userId);
        HttpHelp.getInstance().getApi()
                .secret_updateSecretState(object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscribe<String>(1, context) {


                    @Override
                    public void onSuccess(int what, final BaseEntity<String> t) throws Exception {
                        e("onSuccess", "删除秘密成功");
                        if (onDeleteTalkImp != null) {
                            onDeleteTalkImp.deleteSuccess();
                        }
                    }

                    @Override
                    public void onCodeError(int what, int code, final String errorMessage) throws Exception {
                        e("onCodeError :" + code + " " + errorMessage);
                    }

                    @Override
                    public void onFailure(int what, Throwable e, String error, boolean isNetWorkError) throws Exception {
                        e("onFailure :" + e + "      " + error);
                    }

                });
    }

    private DiaryMode diaryMode;

    private void onArticleMode(int userId, String content, String key) {
        diaryMode = new DiaryMode();
        diaryMode.setCity(GenerateUtils.generateCity());
        diaryMode.setDiaryTitle("送上一篇美文");
        diaryMode.setCreatorId(String.valueOf(userId));
        diaryMode.setContents(onAssembleData(content, key));
    }

    //组装列表数据
    private List<DiaryMode.DiaryContentInsertDomain> onAssembleData(String content, String path) {
        List<DiaryMode.DiaryContentInsertDomain> diaryContentInsertDomain = new ArrayList<>();
        int index = 0;


        DiaryMode.DiaryContentInsertDomain bean = new DiaryMode.DiaryContentInsertDomain();
        bean.setTextOrPicture(content);
        bean.setContenType(0);
        index++;
        bean.setSorting(index);
        diaryContentInsertDomain.add(bean);

        DiaryMode.DiaryContentInsertDomain beanImg = new DiaryMode.DiaryContentInsertDomain();
        beanImg.setTextOrPicture(path);
        beanImg.setContenType(1);
        index++;
        beanImg.setSorting(index);
        diaryContentInsertDomain.add(beanImg);
        return diaryContentInsertDomain;
    }

    public void releaseArticle(int userId, String imgPath, String content, String key, BaseEntity<List<FileMultiBean>> t, final UpLoadQiNiuArtilceImp upLoadQiNiuImp) {
        onArticleMode(userId, content, imgPath);
        for (int i = 0; i < t.getData().size(); i++) {
            UploadUtils.getInstance().upload(imgPath, key, t.getData().get(i).getToken(), new UploadUtils.LoadCallBack() {
                @Override
                public void onSuccess(String key, String extendData) {
                    e("onSuccess", "长传七牛成功");
                    if (diaryMode != null) {
                        diaryMode.getContents().get(1).setExtendData(extendData);
                        diaryMode.getContents().get(1).setTextOrPicture(key);
                        if (upLoadQiNiuImp != null) {
                            upLoadQiNiuImp.upLoadQiniuSuccess(diaryMode);
                        }
                    }
                }
            });
        }
    }

    public interface UpLoadQiNiuArtilceImp {
        void upLoadQiniuSuccess(DiaryMode mode);
    }

    //上传服务器
    public void releaseArticle(final Context context, DiaryMode mode, final ReleaseSuccessImp releaseSuccessImp) {

        HttpHelp.getInstance().getApi()
                .diary_insert(mode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscribe<UpLoadTalkSuccess>(1, context) {

                    @Override
                    public void onSuccess(int what, final BaseEntity<UpLoadTalkSuccess> t) throws Exception {
                        e("onSuccess", "releaseTalk");
                        if (releaseSuccessImp != null) {
                            releaseSuccessImp.onReleaseSuccess((t.getData().getRewardType() == 1),t.getData().getDiaryId());
                        }
                    }

                    @Override
                    public void onCodeError(int what, int code, final String errorMessage) throws Exception {
                        e("onCodeError :" + code + " " + errorMessage);
                    }

                    @Override
                    public void onFailure(int what, Throwable e, String error, boolean isNetWorkError) throws Exception {
                        e("onFailure :" + e + "      " + error);
                    }

                });
    }

    //删除文章
    public void deleteArticle(Context context, int userId, int diaryId, final onDeleteTalkImp onDeleteTalkImp) {
        JSONObject object = new JSONObject();
        object.put("userId",userId);
        object.put("diaryId", diaryId);
        HttpHelp.getInstance().getApi()
                .life_updateDiaryState(object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscribe<String>(1, context) {


                    @Override
                    public void onSuccess(int what, final BaseEntity<String> t) throws Exception {
                        e("onSuccess", "删除文章成功");
                        if (onDeleteTalkImp != null) {
                            onDeleteTalkImp.deleteSuccess();
                        }
                    }

                    @Override
                    public void onCodeError(int what, int code, final String errorMessage) throws Exception {
                        e("onCodeError :" + code + " " + errorMessage);
                    }

                    @Override
                    public void onFailure(int what, Throwable e, String error, boolean isNetWorkError) throws Exception {
                        e("onFailure :" + e + "      " + error);
                    }

                });
    }


}
