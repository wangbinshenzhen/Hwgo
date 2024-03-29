package com.hwgo.base.http

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**

 * @Author wangbin

 * @Date 2019-07-09 17:39

 */
class CTRetrofitFactory private constructor() {

    /*
        单例实现
     */
    companion object {
        val instance: CTRetrofitFactory by lazy { CTRetrofitFactory() }
    }

    private val retrofit: Retrofit

    //初始化
    init {
        //Retrofit实例化
        retrofit = initRetrofit(CTNetworkInitHelper.baseUrl)
    }

    /*
        日志拦截器
     */
    private fun initRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(CTOkHttpClient.instance)
                .build()
    }

    /*
        具体服务实例化
     */
    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

    /**
     * 使用自定义的Url实例化服务
     */
    fun <T> create(baseUrl: String, service: Class<T>): T {
        return initRetrofit(baseUrl).create(service)
    }
}