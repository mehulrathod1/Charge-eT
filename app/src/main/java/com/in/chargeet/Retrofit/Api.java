package com.in.chargeet.Retrofit;

import com.google.gson.annotations.Expose;
import com.in.chargeet.Model.CountryModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("get_country")
    Call<CountryModel> getCountry(
            @Field("token") String token

    );
}
