package com.daiy.dylib.h5;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * 解决android webview加载h5页面，
 * 输入键盘遮住输入框的bug。
 */
public class AndroidBugForH5 {
    public static void assistActivity(Activity activity, boolean translucentStatusBar) {
        new AndroidBugForH5(activity, translucentStatusBar);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;

    private AndroidBugForH5(Activity activity, final boolean translucentStatusBar) {
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent(translucentStatusBar);

            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    private void possiblyResizeChildOfContent(boolean translucentStatusBar) {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // keyboard probably just became visible
                if (translucentStatusBar) {
                    //浸式状态栏样式，计算时需要加上状态栏高度
                    frameLayoutParams.height = usableHeightNow + getStatusBarHeight(mChildOfContent.getContext());
                } else {
                    frameLayoutParams.height = usableHeightNow;
                }
            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = ((FrameLayout) mChildOfContent.getParent()).getHeight();
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

	/**
	 *获取除虚拟按键、输入软键盘、状态栏以外的可视区域的高度
	 */
    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);// 全屏模式下： return r.bottom
    }

	/**
	 *获取状态栏的高度
	 */
    private int getStatusBarHeight(Context context) {
        int height = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = context.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }
}
