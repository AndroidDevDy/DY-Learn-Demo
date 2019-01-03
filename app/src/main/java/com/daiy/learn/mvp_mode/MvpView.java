package com.daiy.learn.mvp_mode;

import com.daiy.dylib.mvp.IView;

public interface MvpView extends IView {

    /**
     * 显示数据
     */
    public void showData(Object o);
}
