package com.us.onlineop.Models;

public class Opusers {

    String name;
    String docid;
    String gender;
    String symptoms;
    String age;
    String status;
    String userkey;
    String opuseridkey;
    String opid;


    public String getOpuseridkey() {
        return opuseridkey;
    }

    public void setOpuseridkey(String opuseridkey) {
        this.opuseridkey = opuseridkey;
    }

    public Opusers() {
    }

    public Opusers(String name, String docid) {
        this.name = name;
        this.docid = docid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getStatus() {
        return status;
    }

    public String getUserkey() {
        return userkey;
    }

    public void setUserkey(String userkey) {
        this.userkey = userkey;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpid() {
        return opid;
    }

    public void setOpid(String opid) {
        this.opid = opid;
    }
}
