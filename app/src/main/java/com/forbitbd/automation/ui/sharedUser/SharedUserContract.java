package com.forbitbd.automation.ui.sharedUser;



import com.forbitbd.automation.models.Device;
import com.forbitbd.automation.models.SharedUser;

import java.util.List;

public interface SharedUserContract {

    interface Presenter{
        void getSharedUsers(Device device);
        void deleteSharedUser(SharedUser sharedUser);
    }

    interface View{
        void showProgressDialog();
        void hideProgressDialog();
        void renderAdapter(List<SharedUser> sharedUserList);
        void removeItemFromAdapter(SharedUser sharedUser);
    }
}
