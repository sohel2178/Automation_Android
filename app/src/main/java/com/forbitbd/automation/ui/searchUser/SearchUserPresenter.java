package com.forbitbd.automation.ui.searchUser;


import com.forbitbd.automation.api.ApiClient;
import com.forbitbd.automation.api.ServiceGenerator;
import com.forbitbd.automation.models.Device;
import com.forbitbd.automation.models.ShareDeviceRequest;
import com.forbitbd.automation.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchUserPresenter implements SearchUserContract.Presenter {

    private SearchUserContract.View mView;

    public SearchUserPresenter(SearchUserContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void loadData(String query) {
        mView.showProgressDialog();
        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.getQueryUsers(query)
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        mView.hideProgressDialog();
                        if(response.isSuccessful()){
                            mView.addUsersToAdapter(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });
    }

    @Override
    public void filter(String query) {
        mView.filter(query);
    }

    @Override
    public void shareDevice(User user, Device device) {
        mView.showProgressDialog();

        ShareDeviceRequest request = new ShareDeviceRequest(device.getDevice_id(),user.get_id());

        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        client.sharedDevice(request)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            mView.showSuccessDialog();
                        }else{
                            mView.showErrorDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });
    }

}
