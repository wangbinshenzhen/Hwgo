package com.hwgo.base.monet.scene.transform;


import com.hwgo.base.monet.loader.Monet;

/**
 * <br> ClassName:   TransformHelper
 * <br> Description: 图片变换Helper
 * <br>
 */
public class TransformHelper {

    /**
     *<br> Description: 获取Helper
     */
    private static ITransform getHelper() {
        switch (Monet.getCurrentStrategy()) {
            case Monet.STRATEGY_GLIDE:
                return new GlideTransform();
            default:
                return new GlideTransform();
        }
    }

    public static Object fitCenter() {
        return getHelper().fitCenter();
    }

    public static Object centerCrop() {
        return getHelper().centerCrop();
    }

    public static Object centerInside() {
        return getHelper().centerInside();
    }

    public static Object connerCrop(int radius) {
        return getHelper().connerCrop(radius);
    }

    public static Object circleCrop() {
        return getHelper().circleCrop();
    }

    public static Object fitXY() { return getHelper().fitXY(); }
}
