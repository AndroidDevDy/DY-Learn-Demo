package com.daiy.dylib.application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * 通过LeakCanary来监测内存泄漏
 */
public class LeakCanaryApplication extends BaseApplication {
    public RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        initLeakCanary();
    }

    /**
     * Explain : 初始化内存泄漏检测
     *
     * @author LiXaing
     */
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        refWatcher = LeakCanary.install(this);
    }
}
