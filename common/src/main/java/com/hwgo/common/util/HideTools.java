package com.hwgo.common.util;


import android.text.TextUtils;

/**
 * <br> ClassName:   HideTools
 * <br> Description: 隐藏工具类，用于隐藏一些敏感特殊信息
 * <br>
 * <br> Author:      Administrator
 */
public class HideTools {

    /**
     * <br> Description: 检验是否是手机号码并隐藏手机号码
     *
     * @param phoneNum 要隐藏的号码
     * @return 返回隐藏后的号码，无效号码返回“***********”
     */
    public static String setHidePhoneWithDefault(String phoneNum) {
        if (ValidateUtil.isValidatePhone(phoneNum)) {
            return setHidePhone(phoneNum);
        }
        return "***********";
    }

    /**
     * <br> Description: 隐藏手机号码中间6位数字
     *
     * @param src 要隐藏的号码
     * @return 返回隐藏后的号码，无效号码返回“”
     */
    public static String setHidePhone(String src) {
        return setHidePhone(src, 2, 3);
    }

    /**
     * <br> Description: 隐藏手机号码
     *
     * @param src       要隐藏的号码
     * @param headCount 前面要显示的号码位数
     * @param tailCount 后面要显示的号码位数
     * @return 返回隐藏后的号码，无效号码返回“”
     */
    public static String setHidePhone(String src, int headCount, int tailCount) {
        if (TextUtils.isEmpty(src)) {
            return "";
        }
        int length = src.length();
        StringBuffer sb = new StringBuffer();
        sb.append(src.substring(0, headCount));
        for (int i = headCount; i < length - tailCount; i++) {
            sb = sb.append("*");
        }
        sb.append(src.substring(length - tailCount, length));
        return sb.toString();
    }

    /**
     * <br> Description: 隐藏邮箱信息
     *
     * @param src 要隐藏的邮箱
     * @return 返回隐藏后的邮箱
     */
    public static String setHideEmail(String src) {
        if (src == null || "".equals(src)) {
            return "";
        }
        int length = src.length();
        StringBuffer sb = new StringBuffer();
        sb.append(src.substring(0, 1));
        for (int i = 0; i < length - 8; i++) {
            sb = sb.append("*");
        }
        sb.append(src.substring(length - 6, length));
        return sb.toString();
    }

    /**
     * <br> Description: 隐藏银行账户信息
     *
     * @param src 要隐藏的银行账户信息
     * @return 返回隐藏后的银行账户信息
     */
    public static String setHideBankAccount(String src) {
        if (src == null || "".equals(src)) {
            return "";
        }
        int length = src.length();
        StringBuffer sb = new StringBuffer();
        sb.append(src.substring(0, 3));
        for (int i = 0; i < length - 6; i++) {
            sb = sb.append("*");
        }
        sb.append(src.substring(length - 4, length));
        return sb.toString();
    }
}
