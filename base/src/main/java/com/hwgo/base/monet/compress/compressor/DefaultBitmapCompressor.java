package com.hwgo.base.monet.compress.compressor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <br> ClassName:   DefaultBitmapCompressor
 * <br> Description: 图片压缩类
 * <br>
 */

public class DefaultBitmapCompressor implements IBitmapCompressor {
    private int mMaxSize = 200 * 1024;
    private int mMinWidth = 1080;
    private Context mContext;

    public DefaultBitmapCompressor(Context context) {
        mContext = context;
    }

    @Override
    public void setCompressLimitSize(int maxSize, int minWidth) {
        mMaxSize = maxSize;
        mMinWidth = minWidth;
    }

    @Override
    public ByteArrayOutputStream compress(Uri uri) {
        Bitmap bitmap = null;
        try {
//            LoggerManager.d("compress", "decode begin");
            bitmap = samplingCompress(uri);
//            LoggerManager.d("compress", "bitmap after samplingCompress width,height("
//                    + bitmap.getWidth()
//                    + "," + bitmap.getHeight() + ")");
            ByteArrayOutputStream out = qualityCompress(bitmap);
//            LoggerManager.d("compress", "decode end");
            return out;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
        return null;
    }

    /**
     *<br> Description: 采样率压缩
       * @param uri    图片资源地址
     * @return  Bitmap
     * @throws IOException IO错误
     */
    private Bitmap samplingCompress(Uri uri) throws IOException {
        BitmapFactory.Options sourceOption = new BitmapFactory.Options();
        sourceOption.inJustDecodeBounds = true;
        InputStream sourceStream = mContext.getContentResolver().openInputStream(uri);
        BitmapFactory.decodeStream(sourceStream, null, sourceOption);

        if (sourceStream != null) {
            sourceStream.close();
        }

//        LoggerManager.d("compress", "samplingCompress begin");
//        LoggerManager.d("compress", "bitmap before samplingCompress width,height:("
//                + sourceOption.outWidth + "," + sourceOption.outHeight + ")");

        int be = computeSampleSize(sourceOption);
//        LoggerManager.d("compress", "be = " + be);
        BitmapFactory.Options targetOption = new BitmapFactory.Options();
        targetOption.inSampleSize = be;
        targetOption.inPreferredConfig = Bitmap.Config.RGB_565;
        InputStream targetStream = mContext.getContentResolver().openInputStream(uri);

        //旋转为0度图片
        int degree = getBitmapDegree(uri.getPath());
//        LoggerManager.d("compress", "rotate Bitmap " + degree + " degree");
        Bitmap rotateBitmap = rotateBitmapByDegree(BitmapFactory.decodeStream(targetStream, null, targetOption),degree);
        if (targetStream != null) {
            targetStream.close();
        }
        return rotateBitmap;
    }

    /**
     *<br> Description: 读取图片的旋转的角度
      * @param path
     *            图片绝对路径
     * @return 图片的旋转角度
     */
    private int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     *<br> Description: 将图片按照某个角度进行旋转
      * @param bm
     *            需要旋转的图片
     * @param degree
     *            旋转角度
     * @return 旋转后的图片
     */
    private static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        if(degree == 0)
            return bm;

        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    /**
     *<br> Description: 采样率压缩计算
      * @param options  压缩配置
     * @return 压缩比例
     */
    private int computeSampleSize(BitmapFactory.Options options) {
        int initialSize = computeInitialSampleSize(options);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    /**
     *<br> Description: 计算压缩比例
      * @param options  压缩配置
     * @return 压缩比例
     */
    private int computeInitialSampleSize(BitmapFactory.Options options) {
        double w = options.outWidth;
        double h = options.outHeight;

        return (mMinWidth == -1) ? 4 : (int) Math.min(Math.round(w / mMinWidth),
                Math.round(h / mMinWidth));
    }

    /**
     *<br> Description: 质量压缩
      * @param bitmap   bitmap图
     * @return         ByteArrayOutputStream输出流
     */
    private ByteArrayOutputStream qualityCompress(Bitmap bitmap) {
//        LoggerManager.d("compress", "compress begin");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
//        LoggerManager.d("compress", "before compress size :" + baos.size() / 1024 + "Kb");

        int options = 100;
        int step;

        // 循环判断和压缩图片
        while (shouldQualityCompress(baos) && options > 0) {
            step = computeQualityPercentStep(baos);
            options -= step;
            baos.reset();
//            LoggerManager.d("compress", "compress again quality compress percent : " + options + "%");
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
//            LoggerManager.d("compress", "after quality compress size :" + baos.size() / 1024 + "Kb");
        }
//        LoggerManager.d("compress", "compress end");
        return baos;
    }

    /**
     *<br> Description: 判断是否需要再进行质量压缩
      * @param baos     压缩输出流
     * @return         boolean
     */
    private boolean shouldQualityCompress(ByteArrayOutputStream baos) {
        //如果小于允许的质量范围，就不再压缩
        if (baos.size() <= mMaxSize) {
            return false;
        }

        double percent = (double)baos.size() / (double) mMaxSize;
        //计算当前压缩流的大小与质量范围的差值
        double diff = percent - 1;

        //小额的质量大小[0 - 200]
        int minRange = 200 * 1024;
        //小额的质量控制范围
        double minRangeDiff = 0.5f;
        //大额的质量大小[1024 - max]
        int maxRange = 1024 * 1024;
        //大额的质量控制范围
        double maxRangeDiff = 0.1f;
        //中额的质量控制范围(200 - 1024)
        double middleRangeDiff = 0.2f;

        //如果差值质量控范围内就不再压缩
        if (mMaxSize <= minRange && diff <= minRangeDiff) {
            return false;
        } else if (mMaxSize >= maxRange && diff <= maxRangeDiff) {
            return false;
        } else {
            if (diff <= middleRangeDiff) {
                return false;
            }
        }

        return true;
    }

    /**
     *<br> Description: 计算质量压缩比例的压缩粒度
      * @param baos     压缩输出流
     * @return         压缩粒度
     */
    private int computeQualityPercentStep(ByteArrayOutputStream baos) {
        int step;
        if (baos.size() / mMaxSize > 2) {
            step = 5;
        } else {
            step = 2;
        }
        return step;
    }
}
