package com.in.chargeet.Model;

public class FilterConnectorModel {

    String connectorName;
    int connectorIcon;
    private Boolean isSelected = false;



    public FilterConnectorModel(String connectorName, int connectorIcon) {
        this.connectorName = connectorName;
        this.connectorIcon = connectorIcon;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

    public int getConnectorIcon() {
        return connectorIcon;
    }

    public void setConnectorIcon(int connectorIcon) {
        this.connectorIcon = connectorIcon;
    }
}
