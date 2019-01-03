package com.daiy.dylib.tools;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.daiy.dylib.logcat.LogTools;

public class ScreenUtils {
    private static int DEVICE_WIDTH;
    private static int DEVICE_HEIGHT;
    private static float DEVICE_DENSITY;

    public static void initDeviceInfo(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (manager == null) {
            LogTools.loge("WINDOW_SERVICE, getSystemService fails, WindowManager is null");
            return;
        }
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        DEVICE_WIDTH = metrics.widthPixels;
        DEVICE_HEIGHT = metrics.heightPixels;
        DEVICE_DENSITY = metrics.density;
    }

    public static int getDeviceWidth() {
        return DEVICE_WIDTH;
    }

    public static int getDeviceHeight() {
        return DEVICE_HEIGHT;
    }

    public static float getDeviceDensity() {
        return DEVICE_DENSITY;
    }
}
