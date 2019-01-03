package com.daiy.dylib.logcat;

import android.util.Log;

import com.daiy.dylib.Constants;
import com.daiy.dylib.status.StatusTools;

/**
 * Tool class for printing logs
 * 日志打印工具类
 *
 * @author daiyue
 */
public class LogTools {

    /**
     * Print the log at the error level
     * 以error的级别打印日志
     *
     * @param str string
     */
    public static void loge(String str) {
        if (StatusTools.isDebug()) {
            try {
                Log.e(Constants.TAG, str);
            } catch (Exception e) {
                System.out.println(Constants.TAG + str);
            }
        }
    }

    public static void loge(String str, Throwable t) {
        if (StatusTools.isDebug()) {
            try {
                Log.e(Constants.TAG, str, t);
            } catch (Exception e) {
                System.out.println(Constants.TAG + str);
            }
        }
    }

    /**
     * Print the log at the info level
     * 以info的级别打印日志
     *
     * @param str string
     */
    public static void logi(String str) {
        if (StatusTools.isDebug()) {
            try {
                Log.i(Constants.TAG, str);
            } catch (Exception e) {
                System.out.println(Constants.TAG + str);
            }
        }
    }

    public static void logi(String str, Throwable t) {
        if (StatusTools.isDebug()) {
            try {
                Log.i(Constants.TAG, str, t);
            } catch (Exception e) {
                System.out.println(Constants.TAG + str);
            }
        }
    }
}
