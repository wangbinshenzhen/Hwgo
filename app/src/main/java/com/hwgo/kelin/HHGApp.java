package com.hwgo.kelin;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.hwgo.base.util.ApplicationContext;


/**
 * ClassName: Hhg app
 * Description: 主Application  app启动位置
 * <p>
 * Author: wangbin
 * Date: 2019/06/06 11:48:28
 */
public class HHGApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationContext.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //多dex支撑
        MultiDex.install(this);
    }
}
