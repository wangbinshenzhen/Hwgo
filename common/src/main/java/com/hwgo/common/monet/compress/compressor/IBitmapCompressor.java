package com.hwgo.common.monet.compress.compressor;

import android.net.Uri;

import java.io.ByteArrayOutputStream;

/**
 * <br> ClassName:   IBitmapCompressor
 * <br> Description: 图片压缩模块接口
 * <br>
  */

public interface IBitmapCompressor {
    /**
     *<br> Description: 设置压缩参数
       * @param maxSize     最大压缩大小
     * @param minWidth    最小压缩长度
     */
    void setCompressLimitSize(int maxSize, int minWidth);

    /**
     *<br> Description: 图片压缩
       * @param uri      图片资源
     * @return         输出流
     */
    ByteArrayOutputStream compress(Uri uri);
}
