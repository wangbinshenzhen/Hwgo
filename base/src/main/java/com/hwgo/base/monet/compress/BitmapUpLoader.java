package com.hwgo.base.monet.compress;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;


import com.hwgo.base.monet.compress.compressor.DefaultBitmapCompressor;
import com.hwgo.base.monet.compress.compressor.IBitmapCompressor;
import com.hwgo.base.monet.compress.encode.DefaultCodeProcessor;
import com.hwgo.base.monet.compress.encode.ICodeProcessor;
import com.hwgo.base.monet.compress.file.DefaultUpLoadFileLocator;
import com.hwgo.base.monet.compress.file.IUpLoadFileLocator;
import com.hwgo.base.monet.compress.wifichecker.DefaultWifiChecker;
import com.hwgo.base.monet.compress.wifichecker.IWifiChecker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * <br> ClassName:   BitmapUpLoader                        
 * <br> Description: 图片上传
 * <br>  
 */

public class BitmapUpLoader {
    /**
     * wifi下最大压缩大小
     */
    private final int WIFI_NET_UPLOAD_MAX_SIZE = 1024 * 1024;
    /**
     * 移动网络下最大压缩大小
     */
    private final int MOBILE_NET_UPLOAD_MAX_SIZE = 200 * 1024;
    /**
     * wifi下最小压缩长度
     */
    private final int UPLOAD_MIN_WIDTH = 1080;

    /**
     * 输出流为空错误
     */
    public static final int ERROR_BAO_NULL = 1;
    /**
     * 文件保存失败错误
     */
    public static final int ERROR_PATH_NULL = 2;

    private IBitmapCompressor mBitmapCompressor;
    private IUpLoadFileLocator mUpLoadFileLocator;
    private ICodeProcessor mCodeProcessor;
    private IWifiChecker mWifiChecker;
    private int mMinWidth = UPLOAD_MIN_WIDTH;
    private int mMaxSizeInMobileNet = MOBILE_NET_UPLOAD_MAX_SIZE;
    private int mMaxSizeInWifiNet = WIFI_NET_UPLOAD_MAX_SIZE;

    /**
     * 初始化
     */
    public BitmapUpLoader(Context context) {
        mBitmapCompressor = new DefaultBitmapCompressor(context);
        mUpLoadFileLocator = new DefaultUpLoadFileLocator(context);
        mCodeProcessor = new DefaultCodeProcessor();
        mWifiChecker = new DefaultWifiChecker(context);
    }

    /**
     *<br> Description: 压缩配置信息
     *<br> Author:      yexiaochuan
     *<br> Date:        2017/8/1 17:24
     * @param minWidth
     *                  最小压缩分辨率
     * @param maxSizeInMobileNet
     *                  移动网络最大的压缩质量大小
     * @param maxSizeInWifiNet
     *                  wifi网络最大的压缩质量大小
     */
    public void setConfig(int minWidth,int maxSizeInMobileNet,int maxSizeInWifiNet){
        if(minWidth != 0) {
            mMinWidth = minWidth;
        }

        if (maxSizeInMobileNet != 0) {
            mMaxSizeInMobileNet = maxSizeInMobileNet;
        }

        if (maxSizeInWifiNet != 0) {
            mMaxSizeInWifiNet = maxSizeInWifiNet;
        }
    }

    /**
     *<br> Description: 压缩图片资源
     *<br> Author:      wangbin
     *<br> Date:        2017/5/24 11:08
     *
     * @param uri      图片自资源地址
     * @param callback 异步压缩回调
     */
    public void compress(final Uri uri,
                         final IBitmapCompressCallback callback) {
        new AsyncTask<Uri, Integer, String>() {
            @Override
            protected String doInBackground(Uri... params) {
                if (mWifiChecker.isWifiNet()) {
                    mBitmapCompressor.setCompressLimitSize(mMaxSizeInWifiNet,
                            mMinWidth);
//                    LoggerManager.d("compress", "wifi_net max_size : "
//                            + mMaxSizeInWifiNet / 1024
//                            + "Kb min_width :"
//                            + mMinWidth);
                } else {
                    mBitmapCompressor.setCompressLimitSize(mMaxSizeInMobileNet,
                            mMinWidth);
//                    LoggerManager.d("compress", "mobile_net"
//                            + mMaxSizeInMobileNet / 1024
//                            + "Kb min_width :"
//                            + mMinWidth);
                }

                ByteArrayOutputStream baos = mBitmapCompressor.compress(uri);

                if (baos == null) {
                    callback.onFailed(ERROR_BAO_NULL, uri);
                    return null;
                }

                String path = mUpLoadFileLocator.saveCompress2File(baos);

                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (path == null) {
                    callback.onFailed(ERROR_PATH_NULL,uri);
                }

                return path;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                callback.onCompressing(values[0],uri);
            }

            @Override
            protected void onPostExecute(String s) {
                if (s != null) {
                    callback.onFinish(s, uri);
                }
            }
        }.execute(uri);
    }

    /**
     *<br> Description: 压缩图片资源(同步方法)
     *<br> Author:      wangbin
     *<br> Date:        2017/5/24 11:08
     *
     * @param uri      图片自资源地址
     */
    public String compress(final Uri uri) {
        if (mWifiChecker.isWifiNet()) {
            mBitmapCompressor.setCompressLimitSize(mMaxSizeInWifiNet,
                    mMinWidth);
//            LoggerManager.d("compress", "wifi_net max_size : "
//                    + mMaxSizeInWifiNet / 1024
//                    + "Kb min_width :"
//                    + mMinWidth);
        } else {
            mBitmapCompressor.setCompressLimitSize(mMaxSizeInMobileNet,
                    mMinWidth);
//            LoggerManager.d("compress", "mobile_net"
//                    + mMaxSizeInMobileNet / 1024
//                    + "Kb min_width :"
//                    + mMinWidth);
        }

        ByteArrayOutputStream baos = mBitmapCompressor.compress(uri);

        if (baos == null) {
            return null;
        }

        String path = mUpLoadFileLocator.saveCompress2File(baos);

        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }

    /**
     *<br> Description: 获取图片编码后的字符串
     *<br> Author:      wangbin
     *<br> Date:        2017/5/24 14:08
     * @param path     文件地址
     * @return         字符串
     */
    public String getBitmapEncryption(String path) {
        ByteArrayOutputStream baos = mUpLoadFileLocator.getBitmapBytes(path);
        String encryption = mCodeProcessor.encryptCode(baos);
        try {
            if (baos != null) {
                baos.flush();
                baos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encryption;
    }

    /**
     *<br> Description: 设置图片压缩模块接口
     *<br> Author:      wangbin
     *<br> Date:        2017/5/24 14:09
     * @param bitmapCompressor    图片压缩接口实现类
     */
    public void setmBitmapCompressor(IBitmapCompressor bitmapCompressor) {
        this.mBitmapCompressor = bitmapCompressor;
    }

    /**
     *<br> Description: 设置文件模块接口
     *<br> Author:      wangbin
     *<br> Date:        2017/5/24 14:10
     * @param upLoadFileLocator    文件模块接口实现类
     */
    public void setmUpLoadFileLocator(IUpLoadFileLocator upLoadFileLocator) {
        this.mUpLoadFileLocator = upLoadFileLocator;
    }

    /**
     *<br> Description: 设置流编码模块接口
     *<br> Author:      wangbin
     *<br> Date:        2017/5/24 14:11
     * @param codeProcessor    流编码模块实现类
     */
    public void setmCodeProcessor(ICodeProcessor codeProcessor) {
        this.mCodeProcessor = codeProcessor;
    }

    /**
     *<br> Description: 设置wifi检测模块接口
     *<br> Author:      wangbin
     *<br> Date:        2017/5/24 14:13
     * @param wifiChecker    wifi检测接口实现类
     */
    public void setmWifiChecker(IWifiChecker wifiChecker) {
        this.mWifiChecker = wifiChecker;
    }
}
