package com.forbitbd.automation.ui.signup;

public interface SignUpContract {

    interface Presenter{
        boolean validate(String email, String password);

        void signUp(String email, String password);

    }

    interface View{

        void clearPreError();
        void startLoginActivity();
        void showToast(String message, int type);
        void showErrorMessage(String message, int type);

        void showDialog();
        void hideDialog();
        void showSignupSuceesDialog();
        void complete();

    }
}
