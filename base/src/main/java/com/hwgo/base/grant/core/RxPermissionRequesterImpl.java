package com.hwgo.base.grant.core;

import androidx.fragment.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.hwgo.base.grant.PermissionCallback;
import com.hwgo.base.grant.PermissionRequester;
import com.hwgo.base.grant.PermissionResult;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * <br> ClassName:   RxPermissionRequesterImpl
 * <br> Description: 权限请求器的实现类，使用RxPermissions实现权限请求功能
 * <p>
 */
class RxPermissionRequesterImpl
        implements PermissionRequester {

    private final FragmentActivity mActivity;
    private final LinkedList<String> mList = new LinkedList<>();

    RxPermissionRequesterImpl(FragmentActivity activity) {
        mActivity = activity;
    }

    @Override
    public PermissionRequester addPermission(@NonNull String... permission) {
        mList.addAll(Arrays.asList(permission));
        return this;
    }

    @Override
    public void request(@NonNull final PermissionCallback callback) {
        observable().subscribe(new Consumer<List<PermissionResult>>() {
            @Override
            public void accept(@NonNull List<PermissionResult> permissionResults) throws Exception {
                LinkedList<String> mRefusedList = new LinkedList<>();
                LinkedList<String> mNoAskList = new LinkedList<>();
                //
                for (PermissionResult result : permissionResults) {
                    switch (result.getType()) {

                        case DENIED:
                            mRefusedList.add(result.getName());
                            break;

                        case NO_ASK:
                            mNoAskList.add(result.getName());
                            break;
                    }
                }
                //
                if ((mRefusedList.size() + mNoAskList.size()) == 0) {
                    callback.onAllAllow();
                } else {
                    if (mRefusedList.size() != 0) {
                        callback.onHasRefusedPermissions(mRefusedList);
                    }
                    if (mNoAskList.size() != 0) {
                        callback.onHadNoAskPermissions(mNoAskList);
                    }
                }
            }
        });
    }

    @Override
    public Observable<List<PermissionResult>> observable() {
        int size = mList.size();
        if (size == 0) {
            throw new IllegalStateException("need addPermission()");
        }
        return new RxPermissions(mActivity)
                .requestEach(mList.toArray(new String[size]))
                .map(new Function<Permission, PermissionResult>() {
                    @Override
                    public PermissionResult apply(@NonNull Permission permission) throws Exception {
                        if (permission.granted) {
                            return new PermissionResult(permission.name, PermissionResult.Type.GRANTED);
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            return new PermissionResult(permission.name, PermissionResult.Type.DENIED);
                        } else {
                            return new PermissionResult(permission.name, PermissionResult.Type.NO_ASK);
                        }
                    }
                })
                .buffer(size);
    }
}
