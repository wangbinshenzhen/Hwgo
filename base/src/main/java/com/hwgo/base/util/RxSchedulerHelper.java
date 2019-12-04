package com.hwgo.base.util;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 线程调度工具类引用RxJava内线程资源
 * <p>
 * 使用线程池的好处是减少在创建和销毁线程上所花的时间以及系统资源的开销，
 * 解决资源不足的问题。如果不使用线程池，有可能造成系统创建大量同类线程而导致消耗完内存或者“过度切换”的问题。
 *
 * @date 2019-06-15
 */
public class RxSchedulerHelper {
    private RxSchedulerHelper() {
    }

    public static Disposable runOnUIThread(Runnable runnable) {
        return runOnUIThreadDelay(runnable, 0);
    }

    public static Disposable runOnUIThreadDelay(Runnable runnable, long delay) {
        return post(runnable, delay, AndroidSchedulers.mainThread());
    }

    public static Disposable runOnIOThread(Runnable runnable) {
        return runOnIOThreadDelay(runnable, 0);
    }

    public static Disposable runOnIOThreadDelay(Runnable runnable, long delay) {
        return post(runnable, delay, Schedulers.io());
    }

    public static Disposable runOnComputationThread(Runnable runnable) {
        return runOnComputationThreadDelay(runnable, 0);
    }

    public static Disposable runOnComputationThreadDelay(Runnable runnable, long delay) {
        return post(runnable, delay, Schedulers.computation());
    }

    private static Disposable post(@NonNull Runnable runnable, long delay, Scheduler scheduler) {
        if (delay > 0) {
            return Maybe.just(runnable).delay(delay, TimeUnit.MILLISECONDS).observeOn(scheduler).subscribe(new Consumer<Runnable>() {
                @Override
                public void accept(Runnable mRunnable) throws Exception {
                    mRunnable.run();
                }
            });
        } else {
            return Maybe.fromRunnable(runnable).subscribeOn(scheduler).subscribe();
        }
    }

    public static void disposable(Disposable disposable) {
        if(null!=disposable && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
