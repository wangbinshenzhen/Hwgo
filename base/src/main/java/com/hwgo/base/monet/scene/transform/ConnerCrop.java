package com.hwgo.base.monet.scene.transform;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.util.Preconditions;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

/**
 * <br> ClassName:   ConnerCrop
 * <br> Description: 圆角裁剪，修复Glide原生的圆角裁剪会与fitcenter共用时产生图片像素拉伸的问题
 * <br>
 */
public class ConnerCrop extends BitmapTransformation {
    private static final String ID = "com.paisheng.lib.picture.scene.transform.ConnerCrop";
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    private final int roundingRadius;

    /**
     *<br> Description: 构造
     * @param roundingRadius
     *                  角度
     */
    public ConnerCrop(int roundingRadius) {
        Preconditions.checkArgument(roundingRadius > 0, "roundingRadius must be greater than 0.");
        this.roundingRadius = roundingRadius;
    }


    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap inBitmap, int outWidth,
                               int outHeight) {
        Preconditions.checkArgument(roundingRadius > 0, "roundingRadius must be greater than 0.");

        // Alpha is required for this transformation.
        Bitmap toTransform = getAlphaSafeBitmap(pool, inBitmap);
        Bitmap result = pool.get(toTransform.getWidth(), toTransform.getHeight(), Bitmap.Config.ARGB_8888);

        result.setHasAlpha(true);

        BitmapShader shader = new BitmapShader(toTransform, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);
        RectF rect = new RectF(0, 0, result.getWidth(), result.getHeight());
        Canvas canvas = new Canvas(result);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawRoundRect(rect, roundingRadius, roundingRadius, paint);
        canvas.setBitmap(null);

        if (!toTransform.equals(inBitmap)) {
            pool.put(toTransform);
        }

        return result;
    }

    /**
     *<br> Description: 获取ARGB_8888的Bitmap
     * @param pool
     *                  图片池
     * @param maybeAlphaSafe
     *                  若Bitmap为ARGB_8888则使用此Bitmap，否则重新创建ARGB_8888的Bitmap
     * @return
     *                  ARGB_8888的Bitmap
     */
    private Bitmap getAlphaSafeBitmap(@NonNull BitmapPool pool,
                                      @NonNull Bitmap maybeAlphaSafe) {
        if (Bitmap.Config.ARGB_8888.equals(maybeAlphaSafe.getConfig())) {
            return maybeAlphaSafe;
        }

        Bitmap argbBitmap = pool.get(maybeAlphaSafe.getWidth(), maybeAlphaSafe.getHeight(),
                Bitmap.Config.ARGB_8888);
        new Canvas(argbBitmap).drawBitmap(maybeAlphaSafe, 0 /*left*/, 0 /*top*/, null /*pain*/);

        // We now own this Bitmap. It's our responsibility to replace it in the pool outside this method
        // when we're finished with it.
        return argbBitmap;
    }

    @Override
    public int hashCode() {
        return ID.hashCode() + roundingRadius;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);

        byte[] radiusData = ByteBuffer.allocate(4).putInt(roundingRadius).array();
        messageDigest.update(radiusData);
    }
}
