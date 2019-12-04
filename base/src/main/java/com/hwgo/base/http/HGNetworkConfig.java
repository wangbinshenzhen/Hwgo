package com.hwgo.base.http;

/**
 * Description ：网络参数配置
 */
public class HGNetworkConfig {
    private HGNetworkConfig() {
    }

    public static final String TERMINAL_API_V2 = "terminal-api-v2";
    /**
     * 读取超时
     */
    public static final long READ_TIME_OUT = 20 * 1000L;
    /**
     * 连接超时
     */
    public static final long CONNECT_TIME_OUT = 20 * 1000L;
    /**
     * 缓存大小
     */
    public static final long CACHE_STALE_SEC = 10 * 1024 * 1024L;

}
