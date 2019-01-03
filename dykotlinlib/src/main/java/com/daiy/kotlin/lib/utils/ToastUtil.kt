package com.daiy.kotlin.lib.utils

import android.content.Context
import android.widget.Toast

object ToastUtil {
    private var toast: Toast? = null
    fun show(context: Context, args: String) {
        if (toast == null) {
            toast = Toast.makeText(context, args, Toast.LENGTH_SHORT)
            toast?.show()
        } else {
            toast?.setText(args)
            toast?.show()
        }
    }
}