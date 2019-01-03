package com.daiy.dylib.mvp;

import android.content.Context;

/**
 * author：JiaXing
 * e-mail：JiaXingGoo@gmail.com
 */
public interface IPresenter<T> {
    void attachView(Context context, T view);
    void detachView();
}
