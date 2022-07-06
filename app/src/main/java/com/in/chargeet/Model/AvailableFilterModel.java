package com.in.chargeet.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvailableFilterModel {


    @SerializedName("id")
    @Expose
    String id;


    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("description")
    @Expose
    String description;

    @SerializedName("power")
    @Expose
    String power;

    @SerializedName("rate")
    @Expose
    String rate;

    @SerializedName("connectors")
    @Expose
    String connectors;

    @SerializedName("latitude")
    @Expose
    String latitude;

    @SerializedName("longitude")
    @Expose
    String longitude;

    @SerializedName("icon")
    @Expose
    String icon;


    public AvailableFilterModel(String id, String name, String description, String power, String rate, String connectors, String latitude, String longitude, String icon) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.power = power;
        this.rate = rate;
        this.connectors = connectors;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getConnectors() {
        return connectors;
    }

    public void setConnectors(String connectors) {
        this.connectors = connectors;
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
