package com.hwgo.common.monet.scene.transform;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

/**
 * <br> ClassName:   GlideTransform
 * <br> Description: Glide图片变换
 * <br>
 */
public class GlideTransform implements ITransform {
    @Override
    public Object fitCenter() {
        return new FitCenter();
    }

    @Override
    public Object fitXY() {
        return new FitXy();
    }

    @Override
    public Object centerCrop() {
        return new CenterCrop();
    }

    @Override
    public Object centerInside() {
        return new CenterInside();
    }

    @Override
    public Object connerCrop(int radius) {
        return new RoundedCorners(radius);
    }

    @Override
    public Object circleCrop() {
        return new CircleCrop();
    }
}
