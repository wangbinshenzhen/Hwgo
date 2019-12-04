package com.wangbin.demo.coordinator

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.alibaba.android.arouter.facade.annotation.Route
import com.hwgo.common.router.path.RouterPath
import com.wangbin.demo.R

/**
 * @Author wangbin
 * @Date 2019-06-15 16:57
 */
@Route(path = RouterPath.Demo.CoordinatorLayoutActivity)
class CoordinatorLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_coordinator_layout)
        supportActionBar!!.hide()
        val nestedScrollView = findViewById<NestedScrollView>(R.id.nestedScrollView)
        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY -> Log.d("wangbin", String.format("oldScrollX=%s scrollY=%s", "" + oldScrollY, "" + scrollY)) })

    }
}
