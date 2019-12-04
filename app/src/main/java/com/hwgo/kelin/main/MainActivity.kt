package com.hwgo.kelin.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.hwgo.common.router.helper.ForwardHelper
import com.hwgo.common.router.path.RouterPath
import com.hwgo.kelin.R
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = RouterPath.Main.MainActivity)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFlutter.setOnClickListener {
            ForwardHelper.forward2NativePage(RouterPath.Flutter.CommonFlutterActivity, null)
        }
        findViewById<View>(R.id.button3).setOnClickListener {
            ForwardHelper.forward2NativePage(RouterPath.Demo.ClientActivity, null)

        }
        findViewById<View>(R.id.button7).setOnClickListener {
            ForwardHelper.forward2NativePage(RouterPath.Demo.ThreadDemoActivity, null)
        }
        findViewById<View>(R.id.gotoStudyActivity).setOnClickListener {
            ForwardHelper.forward2NativePage(RouterPath.Study.RxStudyActivity, null)
        }
        findViewById<View>(R.id.gotoCoordinatorLayoutActivity).setOnClickListener {
            ForwardHelper.forward2NativePage(RouterPath.Demo.CoordinatorLayoutActivity, null)
        }

    }


}
