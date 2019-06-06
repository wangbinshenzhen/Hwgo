package com.hwgo.base.util;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.Locale;

/**
 * <br> ClassName:   PhoneUtil
 * <br> Description: 手机、系统相关工具类
 * <br>
 */
public class PhoneUtil {
    /**
     * <br> Description: 获取操作系统
     *
     * @return 返回操作系统
     */
    public static String getOS() {
        return "Android";
    }

    /**
     * <br> Description: 获取系统类别
     *
     * @return 返回系统类别
     */
    public static String getSysNo() {
        return "TDW_APP";
    }

    /**
     * <br> Description: 获取操作系统版本
     *
     * @return 返回操作系统版本
     */
    public static String getOSVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * <br> Description: 获取设备语言
     *
     * @return 返回设备语言
     */
    public static String getDeviceLanguage() {
        Locale l = Locale.getDefault();
        return l.getLanguage();
    }

    /**
     * <br> Description: 获取手机品牌
     *
     * @return 返回手机品牌
     */
    public static String getBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * <br> Description: 获取手机型号
     *
     * @return 返回手机型号
     */
    public static String getModel() {
        return android.os.Build.MODEL;
    }

    /**
     * <br> Description: 获取运营商信息
     *
     * @return 返回运营商信息
     */
    public static String getSimOperatorInfo() {
        TelephonyManager telephonyManager = (TelephonyManager) ApplicationContext.instance().getSystemService(Context.TELEPHONY_SERVICE);
        String operatorString = telephonyManager.getSimOperator();

        if (operatorString == null) {
            return "";
        } else {
            return operatorString;
        }
    }

    /**
     * <br> Description: 获取手机IMEI
     *
     * @return 返回手机IMEI
     */
    public static String getIMEI() {
        try {
            String imei = ((TelephonyManager) ApplicationContext.instance().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            if (!TextUtils.isEmpty(imei)) {
                return imei;
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * <br> Description: 获取手机屏幕密度api
     *
     * @return 返回手机屏幕密度
     */
    public static float getScreenDensity() {
        DisplayMetrics dm = ApplicationContext.instance().getResources().getDisplayMetrics();
        return dm.density;
    }

    /**
     * <br> Description: 获取屏幕宽度
     *
     * @param context 上下文
     * @return 返回屏幕宽度
     */
    public static int getScreenPixelsWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * <br> Description: 获取屏幕高度
     *
     * @param context 上下文
     * @return 返回屏幕高度
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * <br> Description: 获取屏幕分辨率
     *
     * @return 返回屏幕分辨率
     */
    public static String getResolution() {
        return getScreenPixelsWidth(ApplicationContext.instance()) + "*" + getScreenHeight(ApplicationContext.instance());
    }

    /**
     * <br> Description: 获取屏幕宽高
     *
     * @param mContext 上下文
     * @return 返回屏幕宽高数组
     */
    public static int[] getDefaultDisplay(Context mContext) {
        int[] size = new int[]{0, 0};
        if (mContext != null) {
            WindowManager wm = (WindowManager) mContext
                    .getSystemService(Context.WINDOW_SERVICE);
            size = new int[]{wm.getDefaultDisplay().getWidth(), wm.getDefaultDisplay().getHeight()};
        }
        return size;
    }

    /**
     *<br> Description: 不需要权限的deviceId
     */
    public static String getUniqueId(){
        String android_id =  Settings.Secure
                .getString(ApplicationContext.instance().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        if (TextUtils.isEmpty(android_id)){
            android_id = "unknown";
        }
        return  android_id;
    }

}
