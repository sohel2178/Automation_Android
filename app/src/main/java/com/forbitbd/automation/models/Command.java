package com.forbitbd.automation.models;

public class Command {

    private String device_id;
    private String switch_id;
    private String command_type;
    private String command;


    public Command() {
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getSwitch_id() {
        return switch_id;
    }

    public void setSwitch_id(String switch_id) {
        this.switch_id = switch_id;
    }

    public String getCommand_type() {
        return command_type;
    }

    public void setCommand_type(String command_type) {
        this.command_type = command_type;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
