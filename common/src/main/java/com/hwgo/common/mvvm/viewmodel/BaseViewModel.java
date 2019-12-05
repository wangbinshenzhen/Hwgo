package com.hwgo.common.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;


/**
 * ClassName: Cec base view model
 * Description: 基础的ViewModel
 * <p>
 * Author: wangbin
 * Date: 2019/12/05 10:10:07
 */
public class BaseViewModel extends AndroidViewModel {

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

}
