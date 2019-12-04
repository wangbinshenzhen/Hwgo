package com.hwgo.common.flutter;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;

import io.flutter.facade.FlutterAppCompatActivity;
import io.flutter.view.FlutterNativeView;
import io.flutter.view.FlutterView;

/**
 * @Author wangbin
 * @Date 2019-12-03 20:20
 */
public class BaseFlutterActivity extends FlutterAppCompatActivity {

    @Override
    public FlutterView createFlutterView(Context context) {
        FlutterNativeView flutterNativeView=this.createFlutterNativeView();
        FlutterView flutterView = new FlutterView(this,null,flutterNativeView);
        WindowManager.LayoutParams layout = new WindowManager.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        flutterView.setLayoutParams(layout);
        flutterView.enableTransparentBackground();
        setContentView(flutterView);
        return flutterView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
