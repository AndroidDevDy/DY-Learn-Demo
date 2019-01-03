package com.daiy.dylib.activity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个Activity的管理栈，
 * 处理activity的保存、关闭、跳转等操作
 * Created by Administrator on 2017/11/22 0022.
 */
public class ActivityTask {

    private List<Activity> activityList = new ArrayList<>();
    private static ActivityTask task = new ActivityTask();

    public static ActivityTask getInstance() {
        return task;
    }

    /**
     * 添加Activity
     */
    public void add(Activity activity) {
        if (activityList != null)
            activityList.add(activity);
    }

    /**
     * 移除Activity
     */
    public void remove(Activity activity) {
        if (activityList != null)
            activityList.remove(activity);
    }

    /**
     * 跳转栈内之前保存的某一个Activity
     */
    public void moveToBefore(String activityName) {
        int length = activityList.size();
        for (int i = length - 1; i >= 0; i--) {
            Activity activity = activityList.get(i);
            if (activity.getClass().getSimpleName().equals(activityName)) {
                break;
            }
            remove(activity);
            activity.finish();
        }
    }

    /**
     * 完全退出App
     */
    public void exit() {
        int length = activityList.size();
        for (int i = length - 1; i >= 0; i--) {
            activityList.get(i).finish();
        }
        activityList.clear();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}