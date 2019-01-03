package com.daiy.kotlin.lib.h5

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.widget.FrameLayout

/**
 * 解决android webview加载h5页面，
 * 输入键盘遮住输入框的bug。
 */
class AndroidBugForH5 private constructor(activity: Activity, translucentStatusBar: Boolean) {

    private val mChildOfContent: View
    private var usableHeightPrevious: Int = 0
    private val frameLayoutParams: FrameLayout.LayoutParams

    init {
        val content = activity.findViewById<View>(android.R.id.content) as FrameLayout
        mChildOfContent = content.getChildAt(0)
        mChildOfContent.viewTreeObserver.addOnGlobalLayoutListener { possiblyResizeChildOfContent(translucentStatusBar) }
        frameLayoutParams = mChildOfContent.layoutParams as FrameLayout.LayoutParams
    }

    private fun possiblyResizeChildOfContent(translucentStatusBar: Boolean) {
        val usableHeightNow = computeUsableHeight()
        if (usableHeightNow != usableHeightPrevious) {
            val usableHeightSansKeyboard = mChildOfContent.rootView.height
            val heightDifference = usableHeightSansKeyboard - usableHeightNow
            if (heightDifference > usableHeightSansKeyboard / 4) {
                // keyboard probably just became visible
                if (translucentStatusBar) {
                    //浸式状态栏样式，计算时需要加上状态栏高度
                    frameLayoutParams.height = usableHeightNow + getStatusBarHeight(mChildOfContent.context)
                } else {
                    frameLayoutParams.height = usableHeightNow
                }
            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = (mChildOfContent.parent as FrameLayout).height
            }
            mChildOfContent.requestLayout()
            usableHeightPrevious = usableHeightNow
        }
    }

    /**
     * 获取除虚拟按键、输入软键盘、状态栏以外的可视区域的高度
     */
    private fun computeUsableHeight(): Int {
        val r = Rect()
        mChildOfContent.getWindowVisibleDisplayFrame(r)
        return r.bottom - r.top// 全屏模式下： return r.bottom
    }

    /**
     * 获取状态栏的高度
     */
    private fun getStatusBarHeight(context: Context): Int {
        var height = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            height = context.resources.getDimensionPixelSize(resourceId)
        }
        return height
    }

    companion object {
        fun assistActivity(activity: Activity, translucentStatusBar: Boolean) {
            AndroidBugForH5(activity, translucentStatusBar)
        }
    }
}
