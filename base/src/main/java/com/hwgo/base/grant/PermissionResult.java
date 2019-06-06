package com.hwgo.base.grant;

/**
 * <br> ClassName:   PermissionResult
 * <br> Description: 单个权限的请求结果
 */
public class PermissionResult {

    private final String name;
    private final Type   type;

    public enum Type {
        /**已授权*/
        GRANTED,
        /**未授权*/
        DENIED,
        /**未授权，不再询问*/
        NO_ASK
    }

    public PermissionResult(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

}
