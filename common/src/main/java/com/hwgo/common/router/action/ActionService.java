package com.hwgo.common.router.action;

import android.content.Context;

import com.hwgo.common.router.helper.ProtocolHelper;

/**
 * Description:  根据路由协议跳转界面和发送事件服务
 */
public class ActionService implements IActionService {

    private static class InstanceHolder {
        private static final ActionService ACTION_SERVICE = new ActionService();
    }

    public static ActionService getInstance() {
        return InstanceHolder.ACTION_SERVICE;
    }

    private ActionService() {
    }

    @Override
    public void init(Context context) {
        //do nothing
    }

    @Override
    public void performWithUri(String url) {
        ProtocolHelper.handleWithUrl(url, false);
    }

    @Override
    public void replaceWithUri(String url) {
        ProtocolHelper.handleWithUrl(url, true);
    }


}
