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


        @SerializedName("booking_date")
        @Expose
        String booking_date;


        @SerializedName("power_station_name")
        @Expose
        String power_station_name;


        @SerializedName("description")
        @Expose
        String description;


        @SerializedName("created_at")
        @Expose
        String created_at;


        public Booking(String id, String booking_date, String power_station_name, String description, String created_at) {
            this.id = id;
            this.booking_date = booking_date;
            this.power_station_name = power_station_name;
            this.description = description;
            this.created_at = created_at;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }
}
