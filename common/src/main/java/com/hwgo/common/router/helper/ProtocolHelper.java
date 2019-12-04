package com.hwgo.common.router.helper;

import android.net.Uri;
import android.text.TextUtils;

import com.hwgo.common.router.action.IActionService;


/**
 * ClassName: 协议相关辅助类
 * Description:
 * <p>
 * Author: wangbin
 * Date: 2019/12/04 11:34:32
 */
public class ProtocolHelper {
    private ProtocolHelper() {
    }

    /**
     * 处理跳转协议
     *
     * @param url       协议url
     * @param isReplace 是否替换栈顶的页面
     */
    public static void handleWithUrl(String url, boolean isReplace) {
        try {
            if (TextUtils.isEmpty(url)) {
                return;
            }
            Uri uri = Uri.parse(url);
            if (!IActionService.SCHEME.equals(uri.getScheme())) {
                return;
            }
            if (TextUtils.isEmpty(uri.getHost())) {
                return;
            }
            if (TextUtils.equals(IActionService.ROUTER_HOST, uri.getHost())) {
                ForwardHelper.forward2TargetPageWithUri(uri, isReplace);
            } else if (TextUtils.equals(IActionService.EVENT_HOST, uri.getHost())) {
                EventHelper.eventDispatchWithUrl(url);
            }
        } catch (IllegalArgumentException e) {
            //do nothing
        }
    }


}
