package com.forbitbd.automation.models;

import java.io.Serializable;
import java.util.List;

public class Device implements Serializable {

    private String device_id;
    private String name;
    private List<Switch> switches;
    private boolean power_save;

    public Device() {
    }


    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Switch> getSwitches() {
        return switches;
    }

    public void setSwitches(List<Switch> switches) {
        this.switches = switches;
    }

    public boolean isPower_save() {
        return power_save;
    }

    public void setPower_save(boolean power_save) {
        this.power_save = power_save;
    }
}
