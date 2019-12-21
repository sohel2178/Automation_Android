package com.forbitbd.automation.dialog;

import com.forbitbd.automation.models.Device;
import com.forbitbd.automation.models.Switch;

public interface EditListener {

    void updateDevice(Device device);
    void updateSwitch(Switch aSwitch);
}
