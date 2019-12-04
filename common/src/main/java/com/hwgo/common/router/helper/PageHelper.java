package com.hwgo.common.router.helper;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.hwgo.common.router.action.IActionService;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * ClassName: 页面信息相关辅助类
 * Description:
 * <p>
 * Author: wangbin
 * Date: 2019/12/04 11:05:34
 */
public class PageHelper {

    private PageHelper() {
    }

    /**
     * 获取moduleName,唯一标识某一模块
     */
    public static String getModuleNameWithUrl(String protocolUrl) {
        try {
            if (TextUtils.isEmpty(protocolUrl)) {
                return Uri.EMPTY.toString();
            }
            Uri uri = Uri.parse(protocolUrl);
            List<String> pathSegments = uri.getPathSegments();
            if (pathSegments == null || pathSegments.size() < 2) {
                return Uri.EMPTY.toString();
            }
            StringBuilder sb = new StringBuilder();
            for (int index = 1, size = pathSegments.size(); index < size; index++) {
                if (index != 1) {
                    sb.append("/");
                }
                sb.append(pathSegments.get(index));
            }
            return sb.toString();
        } catch (IllegalArgumentException e) {
            return Uri.EMPTY.toString();
        }
    }

    /**
     * 解析query参数
     */
    public static Map getQueryWithinUrl(String protocolUrl) {
        if (TextUtils.isEmpty(protocolUrl)) {
            return null;
        }
        Uri uri = Uri.parse(protocolUrl);
        Set<String> queryNames = uri.getQueryParameterNames();
        if (queryNames == null || queryNames.isEmpty()) {
            return null;
        }
        if (!queryNames.contains(IActionService.QUERY_KEY)) {
            return null;
        }
        String queryValue = uri.getQueryParameter(IActionService.QUERY_KEY);
        if (TextUtils.isEmpty(queryValue)) {
            return null;
        }
        return new Gson().fromJson(queryValue, Map.class);
    }


    /**
     * 解析query参数
     */
    public static String getQueryValue(String protocolUrl) {
        if (TextUtils.isEmpty(protocolUrl)) {
            return null;
        }
        Uri uri = Uri.parse(protocolUrl);
        Set<String> queryNames = uri.getQueryParameterNames();
        if (queryNames == null || queryNames.isEmpty()) {
            return null;
        }
        if (!queryNames.contains(IActionService.QUERY_KEY)) {
            return null;
        }
        String queryValue = uri.getQueryParameter(IActionService.QUERY_KEY);
        return queryValue;
    }

    /**
     * 根据Uri协议获取query参数，并封装到bundle中
     */
    public static Bundle getParamsBundleWithUri(Uri uri) {
        Bundle bundle = new Bundle();
        if (uri == null) {
            return bundle;
        }
        Set<String> queryParameterNames = uri.getQueryParameterNames();
        if (queryParameterNames == null || queryParameterNames.isEmpty()) {
            return bundle;
        }
        for (String queryParameterName : queryParameterNames) {
            bundle.putString(queryParameterName, uri.getQueryParameter(queryParameterName));
        }
        return bundle;
    }

    /**
     * 根据Uri协议获取Activity的PageName
     */
    public static String getPageNameWithUri(Uri uri) {
        if (uri == null) {
            return Uri.EMPTY.toString();
        }
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments == null || pathSegments.size() < 2) {
            return Uri.EMPTY.toString();
        }
        StringBuilder sb = new StringBuilder();
        String remove = pathSegments.get(0);
        if (IActionService.NATIVE_PATH_PREFIX.equals(remove)) {
            for (int index = 1, size = pathSegments.size(); index < size; index++) {
                sb.append("/").append(pathSegments.get(index));
            }
        }
        return sb.toString();
    }


}
