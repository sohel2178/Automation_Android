package com.forbitbd.automation.ui.main.sharedDevices;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.forbitbd.automation.api.ApiClient;
import com.forbitbd.automation.api.ServiceGenerator;
import com.forbitbd.automation.firebase.MyDatabaseRef;
import com.forbitbd.automation.models.Device;
import com.forbitbd.automation.models.SharedDevice;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SharedDevicesPresenter implements SharedDevicesContract.Presenter {

    private SharedDevicesContract.View mView;

    public SharedDevicesPresenter(SharedDevicesContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getSharedDevices(String uid) {
        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        client.getSharedDevices(uid)
                .enqueue(new Callback<List<SharedDevice>>() {
                    @Override
                    public void onResponse(Call<List<SharedDevice>> call, Response<List<SharedDevice>> response) {
                        if(response.isSuccessful()){
                            mView.renderDevices(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SharedDevice>> call, Throwable t) {

                    }
                });
    }

    @Override
    public void readFromFirebase(String deviceID) {
        MyDatabaseRef.getInstance().getDeviceRef()
                .child(deviceID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Device device = dataSnapshot.getValue(Device.class);
                        mView.addDeviceToAdapter(device);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void updateSwitches(final Device device) {
        MyDatabaseRef.getInstance()
                .getDeviceRef()
                .child(device.getDevice_id())
                .child("switches")
                .setValue(device.getSwitches(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        mView.updateDeviceInAdapter(device);
                    }
                });
    }
}
