package com.forbitbd.automation.ui.sharedUser;



import com.forbitbd.automation.api.ApiClient;
import com.forbitbd.automation.api.ServiceGenerator;
import com.forbitbd.automation.models.Device;
import com.forbitbd.automation.models.SharedUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SharedUserPresenter implements SharedUserContract.Presenter {

    private SharedUserContract.View mView;

    public SharedUserPresenter(SharedUserContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getSharedUsers(Device device) {
        mView.showProgressDialog();
        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.getSharedUser(device.getDevice_id())
                .enqueue(new Callback<List<SharedUser>>() {
                    @Override
                    public void onResponse(Call<List<SharedUser>> call, Response<List<SharedUser>> response) {
                        mView.hideProgressDialog();

                        if(response.isSuccessful()){
                            mView.renderAdapter(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SharedUser>> call, Throwable t) {
                        mView.hideProgressDialog();
                    }
                });
    }

    @Override
    public void deleteSharedUser(final SharedUser sharedUser) {
        ApiClient client = ServiceGenerator.createService(ApiClient.class);

        client.deleteSharedUser(sharedUser.get_id())
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            mView.removeItemFromAdapter(sharedUser);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
    }
}
