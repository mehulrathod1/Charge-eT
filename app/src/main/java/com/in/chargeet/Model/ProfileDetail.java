package com.in.chargeet.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileDetail {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("data")
    @Expose
    Detail data;

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

    public Detail getData() {
        return data;
    }

    public void setData(Detail data) {
        this.data = data;
    }

    public class Detail {

        @SerializedName("user_id")
        @Expose
        String user_id;

        @SerializedName("name")
        @Expose
        String name;

        @SerializedName("surname")
        @Expose
        String surname;

        @SerializedName("profile_image")
        @Expose
        String profile_image;

        @SerializedName("username")
        @Expose
        String username;

        @SerializedName("email")
        @Expose
        String email;

        @SerializedName("phone_number")
        @Expose
        String phone_number;

        @SerializedName("gender")
        @Expose
        String gender;

        @SerializedName("town")
        @Expose
        String town;

        @SerializedName("country")
        @Expose
        String country;

        @SerializedName("dob")
        @Expose
        String dob;


        @SerializedName("description")
        @Expose
        String description;


        @SerializedName("town_id")
        @Expose
        String town_id;


        @SerializedName("country_id")
        @Expose
        String country_id;


        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTown_id() {
            return town_id;
        }

        public void setTown_id(String town_id) {
            this.town_id = town_id;
        }

        public String getCountry_id() {
            return country_id;
        }

        public void setCountry_id(String country_id) {
            this.country_id = country_id;
        }
    }
}
