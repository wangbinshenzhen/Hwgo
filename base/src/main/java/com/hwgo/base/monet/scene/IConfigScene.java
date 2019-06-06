package com.hwgo.base.monet.scene;

/**
 * <br> ClassName:   IConfigScene
 * <br> Description: 场景配置接口
 * <br>
 */
public interface IConfigScene<T> {
    /**
     *<br> Description: 图片加载参数配置
     * @param info
     *                  图片参数
     */
    void config(T info);
}
