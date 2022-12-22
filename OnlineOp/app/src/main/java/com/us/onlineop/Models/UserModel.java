package com.us.onlineop.Models;

public class UserModel  {

    String emailid;
    String name;
    String password;
    String mobile;

    public UserModel() {
    }

    public UserModel(String emailid, String name, String password, String mobile) {
        this.emailid = emailid;
        this.name = name;
        this.password = password;
        this.mobile = mobile;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
