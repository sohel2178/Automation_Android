package com.forbitbd.automation.ui.searchUser;


import com.forbitbd.automation.models.Device;
import com.forbitbd.automation.models.User;

import java.util.List;

public interface SearchUserContract {

    interface Presenter{
        void loadData(String query);
        void filter(String query);
        void shareDevice(User user, Device device);
    }

    interface View{
        void showProgressDialog();
        void hideProgressDialog();
        void filter(String query);
        void addUsersToAdapter(List<User> userList);
        void shareDevice(User user);
        void showSuccessDialog();
        void showErrorDialog();
    }
}
