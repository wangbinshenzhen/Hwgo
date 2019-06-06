package com.hwgo.base.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * <br> ClassName:   UtilArith
 * <br> Description: java运算工具
 * 由于Java的简单类型不能够精确的对浮点数进行运算，
 * 这个工具类提供精确的浮点数运算，包括加减乘除和四舍五入。
 * <br>
 */
public class UtilArith {

    /***默认除法运算精度***/
    private static final int DEF_DIV_SCALE = 10;

    /**
     * <br> Description: 提供精确的加法运算
     * <br> Author:      fangbingran
     * <br> Date:        2017/7/25 17:36
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 返回两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * <br> Description:提供精确的加法运算
     * <br> Author:      fangbingran
     *
     * @param v1       被加数
     * @param v2       加数
     * @param newScale 保留几位,不四舍五入
     * @return 返回两个参数的和
     */
    public static double add(double v1, double v2, int newScale) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).setScale(newScale, BigDecimal.ROUND_FLOOR).doubleValue();
    }

    /**
     * <br> Description: 提供精确的减法运算
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 返回两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();

    }

    /**
     * <br> Description: 提供精确的减法运算，默认四舍五入
     *
     * @param v1    被减数
     * @param v2    减数
     * @param scale 保留位数
     * @return 默认四舍五入
     */
    public static double sub(double v1, double v2, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * <br> Description: 提供精确的乘法运算*
     * @param v1 被乘数
     * @param v2 乘数
     * @return 返回两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();

    }

    /**
     *<br> Description: 精确比较两数大小
     * @param v1
     *                  v1
     * @param v2
     *                  v2
     * @return
     *                  1:v1>v2 -1:v1<v2 0:v1==v2
     */
    public static int compare(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(2, RoundingMode.HALF_UP);
        return b1.compareTo(b2);
    }

    /**
     * <br> Description: 提供精确的乘法运算，默认保留两位小数
     *
     * @param v1   被乘数
     * @param v2   乘数
     * @param mode 保留方式
     * @return 默认保留两位小数
     */
    public static double mul(double v1, double v2, RoundingMode mode) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        if (mode == RoundingMode.DOWN) {
            double value = b1.multiply(b2).doubleValue();
            DecimalFormat df = new DecimalFormat("#0.00", new DecimalFormatSymbols(Locale.CHINA));
            df.setRoundingMode(RoundingMode.DOWN);
            String s = df.format(value);
            return Double.valueOf(s);
        } else {
            return b1.multiply(b2).setScale(2, mode).doubleValue();
        }
    }

    /**
     * <br> Description: 提供精确的乘法运算，默认四舍五入
     *
     * @param v1    被乘数
     * @param v2    乘数
     * @param scale 保留位数
     * @return 默认四舍五入
     */
    public static double mul(double v1, double v2, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * <br> Description: 提供精确的乘法运算，默认四舍五入
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 默认四舍五入
     */
    public static int mulToInt(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).setScale(0, RoundingMode.HALF_UP).intValue();
    }

    /**
     * <br> Description: 提供（相对）精确的除法运算，当发生除不尽的情况时，
     * 精确到小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);

    }

    /**
     * <br> Description: 提供（相对）精确的除法运算。当发生除不尽的情况时，
     * 由scale参数指定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

    /**
     * <br> Description: 除法运算。
     *
     * @param v1   被除数
     * @param v2   除数
     * @param mode 位数保留方式
     * @return 返回值
     */
    public static int divToInt(double v1, double v2, RoundingMode mode) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        if (mode == RoundingMode.DOWN) {
            double value = div(v1, v2);
            DecimalFormat df = new DecimalFormat("#0", new DecimalFormatSymbols(Locale.CHINA));
            df.setRoundingMode(RoundingMode.DOWN);
            String s = df.format(value);
            return Integer.valueOf(s);
        } else {
            return b1.divide(b2, 0, mode).intValue();
        }
    }

    /**
     * <br> Description: 提供精确的小数位四舍五入处理
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 返回四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

    }
}
