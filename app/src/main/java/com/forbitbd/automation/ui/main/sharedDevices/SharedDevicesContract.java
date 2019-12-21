package com.forbitbd.automation.ui.main.sharedDevices;


import com.forbitbd.automation.models.Command;
import com.forbitbd.automation.models.Device;
import com.forbitbd.automation.models.SharedDevice;

import java.util.List;

public interface SharedDevicesContract {

    interface Presenter{
        void getSharedDevices(String uid);
        void readFromFirebase(String deviceID);
        void updateSwitches(Device device);
    }

    interface View{
        void renderDevices(List<SharedDevice> sharedDeviceList);
        void addDeviceToAdapter(Device device);
        void updateSwitches(Device device);
        void updateDeviceInAdapter(Device device);
    }
}
