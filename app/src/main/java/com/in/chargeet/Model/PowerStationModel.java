package com.in.chargeet.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PowerStationModel {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    List<PowerStation> dataList = new ArrayList<>();

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

    public List<PowerStation> getDataList() {
        return dataList;
    }

    public void setDataList(List<PowerStation> dataList) {
        this.dataList = dataList;
    }

    public static class PowerStation {

        @SerializedName("id")
        @Expose
        String id;

        @SerializedName("name")
        @Expose
        String name;

        @SerializedName("latitude")
        @Expose
        String latitude;

        @SerializedName("longitude")
        @Expose
        String longitude;

        @SerializedName("icon")
        @Expose
        String icon;


        public PowerStation(String id, String name, String latitude, String longitude, String icon) {
            this.id = id;
            this.name = name;
            this.latitude = latitude;
            this.longitude = longitude;
            this.icon = icon;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
