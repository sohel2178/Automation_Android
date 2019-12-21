package com.forbitbd.automation.models;

public class ShareDeviceRequest {

    private String device_id;
    private String uid;

    public ShareDeviceRequest(String device_id, String uid) {
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
}
