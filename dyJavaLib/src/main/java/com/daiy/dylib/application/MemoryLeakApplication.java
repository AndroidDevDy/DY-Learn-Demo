package com.daiy.dylib.application;

import android.os.StrictMode;

/**
 * 通过StrictMode来监测内存泄漏
 */
public class MemoryLeakApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectAll()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
//                .detectResourceMismatches()
                .detectCustomSlowCalls()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                .detectAll()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
//                .detectLeakedRegistrationObjects()
                .detectActivityLeaks()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }
}
