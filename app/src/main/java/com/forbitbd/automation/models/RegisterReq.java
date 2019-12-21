package com.forbitbd.automation.models;

public class RegisterReq {

    private String device_id;
    private String uid;
    private int number_of_switch;


    public RegisterReq() {
    }

    public RegisterReq(String device_id, String uid) {
        this.device_id = device_id;
        this.uid = uid;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getNumber_of_switch() {
        return number_of_switch;
    }

    public void setNumber_of_switch(int number_of_switch) {
        this.number_of_switch = number_of_switch;
    }
}
