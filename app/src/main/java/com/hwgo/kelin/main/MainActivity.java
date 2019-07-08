package com.hwgo.kelin.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hwgo.firstmodule.RxStudyActivity;
import com.hwgo.kelin.R;
import com.hwgo.base.http.HttpRequestManager;
import com.hwgo.base.http.HttpResult;
import com.hwgo.kelin.main.bean.User;
import com.hwgo.kelin.main.service.MovieService;
import com.hwgo.kelin.messengerdemo.ClientActivity;
import com.hwgo.kelin.threaddemo.ThreadDemoActivity;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button3).setOnClickListener(v -> {
            startActivity(new Intent(this, ClientActivity.class));
        });

        MovieService moviceService = HttpRequestManager.getInstance().createService(MovieService.class);
        Observable<HttpResult<User>> kelin = moviceService.getUser();
        kelin.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<HttpResult<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpResult<User> userHttpResult) {
                        Log.d("kelin", "onNext =" + userHttpResult.code);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("kelin", "Throwable =" + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d("kelin", "onComplete ");
                    }
                });
        findViewById(R.id.button7).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ThreadDemoActivity.class));
        });
        findViewById(R.id.gotoStudyActivity).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RxStudyActivity.class));
        });
        findViewById(R.id.gotoCoordinatorLayoutActivity).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CoordinatorLayoutActivity.class));
        });

    }

    private class MyObserver<T> implements Observer<T> {


        @Override
        public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
        }

        @Override
        public void onNext(T t) {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
