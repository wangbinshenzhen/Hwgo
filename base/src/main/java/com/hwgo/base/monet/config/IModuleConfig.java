package com.hwgo.base.monet.config;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;

public interface IModuleConfig {
    /**
     *<br> Description: 配置Glide的缓存大小、Disk存储大小等
     * @param context
     *                  上下文
     * @param builder
     *                  配置Builder
     */
    void applyOptions(Context context, GlideBuilder builder);

    /**
     *<br> Description: 注册Glide的组件
     * @param context
     *                  上下文
     * @param registry
     *                  注册器
     */
    void registerComponents(Context context, Registry registry);
}
