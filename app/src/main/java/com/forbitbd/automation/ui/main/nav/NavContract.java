package com.forbitbd.automation.ui.main.nav;

public interface NavContract {

    interface Presenter{
    }


    interface View{
        void hideDialog();
        void showDialog();
        void renderUI();
        void loadHomeFragment();
        void loadProfileFragment();
        void loadSharedDevicesFragment();
        void loadAboutFragment();
        void logout();
    }


}
