package com.in.chargeet.Retrofit;

import com.google.gson.annotations.Expose;
import com.in.chargeet.Model.CommonModel;
import com.in.chargeet.Model.CountryModel;
import com.in.chargeet.Model.LoginModel;
import com.in.chargeet.Model.OtherVehicleModel;
import com.in.chargeet.Model.PowerStationDetailModel;
import com.in.chargeet.Model.PowerStationModel;
import com.in.chargeet.Model.PrimaryVehicleModel;
import com.in.chargeet.Model.ProfileDetail;
import com.in.chargeet.Model.VehicleModel;

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

    @FormUrlEncoded
    @POST("get_profile")
    Call<ProfileDetail> getProfile(
            @Field("token") String token,
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("update_profile")
    Call<CommonModel> updateProfile(

            @Field("token") String token,
            @Field("user_id") String user_id,
            @Field("email") String email,
            @Field("phone_number") String phone_number,
            @Field("name") String name,
            @Field("surname") String surname,
            @Field("gender") String gender,
            @Field("town_id") String town_id,
            @Field("country_id") String country_id,
            @Field("description") String description


    );

    @FormUrlEncoded
    @POST("get_vehicles")
    Call<VehicleModel> getVehicle(

            @Field("token") String token,
            @Field("manufacturer_id") String manufacturer_id,
            @Field("model") String model
    );

    @FormUrlEncoded
    @POST("get_manufacturers")
    Call<CountryModel> getManufacturer(
            @Field("token") String token

    );

    @FormUrlEncoded
    @POST("get_vehicle_models")
    Call<CountryModel> getModel(

            @Field("token") String token,
            @Field("manufacturer_id") String manufacturer_id

    );

    @FormUrlEncoded
    @POST("add_vehicle")
    Call<CommonModel> selectVehicle(

            @Field("token") String token,
            @Field("user_id") String user_id,
            @Field("vehicle_id") String vehicle_id
    );

    @FormUrlEncoded
    @POST("get_primary_vehicles")
    Call<PrimaryVehicleModel> getPrimaryVehicle(

            @Field("token") String token,
            @Field("user_id") String user_id

    );

    @FormUrlEncoded
    @POST("get_other_vehicles")
    Call<OtherVehicleModel> getOtherVehicle(

            @Field("token") String token,
            @Field("user_id") String user_id

    );

    @FormUrlEncoded
    @POST("make_primary_vehicle")
    Call<CommonModel> makePrimary(

            @Field("token") String token,
            @Field("user_id") String user_id,
            @Field("user_vehicle_id") String user_vehicle_id

    );

    @FormUrlEncoded
    @POST("remove_vehicle")
    Call<CommonModel> removeVehicle(

            @Field("token") String token,
            @Field("user_id") String user_id,
            @Field("user_vehicle_id") String user_vehicle_id

    );

    @FormUrlEncoded
    @POST("get_power_stations")
    Call<PowerStationModel> getPowerStation(

            @Field("token") String token

    );


    @FormUrlEncoded
    @POST("get_power_station_detail")
    Call<PowerStationDetailModel> getPowerStationDetail(

            @Field("token") String token,
            @Field("power_station_id") String power_station_id

    );


    @FormUrlEncoded
    @POST("booking_charging_point")
    Call<CommonModel> bookChargingPoint(

            @Field("token") String token,
            @Field("user_id") String user_id,
            @Field("power_station_id") String power_station_id,
            @Field("connectors_id") String connectors_id,
            @Field("booking_date") String booking_date,
                @Field("booking_time") String booking_time,
            @Field("payment_method") String payment_method

    );

}
