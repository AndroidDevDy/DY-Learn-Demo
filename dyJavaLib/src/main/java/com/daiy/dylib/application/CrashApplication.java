package com.daiy.dylib.application;

import com.daiy.dylib.crash.CrashHandler;

/**
 * 通过StrictMode来监测内存泄漏
 */
public class CrashApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
    }
}
