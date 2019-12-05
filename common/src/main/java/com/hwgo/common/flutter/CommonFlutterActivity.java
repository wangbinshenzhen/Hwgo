package com.hwgo.common.flutter;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hwgo.common.router.path.RouterPath;

import io.flutter.Log;
import io.flutter.facade.FlutterAppCompatActivity;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.view.FlutterNativeView;
import io.flutter.view.FlutterView;

/**
 * @Author wangbin
 * @Date 2019-12-03 20:20
 */
@Route(path = RouterPath.Flutter.CommonFlutterActivity)
public class CommonFlutterActivity extends FlutterAppCompatActivity {
    private static final String METHOD_CHANNEL_COMMON = "method_channel_common";
    private static final String EVENT_CHANNEL_COMMON = "event_channel_common";
    private MethodChannel mMethodChannel;
    private EventChannel mEventChannel;
    int count = 0;

    @Override
    public FlutterView createFlutterView(Context context) {
        FlutterNativeView flutterNativeView = this.createFlutterNativeView();
        FlutterView flutterView = new FlutterView(this, null, flutterNativeView);
        WindowManager.LayoutParams layout = new WindowManager.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        flutterView.setLayoutParams(layout);
        flutterView.enableTransparentBackground();
        setContentView(flutterView);
        return flutterView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFlutterChannel();

    }

    /**
     * 初始化native与flutter通信，通道
     */
    private void initFlutterChannel() {
        mMethodChannel = new MethodChannel(getFlutterView(), METHOD_CHANNEL_COMMON);
        mMethodChannel.setMethodCallHandler((methodCall, result) -> {
            count++;
            Log.d("wangbin", "count=" + count + ",method=" + methodCall.method + ",thread name=" + Thread.currentThread().getName() + ", current=" + this);
            result.success("success");
        });
        mEventChannel = new EventChannel(getFlutterView(), EVENT_CHANNEL_COMMON);
        mEventChannel.setStreamHandler(new EventChannel.StreamHandler() {
            @Override
            public void onListen(Object o, EventChannel.EventSink eventSink) {

            }

            @Override
            public void onCancel(Object o) {

            }
        });
    }

}
