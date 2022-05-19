package com.in.chargeet.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OtherVehicleModel {
    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    List<OtherVehicle> data = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<OtherVehicle> getData() {
        return data;
    }

    public void setData(List<OtherVehicle> data) {
        this.data = data;
    }

    public static class OtherVehicle {

        @SerializedName("id")
        @Expose
        String id;

        @SerializedName("user_vehicle_id")
        @Expose
        String user_vehicle_id;

        @SerializedName("name")
        @Expose
        String name;

        @SerializedName("manufacturer")
        @Expose
        String manufacturer;


        @SerializedName("description")
        @Expose
        String description;


        @SerializedName("rate")
        @Expose
        String rate;

        @SerializedName("image")
        @Expose
        String image;

        public OtherVehicle(String id, String user_vehicle_id, String name, String manufacturer, String description, String rate, String image) {
            this.id = id;
            this.user_vehicle_id = user_vehicle_id;
            this.name = name;
            this.manufacturer = manufacturer;
            this.description = description;
            this.rate = rate;
            this.image = image;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_vehicle_id() {
            return user_vehicle_id;
        }

        public void setUser_vehicle_id(String user_vehicle_id) {
            this.user_vehicle_id = user_vehicle_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
