package com.hwgo.base.util;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 *  ClassName:   DataConversionUtil
 *  Description: 各种数值转换工具类
 **/
public class DataConversionUtil {
    private static final String TAG = "DataConversionUtil";


    /**
     *  Description: 根据概率判断是否被选中
     *
     * @param probalility 概率
     * @return 返回1代表选中，返回0代表未选中，返回-1代表出错
     */
    public static int getProbalility(double probalility) {
        /**
         * 0出现的概率
         */
        double rate0 = 1 - probalility;
        /**
         * 1出现的概率
         */
        double rate1 = probalility;
        double randomNumber = Math.random();
        if (randomNumber >= 0 && randomNumber <= rate0) {
            return 0;
        } else if (randomNumber >= rate0 / 100 && randomNumber <= rate0 + rate1) {
            return 1;
        }
        return -1;
    }



    /**
     *  Description: string类型对象转为JSONObject
     *
     * @param str string对象
     * @return 返回JSONObject对象
     */
    public static JSONObject string2JSON(String str) {
        try {
            return new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    /**
     *  Description: 获取UTF-8编码格式化后的url
     *
     * @param url 网络地址
     * @return 返回格式化url
     */
    public static String encodeUrl(String url) {
        try {
            url = URLEncoder.encode(url, "UTF-8");
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
        return url;
    }

    /**
     *  Description: 二进制转字符串
     *
     * @param b 要转换的二进制
     * @return 返回字符串
     */
    private static String byte2hex(byte[] b) {
        StringBuffer sb = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                sb.append("0" + stmp);
            } else {
                sb.append(stmp);
            }
        }
        return sb.toString();
    }

    /**
     *  Description: 将图片Uri对象转成Bitmap
     *
     * @param uri 图片uri
     * @return 返回图片bitmap对象
     */
    public static Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            ContentResolver cr = ApplicationContext.instance().getContentResolver();
            InputStream is = cr.openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
}
