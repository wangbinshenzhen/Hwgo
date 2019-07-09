package com.hwgo.base.http

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *网络请求默认的线程调度
 * @Author wangbin
 * @Date 2019-07-09 17:35
 */
class CTHttpTransformer<T> : ObservableTransformer<T, T> {


    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) //主线程订阅
    }
}