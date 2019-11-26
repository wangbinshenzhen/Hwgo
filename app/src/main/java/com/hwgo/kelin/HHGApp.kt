package com.hwgo.kelin

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

import com.hwgo.base.util.ApplicationContext


/**
 * ClassName: Hhg app
 * Description: 主Application  app启动位置
 *
 *
 * Author: wangbin
 * Date: 2019/06/06 11:48:28
 */
class HHGApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ApplicationContext.init(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        //多dex支撑
        MultiDex.install(this)
    }

}
