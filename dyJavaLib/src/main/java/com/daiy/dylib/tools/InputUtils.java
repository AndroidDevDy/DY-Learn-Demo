package com.daiy.dylib.tools;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 输入键盘操作工具
 * Created by Administrator on 2017/7/24 0024.
 */

public class InputUtils {
    /**
     * 影藏输入软键盘
     *
     * @param activity 上下文
     * @param view     焦点view
     */
    public static void hideInputView(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
        }
    }

}
