package com.hwgo.base.util;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <br> ClassName:   WriteToSD
 * <br> Description: 将图标写入Sd卡
 * <br>
 */
public class WriteToSD {

    private Context context;

    /**
     * <br> Description:将图标写入Sd卡
     *
     * @param context  上下文
     * @param filePath 要写入的文件夹路径
     * @param imgPath  要写入的图标路径
     */
    public WriteToSD(Context context, InputStream srcInputStream, String filePath, String imgPath) {
        this.context = context;
        if (!isExist(imgPath)) {
            write(srcInputStream, filePath, imgPath);
        }
    }

    /**
     * <br> Description: 将图标写入Sd卡
     *
     * @param filePath 要写入的文件夹路径
     * @param imgPath  要写入的图标路径
     */
    private void write(InputStream srcInputStream, String filePath, String imgPath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(imgPath);
            byte[] buffer = new byte[512];
            int count = 0;
            while ((count = srcInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, count);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            srcInputStream.close();
            System.out.println("success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *<br> Description: 判断图标是否已经存在
     */
    private boolean isExist(String imgPath) {
        File file = new File(imgPath);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }
}
