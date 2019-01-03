package com.daiy.learn.mvp_mode;

import com.daiy.dylib.logcat.LogTools;
import com.daiy.dylib.mvp.BasePresenter;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class MvpPresenter extends BasePresenter<MvpView> {

    /**
     * 获取数据
     */
    public void getData() {
        LogTools.loge("访问接口，获取数据");

        //
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                long time = System.currentTimeMillis();
                emitter.onNext(time);
                if (time % 2 != 0) {
                    emitter.onError(new IllegalStateException("Odd millisecond!"));
                }
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                mView.showData(o);
            }
        }, Throwable::printStackTrace);
        mCompositeDisposable.add(disposable);
    }
}
