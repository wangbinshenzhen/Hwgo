package com.hwgo.base.monet.compress.file;

import java.io.ByteArrayOutputStream;

/**
 * <br> ClassName:   IUpLoadFileLocator
 * <br> Description: 图片文件模块接口
 * <br>
 */

public interface IUpLoadFileLocator {
    /**
     *<br> Description: 将压缩后的字节数组流写入文件
     * @param out 字节数组流
     * @return    字符串
     */
    String saveCompress2File(ByteArrayOutputStream out);

    /**
     *<br> Description: 获取图片字节流
     * @param path  图片本地路径
     * @return      输出流
     */
    ByteArrayOutputStream getBitmapBytes(String path);
}
