package com.hwgo.base.http;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.casstime.base.net.bean.BaseResponse;

import io.reactivex.disposables.Disposable;

/**
 * Description ：BaseObserver的代理类
 */
public class HGObserverProxy<E> extends BaseObserver<E> {

    private BaseObserver<E> mObserver;

    public HGObserverProxy(BaseObserver<E> observer) {
        this.mObserver = observer;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (mObserver == null) {
            return;
        }
        mObserver.onSubscribe(d);
    }

    @Override
    public void onNext(BaseResponse<E> baseResponse) {
        super.onNext(baseResponse);
        if (mObserver == null) {
            return;
        }
        mObserver.onNext(baseResponse);
    }

    @Override
    public void onError(Throwable e) {
        if (mObserver == null) {
            return;
        }
        mObserver.onError(e);
    }

    @Override
    public void onComplete() {
        if (mObserver == null) {
            return;
        }
        mObserver.onComplete();
    }

    @Override
    public void onResponse(@NonNull BaseResponse<E> baseResponse, @Nullable E data) {

    }

    @Override
    public void onFailure(@NonNull BaseResponse<E> baseResponse, int errorCode, String message) {

    }
}
