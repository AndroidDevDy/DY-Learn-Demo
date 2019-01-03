package com.daiy.dylib.fragment;

/**
 * 文件名：OnLeaveOrBackListener
 * 描述：供离开和返回两个时刻调用的方法接口
 * 作者：DaiYue
 * 时间：2016/6/3
 */
public interface OnLeaveOrBackListener {
    /**
     * 当离开时执行
     */
    void leave();

    /**
     * 当回来时执行
     */
    void back();
}
