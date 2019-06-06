package com.hwgo.common.grant;

import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * <br> ClassName:   PermissionCallback
 * <br> Description: 权限请求的结果监听者
 * <p>
 */
public interface PermissionCallback {

    /**
     *<br> Description: 所有权限请求都通过时的回调
     */
    void onAllAllow();

    /**
     *<br> Description: 存在未通过的权限
     */
    void onHasRefusedPermissions(@NonNull List<String> permissions);

    /**
     *<br> Description: 存在不再询问的权限
     */
    void onHadNoAskPermissions(@NonNull List<String> permissions);
}
