package com.hwgo.base.statusbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * 而要监听虚拟按键的展示收起和展示，可以在需要监听的页面为最外面的查看在onResume（）时
 * 添加mContainer.getViewTreeObserver().addOnGlobalLayoutListener(this);
 * 在onPause（）时移除mContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
 * 在onGlobalLayout（）中调用checkDeviceHasNavigationBar来检查从而进行判断。
 * <p>
 */
public class NavigationBarTool {
    /**
     * 下面方法可判断手机是否有虚拟按键功能：
     *
     * @param activity
     * @return
     */
    public static boolean hasNavigationBar(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        Display display = activity.getWindowManager().getDefaultDisplay();
        display.getMetrics(dm);

        DisplayMetrics realDisplayMetrics = new DisplayMetrics();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealMetrics(realDisplayMetrics);
        } else {
            Class c;
            try {
                c = Class.forName("android.view.Display");
                Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, realDisplayMetrics);
            } catch (Exception e) {
                realDisplayMetrics.setToDefaults();
                e.printStackTrace();
            }
        }

        Resources rs = activity.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        boolean hasNavBarFun = false;
        if (id > 0) {
            hasNavBarFun = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavBarFun = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavBarFun = true;
            }
        } catch (Exception e) {
            hasNavBarFun = false;
        }
        return hasNavBarFun;
    }

    /***虚拟键的高度z***/
    private static int NavigateBarHeight = -1;

    /**
     * 获取虚拟按键高度
     *
     * @param activity
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int getNavigationBarHeight(Activity activity) {
        if (NavigateBarHeight > 0) return NavigateBarHeight;
        DisplayMetrics dm = new DisplayMetrics();
        Display display = activity.getWindowManager().getDefaultDisplay();
        display.getMetrics(dm);
        int screenHeight = dm.heightPixels;

        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        display.getRealMetrics(realDisplayMetrics);
        int screenRealHeight = realDisplayMetrics.heightPixels;
        NavigateBarHeight = screenRealHeight - screenHeight;
        return NavigateBarHeight;    //screenRealHeight上面方法中有计算
    }

    /**
     * 检查在有虚拟按键的设备上面，虚拟按键是否展示出来（收起或展示）
     *
     * @param windowManager
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean checkDeviceHasNavigationBar(WindowManager windowManager) {
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        display.getRealMetrics(realDisplayMetrics);
        int screenRealHeight = realDisplayMetrics.heightPixels;
        int screenRealWidth = realDisplayMetrics.widthPixels;
        return (screenRealHeight - screenHeight) > 0;//screenRealHeight上面方法中有计算
    }

    /**
     * 通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
     *
     * @param activity
     * @return
     */
    public static boolean checkDeviceHasNavigationBar(Context activity) {

        boolean hasMenuKey = ViewConfiguration.get(activity)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            // 做任何你需要做的,这个设备有一个导航栏
            return true;
        }
        return false;
    }

    /**
     * <br> Description: 状态栏的高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
