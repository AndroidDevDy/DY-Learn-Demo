package com.daiy.kotlin.learn

import android.content.Context
import com.daiy.kotlin.lib.common.OnMessageBack
import com.daiy.kotlin.lib.common.OnSelectBack
import com.daiy.kotlin.lib.utils.ToastUtil

/**
 * kotlin使用指南1
 */
class Example1 {

    /**
     * 方法中的参数用vararg修饰，可以传入多个
     */
    fun showMsg(context: Context, vararg msg: String) {
        for (item in msg)
            ToastUtil.show(context, item)
    }

    /**
     * 方法中的参数，是一个不带参数的方法
     */
    fun showMsg(show: () -> Unit) {
        show()
    }

    /**
     * 方法中的参数，是带参数的方法
     */
    fun showMsg(msg: String, show: (arg: String) -> Unit) {
        show(msg)
    }

    /**
     * 方法中参数,是一个接口
     */
    fun showMsg(msg: String, back: OnMessageBack) {
        back.onBack(msg)
    }

    /**
     * 方法中参数,是一个接口
     */
    fun showMsg(b: Boolean, back: OnSelectBack) {
        back.onback(b)
    }
}