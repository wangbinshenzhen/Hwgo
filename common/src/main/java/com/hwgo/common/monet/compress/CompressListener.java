package com.hwgo.common.monet.compress;

import android.support.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Created by DELL .
 */

public class CompressListener<R> implements RequestListener<R> {
    private int width, height, maxSize;

    public CompressListener(int width, int height, int maxSize) {
        this.width = width;
        this.height = height;
        this.maxSize = maxSize;
    }

    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<R> target, boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
        return false;
    }
}
