package com.hwgo.kelin

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.hwgo.base.util.ApplicationContext
import com.hwgo.base.util.CECAppHelper
import io.flutter.view.FlutterMain


/**
 * ClassName: Hhg app
 * Description: 主Application  app启动位置
 *
 *
 * Author: wangbin
 * Date: 2019/06/06 11:48:28
 */
class HWGApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ApplicationContext.init(this)
        initWhenApplicationCreated(this)
        FlutterMain.startInitialization(this);
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        //多dex支撑
        MultiDex.install(this)
    }

    /**
     * 初始化
     * @param application Application
     */
    private fun initWhenApplicationCreated(application: Application) {
        //todo 初始化
        initMainProcess(application)
    }

    /**
     * 初始化主进程
     * @param application Application
     */
    private fun initMainProcess(application: Application) {
        if (!CECAppHelper.isMainProcess(application)) {
            return
        }
        //路由
        initRouter(application, BuildConfig.DEBUG)


    }

    /**
     * 初始化router
     * @param application Application
     * @param debug Boolean
     */
    private fun initRouter(application: Application, debug: Boolean) {
        if (debug) {
            ARouter.openLog()
            ARouter.openDebug()
            ARouter.printStackTrace()
        }
        ARouter.init(application)

    }
}
