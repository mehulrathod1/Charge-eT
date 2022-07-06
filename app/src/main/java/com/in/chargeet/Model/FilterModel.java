package com.in.chargeet.Model;

import android.hardware.lights.LightState;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FilterModel {
    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;


    @SerializedName("data")
    @Expose
    FilterData filterData;

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

    public FilterData getFilterData() {
        return filterData;
    }

    public void setFilterData(FilterData filterData) {
        this.filterData = filterData;
    }

    public class FilterData {

        @SerializedName("available")
        @Expose
        List<AvailableFilterModel> availableFilterModelList = new ArrayList<>();

        @SerializedName("not_available")
        @Expose
        List<NotAvailableFilterModel> notAvailableFilterModelList = new ArrayList<>();


        public List<AvailableFilterModel> getAvailableFilterModelList() {
            return availableFilterModelList;
        }

        public void setAvailableFilterModelList(List<AvailableFilterModel> availableFilterModelList) {
            this.availableFilterModelList = availableFilterModelList;
        }

        public List<NotAvailableFilterModel> getNotAvailableFilterModelList() {
            return notAvailableFilterModelList;
        }

        public void setNotAvailableFilterModelList(List<NotAvailableFilterModel> notAvailableFilterModelList) {
            this.notAvailableFilterModelList = notAvailableFilterModelList;
        }
    }
}
