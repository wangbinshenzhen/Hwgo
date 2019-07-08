package com.hwgo.firstmodule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxStudyActivity extends AppCompatActivity {
    static final String TAG = RxStudyActivity.class.getSimpleName();

    public static void main(String[] args) {
        // rxSchedulerCut();
        rxConcat();
    }

    @Override
    protected void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //rxDemo1();
        //rxSchedulerCut();
        //rxMap();
        //rxConcat();
    }

    private void rxDemo1() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "Observable emit 1" + "\n");
                emitter.onNext(1);
                Log.d(TAG, "Observable emit 2" + "\n");
                emitter.onNext(2);
                Log.d(TAG, "Observable emit 3" + "\n");
                emitter.onNext(3);
                emitter.onComplete();
                Log.d(TAG, "Observable emit 4" + "\n");
                emitter.onNext(4);

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Integer>() {
            private int i;
            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
                Log.d(TAG, "onSubscribe :" + mDisposable.isDisposed());
            }

            @Override
            public void onNext(Integer integer) {
                i++;
                if (i == 2) {
                    mDisposable.dispose();
                }
                Log.d(TAG, "onNext : value : " + integer);
                Log.d(TAG, "onNext : isDisposable :" + mDisposable.isDisposed());
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError : value : " + e.getMessage() + "\n");

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
    }

    /**
     * 线程切换
     * (多次指定发射事件的线程只有第一次指定的有效，也就是说多次调用 subscribeOn() 只有第一次的有效，其余的会被忽略。
     * 但多次指定订阅者接收线程是可以的，也就是说每调用一次 observerOn()，下游的线程就会切换一次)
     */
    public static void rxSchedulerCut() {
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            emitter.onNext(1);
            emitter.onComplete();
            Log.e(TAG, "Observable thread is : " + Thread.currentThread().getName());

        }).subscribeOn(Schedulers.io())
                //.subscribeOn(AndroidSchedulers.mainThread())
                // .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(integer -> Log.e(TAG, "After observeOn(mainThread)，Current thread is " + Thread.currentThread().getName()))
                .doOnNext(integer -> Log.e(TAG, "After observeOn(mainThread)，Current thread is " + Thread.currentThread().getName()))
                .doOnNext(integer -> Log.e(TAG, "After observeOn(mainThread)，Current thread is " + Thread.currentThread().getName()))
                .observeOn(Schedulers.io())
                .subscribe(integer -> Log.e(TAG, "After observeOn(io)，Current thread is " + Thread.currentThread().getName()));
    }

    public static void rxMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.e(TAG, "subscribe，Current thread is " + Thread.currentThread().getName());
                emitter.onNext(1);

            }
        }).observeOn(AndroidSchedulers.mainThread()).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer response) throws Exception {
                Log.e(TAG, " map apply，Current thread is " + Thread.currentThread().getName());
                return response + "";
            }
        }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, " doOnNext accept，Current thread is " + Thread.currentThread().getName());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, " subscribe accept，Current thread is " + Thread.currentThread().getName());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, " subscribe accept Throwable，Current thread is " + Thread.currentThread().getName());

                    }
                });
    }

    public static void rxConcat() {
        Observable.just("abc").subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d("wangbin", "onNext =" + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("wangbin", "onError");
            }

            @Override
            public void onComplete() {
                Log.d("wangbin", "onComplete");
            }
        });
    }

}