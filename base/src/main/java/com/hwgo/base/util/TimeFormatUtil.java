package com.hwgo.base.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * <br> ClassName:   TimeFormatUtil
 * <br> Description: 时间格式转换工具类
 * <br>
 */
public class TimeFormatUtil {

    public static String DATE_FORMAT_YM = "yyyy-MM";
    public static String DATE_FORMAT_YMD = "yyyy-MM-dd";
    public static String DATE_FORMAT_YMDHM = "yyyy-MM-dd HH:mm";
    public static String DATE_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_FORMAT_YMDHMS_SLASH = "yyyy/MM/dd HH:mm:ss";

    public static String DATE_FORMAT_MD = "MM-dd";
    public static String DATE_FORMAT_MDHM = "MM-dd\nHH:mm";

    public static String DATE_FORMAT_HMS = "HH:mm:ss";
    public static String DATE_FORMAT_YMD_SPOT = "yyyy.MM.dd";

    public static String DATE_FORMAT_M_ZH = "MM月";
    public static String DATE_FORMAT_MD_ZH = "MM月dd日";
    public static String DATE_FORMAT_MDH_ZH = "MM月dd日 HH时";
    public static String DATE_FORMAT_MDHM_ZH = "MM月dd日 HH:mm";

    public static String DATE_FORMAT_YM_ZH = "yyyy年MM月";
    public static String DATE_FORMAT_YMD_ZH = "yyyy年MM月dd日";
    public static String DATE_FORMAT_YMDHM_ZH = "yyyy年MM月dd日  HH时mm分";

    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>();

    public static SimpleDateFormat getDateFormat(String date_format) {
        SimpleDateFormat df = threadLocal.get();
        if (df == null || !date_format.equals(df.toPattern())) {
            threadLocal.remove();
            df = new SimpleDateFormat(date_format);
            threadLocal.set(df);
        }
        return df;
    }

    /**
     * <br> Description: 获取当前时间的日期（默认格式为yyyy-MM-dd HH:mm:ss）
     *
     * @return 返回当前时间的日期
     */
    public static String formatDate() {
        return getDateFormat(DATE_FORMAT_YMDHMS).format(new Date());
    }

    /**
     * <br> Description: 将当成时间转成想要的日期格式
     *
     * @param date_format 想要转成的格式
     * @return 返回转换后的日期
     */
    public static String formatDate(String date_format) {
        return getDateFormat(date_format).format(new Date());
    }

    /**
     * <br> Description: 将String类型的日期转成想要的格式
     *
     * @param dateStr         要转换的String类型的日期
     * @param date_format     想要转成的日期格式
     * @param specifiedFormat 几种固定的转换格式
     * @return 返回转换格式后的日期
     */
    public static String formatDate(String dateStr, String date_format, String specifiedFormat) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(specifiedFormat);
//            SimpleDateFormat dateFormat = getDateFormat(specifiedFormat);
            Date parse = simpleDateFormat.parse(dateStr);
            simpleDateFormat = getDateFormat(date_format);
            String format = simpleDateFormat.format(parse);
            return format;
        } catch (ParseException e) {
        }
        return "";
    }

    /**
     * <br> Description: 将String类型的日期转成想要的格式
     *
     * @param dateStr     要转换的String类型的日期
     * @param date_format 想要转成的日期格式
     * @return 返回转换格式后的日期
     */
    public static String formatDate(String dateStr, String date_format) {
        if (TextUtils.isEmpty(dateStr)) {
            return "";
        }
        String format = formatDate(dateStr, date_format, DATE_FORMAT_YMDHMS);
        if (!TextUtils.isEmpty(format)) {
            return format;
        }
        format = formatDate(dateStr, date_format, DATE_FORMAT_YMDHM);
        if (!TextUtils.isEmpty(format)) {
            return format;
        }
        format = formatDate(dateStr, date_format, DATE_FORMAT_YMD);
        if (!TextUtils.isEmpty(format)) {
            return format;
        }
        format = formatDate(dateStr, date_format, DATE_FORMAT_YM);
        return format;
    }

    /**
     * <br> Description: 将Date类型的日期转成想要的格式
     *
     * @param date        要转换的Date类型的日期
     * @param date_format 想要转成的日期格式
     * @return 返回转换格式后的日期
     */
    public static String formatDate(Date date, String date_format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = getDateFormat(date_format);
        return dateFormat.format(date);
    }

    public static String formtMdHmByYesterday() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        return format.format(date);
    }

    public static String formtYMD(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * <br> Description: 判断时间距现在是否超过一周
     *
     * @param time1 要判断的时间
     * @return 返回true则代表老师好过一周，反之则不超过
     */
    public static boolean isMoreOneWeek(long time1) {
        if (time1 <= 0) {
            return true;
        }
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        cal.setTime(new Date());
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        if (Integer.parseInt(String.valueOf(between_days)) < 7) {
            return false;
        }
        return true;
    }

    /**
     * <br> Description: 获取月份
     *
     * @param dateStr 时间
     * @return 返回月份，是当前月则返回本月
     */
    public static String getMonth(String dateStr) {
        try {
            if (TextUtils.isEmpty(dateStr)) {
                return "";
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Calendar a = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
            int currentYear = a.get(Calendar.YEAR);
            int currentMonth = (a.get(Calendar.MONTH) + 1);
            Date retDate = format.parse(dateStr);
            if (retDate.getYear() == currentYear && retDate.getMonth() == currentMonth) {
                return "本月";
            } else {
                return retDate.getMonth() + "月";
            }
        } catch (ParseException e) {
        }
        return "";
    }

    /**
     * <br> Description: 获取天时分秒
     *
     * @param second 时间戳
     * @return 返回转换后的天时分秒
     */
    public static String getDHMD(long second) {
        int m = 60;
        int h = m * 60;
        int d = h * 24;
        int dVal = (int) (second / d);
        int hVal = (int) (second % d / h);
        int mVal = (int) (second % d % h / m);
        int sVal = (int) (second % d % h % m);
        if (dVal > 0) {
            return dVal + "天" + hVal + "小时" + mVal + "分" + sVal + "秒";
        } else if (hVal > 0) {
            return hVal + "小时" + mVal + "分" + sVal + "秒";
        } else if (mVal > 0) {
            return mVal + "分" + sVal + "秒";
        } else {
            return sVal + "秒";
        }
    }

    /**
     * <br> Description: 获取带字体样式的天时分秒
     *
     * @param second 时间戳
     * @return 返回转换后的天时分秒
     */
    public static String getDHMDColor(long second) {
        int m = 60;
        int h = m * 60;
        int d = h * 24;
        int dVal = (int) (second / d);
        int hVal = (int) (second % d / h);
        int mVal = (int) (second % d % h / m);
        int sVal = (int) (second % d % h % m);
        if (dVal > 0) {
            return "<font color='#fa7d00'>" + dVal + "</font>天<font color='#fa7d00'>" + hVal + "</font>时<font color='#fa7d00'>" + mVal + "</font>分<font color='#fa7d00'>" + sVal + "</font>秒";
        } else if (hVal > 0) {
            return "<font color='#fa7d00'>" + hVal + "</font>时<font color='#fa7d00'>" + mVal + "</font>分<font color='#fa7d00'>" + sVal + "</font>秒";
        } else if (mVal > 0) {
            return "<font color='#fa7d00'>" + mVal + "</font>分<font color='#fa7d00'>" + sVal + "</font>秒";
        } else {
            return "<font color='#fa7d00'>" + sVal + "</font>秒";
        }
    }

    /**
     * Description: 冒号分隔 时：分：秒
     */
    public static String getHMDByColon(long second) {
        int m = 60;
        int h = m * 60;
        int d = h * 24;
        int dVal = (int) (second / d);
        int hVal = (int) (second % d / h);
        int mVal = (int) (second % d % h / m);
        int sVal = (int) (second % d % h % m);

        hVal = dVal * 24 + hVal;
        String hValStr = hVal < 10 ? "0" + hVal : String.valueOf(hVal);
        String mValStr = mVal < 10 ? "0" + mVal : String.valueOf(mVal);
        String sValStr = sVal < 10 ? "0" + sVal : String.valueOf(sVal);
        StringBuffer sb = new StringBuffer();
        sb.append(hValStr).append(":").append(mValStr).append(":").append(sValStr);
        return sb.toString();
    }

    /**
     * <br> Description: 根据时间获取时间戳
     *
     * @param dateStr 时间
     * @return 返回当时的时间戳
     */
    public static long formtYMDHMSTime(String dateStr) {
        try {
            if (TextUtils.isEmpty(dateStr)) {
                return 0;
            }
            SimpleDateFormat dateFormat = getDateFormat(DATE_FORMAT_YMDHMS);
            Date parse = dateFormat.parse(dateStr);
            return parse.getTime() / 1000;
        } catch (ParseException e) {
        }
        return 0;
    }

    /**
     * <br> Description: 格式化日期 yyyy-MM-ddHH:mm:ss
     *
     * @param dateStr 要格式化的日期
     * @return 返回格式化后的Date日期
     */
    public static Date formatYMDHMSDate(String dateStr) {
        try {
            if (TextUtils.isEmpty(dateStr)) {
                return null;
            }
            SimpleDateFormat dateFormat = getDateFormat("yyyy-MM-ddHH:mm:ss");
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
        }
        return null;
    }
}
