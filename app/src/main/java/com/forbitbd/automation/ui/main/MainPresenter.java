package com.forbitbd.automation.ui.main;

import android.util.Log;

import com.forbitbd.automation.api.ApiClient;
import com.forbitbd.automation.api.ServiceGenerator;
import com.forbitbd.automation.models.Device;
import com.forbitbd.automation.models.RegisterReq;
import com.forbitbd.automation.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private FirebaseUser mCurrentUser;

    public MainPresenter(MainContract.View mView) {
        this.mView = mView;
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void checkUser() {
        if(mCurrentUser==null){
            mView.startLoginActivity();
        }else{
            //mView.initialize();

            Log.d("YYYYY","Call");

            mView.showProgressDialog();

            ApiClient client = ServiceGenerator.createService(ApiClient.class);

            client.getUser(mCurrentUser.getEmail())
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Log.d("YYYYY","Call "+response.body().getName());
                            mView.hideProgressDialog();
                            if(response.isSuccessful()){
                                mView.initialize(response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.d("YYYYY","Error "+t.getMessage());
                            mView.hideProgressDialog();
                        }
                    });
        }
    }

    @Override
    public void registerDevice(RegisterReq registerReq) {
        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);

        apiClient.registerDevice(registerReq)
                .enqueue(new Callback<Device>() {
                    @Override
                    public void onResponse(Call<Device> call, Response<Device> response) {
                        Log.d("GGGGGGG","Call in Response");

                        if(response.isSuccessful()){
                            Log.d("GGGGGGG","REsponse Success");
                        }else if (response.code()==300){
                            Log.d("GGGGGGG","Show Show a Dialog");
                        }
                    }

                    @Override
                    public void onFailure(Call<Device> call, Throwable t) {
                        Log.d("GGGGGGG","Call in Error");
                    }
                });
    }


}
