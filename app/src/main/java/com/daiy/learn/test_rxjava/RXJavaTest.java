package com.daiy.learn.test_rxjava;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RXJavaTest {

    public void test() throws Exception {
        test6();
    }

    private void test1() {
        Flowable.just("second").subscribe(System.out::println).dispose();
        Flowable.range(1, 5)
                .map(v -> v * v)
                .filter(v -> v % 3 == 0)
                .subscribe(System.out::println)
                .dispose();
    }

    private void test2() throws InterruptedException {
        Flowable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(1000); //  imitate expensive computation
                return "Done";
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(System.out::println, Throwable::printStackTrace);
        Thread.sleep(2000);
    }

    private void test3() {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                while (!emitter.isDisposed()) {
                    long time = System.currentTimeMillis();
                    emitter.onNext(time);
                    if (time % 2 != 0) {
                        emitter.onError(new IllegalStateException("Odd millisecond!"));
                        break;
                    }
                }
            }
        }).subscribe(System.out::println, Throwable::printStackTrace).dispose();
    }

    private void test4() {
        Flowable.range(1, 10)
                .observeOn(Schedulers.computation())
                .map(v -> v * v)
                .blockingSubscribe(System.out::println);
    }

    private void test4_1() {
        Flowable.range(1, 10)
                .flatMap(v -> Flowable
                        .just(v)
                        .observeOn(Schedulers.computation())
                        .map(w -> w * w)
                )
                .blockingSubscribe(System.out::println);
    }

    private void test4_2() {
        Flowable.range(1, 10)
                .concatMap(v -> Flowable
                        .just(v)
                        .observeOn(Schedulers.computation())
                        .map(w -> w * w)
                )
                .blockingSubscribe(System.out::println);
    }

    private void test4_3() {
        Flowable.range(1, 10)
                .concatMapEager(v -> Flowable
                        .just(v)
                        .observeOn(Schedulers.computation())
                        .map(w -> w * w)
                )
                .blockingSubscribe(System.out::println);
    }

    private void test4_4() {
        Flowable.range(1, 10)
                .parallel()
                .runOn(Schedulers.computation())
                .map(v -> v * v)
                .sequential()
                .blockingSubscribe(System.out::println);
    }

    private void test5() {
        AtomicInteger count = new AtomicInteger();

        Observable.range(1, 10)
                .doOnNext(ignored -> count.incrementAndGet())
                .ignoreElements()
                .andThen(Single.just(count.get()))
                .subscribe(System.out::println);
    }

    private void test5_1() {
        AtomicInteger count = new AtomicInteger();

        Observable.range(1, 10)
                .doOnNext(ignored -> count.incrementAndGet())
                .ignoreElements()
                .andThen(Single.fromCallable(() -> count.get()))
                .subscribe(System.out::println);
    }

    private void test5_2() {
        AtomicInteger count = new AtomicInteger();

        Observable.range(1, 10)
                .doOnNext(ignored -> count.incrementAndGet())
                .ignoreElements()
                .andThen(Single.defer(() -> Single.just(count.get())))
                .subscribe(System.out::println);
    }

    private void test6() {
        Observable.just("one", "two", "three", "four", "five")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(System.out::println);
    }


}
