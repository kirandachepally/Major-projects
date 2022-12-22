package com.us.onlineop.Models;

public class HosptialModel {

    String hospitalname;
    String email;
    String password;
    String hospitaltype;
    String state;
    String city;
    String street;
    String mobile;
    String hospitalkey;

    public HosptialModel() {
    }

    public HosptialModel(String hospitalname, String email, String password, String hospitaltype, String state, String city, String street, String mobile) {
        this.hospitalname = hospitalname;
        this.email = email;
        this.password = password;
        this.hospitaltype = hospitaltype;
        this.state = state;
        this.city = city;
        this.street = street;
        this.mobile = mobile;
    }

    public String getHospitalname() {
        return hospitalname;
    }

    public void setHospitalname(String hospitalname) {
        this.hospitalname = hospitalname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHospitaltype() {
        return hospitaltype;
    }

    public void setHospitaltype(String hospitaltype) {
        this.hospitaltype = hospitaltype;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHospitalkey() {
        return hospitalkey;
    }

    public void setHospitalkey(String hospitalkey) {
        this.hospitalkey = hospitalkey;
    }
}
