package com.daiy.learn.test_retrofit;

import java.io.File;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiService {

    @POST("login/login")
    @FormUrlEncoded
    Call<Object> login2(@Field("phone") String phone,
                        @Field("password") String password);

    @HTTP(method = "POST", path = "login/login", hasBody = true)
    @FormUrlEncoded
    Call<Object> login(@Field("phone") String phone,
                       @Field("password") String password);

    @GET("")
    void getUser(@Query("id") String userId);

    @DELETE("")
    @Headers("token:123456789")
    void delMsg(@Query("id") String msgId);

    @PUT("")
    void updateUserIcon(@Header("token") String token, @Field("file") File file);

    @GET("http://120.77.35.55:8762/goods/all")
    Call<Object> getAllGoods();

    @POST("login/login")
    @FormUrlEncoded
    Observable<Object> login3(@Field("phone") String phone,
                              @Field("password") String password);
}
