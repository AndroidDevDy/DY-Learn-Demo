package com.daiy.learn.test_retrofit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {
    private static ApiManager manager;

    public static ApiManager getInstance() {
        if (manager == null) {
            synchronized (ApiManager.class) {
                if (manager == null) {
                    manager = new ApiManager();
                }
            }
        }
        return manager;
    }

    private final String baseUrl = "http://yilingmuyan.zpftech.com/api/common/";
    private Retrofit retrofit;
    private ApiService apiService;

    public ApiManager() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.SECONDS).build();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public void login() {
        apiService.login("17761261237", "123456")
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        System.out.println("ASD===========" + response.toString());
                        System.out.println("ASD===========" + response.body());
                        try {
                            System.out.println("ASD===========" + response.body().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        System.out.println("ASD===========" + t.toString());
                    }
                });
    }

    public void login2() {
        try {
            Response<Object> response = apiService.getAllGoods().execute();
            System.out.println("ASD===========" + response.toString());
            System.out.println("ASD===========" + response.body());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ASD===========" + e.toString());
        }
    }

    public void login3() {
        try {
            Response<Object> response = apiService.login2("17761261237", "123456").execute();
            System.out.println("ASD===========" + response.toString());
            System.out.println("ASD===========" + response.body());
            System.out.println("ASD===========" + response.body().toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ASD===========" + e.toString());
        }
    }

    public void login4() {
        apiService.login3("17761261237", "123456")
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.single())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        System.out.println("ASD===========" + o.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }).dispose();
    }

    public void login5() {
        apiService.login3("17761261237", "123456")
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.single())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        System.out.println("ASD===========" + o.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void login6() {
        apiService.login3("17761261237", "123456")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        System.out.println("ASD===========" + o.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }).dispose();
    }

}
