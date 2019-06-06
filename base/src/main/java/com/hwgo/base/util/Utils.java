package com.hwgo.base.util;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by wangbin on 2017/12/13.
 */

public class Utils {
    /**
     * 网络连接是否可用
     *
     * @param context
     * @return
     */
    public static boolean networkEnable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);
        try {
            return connectivityManager.getActiveNetworkInfo().isAvailable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
}
