package com.us.onlineop.Models;

public class DoctorModel {

    String hospid;
    String docid;
    String docname;
    String docspec;
    String dockey;

    public DoctorModel() {
    }

    public DoctorModel(String hospid, String docid, String docname, String docspec) {
        this.hospid = hospid;
        this.docid = docid;
        this.docname = docname;
        this.docspec = docspec;
    }

    public String getHospid() {
        return hospid;
    }

    public void setHospid(String hospid) {
        this.hospid = hospid;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getDocspec() {
        return docspec;
    }

    public void setDocspec(String docspec) {
        this.docspec = docspec;
    }

    public String getDockey() {
        return dockey;
    }

    public void setDockey(String dockey) {
        this.dockey = dockey;
    }
}
