package com.hwgo.base.mvp;

/**
 * @author: wangbin
 * @Filename:
 * @Description:
 * @date: 2019/4/24 11:09
 */
public interface IMvpView {

    void showLoading(String tips);

    void dismissLoading();

    void showToast(String info);

    void onFinish();

}
