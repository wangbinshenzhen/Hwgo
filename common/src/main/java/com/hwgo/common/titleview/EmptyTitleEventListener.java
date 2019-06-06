package com.hwgo.common.titleview;

import android.view.View;

/**
 * <br> ClassName:   EmptyTitleEventListener
 * <br> Description: 全部为空实现的监听者
 * <p>
 * <br> Date:        2018/3/7
 */

public class EmptyTitleEventListener
        implements TitleView.TitleEventListener {

    @Override
    public void onLeft1Click(View v) {}

    @Override
    public void onLeft2Click(View v) {}

    @Override
    public void onMiddleClick(View v) {}

    @Override
    public void onRight1Click(View v) {}

    @Override
    public void onRight2Click(View v) {}
}
