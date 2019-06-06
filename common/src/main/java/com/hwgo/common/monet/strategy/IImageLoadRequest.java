package com.hwgo.common.monet.strategy;

import android.net.Uri;

/**
 * <br> ClassName:   IImageLoadRequest
 * <br> Description: 加载请求管理接口
 * <br>
 */
public interface IImageLoadRequest {
    /**
     *<br> Description: 设置加载资源
     * @param resourceID
     *                  本地资源ID
     * @return
     *                  配置器
     */
    IImageSceneRequest load(int resourceID);

    /**
     *<br> Description: 设置加载资源
     * @param url
     *                  网络URL地址，本地文件地址等
     * @return
     *                  配置器
     */
    IImageSceneRequest load(String url);

    /**
     *<br> Description: 设置加载资源
     * @param uri
     *                  本地资源uri
     * @return
     *                  配置器
     */
    IImageSceneRequest load(Uri uri);

    /**
     *<br> Description: 缓存清理
     */
    void clearMemory();

    /**
     *<br> Description: 清除硬盘缓存
     */
    void clearDisk();
}
