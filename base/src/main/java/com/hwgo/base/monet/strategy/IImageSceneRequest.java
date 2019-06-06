package com.hwgo.base.monet.strategy;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.widget.ImageView;


import com.hwgo.base.monet.config.MonetConfig;
import com.hwgo.base.monet.scene.IConfigScene;
import com.hwgo.base.monet.target.ITarget;


/**
 * <br> ClassName:   IImageSceneRequest
 * <br> Description: 加载场景请求接口
 * <br>
 */
public interface IImageSceneRequest<Config> {
    /**
     * <br> Description: 设置图片加载器的配置
     *
     * @param configScene 场景配置
     */
    IImageSceneRequest scene(IConfigScene<Config> configScene);

    /**
     * <br> Description: 占位符
     *
     * @param resourceId 资源id
     * @return
     */
    IImageSceneRequest placeholder(int resourceId);

    /**
     * <br> Description: 占位符
     *
     * @param drawable drawable
     * @return
     */
    IImageSceneRequest placeholder(Drawable drawable);

    /**
     * <br> Description: 错误占位符
     *
     * @param resourceId 资源ID
     * @return
     */
    IImageSceneRequest error(int resourceId);

    /**
     * <br> Description: 错误占位符
     *
     * @param drawable drawable
     * @return
     */
    IImageSceneRequest error(Drawable drawable);

    /**
     * <br> Description: 中心适应
     */
    IImageSceneRequest fitCenter();

    /**
     * <br> Description: 铺满xy
     */
    IImageSceneRequest fitXY();

    /**
     * <br> Description: 中心裁剪
     */
    IImageSceneRequest centerCrop();

    /**
     * <br> Description: 中心包裹
     */
    IImageSceneRequest centerInside();

    /**
     * <br> Description: 圆角裁剪
     *
     * @param radius 角度
     * @return
     */
    IImageSceneRequest connerCrop(int radius);

    /**
     * <br> Description: 圆形裁剪
     */
    IImageSceneRequest circleCrop();

    /**
     * <br> Description: 图形转换
     *
     * @param transform 转换参数
     * @return
     */
    IImageSceneRequest transform(Object... transform);

    /**
     * <br> Description: 图片大小限制
     *
     * @param width  宽度限制
     * @param height 高度限制
     * @return
     */
    IImageSceneRequest resize(int width, int height);

    /**
     * <br> Description: 缓存设置
     *
     * @param type 缓存类型
     * @return
     */
    IImageSceneRequest cache(@IntRange(from = MonetConfig.CACHE_TYPE_NONE, to = MonetConfig.CACHE_TYPE_ALL) int type);

    /**
     * <br> Description: 解码设置
     *
     * @param type 解码类型
     * @return
     */
    IImageSceneRequest decode(@IntRange(from = MonetConfig.DECODE_TYPE_565, to = MonetConfig.DECODE_TYPE_8888) int type);

    /**
     * <br> Description: 输出Bitmap
     *
     * @param target Bitmap回调接口
     */
    void asBitmap(ITarget<Bitmap> target);

    /**
     * <br> Description: 输出Drawable
     *
     * @param target Drawable回调接口
     */
    void asDrawable(ITarget<Drawable> target);

    void downloadOnly();

    /**
     * <br> Description: 控件加载
     *
     * @param iv     ImageVIew加载
     * @param target Drawable回调接口
     */
    void into(ImageView iv, ITarget<Drawable> target);

    /**
     * <br> Description: 控件加载
     *
     * @param iv ImageVIew加载
     */
    void into(ImageView iv);
}
