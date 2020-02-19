package com.forbitbd.automation.ui.main.sharedDevices;


import com.forbitbd.automation.models.Command;
import com.forbitbd.automation.models.Device;
import com.forbitbd.automation.models.SharedDevice;
import com.forbitbd.automation.models.Switch;

import java.util.List;

public interface SharedDevicesContract {

    interface Presenter{
        void getSharedDevices(String uid);
        void readFromFirebase(String deviceID);
        void updateSwitches(Device device);
        void switchClick(Switch aSwitch);
    }

    interface View{
        void renderDevices(List<SharedDevice> sharedDeviceList);
        void addDeviceToAdapter(Device device);
        void updateSwitches(Device device);
        void updateSwitch(Switch aSwitch);
        void updateDeviceInAdapter(Device device);
    }
}
