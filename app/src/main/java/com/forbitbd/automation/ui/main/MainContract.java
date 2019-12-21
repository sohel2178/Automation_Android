package com.forbitbd.automation.ui.main;


import com.forbitbd.automation.models.RegisterReq;
import com.forbitbd.automation.models.User;

public interface MainContract {

    interface Presenter{
        void checkUser();
        void registerDevice(RegisterReq registerReq);
    }

    interface View{
        void startLoginActivity();
        void initialize(User user);
        void setTitle(String title);
        void updateNav(User user);
        void logout();
        void showProgressDialog();
        void hideProgressDialog();
        void startScannerActivity();
    }
}
