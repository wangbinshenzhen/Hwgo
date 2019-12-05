package com.hwgo.kelin.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hwgo.common.router.path.RouterPath
import com.hwgo.kelin.R
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = RouterPath.Main.MainActivity)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFlutter.setOnClickListener {
            ARouter.getInstance().build(RouterPath.Flutter.CommonFlutterActivity).navigation()
            openFlutter.postDelayed({
                ARouter.getInstance().build(RouterPath.Flutter.CommonFlutterActivity).navigation()
            },3000)
        }
        gotoClientActivityDemo.setOnClickListener {
            ARouter.getInstance().build(RouterPath.Demo.ClientActivity).navigation()
        }
        gotoThreadDemoActivity.setOnClickListener {
            ARouter.getInstance().build(RouterPath.Demo.ThreadDemoActivity).navigation()
        }
        gotoRxJavaStudyActivity.setOnClickListener {
            ARouter.getInstance().build(RouterPath.Study.RxStudyActivity).navigation()
        }
        gotoCoordinatorLayoutActivity.setOnClickListener {
            ARouter.getInstance().build(RouterPath.Demo.CoordinatorLayoutActivity).navigation()
        }
        openMain.setOnClickListener {
            ARouter.getInstance().build(RouterPath.Main.MainActivity).navigation()
        }

    }


}
