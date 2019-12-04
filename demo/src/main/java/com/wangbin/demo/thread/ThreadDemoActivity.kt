package com.wangbin.demo.thread

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.hwgo.common.router.path.RouterPath

import com.wangbin.demo.R

@Route(path = RouterPath.Demo.ThreadDemoActivity)
class ThreadDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_activity_thread_demo)
        val t = object : Thread() {
            override fun run() {
                var i = 0
                while (!isInterrupted) {
                    Log.d("kelin", "i=$i")
                    try {
                        Thread.sleep(1000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                        Log.d("kelin", "" + e)
                        interrupt()


                    }

                    i++
                }
            }
        }

        t.start()
        window.decorView.postDelayed({ t.interrupt() }, 1000)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

        }
    }
}
