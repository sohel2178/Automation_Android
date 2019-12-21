package com.forbitbd.automation.ui.main.profile;

import android.util.Log;


import com.forbitbd.automation.api.ApiClient;
import com.forbitbd.automation.api.ServiceGenerator;
import com.forbitbd.automation.models.User;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileContract.View mView;

    public ProfilePresenter(ProfileContract.View mView) {
        this.mView = mView;
    }

    @Override
    public boolean validate(User user) {
        mView.clearPreErrors();

        if(user.getName()==null || user.getName().equals("")){
            mView.showErrorMessage(1,"Please Enter Your Name");
            return false;
        }

        if(user.getContact()==null || user.getContact().equals("")){
            mView.showErrorMessage(2,"Contact Number Empty");
            return false;
        }

        if(user.getOrganization_name()==null || user.getOrganization_name().equals("")){
            mView.showErrorMessage(3,"Organization Name Empty");
            return false;
        }

        if(user.getAddress()==null || user.getAddress().equals("")){
            mView.showErrorMessage(4,"Address Should not Empty");
            return false;
        }

        return true;
    }

    @Override
    public void updateUser(User user, byte[] bytes) {

        mView.showDialog();

        MultipartBody.Part part=null;

        if(bytes!=null){
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), bytes);
            //MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
            // RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), bytes);
            // Create MultipartBody.Part using file request-body,file name and part name
            part = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
        }


        // Create a request body with file and image media type



        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), user.getName());
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), user.getContact());
        RequestBody address = RequestBody.create(MediaType.parse("text/plain"), user.getAddress());
        RequestBody organization_name = RequestBody.create(MediaType.parse("text/plain"), user.getOrganization_name());

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("name", name);
        map.put("contact", phone);
        map.put("organization_name", organization_name);
        map.put("address", address);

        ApiClient cmClient = ServiceGenerator.createService(ApiClient.class);
        cmClient.updateProfile(user.get_id(),part,map)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        mView.hideDialog();
                        if(response.code()==201){
                            User updatedUser = response.body();
                            mView.updateUser(updatedUser);
                        }else{
                            mView.showToast("Failed to Update User");
                        }
                    }

                    @Override
                    public void onFailure(Call<com.forbitbd.automation.models.User> call, Throwable t) {
                        Log.d("HHHH","JJJJJJ "+t.getMessage());
                    }
                });


    }

    @Override
    public void uploadUserImage(byte[] bytes) {

    }

    @Override
    public void browseClick() {
        mView.openCropImageActivity();
    }

    @Override
    public void bind() {
        mView.bind();
    }

}
