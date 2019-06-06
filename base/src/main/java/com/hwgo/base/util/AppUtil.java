package com.hwgo.base.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.TypedArray;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Filename:
 * @Description:
 */
public class AppUtil {

    /**
     * <br> Description: 是否有网络
     * <br> Date:        2017/6/14 16:49
     *
     * @return boolean
     */
    public static boolean isNetWorkAvailable() {
        ConnectivityManager mgr = (ConnectivityManager) ApplicationContext.instance().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr != null) {
            NetworkInfo info = mgr.getActiveNetworkInfo();
            if (info != null && info.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

//    /**
//     * <br> Description: 获取IP地址
//     *
//     * @return 返回ip地址
//     */
//    public static String getLocalIpAddress() {
//        // 得到本机IP地址
//        try {
//            Enumeration<NetworkInterface> en = NetworkInterface
//                    .getNetworkInterfaces();
//            while (en.hasMoreElements()) {
//                NetworkInterface nif = en.nextElement();
//                Enumeration<InetAddress> enumIpAddr = nif.getInetAddresses();
//                while (enumIpAddr.hasMoreElements()) {
//                    InetAddress mInetAddress = enumIpAddr.nextElement();
//                    if (!mInetAddress.isLoopbackAddress()
//                            && InetAddressUtils.isIPv4Address(mInetAddress
//                            .getHostAddress())) {
//                        return mInetAddress.getHostAddress().toString();
//                    }
//                }
//            }
//        } catch (SocketException ex) {
////            LoggerManager.e("MyFeiGeActivity", "获取本地IP地址失败");
//        }
//        return null;
//
//    }

    private static MediaPlayer mediaPlayer;

    /**
     * <br> Description: 播放系统铃声
     *
     * @param isSystemBll true：默认自带系统铃声，false：自己设置播放铃声
     * @param raw         数据源
     */
    public static void bellPlayed(Context context, boolean isSystemBll, int raw) {
        try {
            mediaPlayer = new MediaPlayer();
            if (isSystemBll) {
                Uri alert = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                mediaPlayer.setDataSource(context, alert);
            } else {
                // 设定数据源，并准备播放
                AssetFileDescriptor file = context.getResources()
                        .openRawResourceFd(raw);
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
            }

            final AudioManager audioManager = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);

            if (audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION) != 0) {

                mediaPlayer
                        .setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
                mediaPlayer.setLooping(false);
                mediaPlayer.prepare();
                mediaPlayer
                        .setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mPlayer) {

                                if (mPlayer != null) {
                                   /* 当播放完毕一次后，重新指向流文件的开头，以准备下次播放。*/
                                    mPlayer.seekTo(0);
                                    mediaPlayer = null;
                                    mPlayer = null;
                                }
                            }
                        });
                mediaPlayer.start();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            mediaPlayer = null;
        } catch (SecurityException e) {
            e.printStackTrace();
            mediaPlayer = null;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            mediaPlayer = null;
        } catch (IOException e) {
            e.printStackTrace();
            mediaPlayer = null;
        }
    }

    /**
     * <br> Description: 获取app版本号*
     * @param context     上下文，推荐使用全局上下文
     * @param packageName app包名
     * @return 返回版本号
     */
    public static String getAppVersion(Context context, String packageName) {
        String version = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * <br> Description: 获取当前App版本号
     *
     * @return 返回版本号
     */
    public static String getCurrentVersion() {
        return getAppVersion(ApplicationContext.instance(), ApplicationContext.instance().getPackageName());
    }

    /**
     *<br> Description: 获取app versionCode
     *
     * @param context     上下文，推荐使用全局上下文
     * @param packageName app包名
     * @return 返回版本号
     */
    public static int getAppVersionCode(Context context, String packageName) {
        int versionCode = 0;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     *<br> Description: 获取app versionCode
     *
     * @return 返回版本号
     */
    public static int getCurrentVersionCode() {
        return getAppVersionCode(ApplicationContext.instance(), ApplicationContext.instance().getPackageName());
    }

    /**
     * <br> Description: 获取网络类型
     *
     * @return 返回网络类型（wifi、移到网络）
     */
    public static String NetType() {
        try {
            ConnectivityManager cm = (ConnectivityManager)ApplicationContext.instance().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            String typeName = info.getTypeName().toUpperCase(); // WIFI/MOBILE
            if (typeName.contains("WIFI")) {
                return typeName;
            } else {
                return info.getExtraInfo();
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * <br> Description: 获取位置
     *
     * @return 返回位置信息
     */
    public static String getLocation() {
        return null;
    }

    /**
     * <br> Description: 校验银行卡卡号
     *
     * @param cardId 银行卡号
     * @return 返回true则代表银行卡号正确，反之则代表不正确
     */
    public static boolean checkBankCard(String cardId) {
//        if(cardId.length() != 16 && cardId.length() != 17 && cardId.length() != 19) {
//			return false;
//		}
        /**修改算法，三端统一成正则表达式,算法暂时保存，进行注释
         if (cardId.length() < 15) {
         return false;
         }
         char bit = getBankCardCheckCode(cardId
         .substring(0, cardId.length() - 1));
         if (bit == 'N') {
         return false;
         }
         return cardId.charAt(cardId.length() - 1) == bit;**/
        if (cardId.length() < 16) {
            return false;
        }
        // 要验证的字符串cardId
        // 银行卡验证规则
        String cardRule = "^[0-9]*$";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(cardRule);
        Matcher matcher = pattern.matcher(cardId);
        // 字符串是否与正则表达式相匹配
        return matcher.matches();
    }

    /**
     * <br> Description: 获取ApiKey
     *
     * @param metaKey 渠道
     * @return 返回apikey
     */
    public static String getMetaValue(String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (ApplicationContext.instance() == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = ApplicationContext.instance() .getPackageManager().getApplicationInfo(ApplicationContext.instance().getPackageName(),
                    PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return apiKey;
    }

    /**
     * <br> Description: 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId 不含校验位的银行卡卡号
     * @return 返回校验位
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            // 如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * <br> Description: 生产sessionID，也就是UUID
     *
     * @return 返回uuid
     */
    public static String getSessionID() {
        return UUID.randomUUID().toString();
    }

    /**
     * <br> Description: 获取图片的Id数组
     *
     * @param context 上下文
     * @param resId   图片array标识
     * @return 返回图片的Id数组
     */
    public static int[] getResIcon(Context context, int resId) {
        TypedArray ar = context.getResources().obtainTypedArray(resId);
        int len = ar.length();
        int[] resIds = new int[len];
        for (int i = 0; i < len; i++)
            resIds[i] = ar.getResourceId(i, 0);

        ar.recycle();
        return resIds;
    }

    /**
     * <br> Description: 隐藏软键盘
     *
     * @param view 控制view
     */
    public static void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager)ApplicationContext.instance().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     *<br> Description: 显示软键盘
     *
     * @param view     控制view
     */
    public static void showSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager)ApplicationContext.instance().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }
    }

    /**
     *<br> Description: 强制隐藏当前页面的软键盘
     */
    public static void hideSoftInputForce(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getApplicationContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        try {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

}
