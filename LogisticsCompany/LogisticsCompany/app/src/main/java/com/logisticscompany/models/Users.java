package com.logisticscompany.models;

public class Users {
    private String Firstname;
    private String Lastname;
    private String Email;
    private String Phoneno;
    private String Username;
    private String Password;

    public Users(String firstname, String lastname, String email, String phoneno, String username, String password) {
        Firstname = firstname;
        Lastname = lastname;
        Email = email;
        Phoneno = phoneno;
        Username = username;
        Password = password;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneno() {
        return Phoneno;
    }

    public void setPhoneno(String phoneno) {
        Phoneno = phoneno;
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

    public Users() {
    }


}
