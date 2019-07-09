package com.hwgo.base.http

import com.hwgo.base.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.net.Proxy
import java.util.concurrent.TimeUnit

/**
 * okhttp client
 * @Author wangbin
 * @Date 2019-07-09 17:33
 *
 */
class CTOkHttpClient private constructor() {

    private object SingletonHolder {

        val holder: OkHttpClient = generateOkHttpClient()

        private fun generateOkHttpClient(): OkHttpClient {
            val cacheFile = File(CTNetworkInitHelper.application.cacheDir, "cache")
            val cache = Cache(cacheFile, CTNetworkInitHelper.Builder.cacheStateSec)
            val client = OkHttpClient.Builder()
                    .readTimeout(CTNetworkInitHelper.Builder.readTimeOut, TimeUnit.MILLISECONDS)
                    .connectTimeout(CTNetworkInitHelper.Builder.connectTimeOut, TimeUnit.MILLISECONDS)
                    .apply {
                        val interceptors = CTNetworkInitHelper.Builder.interceptors
                        if (!interceptors.isEmpty()) {
                            for (interceptor in interceptors) {
                                addInterceptor(interceptor)
                            }
                        }
                    }
                    .apply {
                        val mLogInterceptor = HttpLoggingInterceptor()
                        if (CTNetworkInitHelper.isIsProduction && !BuildConfig.DEBUG) { // 非生产环境模式关闭Log
                            mLogInterceptor.level = HttpLoggingInterceptor.Level.NONE
                        } else {
                            mLogInterceptor.level = HttpLoggingInterceptor.Level.BODY
                        }
                        addNetworkInterceptor(mLogInterceptor)
                    }
                    .apply {
                        //生产环境关闭抓包代理
                        if (CTNetworkInitHelper.isIsProduction && !BuildConfig.DEBUG) {
                            proxy(Proxy.NO_PROXY)
                        }
                    }
                    .cookieJar(CTCookieJarManager.cookieJar)
                    .cache(cache)
                    .build()
            return client
        }
    }

    companion object {

        val instance by lazy { SingletonHolder.holder }

    }
}