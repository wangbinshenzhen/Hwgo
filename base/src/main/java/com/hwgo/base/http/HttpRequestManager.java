package com.hwgo.base.http;

import android.os.Environment;

import com.hwgo.base.util.AppUtil;
import com.hwgo.base.util.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <br> ClassName: Http request manager
 * <br> Description: http请求管理类
 * <br>
 * <br> Author: wangbin
 * <br> Date: 2018/11/30 06:10:20
 */
public class HttpRequestManager {
    private static volatile HttpRequestManager instance;
    private Retrofit mRetrofit;
    private OkHttpClient mOkHttpClient;
    /**
     * 超时时间
     */
    private final int TIMEOUT = 10;

    private HttpRequestManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        Cache cache = new Cache(getCacheDirectory("httpCache"), 1024 * 1024 * 50);
        builder.cache(cache);
        try {
            builder
                    .sslSocketFactory(createSSLSocketFactory());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient = builder.build())
                .baseUrl("https://mapi.juanpi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
    /**
     * 创建 Interceptor
     *
     * @return
     */
    private static Interceptor createInterceptor() {
        Interceptor mInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                boolean netAvailable = AppUtil.isNetWorkAvailable();
                if (netAvailable) {
                    request = request.newBuilder()
                            //网络可用 强制从网络获取数据
                            .cacheControl(CacheControl.FORCE_NETWORK)
                            .build();
                } else {
                    request = request.newBuilder()
                            //网络不可用 从缓存获取
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
               // chain.proceed(request);
                if (netAvailable) {
                    response = response.newBuilder()
                            .removeHeader("Pragma")
                            // 有网络时 设置缓存超时时间1个小时
                            .header("Cache-Control", "public, max-age=" + 60 * 60)
                            .build();
                } else {
                    response = response.newBuilder()
                            .removeHeader("Pragma")
                            // 无网络时，设置超时为1周
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + 7 * 24 * 60 * 60)
                            .build();
                }


                return response;
            }
        };

        return mInterceptor;
    }

    /**
     * 创建 SSLSocketFactory
     *
     * @return
     */
    private static SSLSocketFactory createSSLSocketFactory() throws KeyManagementException, NoSuchAlgorithmException {
        TrustManager[] trustManagers = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };


        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustManagers, new SecureRandom());
        return sslContext.getSocketFactory();


    }

    /**
     * 获取缓存目录
     *
     * @param directory
     * @return
     */
    private static File getCacheDirectory(String directory) {
        File cacheDirectory = null;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            cacheDirectory = ApplicationContext.instance().getExternalCacheDir();
        }
        if (cacheDirectory == null) {
            cacheDirectory = ApplicationContext.instance().getCacheDir();
        }

        if (directory != null) {
            return new File(cacheDirectory, directory);
        }
        return cacheDirectory;

    }

    public static HttpRequestManager getInstance() {
        if (instance == null) {
            synchronized (HttpRequestManager.class) {
                if (instance == null) {
                    instance = new HttpRequestManager();
                }
            }
        }
        return instance;

    }

    /**
     * 创建 Service
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T createService(Class<T> service) {
        return mRetrofit.create(service);
    }

}
