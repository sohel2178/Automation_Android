package com.forbitbd.automation.ui.main.profile;

import android.graphics.Bitmap;

import com.forbitbd.automation.models.User;


public interface ProfileContract {

    interface Presenter {
        boolean validate(User user);
        void updateUser(User user, byte[] bytes);
        void uploadUserImage(byte[] bytes);
        void browseClick();
        void bind();
    }

    interface View{
        void clearPreErrors();
        void showDialog();
        void hideDialog();
        void showErrorMessage(int fieldId, String message);
        void openCropImageActivity();
        void updateUser(User user);
        void setImageBytes(Bitmap bitmap, byte[] bytes);
        void bind();
        void showToast(String message);
    }
}
