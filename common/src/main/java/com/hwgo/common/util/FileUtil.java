package com.hwgo.common.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 *  ClassName:   FileUtil
 *  Description: 文件操作类
 * 
 *  Author:      FileUtil
 */
public class FileUtil {

    /**
     *  Description: 将对象序列化为本地文件
     *
     * @param obj      要序列化的对象
     * @param fileName 文件路径
     */
    public static void serializeFile(Context context, Object obj, String fileName) {
        FileOutputStream outStream = null;
        ObjectOutputStream objStream = null;
        try {
            outStream = context.openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            objStream = new ObjectOutputStream(outStream);
            objStream.writeObject(obj);
            objStream.close();
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objStream != null) {
                    objStream.close();
                }
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  Description: 从本地文件中加载序列化过的对象
     *     * @param fileName 本地文件路径
     * @return 返回序列化过的对象
     */
    public static Object loadSerializeFile(Context context, String fileName) {
        FileInputStream inputStream = null;
        ObjectInputStream objStream = null;
        try {
            if (!existFile(context, fileName)) {
                return null;
            }
            inputStream = context.openFileInput(fileName);
            objStream = new ObjectInputStream(inputStream);
            Object obj = objStream.readObject();
            return obj;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objStream != null) {
                    objStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     *  Description: 是否存在序列化的文件
     *
     * @param context  上下文
     * @param fileName 文件名
     * @return 是否存在 true代表存在，false代表不存在
     */
    private static boolean existFile(Context context, String fileName) {
        String[] files = context.fileList();
        for (String file : files) {
            if (fileName.toLowerCase().equals(file.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     *  Description: 获取SDCard的目录路径功能
     * 使用前需申请sdCard权限
     *  Date:        2017/7/20 11:11
     *
     * @return SDCard的目录路径
     */
    public static String getSDCardPath() {
        File sdcardDir = null;
        // 判断SDCard是否存在
        boolean sdcardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdcardExist) {
            sdcardDir = Environment.getExternalStorageDirectory();
        }
        return sdcardDir != null ? sdcardDir.toString() : "";
    }


    /**
     *  Description: 删除文件
     *
     * @param file 要删除的文件file对象
     * @return 是否删除成功，true代表删除成功，false代表删除失败
     */
    public static boolean deleteFoder(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                if (files != null) {
                    for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                        deleteFoder(files[i]); // 把每个文件 用这个方法进行迭代
                    }
                }
            }
            boolean isSuccess = file.delete();
            if (!isSuccess) {
                return false;
            }
        }
        return true;
    }

    /**
     *  Description: 递归创建文件夹
     *
     * @param file 要创建的文件对象
     * @return 返回文件路径，创建失败返回""
     */
    public static String createFile(File file) {
        try {
            if (file.getParentFile().exists()) {
                file.createNewFile();
                return file.getAbsolutePath();
            } else {
                createDir(file.getParentFile().getAbsolutePath());
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     *  Description: 递归创建文件夹
     *
     * @param dirPath 要创建的文件路径
     * @return 返回文件路径
     */
    public static String createDir(String dirPath) {
        try {
            File file = new File(dirPath);
            if (file.getParentFile().exists()) {
                file.mkdir();
                return file.getAbsolutePath();
            } else {
                createDir(file.getParentFile().getAbsolutePath());
                file.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dirPath;
    }
}
