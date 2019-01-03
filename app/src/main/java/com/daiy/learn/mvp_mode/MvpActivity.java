package com.daiy.learn.mvp_mode;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.daiy.dylib.logcat.LogTools;

public class MvpActivity extends Activity implements MvpView {
    private MvpPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MvpPresenter();
        presenter.attachView(this, this);
        presenter.getData();
    }

    @Override
    public void showData(Object o) {
        LogTools.loge("显示数据");
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }
}
