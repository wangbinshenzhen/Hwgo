package com.hwgo.base.monet.scene.transform;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import java.security.MessageDigest;

/**
 * <br> ClassName:   FitXy
 * <br> Description: fitxy转换类
 * <br>
 */
public class FitXy extends BitmapTransformation {
    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth,
                               int outHeight) {
        Bitmap.Config config = toTransform.getConfig() != null ? toTransform.getConfig() : Bitmap.Config.RGB_565;
        Bitmap toReuse = pool.get(outWidth, outHeight, config);

        final float widthPercentage = outWidth / (float) toTransform.getWidth();
        final float heightPercentage = outHeight / (float) toTransform.getHeight();

        TransformationUtils.setAlpha(toTransform, toReuse);
        Matrix matrix = new Matrix();
        matrix.setScale(widthPercentage,heightPercentage);
        Canvas canvas = new Canvas(toReuse);
        canvas.drawBitmap(toTransform, matrix, null);
        canvas.setBitmap(null);
        return toReuse;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }
}
