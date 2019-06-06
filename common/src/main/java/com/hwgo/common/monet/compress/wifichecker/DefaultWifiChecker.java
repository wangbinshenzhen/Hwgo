package com.hwgo.common.monet.compress.wifichecker;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * <br> ClassName:   DefaultWifiChecker                        
 * <br> Description: wifi检测类
 * <br>  
 */

public class DefaultWifiChecker implements IWifiChecker {

    private Context mContext;

    public DefaultWifiChecker(Context context) {
        mContext = context;
    }

    @Override
    public Boolean isWifiNet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }
}
