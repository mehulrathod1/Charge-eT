package com.in.chargeet.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MyBookingModel {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    List<Booking> bookingList = new ArrayList<>();

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

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public static class Booking {

        @SerializedName("id")
        @Expose
        String id;


        @SerializedName("power_station_id")
        @Expose
        String power_station_id;


        @SerializedName("booking_date")
        @Expose
        String booking_date;


        @SerializedName("power_station_name")
        @Expose
        String power_station_name;


        @SerializedName("connectors_id")
        @Expose
        String connectors_id;


        @SerializedName("description")
        @Expose
        String description;

        @SerializedName("percentage")
        @Expose
        String percentage;

        @SerializedName("unit")
        @Expose
        String unit;

        @SerializedName("time")
        @Expose
        String time;


        @SerializedName("created_at")
        @Expose
        String created_at;

        public Booking(String id, String power_station_id, String booking_date, String power_station_name, String connectors_id, String description, String percentage, String unit, String time, String created_at) {
            this.id = id;
            this.power_station_id = power_station_id;
            this.booking_date = booking_date;
            this.power_station_name = power_station_name;
            this.connectors_id = connectors_id;
            this.description = description;
            this.percentage = percentage;
            this.unit = unit;
            this.time = time;
            this.created_at = created_at;
        }

        public String getPower_station_id() {
            return power_station_id;
        }

        public void setPower_station_id(String power_station_id) {
            this.power_station_id = power_station_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBooking_date() {
            return booking_date;
        }

        public void setBooking_date(String booking_date) {
            this.booking_date = booking_date;
        }

        public String getPower_station_name() {
            return power_station_name;
        }

        public void setPower_station_name(String power_station_name) {
            this.power_station_name = power_station_name;
        }

        public String getConnectors_id() {
            return connectors_id;
        }

        public void setConnectors_id(String connectors_id) {
            this.connectors_id = connectors_id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPercentage() {
            return percentage;
        }

        public void setPercentage(String percentage) {
            this.percentage = percentage;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }
}
