package com.hwgo.base.http;


/**
 * ClassName: Http result
 * Description: 通用网络请求response
 * <p>
 * Author: wangbin
 * Date: 2019/06/06 01:27:24
 */
public class HttpResult<T> {
    /**
     * 请求结果业务状态码 （status=200）
     */
    public int code;
    /**
     * 业务提示信息,错误信息提示等
     */
    public String msg;
    /**
     * 具体业务数据
     */
    public T data;

    @Override
    public String toString() {
        return "HttpResult code=" + code + " ,msg=" + msg;
    }
}