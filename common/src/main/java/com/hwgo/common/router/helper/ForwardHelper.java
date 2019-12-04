package com.hwgo.common.router.helper;

import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hwgo.base.util.RxSchedulerHelper;
import com.hwgo.common.router.action.ActionService;
import com.hwgo.common.router.action.IActionService;
import com.hwgo.common.router.path.RouterPath;

import java.util.List;


/**
 * ClassName: 页面跳转辅助类
 * Description:
 * <p>
 * Author: wangbin
 * Date: 2019/12/04 11:00:50
 */
public class ForwardHelper {
    public static final String PROTOCOL_URI_KEY = "protocolUri";

    private ForwardHelper() {
    }

    /**
     * 页面跳转
     *
     * @param uri       跳转协议
     * @param isReplace
     */
    public static void forward2TargetPageWithUri(final Uri uri, final boolean isReplace) {
        if (uri == null) {
            return;
        }
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments == null || pathSegments.isEmpty()) {
            return;
        }
        if (Looper.getMainLooper() == Looper.myLooper()) {
            forward2TargetPageWithUriOnUiThread(uri, isReplace);
        } else {
            RxSchedulerHelper.runOnUIThread(() -> forward2TargetPageWithUriOnUiThread(uri, isReplace));
        }
    }

    /**
     * 在UI线程进行页面跳转
     *
     * @param uri       跳转协议
     * @param isReplace
     */
    private static void forward2TargetPageWithUriOnUiThread(Uri uri, boolean isReplace) {
        switch (uri.getPathSegments().get(0)) {
            case IActionService.NATIVE_PATH_PREFIX:
                forward2NativePageWithUri(uri, isReplace);
                break;
            case IActionService.RN_PATH_PREFIX:
                forward2rnPageWithUri(uri, isReplace);
                break;
            case IActionService.H5_PATH_PREFIX:
                //forward2h5PageWithUri(uri, isReplace);
                break;
            default:
        }
    }

    /**
     * 跳转原生界面
     *
     * @param uri       跳转协议
     * @param isReplace
     */
    private static void forward2NativePageWithUri(Uri uri, final boolean isReplace) {
        try {
            if (uri == null) {
                return;
            }
            String pageName = PageHelper.getPageNameWithUri(uri);
            if (TextUtils.isEmpty(pageName)) {
                return;
            }
            Bundle bundle = PageHelper.getParamsBundleWithUri(uri);
            ARouter.getInstance().build(pageName).with(bundle).withString(PROTOCOL_URI_KEY, uri.toString())
                    .navigation();
        } catch (IllegalArgumentException e) {
            //do nothing
        }
    }

    /**
     * 跳转RN界面
     *
     * @param uri       跳转协议
     * @param isReplace
     */
    private static void forward2rnPageWithUri(Uri uri, final boolean isReplace) {
        if (uri == null) {
            return;
        }
        ARouter.getInstance()
                .build(RouterPath.Flutter.CommonFlutterActivity)
                .withString(PROTOCOL_URI_KEY, uri.toString())
                .navigation();

    }

//    /**
//     * 跳转H5界面
//     */
//    private static void forward2h5PageWithUri(Uri uri, boolean isReplace) {
//        if (uri == null) {
//            return;
//        }
//        Intent intent = new Intent(topActivity, CECWebViewActivity.class);
//        Map queryMap = PageHelper.getQueryWithinUrl(uri.toString());
//        if (queryMap == null || !queryMap.containsKey(IActionService.H5_QUERY_URL)) {
//            LOG.w("跳转协议缺少url参数");
//            return;
//        }
//        String url = (String) queryMap.get(IActionService.H5_QUERY_URL);
//        if (TextUtils.isEmpty(url)) {
//            LogUtil.w("跳转协议缺少url参数");
//            return;
//        }
//        intent.putExtra(IActionService.H5_QUERY_URL, url);
//        if (queryMap.containsKey(IActionService.H5_QUERY_TITLE)) {
//            String title = (String) queryMap.get(IActionService.H5_QUERY_TITLE);
//            if (TextUtils.isEmpty(title)) {
//                return;
//            }
//            intent.putExtra(IActionService.H5_QUERY_TITLE, title);
//        }
//        topActivity.startActivity(intent);
//        if (isReplace) {
//            topActivity.finish();
//        }
//    }



    /**
     * 构建跳转RN的协议Url
     *
     * @param componentName  rn页面名称
     * @param query          查询参数
     * @param hideNavigation 是否隐藏导航
     * @return
     */
    public static String buildRNPageUrl(String componentName, String query, boolean hideNavigation) {
        if (TextUtils.isEmpty(componentName)) {
            return Uri.EMPTY.toString();
        }
        String url = IActionService.SCHEME + "://" + IActionService.ROUTER_HOST + "/" +
                IActionService.RN_PATH_PREFIX + "/" + componentName;
        Uri.Builder builder = Uri.parse(url).buildUpon();
        if (hideNavigation) {
            builder.appendQueryParameter("nav_hide", "1");
        }
        if (TextUtils.isEmpty(query)) {
            return builder.build().toString();
        }
        builder.appendQueryParameter(IActionService.QUERY_KEY, query);
        return builder.build().toString();
    }

    /**
     * 构建跳转native的协议Url
     *
     * @param componentName 原生页面routerPath
     * @param query         查询参数
     * @return
     */
    public static String buildNativePageUrl(String componentName, String query) {
        if (TextUtils.isEmpty(componentName)) {
            return Uri.EMPTY.toString();
        }
        String url = IActionService.SCHEME + "://" + IActionService.ROUTER_HOST + "/" +
                IActionService.NATIVE_PATH_PREFIX + "/" + componentName;
        Uri.Builder builder = Uri.parse(url).buildUpon();
        if (TextUtils.isEmpty(query)) {
            return builder.build().toString();
        }
        builder.appendQueryParameter(IActionService.QUERY_KEY, query);
        return builder.build().toString();
    }
//    /**
//     * 拼接跳转h5的协议Url
//     */
//    public static String buildH5PageUrl(String url, String title) {
//        if (TextUtils.isEmpty(url)) {
//            return Uri.EMPTY.toString();
//        }
//        String h5Url = IActionService.SCHEME + "://" + IActionService.ROUTER_HOST + "/" +
//                IActionService.H5_PATH_PREFIX + "/" + CECWebViewActivity.class.getSimpleName();
//        Uri.Builder builder = Uri.parse(h5Url).buildUpon();
//
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty(IActionService.H5_QUERY_URL, url);
//        if (!TextUtils.isEmpty(title)) {
//            jsonObject.addProperty(IActionService.H5_QUERY_TITLE, title);
//        }
//        builder.appendQueryParameter(PageHelper.QUERY_KEY, jsonObject.toString());
//        return builder.build().toString();
//    }
    /**
     * 跳转到rn页面
     *
     * @param componentName  rn页面名称
     * @param query          查询参数
     * @param hideNavigation
     */
    public static void forward2RNPage(String componentName, String query, boolean hideNavigation) {
        String targetUrl = buildRNPageUrl(componentName, query, hideNavigation);
        if (TextUtils.isEmpty(targetUrl)) {
            return;
        }
        ActionService.getInstance().performWithUri(targetUrl);
    }

    /**
     * 跳转到native页面
     *
     * @param componentName 页面routerPath
     * @param query         查询参数
     */
    public static void forward2NativePage(String componentName, String query) {
        String targetUrl = buildNativePageUrl(componentName, query);
        if (TextUtils.isEmpty(targetUrl)) {
            return;
        }
        ActionService.getInstance().performWithUri(targetUrl);
    }
//    /**
//     * 跳转到h5页面
//     */
//    public static void forward2WebViewPage(String url, String title) {
//        String h5Path = buildH5PageUrl(url, title);
//        forward2h5PageWithUri(Uri.parse(h5Path), false);
//    }

}
