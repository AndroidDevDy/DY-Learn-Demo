package com.daiy.kotlin.learn

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.daiy.kotlin.lib.notch.NotchCompat
import com.daiy.kotlin.lib.utils.ToastUtil

class MainActivity : AppCompatActivity() {
    //定义变量，并声明稍后初始化
    private lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn = findViewById(R.id.btn_1)
        btn.setOnClickListener {
            //activity跳转
            //            startActivity(Intent(this, MainActivity::class.java))
            val b = NotchCompat.hasDisplayCutout(window)
            Log.e("ASD", "Notch:=========" + b.toString())
            val list = NotchCompat.getDisplayCutoutSize(window)
            //for循环遍历
            for (rect in list) {
                Log.e("ASD", "Rect:=========" + rect.toString())
                Log.e("ASD", "Height:=========" + (rect.bottom - rect.top))
            }
            showT("1234567890", "asdfghj", "zxcvb")

            showObj("asdzxc123", { msg -> showT(msg) }
                    , { msg -> showT(msg + "\n") })
            //匿名内部内，用于回调接口实例
            showInf("123qweasd", object : testInf<String> {
                override fun show(url: String) {
                    showT(url)
                }
            })
        }
    }

    /**
     * 定义静态变量
     */
    companion object {
        var toast: Toast? = null
    }

    /**
     * 支持多参数的方法定义
     */
    fun showT(vararg args: String): Unit {
        var result = ""
        for (item in args) {
            val itemN = "\n" + item
            result += itemN
        }
        ToastUtil.show(this, result.substring(1))
    }

    /**
     * 定义的方法参数中含有函数
     * 在使用时需要实现该函数
     * 类似回调
     */
    fun showObj(msg: String, vararg show: (msg: String) -> Unit) {
        for (item in show) {
            item(msg)
        }
    }

    /**
     * 定义的方法参数中含有接口
     * 与java中的回调一致
     */
    fun showInf(url: String, inf: testInf<String>): Unit {
        inf.show(url)
    }

    /**
     * 定义接口
     */
    interface testInf<T> {
        fun show(url: T)
    }

    /**
     * 继承接口
     */
    class myInf<T> : testInf<T> {
        override fun show(url: T) {

        }
    }
}
