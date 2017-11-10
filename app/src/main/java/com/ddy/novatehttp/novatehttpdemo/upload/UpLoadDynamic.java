package com.ddy.novatehttp.novatehttpdemo.upload;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.ddy.novatehttp.entity.BaseEntity;
import com.ddy.novatehttp.http.base.BaseSubscribe;
import com.ddy.novatehttp.novatehttpdemo.base.BaseActivity;
import com.ddy.novatehttp.novatehttpdemo.bean.EarnTwiceMoneyBean;
import com.ddy.novatehttp.novatehttpdemo.bean.FileMultiBean;
import com.ddy.novatehttp.novatehttpdemo.bean.TalkRequestModel;
import com.ddy.novatehttp.novatehttpdemo.bean.UpLoadTalkSuccess;
import com.ddy.novatehttp.novatehttpdemo.help.HttpHelp;
import com.ddy.novatehttp.novatehttpdemo.utils.GenerateUtils;
import com.ddy.novatehttp.novatehttpdemo.utils.PreferenceHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.ddy.novatehttp.LLog.e;

/**
 * Created by Administrator on 2017/11/9 0009.
 */

public class UpLoadDynamic {


    TalkRequestModel model;

    public void getTalkRequestModel(String imgPath, int userId, String content) {
        model = new TalkRequestModel();
        model.setCity(GenerateUtils.generateCity());
        model.setLatitude(GenerateUtils.generateLa() + "");
        model.setLongitude(GenerateUtils.generateLo() + "");
        model.setContent(content);
        model.setMediaType("3");//说说
        model.setTalkResource(onAssembleData(imgPath));
        model.setCreatorId(String.valueOf(userId));
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


    public void uploadTalk(final Context context, int userId, String imgPath, String content, String key, BaseEntity<List<FileMultiBean>> t, final UpLoadQiNiuImp upLoadQiNiuImp) {
        getTalkRequestModel(imgPath, userId, content);
        for (int i = 0; i < t.getData().size(); i++) {
            UploadUtils.getInstance().upload(imgPath, key, t.getData().get(i).getToken(), new UploadUtils.LoadCallBack() {
                @Override
                public void onSuccess(String key, String extendData) {
                    if (model != null) {
                        model.getTalkResource().get(0).setUrl(key);
                        if (upLoadQiNiuImp != null) {
                            upLoadQiNiuImp.upLoadQiniuSuccess(model);
                        }
                    }
                }
            });
        }
    }

    public interface UpLoadQiNiuImp {
        void upLoadQiniuSuccess(TalkRequestModel mode);
    }

    //上传服务器
    public void releaseTalk(final Context context, TalkRequestModel mode, final ReleaseSuccessImp releaseSuccessImp) {

        HttpHelp.getInstance().getApi()
                .releaseTalk(mode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(((BaseActivity) context).<BaseEntity<UpLoadTalkSuccess>>bindToLifecycle())
                .subscribe(new BaseSubscribe<UpLoadTalkSuccess>(1, context) {


                    @Override
                    public void onSuccess(int what, final BaseEntity<UpLoadTalkSuccess> t) throws Exception {
                        e("onSuccess", "releaseTalk");
                        if (t.getData().getRewardType() == 1) {
                            if (releaseSuccessImp != null) {
                                releaseSuccessImp.onReleaseSuccess(t.getData().getDynamicId());
                            }

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
        void onReleaseSuccess(int dynamicId);
    }

    public void updateActivityEarnTwice(Context context, int dynamicId, int userId, final UpdateActivityEarnTwiceImp updateActivityEarnTwiceImp) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId",userId);
        jsonObject.put("dynamicId", dynamicId);
        jsonObject.put("earnType", "talk");

        HttpHelp.getInstance().getApi()
                .updateActivityEarnTwice(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(((BaseActivity) context).<BaseEntity<EarnTwiceMoneyBean>>bindToLifecycle())
                .subscribe(new BaseSubscribe<EarnTwiceMoneyBean>(1, context) {


                    @Override
                    public void onSuccess(int what, final BaseEntity<EarnTwiceMoneyBean> t) throws Exception {
                        e("onSuccess", "updateActivityEarnTwice");
                        if(updateActivityEarnTwiceImp!=null){
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

    public  interface  UpdateActivityEarnTwiceImp{
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
                .compose(((BaseActivity) context).<BaseEntity<String>>bindToLifecycle())
                .subscribe(new BaseSubscribe<String>(1, context) {


                    @Override
                    public void onSuccess(int what, final BaseEntity<String> t) throws Exception {
                        e("onSuccess", "deleteDynamicDetails");
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
}
