package com.forbitbd.automation.ui.login;



public interface LoginContract {

    interface Presenter{
        void signupClick();
        void google_click();

        boolean validate(String email, String password);
        void login(String email, String password);
    }

    interface View {
        void googleSignIn();
        void showDialog();
        void handledatabaseError();
        void hideDialogandFinish();
        void showAutheticationFailureToast();

        void startSignUpActivity();
        void startMainActivity();
        void clearPreError();
        void showErrorMessage(String message, int fieldId);
        void showToast(String message, int fieldId);
        void hideDialog();
        void showVarificationDialog();
        void complete();

    }
}
