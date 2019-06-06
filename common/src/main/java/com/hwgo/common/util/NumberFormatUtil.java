package com.hwgo.common.util;

import android.text.TextUtils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * 数字格式工具类
 *
 * @Filename: NumberFormatUtil
 */
public class NumberFormatUtil {
    /**
     * 单位（元），","分隔符，不四舍五入，最多保留小数点后2位,去除多余小数点和0
     *
     * @param num
     * @return
     */
    public static String formatMoneyYuan(String num) {
        if (TextUtils.isEmpty(num)) {
            return "0";
        }
        return formatMoneyYuan(Double.valueOf(num));
    }

    /**
     * 单位（元），","分隔符，不四舍五入，最多保留小数点后2位,去除多余小数点和0
     *
     * @param num
     * @return
     */
    public static String formatMoneyYuan(double num) {
        try {
            // 利用多余的小数位进行截取(避免6位小数点内的进位)
            DecimalFormat numberFormatT = new DecimalFormat("#,##0.######", new DecimalFormatSymbols(Locale.CHINA));
            String numStr = numberFormatT.format(num);
            if (numStr.indexOf(".") > 0) {
                int end = numStr.length() - numStr.indexOf(".");
                if (end > 3) {
                    numStr = numStr.substring(0, numStr.indexOf(".") + 3);
                }
                numStr = numStr.replaceAll("0+?$", "");// 去掉多余的0
                numStr = numStr.replaceAll("[.]$", "");// 如最后一位是.则去掉
            }
            return numStr;
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    /**
     * 单位（元），","分隔符，保留小数点后2位 不足补0
     *
     * @param num
     * @param rounding (是否四舍五入)
     * @return
     */
    public static String formatMoneyYuan2(double num, boolean rounding) {
        if (num == 0) {
            return "0.00";
        }
        try {
            if (!rounding) {
                // 非四舍五入
                DecimalFormat numberFormatT = new DecimalFormat("#,##0.00####", new DecimalFormatSymbols(Locale.CHINA));
                String numStr = numberFormatT.format(num);
                if (numStr.indexOf(".") > 0) {
                    int end = numStr.length() - numStr.indexOf(".");
                    if (end > 3) {
                        numStr = numStr.substring(0, numStr.indexOf(".") + 3);
                    }
                }
                return numStr;
            } else {
                // 四舍五入
                DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(Locale.CHINA));
                decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
                return decimalFormat.format(num);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "0.00";
        }
    }

    /**
     * 小于10000单位（元）否则单位（万），","分隔符，不四舍五入，最多保留小数点后2位,去除多余小数点和0
     *
     * @param num
     * @return
     */
    public static String formatMoneyWan(String num) {
        if (TextUtils.isEmpty(num)) {
            return "0";
        }
        return formatMoneyWan(Double.parseDouble(num));
    }

    /**
     * 小于10000单位（元）否则单位（万），","分隔符，不四舍五入，最多保留小数点后2位,去除多余小数点和0
     *
     * @param numd
     * @return
     */
    public static String formatMoneyWan(double numd) {
        if (numd < 10000d) {
            return formatMoneyYuan(numd);
        } else {
            numd = numd / 10000d;
            return formatMoneyYuan(numd);
        }
    }

    /**
     * 小于10000单位（元）否则单位（万）， 非逗号格式化，不四舍五入，最多保留小数点后2位,去除多余小数点和0
     *
     * @param numd
     * @return
     */
    public static String formatMoneyWanYuan(double numd) {
        if (numd < 10000) {
            return formatNumberByTwo(numd);
        } else {
            numd = numd / 10000;
            return formatNumberByTwo(numd);
        }
    }

    /**
     * 获取单位,小于10000单位（元）否则单位（万）
     *
     * @param num
     * @return
     */
    public static String formatMoneyWanUnit(String num) {
        if (TextUtils.isEmpty(num)) {
            return "元";
        }
        return formatMoneyWanUnit(Double.parseDouble(num));
    }

    /**
     * 获取单位,小于10000单位（元）否则单位（万）
     *
     * @param numd
     * @return
     */
    public static String formatMoneyWanUnit(double numd) {
        if (numd < 10000) {
            return "元";
        } else {
            return "万";
        }
    }

    /**
     * 获取单位,小于10000单位（元）否则单位（万元）
     *
     * @param numd
     * @return
     */
    public static String formatMoneyWanYuanUnit(double numd) {
        if (numd < 10000) {
            return "元";
        } else {
            return "万元";
        }
    }

    /**
     * 最多保留小数点后2位,去除多余小数点和0
     *
     * @param numStr
     * @return
     */
    public static String formatNumberByTwo(String numStr) {
        if (TextUtils.isEmpty(numStr)) {
            return "0";
        }
        return formatNumberByTwo(Double.valueOf(numStr));
    }

    /**
     * 最多保留小数点后2位,去除多余小数点和0 非逗号格式化
     *
     * @param num
     * @return
     */
    public static String formatNumberByTwo(double num) {
        if (num == 0) {
            return "0";
        }
        DecimalFormat numberFormatT = new DecimalFormat("#0.######", new DecimalFormatSymbols(Locale.CHINA));
        String numStr = numberFormatT.format(num);
        if (numStr.indexOf(".") > 0) {
            int end = numStr.length() - numStr.indexOf(".");
            if (end > 3) {
                numStr = numStr.substring(0, numStr.indexOf(".") + 3);
            }
            numStr = numStr.replaceAll("0+?$", "");// 去掉多余的0
            numStr = numStr.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return numStr;
    }

    /**
     * 最多保留小数点后4位,去除多余小数点和0
     *
     * @param numStr
     * @return
     */
    public static String formatNumberByFour(String numStr) {
        if (TextUtils.isEmpty(numStr)) {
            return "0";
        }
        return formatNumberByFour(Double.valueOf(numStr));
    }

    /**
     * 最多保留小数点后4位,去除多余小数点和0 非逗号格式化
     *
     * @param num
     * @return
     */
    public static String formatNumberByFour(double num) {
        if (num == 0) {
            return "0";
        }
        DecimalFormat numberFormatT = new DecimalFormat("#0.######", new DecimalFormatSymbols(Locale.CHINA));
        String numStr = numberFormatT.format(num);
        if (numStr.indexOf(".") > 0) {
            int end = numStr.length() - numStr.indexOf(".");
            if (end > 5) {
                numStr = numStr.substring(0, numStr.indexOf(".") + 5);
            }
            numStr = numStr.replaceAll("0+?$", "");// 去掉多余的0
            numStr = numStr.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return numStr;
    }

    /**
     * 不四舍五入，保留两位小数，不足补零
     *
     * @return
     */
    public static String formatNumberKeepTwoDecimal(String num) {
        if (TextUtils.isEmpty(num)) {
            return "0";
        }
        return formatNumberKeepTwoDecimal(Double.valueOf(num));
    }

    /**
     * 不四舍五入，保留两位小数，不足补零
     *
     * @return
     */
    public static String formatNumberKeepTwoDecimal(double num) {
        return formatMoneyYuan2(num, false);
    }


    /**
     * 最多保留小数点后1位,去除多余小数点和0 非四舍五入 非逗号格式化
     *
     * @param numStr
     * @return
     */
    public static String formatNumberByOne(String numStr) {
        if (TextUtils.isEmpty(numStr)) {
            return "0";
        }
        return formatNumberByOne(Double.valueOf(numStr));
    }

    /**
     * 最多保留小数点后1位,去除多余小数点和0 非四舍五入 非逗号格式化
     *
     * @param num
     * @return
     */
    public static String formatNumberByOne(double num) {
        if (num == 0) {
            return "0";
        }
        DecimalFormat numberFormatT = new DecimalFormat("#0.######", new DecimalFormatSymbols(Locale.CHINA));
        String numStr = numberFormatT.format(num);
        if (numStr.indexOf(".") > 0) {
            int end = numStr.length() - numStr.indexOf(".");
            if (end > 2) {
                numStr = numStr.substring(0, numStr.indexOf(".") + 2);
            }
            numStr = numStr.replaceAll("0+?$", "");// 去掉多余的0
            numStr = numStr.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return numStr;
    }

    /**
     * 数字左边补零
     *
     * @param num
     * @return
     */
    public static String numLeftZero(int num) {
        // 得到一个NumberFormat的实例
        NumberFormat nf = NumberFormat.getInstance();
        // 设置是否使用分组
        nf.setGroupingUsed(false);
        // 设置最大整数位数
        nf.setMaximumIntegerDigits(2);
        // 设置最小整数位数
        nf.setMinimumIntegerDigits(2);

        return nf.format(num);
    }


    /**
     * 判断字符串是否正整数、正小数
     *
     * @param str
     * @return
     */
    public static boolean isNumeri(String str) {
        Pattern pattern = Pattern.compile("^(0|[1-9][0-9]*)+\\.{0,1}[0-9]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 0-9 阿拉伯数字转中文
     *
     * @param str
     * @return
     */
    private static String[] numArray = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};

    public static String numToHanzi(int num) {
        if (num >= 0 && num < numArray.length) {
            return numArray[num];
        }
        return "零";
    }


    /**
     * <br> Description: 金额的格式化,""或者0显示0.00,最大9999999999.99
     *
     * @param num 输入金额
     * @return String
     */
    public static String formatMoney(String num, String initStr) {
        Double min = 0.00;
        Double max = 9999999999.99;
        if (TextUtils.isEmpty(num)) {
            return initStr;
        }
        try {
            Double aDouble = Double.valueOf(num);
            if (aDouble >= min && aDouble <= max) {
                return NumberFormatUtil.formatMoneyYuan2(aDouble, false);
            }
            if (aDouble < min) {
                return "0.00";
            }
            if (aDouble >= max) {
                return "9,999,999,999.99";
            }
        } catch (NumberFormatException e) {
            return initStr;
        }
        return initStr;
    }


    /**
     * <br> Description: String to  Int
     *
     * @param str 数字类型的字符串
     * @return 整形数字
     */
    public static int strToInt(String str) {
        int num;

        try {
            num = Integer.valueOf(str);
            if (num <= -1) {
                num = 0;
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            num = 0;
        }

        return num;
    }

}
