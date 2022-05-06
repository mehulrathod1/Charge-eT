package com.in.chargeet.Retrofit;

import com.google.gson.annotations.Expose;
import com.in.chargeet.Model.CommonModel;
import com.in.chargeet.Model.CountryModel;
import com.in.chargeet.Model.LoginModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("login")
    Call<LoginModel> login(
            @Field("token") String token,
            @Field("email") String email,
            @Field("password") String password

    );

    @FormUrlEncoded
    @POST("get_country")
    Call<CountryModel> getCountry(
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("forgot_password")
    Call<CommonModel> forgotPassword(
            @Field("token") String token,
            @Field("email") String email
    );
}
