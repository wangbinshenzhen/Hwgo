package com.hwgo.common.grant.utils;

import android.Manifest;
import android.os.Build;

import java.util.HashMap;
import java.util.List;

/**
 * <br> ClassName:   PermissionMessageUtils
 * <br> Description: 获取Permission的中文描述
 * <p>
 */
public class PermissionMessageUtils {

    private static final HashMap<String, String> sPermissionMessages;

    static {
        sPermissionMessages = new HashMap<>(32);
        sPermissionMessages.put(Manifest.permission.READ_CALENDAR, "读取日历");
        sPermissionMessages.put(Manifest.permission.WRITE_CALENDAR, "写入日历");
        sPermissionMessages.put(Manifest.permission.CAMERA, "打开相机");
        sPermissionMessages.put(Manifest.permission.READ_CONTACTS, "读取通讯录");
        sPermissionMessages.put(Manifest.permission.WRITE_CONTACTS, "写入通讯录");
        sPermissionMessages.put(Manifest.permission.GET_ACCOUNTS, "访问账户");
        sPermissionMessages.put(Manifest.permission.ACCESS_COARSE_LOCATION, "获取粗略定位");
        sPermissionMessages.put(Manifest.permission.ACCESS_FINE_LOCATION, "获取位置");
        sPermissionMessages.put(Manifest.permission.RECORD_AUDIO, "麦克风");
        sPermissionMessages.put(Manifest.permission.READ_PHONE_STATE, "读取手机状态");
        sPermissionMessages.put(Manifest.permission.CALL_PHONE, "打电话");
        sPermissionMessages.put(Manifest.permission.ADD_VOICEMAIL, "添加语音信箱");
        sPermissionMessages.put(Manifest.permission.USE_SIP, "使用SIP");
        sPermissionMessages.put(Manifest.permission.PROCESS_OUTGOING_CALLS, "过程输出调用");
        sPermissionMessages.put(Manifest.permission.SEND_SMS, "发送短信");
        sPermissionMessages.put(Manifest.permission.RECEIVE_SMS, "接收短信");
        sPermissionMessages.put(Manifest.permission.READ_SMS, "读取短信");
        sPermissionMessages.put(Manifest.permission.RECEIVE_WAP_PUSH, "收到WAP推送");
        sPermissionMessages.put(Manifest.permission.RECEIVE_MMS, " 接收彩信");
        sPermissionMessages.put(Manifest.permission.BROADCAST_SMS, "读取广播");
        sPermissionMessages.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, "写外部存储");
        if (Build.VERSION.SDK_INT >= 16) {
            sPermissionMessages.put(Manifest.permission.READ_CALL_LOG, "查看通话记录");
            sPermissionMessages.put(Manifest.permission.WRITE_CALL_LOG, "写入通话记录");
            sPermissionMessages.put(Manifest.permission.READ_EXTERNAL_STORAGE, " 读外部存储");
        }
        if (Build.VERSION.SDK_INT >= 20) {
            sPermissionMessages.put(Manifest.permission.BODY_SENSORS, "传感器");
        }
    }

    private PermissionMessageUtils(){}

    private static String getMessageByPermission(String permission) {
        String name = sPermissionMessages.get(permission);
        return name != null ? name : permission;
    }

    public static String getPermissionMessage(List<String> permissions) {
        StringBuilder builder = new StringBuilder();
        boolean flag = false;
        for (String permission : permissions) {
            if (flag) {
                builder.append("、");
            } else {
                flag = true;
            }
            builder.append(getMessageByPermission(permission));
        }
        return builder.toString();
    }
}
