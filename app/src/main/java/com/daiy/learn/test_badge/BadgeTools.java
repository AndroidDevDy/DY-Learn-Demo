package com.daiy.learn.test_badge;

import android.content.Context;

import com.daiy.dylib.badge.LauncherBadgeTools;

public class BadgeTools {

    public static void showBadge(Context context, int count) {
        LauncherBadgeTools.showBadge(context, count);
    }
}
