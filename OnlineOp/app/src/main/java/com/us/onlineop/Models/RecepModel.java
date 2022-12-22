package com.us.onlineop.Models;

public class RecepModel {
    String hospid;
    String recepid;
    String recepname;
    String recepkey;

    public RecepModel() {
    }

    public RecepModel(String hospid, String recepid, String recepname) {
        this.hospid = hospid;
        this.recepid = recepid;
        this.recepname = recepname;
    }

    public String getHospid() {
        return hospid;
    }

    public void setHospid(String hospid) {
        this.hospid = hospid;
    }

    public String getRecepid() {
        return recepid;
    }

    public void setRecepid(String recepid) {
        this.recepid = recepid;
    }

    public String getRecepname() {
        return recepname;
    }

    public void setRecepname(String recepname) {
        this.recepname = recepname;
    }

    public String getRecepkey() {
        return recepkey;
    }

    public void setRecepkey(String recepkey) {
        this.recepkey = recepkey;
    }
}
