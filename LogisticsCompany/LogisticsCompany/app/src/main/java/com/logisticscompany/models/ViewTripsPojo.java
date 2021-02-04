package com.logisticscompany.models;

public class ViewTripsPojo {
    private String typeofload;
    private String pickuptime;
    private String pickuplocation;
    private String deliverytime;
    private String deliverylocation;
    private String costperhour;

    public ViewTripsPojo() {
    }

    public ViewTripsPojo(String typeofload, String pickuptime, String pickuplocation, String deliverytime, String deliverylocation, String costperhour) {
        this.typeofload = typeofload;
        this.pickuptime = pickuptime;
        this.pickuplocation = pickuplocation;
        this.deliverytime = deliverytime;
        this.deliverylocation = deliverylocation;
        this.costperhour = costperhour;
    }

    public String getTypeofload() {
        return typeofload;
    }

    public void setTypeofload(String typeofload) {
        this.typeofload = typeofload;
    }

    public String getPickuptime() {
        return pickuptime;
    }

    public void setPickuptime(String pickuptime) {
        this.pickuptime = pickuptime;
    }

    public String getPickuplocation() {
        return pickuplocation;
    }

    public void setPickuplocation(String pickuplocation) {
        this.pickuplocation = pickuplocation;
    }

    public String getDeliverytime() {
        return deliverytime;
    }

    public void setDeliverytime(String deliverytime) {
        this.deliverytime = deliverytime;
    }

    public String getDeliverylocation() {
        return deliverylocation;
    }

    public void setDeliverylocation(String deliverylocation) {
        this.deliverylocation = deliverylocation;
    }

    public String getCostperhour() {
        return costperhour;
    }

    public void setCostperhour(String costperhour) {
        this.costperhour = costperhour;
    }
}
