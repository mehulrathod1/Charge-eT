package com.in.chargeet.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ManufacturerModel {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("status")
    @Expose
    Manufacturer data;

    public class Manufacturer {

        @SerializedName("id")
        @Expose
        String id;

        @SerializedName("name")
        @Expose
        String name;

    }
}
