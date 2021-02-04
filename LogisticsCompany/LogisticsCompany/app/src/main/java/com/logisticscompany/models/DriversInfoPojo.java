package com.logisticscompany.models;

public class DriversInfoPojo {
    private String driverName;
    private String DriverLocation;
    private String costPerHour;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverLocation() {
        return DriverLocation;
    }

    public void setDriverLocation(String driverLocation) {
        DriverLocation = driverLocation;
    }

    public String getCostPerHour() {
        return costPerHour;
    }

    public void setCostPerHour(String costPerHour) {
        this.costPerHour = costPerHour;
    }

    public DriversInfoPojo(String driverName, String driverLocation, String costPerHour) {
        this.driverName = driverName;
        DriverLocation = driverLocation;
        this.costPerHour = costPerHour;
    }
}
