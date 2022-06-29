package com.in.chargeet.Model;

public class WattModel {
    String kiloWatt;
    boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public WattModel(String kiloWatt) {
        this.kiloWatt = kiloWatt;
    }

    public String getKiloWatt() {
        return kiloWatt;
    }

    public void setKiloWatt(String kiloWatt) {
        this.kiloWatt = kiloWatt;
    }
}
