package com.hwgo.base.grant.core;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;

import com.hwgo.base.grant.PermissionRequester;


/**
 * <br> ClassName:   PermissionRequesterFactory
 * <br> Description: 权限请求器的工厂类
 * <p>
 */
public class PermissionRequesterFactory {

    public static PermissionRequester create(FragmentActivity activity){
        int targetVersion ;
        try {
            PackageInfo info = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            targetVersion = info.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            targetVersion = -1;
        }
        if (targetVersion < 23){
            return new NoRuntimePermissionRequesterImpl(activity);
        }else{
            return new RxPermissionRequesterImpl(activity);
        }
    }
}
