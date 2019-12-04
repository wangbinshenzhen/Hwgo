package com.hwgo.kelin.coordinator

import android.os.Bundle
import androidx.core.widget.NestedScrollView
import androidx.appcompat.app.AppCompatActivity
import android.util.Log

import com.hwgo.kelin.R

/**
 * @Author wangbin
 * @Date 2019-06-15 16:57
 */
class CoordinatorLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator_layout)
        supportActionBar!!.hide()
        val nestedScrollView = findViewById<NestedScrollView>(R.id.nestedScrollView)
        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY -> Log.d("wangbin", String.format("oldScrollX=%s scrollY=%s", "" + oldScrollY, "" + scrollY)) })

    }
}