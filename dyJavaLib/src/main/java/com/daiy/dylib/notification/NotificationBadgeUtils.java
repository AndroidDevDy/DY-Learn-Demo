package com.daiy.dylib.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.daiy.dylib.R;
import com.daiy.dylib.receiver.NotificationReceiver;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 通过反射发送通知时，携带消息数，从而改变启动图标角标
 */
public class NotificationBadgeUtils {

    public static String ACTION_NOTIFICATION_OPENED = "android.intent.action.ACTION_NOTIFICATION_OPENED";

    public static int NOTIFICATION_ID = 10001;

    /**
     * 显示未读消息数
     *
     * @param context
     * @param count
     */
    public static void showNumber(Context context, int count) throws Exception {
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent clickIntent = new Intent(context, NotificationReceiver.class); //点击通知之后要发送的广播
        clickIntent.setAction(ACTION_NOTIFICATION_OPENED);
        int id = (int) (System.currentTimeMillis() / 1000);
        PendingIntent contentIntent = PendingIntent.getBroadcast(context, id,
                clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText("你有未读消息!")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.default_icon)
                .setContentIntent(contentIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channelId_100";
            NotificationChannel mChannel = new NotificationChannel(channelId, "推送消息", NotificationManager.IMPORTANCE_DEFAULT);
            mChannel.setDescription("推送消息");
            mChannel.enableLights(true); //是否在桌面icon右上角展示小红点
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(mChannel);
            }
            builder.setChannelId(channelId);
        }
        Notification notification = builder.getNotification();

        Field field = notification.getClass().getDeclaredField("extraNotification");
        Object extraNotification = field.get(notification);
        Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount",
                int.class);
        method.invoke(extraNotification, count);

        if (mNotificationManager != null) {
            mNotificationManager.cancel(NOTIFICATION_ID);
            mNotificationManager.notify(NOTIFICATION_ID, notification);
        }
    }

    public static void showNumber(Context context, int count, int notiID, String title,
                                  String text, int icon) throws Exception {
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent clickIntent = new Intent(context, NotificationReceiver.class); //点击通知之后要发送的广播
        clickIntent.setAction(ACTION_NOTIFICATION_OPENED);
        int id = (int) (System.currentTimeMillis() / 1000);
        PendingIntent contentIntent = PendingIntent.getBroadcast(context, id,
                clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setSmallIcon(icon)
                .setContentIntent(contentIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channelId_100";
            NotificationChannel mChannel = new NotificationChannel(channelId, "推送消息", NotificationManager.IMPORTANCE_DEFAULT);
            mChannel.setDescription("推送消息");
            mChannel.enableLights(true); //是否在桌面icon右上角展示小红点
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(mChannel);
            }
            builder.setChannelId(channelId);
        }
        Notification notification = builder.getNotification();

        Field field = notification.getClass().getDeclaredField("extraNotification");
        Object extraNotification = field.get(notification);
        Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount",
                int.class);
        method.invoke(extraNotification, count);

        if (mNotificationManager != null) {
            mNotificationManager.cancel(notiID);
            if (count > 0)
                mNotificationManager.notify(NOTIFICATION_ID, notification);
        }
    }
}
