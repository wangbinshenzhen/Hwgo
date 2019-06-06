package com.hwgo.kelin.main.service;

import com.hwgo.base.http.HttpResult;
import com.hwgo.kelin.main.bean.User;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;


/**
 * Created by wangbin on 2017/12/12.
 */

public interface MovieService {
    @GET("/goods/zhe/list?ab_info=3_133_447_&apisign=7978cb719860842ce6384843438e755f&app_name=zhe&app_version=4.6.8&catname=newest_zhe&filter_id=&goods_utype=C2&location=%E5%B9%BF%E4%B8%9C%E7%9C%81&page=1&platform=Android&user_group=&user_label=C2&utm=103524&zxgapi=zxgapi_126_414_A")
    Observable<HttpResult<User>> getUser();

    @GET
    Call<HttpResult<User>> getUser(@Field(value = "name", encoded = true) String name, @Field("age") String age);
}
