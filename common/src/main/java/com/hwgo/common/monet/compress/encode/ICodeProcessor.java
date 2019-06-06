package com.hwgo.common.monet.compress.encode;

import java.io.ByteArrayOutputStream;

/**
 * <br> ClassName:   ICodeProcessor
 * <br> Description: 数据流格式化模块接口
 * <br>
 */

public interface ICodeProcessor {
    /**
     *<br> Description: 为数据流编码
     * @param baos    需要编码的输出流
     * @return
     */
    String encryptCode(ByteArrayOutputStream baos);
}
