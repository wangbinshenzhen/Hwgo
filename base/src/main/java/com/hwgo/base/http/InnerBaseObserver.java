package com.hwgo.base.http;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.hwgo.base.http.bean.BaseResponse;

/**
 * 对LiveData内置处理
 **/
public class InnerBaseObserver<T> extends BaseObserver<T> {
    private MutableLiveData<T> mLiveData;

    public InnerBaseObserver(MutableLiveData<T> liveData) {
        this.mLiveData = liveData;
    }

    @Override
    public void onFailure(@NonNull BaseResponse<T> baseResponse, int errorCode, String message) {
        super.onFailure(baseResponse, errorCode, message);
        if (mLiveData == null) {
            return;
        }
        mLiveData.setValue(null);
    }

    @Override
    public void onResponse(@NonNull BaseResponse<T> baseResponse, @Nullable T data) {
        super.onResponse(baseResponse, data);
        if (mLiveData == null) {
            return;
        }
        mLiveData.setValue(data);
    }
}
