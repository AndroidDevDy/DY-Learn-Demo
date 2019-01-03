package com.daiy.dylib.status;

import android.content.Context;

import com.daiy.dylib.BuildConfig;

/**
 * Get compiled version information tool
 * 获取编译版本信息工具
 *
 * @author daiyue
 */
public class StatusTools {

    /**
     * The system compiled version judges, true means the test version, false means the official version.
     * 系统编译版本判断，true表示测试版，false表示正式版
     *
     * @return boolean
     */
    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    public static String getPackage(Context context) {
        return context.getPackageName();
    }
}
