package com.hwgo.kelin.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.hwgo.base.http.HttpRequestManager
import com.hwgo.base.http.HttpResult
import com.hwgo.firstmodule.RxStudyActivity
import com.hwgo.kelin.R
import com.hwgo.kelin.main.bean.User
import com.hwgo.kelin.main.service.MovieService
import com.hwgo.kelin.messengerdemo.ClientActivity
import com.hwgo.kelin.threaddemo.ThreadDemoActivity
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    internal var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.button3).setOnClickListener { v -> startActivity(Intent(this, ClientActivity::class.java)) }

        val moviceService = HttpRequestManager.getInstance().createService(MovieService::class.java)
        val kelin = moviceService.user
        kelin.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : MyObserver<HttpResult<User>>() {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(userHttpResult: HttpResult<User>) {
                        Log.d("kelin", "onNext =" + userHttpResult.code)
                    }

                    override fun onError(e: Throwable) {
                        Log.d("kelin", "Throwable =$e")
                    }

                    override fun onComplete() {
                        Log.d("kelin", "onComplete ")
                    }
                })
        findViewById<View>(R.id.button7).setOnClickListener { v -> startActivity(Intent(this@MainActivity, ThreadDemoActivity::class.java)) }
        findViewById<View>(R.id.gotoStudyActivity).setOnClickListener { v -> startActivity(Intent(this@MainActivity, RxStudyActivity::class.java)) }
        findViewById<View>(R.id.gotoCoordinatorLayoutActivity).setOnClickListener { v -> startActivity(Intent(this@MainActivity, CoordinatorLayoutActivity::class.java)) }

    }

    private open inner class MyObserver<T> : Observer<T> {


        override fun onSubscribe(d: Disposable) {
            compositeDisposable.add(d)
        }

        override fun onNext(t: T) {

        }

        override fun onError(e: Throwable) {

        }

        override fun onComplete() {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
