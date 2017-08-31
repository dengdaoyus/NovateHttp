package com.ddy.novatehttp.novatehttpdemo.http;

import com.alibaba.fastjson.JSONObject;
import com.ddy.novatehttp.entity.BaseEntity;
import com.ddy.novatehttp.novatehttpdemo.bean.DynamicBean;


import java.util.List;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public interface ServiceApi {
    /**
     * 生活圈--最新动态
     */
    @POST("life/dynamic_v34")
    Observable<BaseEntity<List<DynamicBean.DataBean>>> life_dynamic(@Body JSONObject object);
}
