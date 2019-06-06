package com.hwgo.common.grant.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;

/**
 * <br> ClassName:   PermissionPageUtils
 * <br> Description: 跳转到系统设置页的工具类
 * <p>
 */
public class PermissionPageUtils {

    public static void startAndroidSettingsPage(@NonNull Activity activity) {
        activity.startActivity(new Intent(Settings.ACTION_SETTINGS));
    }

    public static void startAndroidGrantPage(@NonNull Activity activity) {
        String packageName = activity.getPackageName();
        Intent intent = new Intent();
        intent.setAction("miui.intent.action.APP_PERM_EDITOR");//适配小米MIUI系统权限管理
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("extra_pkgname", packageName);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(intent);
        } else {
            Uri packageURI = Uri.parse("package:" + packageName);
            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
            if (intent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivity(intent);
            } else {
                startAndroidSettingsPage(activity);
            }
        }
    }
}
