package com.hwgo.base.monet.compress.encode;

import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * <br> ClassName:   DefaultCodeProcessor
 * <br> Description: 编码工具类
 * <br>
 */

public class DefaultCodeProcessor implements ICodeProcessor {
    @Override
    public String encryptCode(ByteArrayOutputStream baos) {
        String encryption = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
//        LoggerManager.d("compress", "encryption size : " + encryption.length() / 1024 + " Kb");
        return encryption;
    }
}
