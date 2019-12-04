package com.hwgo.base.http.bean;

import android.text.TextUtils;

import com.hwgo.base.http.HGHttpErrorHandler;


/**
 * Created by maiwenchang at 2019-06-06 16:06
 * Description ：
 */
public class HGHttpErrorResponse<E> {

    private BaseResponse<E> baseResponse;

    private Throwable e;

    public HGHttpErrorResponse(Throwable e) {
        this.e = e;
    }

    public BaseResponse<E> getBaseResponse() {
        if (baseResponse != null) {
            return baseResponse;
        }
        HGErrorResponseBody responseBody = HGHttpErrorHandler.handle(e);
        String unknownMessage = "服务器异常";
        if (responseBody == null) {
            if (e != null) {
                unknownMessage = e.getMessage();
            }
            baseResponse = new BaseResponse<>(-1, unknownMessage, null, 1000);
            return baseResponse;
        }
        // 服务器有返回错误信息
        String responseErrorMessage;
        if (!TextUtils.isEmpty(responseBody.getErrorMsg())) {
            responseErrorMessage = responseBody.getErrorMsg();
        } else if (!TextUtils.isEmpty(responseBody.getError())) {
            responseErrorMessage = responseBody.getError();
        } else if (!TextUtils.isEmpty(responseBody.getMessage())) {
            responseErrorMessage = responseBody.getMessage();
        } else {
            responseErrorMessage = unknownMessage;
        }
        baseResponse = new BaseResponse<>(responseBody.getStatusCode(), responseErrorMessage, null, 1000);
        return baseResponse;
    }
}
