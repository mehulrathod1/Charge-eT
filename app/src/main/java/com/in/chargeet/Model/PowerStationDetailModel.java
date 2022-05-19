package com.in.chargeet.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PowerStationDetailModel {
    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    PowerStationData data;

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

    public PowerStationData getData() {
        return data;
    }

    public void setData(PowerStationData data) {
        this.data = data;
    }

    public class PowerStationData {

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


        @SerializedName("power")
        @Expose
        String power;


        @SerializedName("rate")
        @Expose
        String rate;


        @SerializedName("description")
        @Expose
        String description;


        @SerializedName("icon")
        @Expose
        String icon;


        @SerializedName("connectors")
        @Expose
        List<Connectors> connectors = new ArrayList<>();

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public List<Connectors> getConnectors() {
            return connectors;
        }

        public void setConnectors(List<Connectors> connectors) {
            this.connectors = connectors;
        }

        public class Connectors {

            @SerializedName("id")
            @Expose
            String id;

            @SerializedName("connectors")
            @Expose
            String connectors;

            @SerializedName("image")
            @Expose
            String image;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getConnectors() {
                return connectors;
            }

            public void setConnectors(String connectors) {
                this.connectors = connectors;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
    }
}
