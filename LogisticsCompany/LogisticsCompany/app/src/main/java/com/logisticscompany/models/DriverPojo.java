package com.logisticscompany.models;

public class DriverPojo {
    private String Firstname;
    private String image;
    private String Lastname;
    private String Phone;
    private String Email;
    private String Username;
    private String Password;
    private String Vehiclerenum;
    private String Vehicle_type;

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getVehiclerenum() {
        return Vehiclerenum;
    }

    public void setVehiclerenum(String vehiclerenum) {
        Vehiclerenum = vehiclerenum;
    }

    public String getVehicle_type() {
        return Vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        Vehicle_type = vehicle_type;
    }

    public DriverPojo(String firstname, String image, String lastname, String phone, String email, String username, String password, String vehiclerenum, String vehicle_type) {
        Firstname = firstname;
        this.image = image;
        Lastname = lastname;
        Phone = phone;
        Email = email;
        Username = username;
        Password = password;
        Vehiclerenum = vehiclerenum;
        Vehicle_type = vehicle_type;

    }

    public DriverPojo() {
    }


}
