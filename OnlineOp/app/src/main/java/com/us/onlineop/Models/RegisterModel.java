package com.us.onlineop.Models;

public class RegisterModel {

    String emailid;
    String password;
    String name;
    String id;
    String type;
    String availability;
    String tommorowavailability;
    String hospitalid;

    public RegisterModel() {
    }

    public RegisterModel(String emailid, String password, String name, String id, String type,String hospitalid) {
        this.emailid = emailid;
        this.password = password;
        this.name = name;
        this.id = id;
        this.type = type;
        this.hospitalid=hospitalid;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getTommorowavailability() {
        return tommorowavailability;
    }

    public void setTommorowavailability(String tommorowavailability) {
        this.tommorowavailability = tommorowavailability;
    }

    public String getHospitalid() {
        return hospitalid;
    }

    public void setHospitalid(String hospitalid) {
        this.hospitalid = hospitalid;
    }
}
