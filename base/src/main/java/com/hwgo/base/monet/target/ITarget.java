package com.hwgo.base.monet.target;

/**
 * <br> ClassName:   ITarget
 * <br> Description: 输出回调接口
 * <br>
 */
public interface ITarget<T> {
    /**
     *<br> Description: 加载成功
     * @param target
     *                  输出回调接口
     * @return
     *                  是否覆盖，只适用于loadin()方法
     */
    boolean onResourceReady(T target);

    /**
     *<br> Description: 加载失败
     * @param message
     *                  失败原因
     */
    void onFailed(String message);
}
