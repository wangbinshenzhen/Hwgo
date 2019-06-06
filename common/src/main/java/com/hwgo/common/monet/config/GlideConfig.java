package com.hwgo.common.monet.config;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

/**
 * <br> ClassName:   GlideConfig
 * <br> Description: Glide配置类
 * <br>
 */
public class GlideConfig {
    /*Glide option配置*/
    private RequestOptions mRequestOptions;
    /*Glide transition option配置*/
    private DrawableTransitionOptions mTransitionOptions;

    public GlideConfig(){
        mRequestOptions = new RequestOptions();
        mTransitionOptions = new DrawableTransitionOptions();
    }

    /**
     *<br> Description: 获取Glide的请求配置
     * @return
     *                  返回RequestOptions
     */
    public RequestOptions getRequestOptions() {
        return mRequestOptions;
    }

    /**
     *<br> Description: 设置Glide的请求配置
     * @param options
     *                  Glide的RequestOptions
     */
    public void setRequestOptions(RequestOptions options) {
        this.mRequestOptions = options;
    }

    /**
     *<br> Description: 获取Glide的TransitionOptions
     * @return
     *                  Glide的TransitionOptions
     */
    public DrawableTransitionOptions getTransitionOptions() {
        return mTransitionOptions;
    }

    /**
     *<br> Description: 设置Glide的TransitionOptions
     * @param options
     *                  Glide的TransitionOptions
     */
    public void setTransitionOptions(DrawableTransitionOptions options) {
        this.mTransitionOptions = options;
    }
}
