package com.billingSystem.demo.model.payload;

public class RegistrationRequest {

    private String user_fname;
    private String User_lname;
    private String user_email;
    private String user_password;


    public String getUser_fname() {
        return user_fname;
    }

    public void setUser_fname(String user_fname) {
        this.user_fname = user_fname;
    }

    public String getUser_lname() {
        return User_lname;
    }

    public void setUser_lname(String user_lname) {
        User_lname = user_lname;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    @Override
    public String toString() {
        return "RegistrationRequest{" +
                "user_fname='" + user_fname + '\'' +
                ", User_lname='" + User_lname + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_password='" + user_password + '\'' +
                '}';
    }
}
