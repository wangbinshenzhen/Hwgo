package com.hwgo.base.grant;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

/**
 * <br> ClassName:   PermissionRequester
 * <br> Description: 权限请求的接口
 * <p>
 */
public interface PermissionRequester {

    /**
     *<br> Description: 添加一个请求的权限，如 Manifest.permission.CALL_PHONE
     *<br> Author:      LongZefeng
     *<br> Date:        2017/9/6 19:13
     */
    PermissionRequester addPermission(@NonNull String... permission);

    /**
     *<br> Description: 进行请求，并监听结果
     *<br> Author:      LongZefeng
     *<br> Date:        2017/9/6 19:13
     */
    void request(@NonNull PermissionCallback observer);

    /**
     *<br> Description: 进行请求，并将请求结果用Observable发射
     *<br> Author:      LongZefeng
     *<br> Date:        2017/9/6 19:13
     */
    Observable<List<PermissionResult>> observable();
}
