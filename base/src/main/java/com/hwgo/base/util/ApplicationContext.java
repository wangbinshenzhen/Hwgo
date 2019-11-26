package com.hwgo.base.util;

import android.app.Application;
import android.content.res.Resources;
import android.os.Build;
import androidx.annotation.ArrayRes;
import androidx.annotation.ColorRes;
import androidx.annotation.StringRes;
import android.util.Log;

/**
 * <br> ClassName:   ApplicationContext
 * <br> Description: 提供全局的ApplicationContext，并封装各类getResources()方法
 * <p>
 */
public class ApplicationContext {

    @SuppressWarnings("all")
    private static Application mApplication;
    @SuppressWarnings("all")
    private static Application mApplicationFromReflect;

    static {
        Application app = null;
        try {
            app = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
            if (app == null) {
                throw new IllegalStateException("Static initialization of Applications must be on main thread.");
            }
        } catch (Exception e) {
            Log.e("ApplicationContext", "Failed to get current application from AppGlobals." + e.getMessage());
            try {
                app = (Application) Class.forName("android.app.ActivityThread")
                                         .getMethod("currentApplication").invoke(null);
            } catch (Exception ex) {
                Log.e("ApplicationContext", "Failed to get current application from ActivityThread." + ex.getMessage());
            }
        } finally {
            mApplicationFromReflect = app;
        }
        if (mApplicationFromReflect == null) {
            mApplicationFromReflect = mApplication;
        }
    }

    public static void init(Application application) {
        if (application == null) {
            throw new NullPointerException("application == null");
        }
        mApplication = application;
    }

    /** 获取ApplicationContext */
    public static Application instance() {
        if (mApplication != null) {
            return mApplication;
        } else if(mApplicationFromReflect != null){
            return mApplicationFromReflect;
        } else {
            throw new IllegalStateException("need init()");
        }
    }

    /**
     * 获得资源
     */
    public static Resources getResources() {
        return instance().getResources();
    }

    /**
     * 获得string类型的数据
     *
     * @param resId
     * @return
     */
    public static String getString(@StringRes int resId) {
        return instance().getString(resId);
    }

    /**
     * 获取string类型
     *
     * @param resId
     * @param formatArgs
     * @return
     */
    public static String getString(@StringRes int resId, Object... formatArgs) {
        return instance().getString(resId, formatArgs);
    }

    /**
     * 获得数组集合
     *
     * @param resId
     * @return
     */
    public static String[] getStringArray(@ArrayRes int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获得颜色值
     *
     * @param resId
     * @return
     */
    public static int getColor(@ColorRes int resId) {
        if (Build.VERSION.SDK_INT >= 23) {
            return getResources().getColor(resId, instance().getTheme());
        }else{
            return getResources().getColor(resId);
        }
    }

    public static int dp2px(float dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(float px) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
