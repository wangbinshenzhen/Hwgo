package com.hwgo.base.mvp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * <br> ClassName:   AbstractMvpFragment
 * <br> Description: MVP架构 Fragment 基类
 * <br>
 */
public abstract class AbstractMvpFragment<T extends BasePresenter>
        extends Fragment implements IMvpView {
    protected T mBasePresenter;
    protected Activity mActivity;

    /**
     * <br> Description: 创建Presenters
     *
     * @return List presenterList
     */
    protected abstract T createPresenter();

    public T getCurrentPresenter() {
        return mBasePresenter;
    }

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                   @Nullable Bundle savedInstanceState) {
        mBasePresenter = createPresenter();
        if (mBasePresenter != null) {
            mBasePresenter.attachView(this);
        }
        return onBindView(inflater, container, savedInstanceState);
    }

    /**
     * <br> Description: 使用onBindView代替onCreateView
     */
    protected abstract View onBindView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBasePresenter != null) {
            mBasePresenter.detachView();
        }
        onUnbindView();
    }

    /**
     * <br> Description: 使用onUnbindView代替onDestroyView
     */
    protected void onUnbindView() {
    }

    ;
}
