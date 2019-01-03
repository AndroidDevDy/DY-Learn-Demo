package com.daiy.dylib.mvp;

import android.content.Context;

import io.reactivex.disposables.CompositeDisposable;

/**
 * author：JiaXing
 * e-mail：JiaXingGoo@gmail.com
 */
public abstract class BasePresenter<T> implements IPresenter<T> {

    protected Context mContext;
    protected T mView;
    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    public void attachView(Context context, T view) {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void detachView() {
        mCompositeDisposable.clear();
        this.mView = null;
    }

}
