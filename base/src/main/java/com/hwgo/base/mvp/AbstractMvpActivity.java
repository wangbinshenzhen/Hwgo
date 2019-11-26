package com.hwgo.base.mvp;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * <br> ClassName:   AbstractMvpActivity
 * <br> Description: MVP架构 Activity基类
 * <br>
 */
public abstract class AbstractMvpActivity<T extends BasePresenter>
        extends AppCompatActivity implements IMvpView {
    protected T mCurrentPresenter;

    /**
     *<br> Description: 创建Presenters
     *
     * @return List presenterList
     */
    protected abstract T createPresenter();

    public T getCurrentPresenter() {
        return mCurrentPresenter;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentPresenter = createPresenter();
        if (mCurrentPresenter != null) {
            mCurrentPresenter.attachView(this);
        }
    }

    @Override
    public void onFinish() {
        if (!isFinishing()) {
            this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCurrentPresenter != null) {
            mCurrentPresenter.detachView();
        }
    }
}
