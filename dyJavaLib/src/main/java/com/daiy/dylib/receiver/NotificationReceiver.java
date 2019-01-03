package com.daiy.dylib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.daiy.dylib.logcat.LogTools;
import com.daiy.dylib.notification.NotificationBadgeUtils;
import com.daiy.dylib.status.StatusTools;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(NotificationBadgeUtils.ACTION_NOTIFICATION_OPENED)) {
            LogTools.loge("收到通知消息点击打开事件");
            Intent LaunchIntent = context.getPackageManager().getLaunchIntentForPackage(StatusTools.getPackage(context));
            LaunchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            LaunchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(LaunchIntent);
        }
    }
}
