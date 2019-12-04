package com.hwgo.common.router.helper;

import android.net.Uri;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.hwgo.common.router.action.ActionService;
import com.hwgo.common.router.action.IActionService;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;


/**
 * ClassName: 事件分发处理类
 * Description:
 * <p>
 * Author: wangbin
 * Date: 2019/12/04 11:23:56
 */
public class EventHelper {
    private static final String S_URL = "hwgapp://event/";

    /**
     * 根据url来进行事件分发
     */
    public static void eventDispatchWithUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (!url.startsWith(S_URL)) {
            return;
        }
        Uri uri = Uri.parse(url);
        String lastPath = uri.getLastPathSegment();
        if (TextUtils.isEmpty(lastPath)) {
            return;
        }
        Map<String, Object> map = new HashMap<>(16);
        String content = uri.getQueryParameter(IActionService.QUERY_KEY);
        if (!TextUtils.isEmpty(content)) {
            map = new Gson().fromJson(content, map.getClass());
        }
        // TODO: 2019-12-04  发送事件
        //CECEventBusHelper.post(new CECEvent(lastPath, map));
    }

    private EventHelper() {
    }


    /**
     * 发事件
     *
     * @param eventName 事件名
     * @param query     参数
     */
    public static void sendEvent(String eventName, String query) {
        if (!TextUtils.isEmpty(eventName)) {
            Uri.Builder builder = new Uri.Builder();
            builder.authority(IActionService.EVENT_HOST);
            builder.scheme(IActionService.SCHEME);
            builder.appendPath(eventName);
            if (TextUtils.isEmpty(query)) {
                builder.appendQueryParameter(IActionService.QUERY_KEY, query);
            }
            String url = builder.build().toString();
            ActionService.getInstance().performWithUri(url);
        }
    }

    /**
     * 发事件
     *
     * @param eventName 事件名
     * @param map       map集合
     */
    public static void sendEvent(String eventName, Map map) {
        String qury = null;
        if (map != null) {
            qury = new Gson().toJson(map);
        }
        sendEvent(eventName, qury);
    }
}
