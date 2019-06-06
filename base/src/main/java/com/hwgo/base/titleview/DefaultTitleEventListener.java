package com.hwgo.base.titleview;

import android.app.Activity;
import android.view.View;

/**
 * <br> ClassName:   DefaultTitleEventListener
 * <br> Description: 默认的点击事件处理，左侧为finish，中间及右侧为空实现
 * <p>
 * <br> Date:        2018/3/2
 */
public class DefaultTitleEventListener extends EmptyTitleEventListener{

    protected final Activity mActivity;

    public DefaultTitleEventListener(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void onLeft1Click(View v) {
        mActivity.finish();
    }

}
