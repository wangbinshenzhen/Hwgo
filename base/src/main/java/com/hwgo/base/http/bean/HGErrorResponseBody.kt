package com.hwgo.base.http.bean


/**
 * Created by WenChang Mai on 2019/1/19 17:06.
 * Description: 网络请求错误时服务器返回的Response Body
 */
data class HGErrorResponseBody(var statusCode: Int,
                               var error: String?=null,
                               var message: String?=null,
                               var errorCode: String?=null,
                               var errState: String?=null,
                               var errorMsg: String?=null)
