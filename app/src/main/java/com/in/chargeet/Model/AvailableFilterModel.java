package com.in.chargeet.Model;

public class AvailableFilterModel {

    String percentage,units,times;

    public AvailableFilterModel(String percentage, String units, String times) {
        this.percentage = percentage;
        this.units = units;
        this.times = times;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
