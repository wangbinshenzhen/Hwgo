package com.hwgo.kelin.main

import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
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
