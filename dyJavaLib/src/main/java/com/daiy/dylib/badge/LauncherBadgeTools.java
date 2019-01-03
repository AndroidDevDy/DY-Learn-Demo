package com.daiy.dylib.badge;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.daiy.dylib.R;
import com.daiy.dylib.notification.NotificationBadgeUtils;

import java.util.Random;

import me.leolin.shortcutbadger.ShortcutBadger;

import static com.daiy.dylib.badge.LauncherBadgeHelper.getLauncherClassName;

/**
 * 设置启动图标标签工具
 */
public class LauncherBadgeTools {
    public static void showBadge(Context context, int count) {
        showBadge(context, count, 0, "新消息", "你有未读消息（" + count + "）",
                R.drawable.default_icon);
    }

    /**
     * 兼容最全面的，启动图标角标设置
     *
     * @param context 上下文
     * @param count   数量
     * @param notiID  通知id
     * @param title   通知标题
     * @param text    通知内容
     * @param icon    通知图标
     */
    public static void showBadge(Context context, int count, int notiID, String title, String text, int icon) {
        if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
            //小米手机兼容
            try {
                NotificationBadgeUtils.showNumber(context, count, notiID, title, text, icon);
            } catch (Exception e) {
                Intent localIntent = new Intent(
                        "android.intent.action.APPLICATION_MESSAGE_UPDATE");
                localIntent.putExtra(
                        "android.intent.extra.update_application_component_name",
                        context.getPackageName() + "/" + getLauncherClassName(context));
                localIntent.putExtra(
                        "android.intent.extra.update_application_message_text", String.valueOf(count == 0 ? "" : count));
                context.sendBroadcast(localIntent);
            }
        } else {
            //其他手机
            ShortcutBadger.applyCount(context, count);
        }
    }
}
