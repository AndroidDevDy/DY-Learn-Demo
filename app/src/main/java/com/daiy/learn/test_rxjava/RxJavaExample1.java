package com.daiy.learn.test_rxjava;

import android.annotation.SuppressLint;

import com.daiy.dylib.logcat.LogTools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Creating Observables
 * 创建可观察对象
 */
public class RxJavaExample1 {
    String str;

    /*
     *=======================================创建操作符=============================================
     */

    /**
     * create的用法1
     * create an Observable from scratch by calling observer methods programmatically
     */
    public void test01() {
        LogTools.loge("======test01======");
        Disposable disposable = Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            emitter.onNext(10);
            emitter.onError(new Throwable("error"));
            emitter.onComplete();
        }).subscribe(integer -> {
            LogTools.loge("test01======" + integer);
        }, throwable -> {
            LogTools.loge("test01======" + throwable.getMessage());
        }, () -> {
            LogTools.loge("test01======complete");
        });
    }

    /**
     * create的用法2
     */
    public void test02() {
        LogTools.loge("======test02======");
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            emitter.onNext(10);
            emitter.onError(new Throwable("error"));
            emitter.onComplete();
        }).subscribe(new DefaultObserver<Integer>() {
            @Override
            public void onNext(Integer integer) {
                LogTools.loge("test02======" + integer);
            }

            @Override
            public void onError(Throwable e) {
                LogTools.loge("test02======" + e.getMessage());
            }

            @Override
            public void onComplete() {
                LogTools.loge("test02======complete");
            }
        });
    }

    /**
     * defer的用法
     * do not create the Observable until the observer subscribes, and create a fresh Observable for each observer
     */
    public void test03() {
        LogTools.loge("======test03======");
        Disposable disposable = Observable.defer(new Callable<ObservableSource<?>>() {
            @Override
            public ObservableSource<?> call() throws Exception {
                return new Observable<Object>() {
                    @Override
                    protected void subscribeActual(Observer<? super Object> observer) {
                        observer.onNext("asdfghjkl");
                    }
                };
            }
        }).subscribe(o -> {
            LogTools.loge("test03======" + o.toString());
        });
    }

    /**
     * defer的用法
     */
    public void test04() {
        LogTools.loge("======test04======");
        Disposable disposable = Observable.defer(new Callable<Observable<?>>() {

            @Override
            public Observable<?> call() {
                Object result = "asdzxcjkl";
                return Observable.just(result);
            }
        }).subscribe(o -> {
            LogTools.loge("test04======" + o.toString());
        });
    }

    /**
     * defer的用法，延迟创建Observable，订阅时才创建
     */
    @SuppressLint("CheckResult")
    public void test05() {
        str = "11111";
        LogTools.loge("======test05======");
        Observable observable1 = Observable.just(str);
        str = "22222";
        observable1.subscribe(o -> {
            LogTools.loge("test05===1===" + o.toString());
        });
        str = "33333";
        observable1.subscribe(o -> {
            LogTools.loge("test05===2===" + o.toString());
        });
        str = "44444";
        Observable observable2 = Observable.defer(() -> Observable.just(str));
        observable2.subscribe(o -> {
            LogTools.loge("test05===3===" + o.toString());
        });
        str = "66666";
        observable2.subscribe(o -> {
            LogTools.loge("test05===4===" + o.toString());
        });
    }

    /**
     * empty的用法
     * create an Observable that emits no items but terminates normally
     */
    @SuppressLint("CheckResult")
    public void test06() {
        LogTools.loge("======test06======");
        Observable.empty().subscribe(o -> {
        }, throwable -> {
        }, () -> {
            LogTools.loge("test06======Completed");
        });
    }

    /**
     * never的用法
     * create an Observable that emits no items and does not terminate
     */
    @SuppressLint("CheckResult")
    public void test07() {
        LogTools.loge("======test07======");
        Observable.never().subscribe(o -> {
            LogTools.loge("test07======1");
        }, throwable -> {
            LogTools.loge("test07======2");
        }, () -> {
            LogTools.loge("test07======3");
        });
    }

    /**
     * error的用法
     * create an Observable that emits no items and terminates with an error
     */
    @SuppressLint("CheckResult")
    public void test08() {
        LogTools.loge("======test08======");
        Observable.error(new Throwable("error")).subscribe(o -> {
            LogTools.loge("test08======1");
        }, throwable -> {
            LogTools.loge("test08======" + throwable.getMessage());
        }, () -> {
            LogTools.loge("test08======3");
        });
    }

    /**
     * fromCallable的用法
     */
    public void test09() {
        LogTools.loge("======test09======");
        Disposable disposable = Observable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return "qwertyuiop";
            }
        }).subscribe(o -> {
            LogTools.loge("test09======" + o.toString());
        });
    }

    /**
     * fromArray的用法
     */
    public void test10() {
        LogTools.loge("======test10======");
        String[] array = new String[]{"111", "222", "333"};
        Disposable disposable = Observable.fromArray(array)
                .subscribe(o -> LogTools.loge("test10======" + o));
        disposable = Observable.fromArray("111", "222", "333")
                .subscribe(o -> LogTools.loge("test10======" + o));
    }

    /**
     * buffer的用法
     */
    public void test11() {
        LogTools.loge("======test11======");
        String[] array = new String[]{"111", "222", "333"};
        Disposable disposable = Observable.fromArray(array)
                .buffer(2)
                .subscribe(o -> LogTools.loge("test11======" + o));
    }

    /**
     * interval的用法
     */
    public void test12() {
        LogTools.loge("======test12======");
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        LogTools.loge("test12======Thread:" + Thread.currentThread().getId() + "=======" + df.format(new Date()) + "===start");
        disposable = Observable.defer(() -> {
            LogTools.loge("test12======Thread:" + Thread.currentThread().getId() + "=======" + df.format(new Date()) + "===create data");
            return Observable.interval(1000, 100, TimeUnit.MILLISECONDS, Schedulers.computation());
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    LogTools.loge("test12======Thread:" + Thread.currentThread().getId() + "=======" + df.format(new Date()) + "===" + o);
                    if (o == 9 && !disposable.isDisposed()) {
                        disposable.dispose();
                    }
                }, throwable -> {
                    LogTools.loge("test12======Thread:" + Thread.currentThread().getId() + "=======" + df.format(new Date()) + "===" + throwable.getMessage());
                });
    }

    Disposable disposable = null;

    /**
     * just的用法
     */
    public void test13() {
        LogTools.loge("======test13======");
        Disposable disposable = Observable.just(1, 2, 3).subscribe(integer -> {
            LogTools.loge("test13======" + String.valueOf(integer));
        });
    }

    /**
     * range的用法
     */
    public void test14() {
        LogTools.loge("======test14======");
        Disposable disposable = Observable.range(1, 5).subscribe(integer -> {
            LogTools.loge("test14======" + String.valueOf(integer));
        });
    }

    /**
     * repeat的用法
     */
    public void test15() {
        LogTools.loge("======test15======");
        disposable2 = Observable.just("repeat_test")
                .repeat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    int i = 0;

                    @Override
                    public void accept(String s) throws Exception {
                        i++;
                        LogTools.loge("test15======Thread:" + Thread.currentThread().getId() + "=====" + s);
                        if (i == 12 && !disposable2.isDisposed()) {
                            disposable2.dispose();
                        }
                    }
                }, throwable -> {
                    LogTools.loge("test15======Thread:" + Thread.currentThread().getId() + "=====error");
                }, () -> {
                    LogTools.loge("test15======Thread:" + Thread.currentThread().getId() + "=====completed");
                });
    }

    Disposable disposable2 = null;

    /**
     * repeat的用法
     */
    public void test16() {
        LogTools.loge("======test16======");
        Observable.just("123")
                .repeat(5)
                .doOnNext(s -> {
                    LogTools.loge("test16======" + s);
                })
                .subscribe();
    }

    /**
     * repeat的用法
     */
    public void test17() {
        LogTools.loge("======test17======");
        Observable.just("123")
                .repeatUntil(new BooleanSupplier() {
                    int j = 0;

                    @Override
                    public boolean getAsBoolean() throws Exception {
                        j++;
                        if (j >= 5)
                            return true;
                        return false;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(s -> {
                    LogTools.loge("test17======" + s);
                })
                .subscribe();
    }

    /**
     * repeat的用法
     */
    public void test18() {
        LogTools.loge("======test18======");
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                emitter.onNext("1");
                emitter.onComplete();
//                emitter.onError(new Throwable("error"));
            }
        })
                .doOnComplete(() -> {
                    LogTools.loge("test18======doOnComplete");
                })
                .doOnError(throwable -> {
                    LogTools.loge("test18======doOnError");
                })
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    int i = 0;

                    @Override
                    public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                        return objectObservable.flatMap(o -> {
                            i++;
                            LogTools.loge("test18======" + i + "===" + o.toString());
                            if (i == 3) {
                                return Observable.empty();
                            }
                            return Observable.just(o);
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    LogTools.loge("test18===1===" + o.toString());
                }, throwable -> {
                    LogTools.loge("test18===1===doOnError");
                }, () -> {
                    LogTools.loge("test18===1===doOnComplete");
                });
    }

    /**
     * timer的用法
     */
    public void test19() {
        LogTools.loge("===test19===");
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .doOnNext(s -> {
                    LogTools.loge("test19===" + s);
                })
                .subscribe();
    }

    /**
     * delay的用法
     */
    public void test20() {
        LogTools.loge("===test20===");
        Observable.just("123", "134", "198", "241", "3341", "1002")
                .delay(1000, TimeUnit.MILLISECONDS)
                .doOnNext(s -> {
                    LogTools.loge("test20===" + s);
                })
                .subscribe();
    }

    /*
     *=======================================变换操作符=============================================
     */

    /**
     * flatMap的用法
     */
    public void test21() {
        LogTools.loge("===test21===");
        Observable.just("123", "315", "289", "301")
                .flatMap(new Function<String, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(String s) throws Exception {
                        if (s.contains("1")) {
                            return Observable.just(s);
                        }
                        return Observable.empty();
                    }
                })
                .doOnNext(s -> {
                    LogTools.loge("test21======" + s);
                }).subscribe();
    }

    /**
     * concatMapIterable的用法
     */
    public void test22() {
        LogTools.loge("===test22===");
        Observable.just("123", "315", "289", "301")
                .concatMapIterable(new Function<String, Iterable<?>>() {
                    @Override
                    public Iterable<?> apply(String s) throws Exception {
                        if (s.contains("1")) {
                            return Arrays.asList(s + "A", s + "B", s + "C");
                        }
                        return Collections.emptyList();
                    }
                })
                .doOnNext(s -> {
                    LogTools.loge("test22======" + s);
                }).subscribe();
    }

    /**
     * switchMap的用法
     */
    public void test23() {
        LogTools.loge("===test23===");
        Observable.just("123", "315", "289", "301")
                .switchMap(new Function<String, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(String s) throws Exception {
                        if (s.contains("1")) {
                            return Observable.just(s + "A", s + "B");
                        }
                        return Observable.empty();
                    }
                })
                .doOnNext(s -> {
                    LogTools.loge("test23======" + s);
                }).subscribe();
    }

    /**
     * groupBy的用法
     */
    public void test24() {
        LogTools.loge("===test24===");
        Observable.just("123", "315", "289", "301")
                .groupBy(new Function<String, Object>() {
                    @Override
                    public Object apply(String s) throws Exception {
                        if (s.contains("1")) return "group1";
                        return "group2";
                    }
                }, new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return s + "___";
                    }
                })
                .doOnNext(s -> {
                    LogTools.loge("test24===group===" + s.getKey());
                })
                .flatMap(new Function<GroupedObservable<Object, String>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(GroupedObservable<Object, String> objectStringGroupedObservable) throws Exception {
                        return objectStringGroupedObservable.map(new Function<String, Object>() {
                            @Override
                            public Object apply(String s) throws Exception {
                                return s + objectStringGroupedObservable.getKey();
                            }
                        });
                    }
                })
                .doOnNext(s -> {
                    LogTools.loge("test24===value===" + s);
                }).subscribe();
    }

    /**
     * map的用法
     */
    public void test25() {
        LogTools.loge("===test25===");
        Observable.just("123", "315", "289", "301")
                .flatMap(new Function<String, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(String s) throws Exception {
                        return Observable.just(s + "_H_");
                    }
                })
                .map(new Function<Object, Object>() {
                    @Override
                    public Object apply(Object o) throws Exception {
                        return o + "ABC";
                    }
                })
                .doOnNext(s -> {
                    LogTools.loge("test25======" + s);
                }).subscribe();
    }

    /**
     * cast的用法
     */
    public void test26() {
        LogTools.loge("===test26===");
        Observable.just(12.3, 31.5, 28.9, 30.1)
                .cast(Object.class)
                .doOnNext(s -> {
                    LogTools.loge("test26======" + s);
                }).subscribe();
    }

    /**
     * scan的用法
     * 累加器
     */
    public void test27() {
        LogTools.loge("===test27===");
        Observable.just(1, 2, 3, 4, 5)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        LogTools.loge("test27===last===" + integer);
                        LogTools.loge("test27===add===" + integer2);
                        return integer + integer2;
                    }
                })
                .doOnNext(s -> {
                    LogTools.loge("test27===result===" + s);
                }).subscribe();
    }

    /**
     * window的用法
     */
    public void test28() {
        LogTools.loge("===test28===");
        Observable.just(1, 2, 3, 4, 5)
                .window(3)
                .flatMap(new Function<Observable<Integer>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Integer> integerObservable) throws Exception {
                        LogTools.loge("test28======flatMap");
                        return integerObservable;
                    }
                })
                .doOnNext(s -> {
                    LogTools.loge("test28======" + s);
                }).subscribe();
    }

    /*
     *=======================================过滤操作符=============================================
     */

    /**
     * debounce的用法
     * 如果源数据的发送间隔小于900毫秒，将被debounce过滤掉
     */
    public void test29() {
        LogTools.loge("===test29===");
        Observable.intervalRange(0, 10, 0, 1000, TimeUnit.MILLISECONDS)
                .debounce(900, TimeUnit.MILLISECONDS)
                .doOnNext(s -> {
                    LogTools.loge("test29======" + s);
                }).subscribe();
    }

    /**
     * distinct的用法
     * 过滤掉重复的项
     */
    public void test30() {
        LogTools.loge("===test30===");
        Observable.just(1, 2, 3, 2, 4, 1, 5, 9)
                .distinct(new Function<Integer, Object>() {
                    @Override
                    public Object apply(Integer integer) throws Exception {
                        String key = "key:" + integer;
                        LogTools.loge("test30======" + key);
                        return key;
                    }
                })
                .doOnNext(s -> {
                    LogTools.loge("test30======" + s);
                }).subscribe();
    }

    /**
     * distinctUntilChanged的用法
     * 过滤掉相邻的重复项
     */
    public void test31() {
        LogTools.loge("===test31===");
        Observable.just(1, 2, 2, 2, 3, 3, 5, 9)
                .distinctUntilChanged(new Function<Integer, Object>() {
                    @Override
                    public Object apply(Integer integer) throws Exception {
                        String key = "key:" + integer;
                        LogTools.loge("test31======" + key);
                        return key;
                    }
                })
                .doOnNext(s -> {
                    LogTools.loge("test31======" + s);
                }).subscribe();
    }

    /**
     * elementAt的用法
     * 只发出指定位置的项
     */
    public void test32() {
        LogTools.loge("===test31===");
        Observable.just(1, 2, 2, 2, 3, 3, 5, 9)
                .elementAt(6, -1)
                .doOnSuccess(o -> {
                    LogTools.loge("test31======" + o);
                }).subscribe();
    }

    /**
     * filter的用法
     * 过滤掉不符合条件的项
     */
    public void test33() {
        LogTools.loge("===test33===");
        Observable.just(1, 2, 6, 2, 4, 3, 5, 9)
                .filter(integer -> integer > 3)
                .doOnNext(o -> {
                    LogTools.loge("test33======" + o);
                }).subscribe();
    }

    /**
     * filter的用法
     * 只取第一个项项
     */
    public void test34() {
        LogTools.loge("===test34===");
        Observable.just(1, 2, 6, 2, 4, 3, 5, 9)
                .first(-1)
                .doOnSuccess(o -> {
                    LogTools.loge("test34======" + o);
                }).subscribe();
    }

    /**
     * filter 的用法
     * 等待可观察对象发出一个项，若有多个项，将直接进入error
     */
    public void test35() {
        LogTools.loge("===test35===");
        Observable.just(1)
                .single(-1)
                .subscribe((integer, throwable) -> {
                    if (integer != null)
                        LogTools.loge("test35======" + integer);
                    if (throwable != null)
                        LogTools.loge("test35======" + throwable);
                });
    }

    /**
     * ignoreElements 的用法
     * 忽略可观察对象的所有发射项，只保留终止通知
     */
    public void test36() {
        LogTools.loge("===test36===");
        Observable.just(1, 2, 3, 4, 5)
                .ignoreElements()
                .doOnComplete(() -> {
                    LogTools.loge("test36======complete");
                })
                .subscribe();
    }

    /**
     * last 的用法
     * 获取可观察对象发出的最后一项
     */
    public void test37() {
        LogTools.loge("===test37===");
        Observable.just(1, 2, 3, 4, 5)
                .last(-1)
                .doOnSuccess(integer -> {
                    LogTools.loge("test37======" + integer);
                })
                .subscribe();
    }

    /**
     * last 的用法
     * 获取可观察对象发出的最后一项
     */
    public void test38() {
        LogTools.loge("===test38===");
        Observable.just(1, 2, 3, 4, 5)
                .last(-1)
                .doOnSuccess(integer -> {
                    LogTools.loge("test38======" + integer);
                })
                .subscribe();
    }

    /**
     * last 的用法
     * 获取可观察对象发出的最后一项
     */
    public void test39() {
        LogTools.loge("===test39===");
        Observable.just(1, 2, 3, 4, 5)
                .last(-1)
                .doOnSuccess(integer -> {
                    LogTools.loge("test39======" + integer);
                })
                .subscribe();
    }

    /**
     * sample 的用法
     * 获取可观察对象发出的最后一项
     */
    public void test40() {
        LogTools.loge("===test40===");
        Observable.just(1, 2, 3, 4, 5)
                .sample(1000, TimeUnit.MILLISECONDS)
                .doOnNext(integer -> {
                    LogTools.loge("test40======" + integer);
                })
                .subscribe();
    }
}
