package com.hwgo.base.http;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.casstime.base.net.bean.BaseResponse;
import com.hwgo.base.http.bean.CECHttpErrorResponse;
import com.hwgo.base.util.ApplicationContext;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * ClassName: Base observer
 * Description: base observer
 * <p>
 * Author: wangbin
 * Date: 2019/12/04 01:51:37
 */
public class BaseObserver<E> implements Observer<BaseResponse<E>> {

    @Override
    public void onSubscribe(Disposable d) {
        //do nothing
    }

    @Override
    public void onNext(BaseResponse<E> baseResponse) {
        if (baseResponse == null) {
            return;
        }
        if (baseResponse.getErrorCode() == 0) {
            onResponse(baseResponse, baseResponse.getData());
        } else {
            onFailure(baseResponse, baseResponse.getErrorCode(), baseResponse.getMessage());
        }
    }

    @Override
    public void onError(Throwable e) {
        // 拦截错误信息
        BaseResponse<E> baseResponse = new CECHttpErrorResponse<E>(e).getBaseResponse();
        onFailure(baseResponse, baseResponse.getErrorCode(), baseResponse.getMessage());
    }

    @Override
    public void onComplete() {
        //do nothing
    }

    /**
     * 请求成功时回调
     *
     * @param baseResponse BaseResponse{errorCode: number;data: any;message: string;teamCode: number;}
     * @param data         数据实体
     */
    public void onResponse(@NonNull BaseResponse<E> baseResponse, @Nullable E data) {
        //do nothing
    }

    /**
     * 请求失败时回调
     *
     * @param baseResponse BaseResponse{errorCode: number;data: any;message: string;teamCode: number;}
     * @param errorCode    错误代码
     * @param message      错误提示
     */
    public void onFailure(@NonNull BaseResponse<E> baseResponse, int errorCode, String message) {
        Log.e(getClass().getSimpleName(), "Http Error : " + message);
        if (errorCode == 652) {
            //鉴权失败不提醒
            return;
        }
        if (TextUtils.isEmpty(message)) {
            return;
        }
        Toast.makeText(ApplicationContext.instance(), message, Toast.LENGTH_SHORT).show();
    }
}
