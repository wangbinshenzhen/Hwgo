package com.hwgo.base.monet.compress;

import android.net.Uri;

/**
 * <br> ClassName:   IBitmapCompressCallback
 * <br>
 */

public interface IBitmapCompressCallback {
    /**
     *<br> Description: 图片压缩中
     * @param progress    压缩进度
     * @param uri         图片资源地址
     */
    void onCompressing(int progress, Uri uri);

    /**
     *<br> Description: 图片压缩结束
     * @param filePath    文件地址
     * @param uri         图片资源地址
     */
    void onFinish(String filePath, Uri uri);

    /**
     *<br> Description: 图片压缩失败
     * @param errorCode    错误代码
     * @param uri          图片资源地址
     */
    void onFailed(int errorCode, Uri uri);
}
