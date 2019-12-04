package com.hwgo.kelin.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.hwgo.common.flutter.BaseFlutterActivity
import com.hwgo.common.router.path.RouterPath
import com.hwgo.firstmodule.RxStudyActivity
import com.hwgo.kelin.HWGApp
import com.hwgo.kelin.R
import com.hwgo.kelin.coordinator.CoordinatorLayoutActivity
import com.hwgo.kelin.messengerdemo.ClientActivity
import com.hwgo.kelin.threaddemo.ThreadDemoActivity
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = RouterPath.Main.MainActivity)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFlutter.setOnClickListener {
            startActivity(Intent(this, BaseFlutterActivity::class.java))
        }
        findViewById<View>(R.id.button3).setOnClickListener { v -> startActivity(Intent(this, ClientActivity::class.java)) }
        findViewById<View>(R.id.button7).setOnClickListener { v -> startActivity(Intent(this@MainActivity, ThreadDemoActivity::class.java)) }
        findViewById<View>(R.id.gotoStudyActivity).setOnClickListener { v -> startActivity(Intent(this@MainActivity, RxStudyActivity::class.java)) }
        findViewById<View>(R.id.gotoCoordinatorLayoutActivity).setOnClickListener { v -> startActivity(Intent(this@MainActivity, CoordinatorLayoutActivity::class.java)) }

    }


}
