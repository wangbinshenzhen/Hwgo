package com.hwgo.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <br> ClassName:   AdjustUtil
 * <br> Description: 一些界面调整的工具类
 * <br>
 */
public class AdjustUtil {

    /**
     * <br> Description: 键盘弹起时，调整不应被遮挡的控件*
     * @param root         最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的view，滚动root,使scrollToView在root可视区域的底部
     * @param srollHeight  用于判断是否键盘显示隐藏引起的变动
     * @return 返回当前界面滚动的高度
     */
    public static int keyBoardShowAdjust(Context context, View root, final View scrollToView, int srollHeight) {
        Rect rect = new Rect();
        if (root == null || rect == null) {
            return srollHeight;
        }
        root.getWindowVisibleDisplayFrame(rect);
        int rootInvisibleHeight = root.getRootView()
                .getHeight() - rect.bottom - getNavigationBarHeight(context);//被擋住的高度
        int[] location = new int[2];
        scrollToView.getLocationInWindow(location);
        int height = (location[1] + scrollToView
                .getHeight()) - rect.bottom;//控件要滚动的高度
//        LoggerManager.e("rootInvisibleHeight:" + rootInvisibleHeight + "----height:" + height);
        if (rootInvisibleHeight > UiUtil.dp2px(33) && (srollHeight < height || srollHeight == 0)) {
            root.scrollTo(0, height > 0 ? height : 0);
            return height;
        } else if (rootInvisibleHeight < UiUtil.dp2px(33) && srollHeight != 0) {
            root.scrollTo(0, 0);
            return 0;
        }
        return srollHeight;
    }

    /**
     * <br> Description: 获取虚拟按键的高度
     * <br> Author:      zhangweiqiang
     * <br> Date:        2017/11/6 10:35
     */
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (hasNavBar(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * <br> Description: 检查是否存在虚拟按键栏
     * <br> Author:      zhangweiqiang
     * <br> Date:        2017/11/6 10:32
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * <br> Description: 判断虚拟按键栏是否重写
     * <br> Author:      zhangweiqiang
     * <br> Date:        2017/11/6 10:32
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return sNavBarOverride;
    }
}
