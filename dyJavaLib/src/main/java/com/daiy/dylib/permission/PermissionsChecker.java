package com.daiy.dylib.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.daiy.dylib.logcat.LogTools;


/**
 * 权限检查的工具类
 * 1-检查是否缺少权限
 * 2-检查是否弹出请求权限的提示框
 * Created by DaiY on 2016/10/15 0015.
 */
public class PermissionsChecker {
    private final Context mContext;

    public PermissionsChecker(Context context) {
        mContext = context;
    }

    /**
     * 判断权限集合，是否缺少权限，缺少返回true
     *
     * @param permissions String...
     * @return
     */
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否缺少权限,缺少返回true
     *
     * @param permission String
     * @return
     */
    private boolean lacksPermission(String permission) {
        if (ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_DENIED) {
            LogTools.loge("PermissionsChecker," + permission + "，权限未打开");
            return true;
        }
        LogTools.loge("PermissionsChecker," + permission + "，权限已打开");
        return false;
    }

    /**
     * 是否应该显示权限提示框
     *
     * @param permissions
     * @return
     */
    public boolean isShowAllRequestPermissionRationale(String... permissions) {
        for (String permission : permissions) {
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否显示权限提示框
     *
     * @param permission
     * @return
     */
    private boolean shouldShowRequestPermissionRationale(String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, permission)) {
            LogTools.loge("PermissionsChecker," + permission + "，请求可以提示");
            return true;
        }
        LogTools.loge("PermissionsChecker," + permission + "，请求将不再提示，被用户忽略");
        return false;
    }
}

