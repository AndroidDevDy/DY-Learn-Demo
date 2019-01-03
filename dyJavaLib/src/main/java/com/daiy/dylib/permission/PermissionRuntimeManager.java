package com.daiy.dylib.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;


/**
 * 运行时权限管理
 * Created by DaiY on 2016/10/15 0015.
 * 本工具类需要在activty中实例化一个对象，并配置checkAndRequestPermissions和dealRequestResult方法
 * checkAndRequestPermissions检查并请求权限，dealRequestResult处理请求返回结果。
 */
public class PermissionRuntimeManager {

    private OnPermissionResultListener onPermissionResult;//回调的接口
    private static final int PERMISSION_REQUEST_CODE = 0;//请求码
    private static final String PACKAGE_URL_SCHEME = "package:";// 方案
    private Context mContext;//上下文
    private PermissionsChecker permissionsChecker;//权限检查工具

    public PermissionRuntimeManager(Context context) {
        this.mContext = context;
    }

    /**
     * 检查权限是否已经打开，若未打开，则进入设置中打开权限
     *
     * @param permissions
     */
    public void checkAndRequestPermissions(String... permissions) {
        if (permissionsChecker == null)
            permissionsChecker = new PermissionsChecker(mContext);
        if (permissionsChecker.lacksPermissions(permissions)) {
            //缺少权限,主动请求权限
            requestPermissions(permissions);
        } else {
            //全部权限已打开
            if (onPermissionResult != null) {
                onPermissionResult.allPermissionGranted();
            }
        }
    }

    /**
     * 请求权限
     *
     * @param permissions
     */
    private void requestPermissions(String[] permissions) {
        if (!permissionsChecker.isShowAllRequestPermissionRationale(permissions)) {
            //用户未拒绝，可以直接申请权限
            ActivityCompat.requestPermissions((Activity) mContext,
                    permissions, PERMISSION_REQUEST_CODE);
        } else {
            //若部分权限用户已经永久拒绝,需要显示提示框
            showMissingPermissionDialog();
        }
    }

    /**
     * 显示缺失权限提示
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("");
        builder.setMessage("");
        builder.setNegativeButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //取消，不进入应用设置界面
                if (onPermissionResult != null) {
                    onPermissionResult.somePermissionDenied();
                }
            }
        });
        builder.setPositiveButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //设置，进入应用设置界面
                startAppSettings();
            }
        });
        builder.show();
    }

    /**
     * 进入应用设置界面
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + mContext.getPackageName()));
        mContext.startActivity(intent);
    }

    /**
     * 在activity的onRequestPermissionsResult()方法中执行，
     * 处理权限请求后返回的结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void dealRequestResult(int requestCode, @NonNull String[] permissions,
                                  @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (hasAllPermissionsGranted(grantResults)) {
                //全部权限已打开
                if (onPermissionResult != null) {
                    onPermissionResult.allPermissionGranted();
                }
            } else {
                //未打开全部权限,用户主动拒绝了部分权限
                if (onPermissionResult != null) {
                    onPermissionResult.somePermissionDenied();
                }
            }
        }
    }

    /**
     * 是否含有全部的权限
     *
     * @param grantResults
     * @return
     */
    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 设置回调
     *
     * @param onPermissionResult
     */
    public void setOnPermissionResultListener(OnPermissionResultListener onPermissionResult) {
        this.onPermissionResult = onPermissionResult;
    }

    public interface OnPermissionResultListener {

        /**
         * 所有权限已获得
         */
        void allPermissionGranted();

        /**
         * 用户主动拒绝了部分权限,
         * 用户拒绝进入应用设置界面
         */
        void somePermissionDenied();
    }

}
