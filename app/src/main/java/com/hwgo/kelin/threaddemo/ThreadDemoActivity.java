package com.hwgo.kelin.threaddemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hwgo.kelin.R;

public class ThreadDemoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_demo);
        Thread t = new Thread() {
            @Override
            public void run() {
                int i = 0;
                while (!isInterrupted()) {
                    Log.d("kelin", "i=" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.d("kelin",""+e);
                        interrupt();


                    }
                    i++;
                }
            }
        };

        t.start();
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                t.interrupt();
            }
        }, 1000);
    }
}
