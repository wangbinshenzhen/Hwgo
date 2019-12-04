package com.hwgo.common.router.action;

import com.alibaba.android.arouter.facade.template.IProvider;


/**
 * ClassName: Icec action service
 * Description:  根据路由协议跳转界面和发送事件服务
 * <p>
 * Author: wangbin
 * Date: 2019/12/04 11:37:50
 */
public interface IActionService extends IProvider {

    /**
     * SCHEME
     */
    String SCHEME = "hwgapp";
    /**
     * 路由
     */
    String ROUTER_HOST = "route";
    /**
     * 事件
     */
    String EVENT_HOST = "event";
    /**
     * rn页面路由
     */
    String RN_PATH_PREFIX = "rn";
    /**
     * h5页面路由
     */
    String H5_PATH_PREFIX = "h5";
    /**
     * native页面路由
     */
    String NATIVE_PATH_PREFIX = "native";
    /**
     * query key
     */
    String QUERY_KEY = "query";
    /**
     * H5_QUERY_URL
     */
    String H5_QUERY_URL = "url";
    /**
     * H5_QUERY_TITLE
     */
    String H5_QUERY_TITLE = "title";

    /**
     * 根据Uri协议跳转界面
     *
     * @param encodeUrl 协议
     */
    void performWithUri(String encodeUrl);

    /**
     * 根据Uri协议替换栈顶的Activity
     *
     * @param encodeUrl 协议
     */
    void replaceWithUri(String encodeUrl);


}
