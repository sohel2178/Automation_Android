package com.forbitbd.automation.ui.main.home;

import android.util.Log;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.forbitbd.automation.api.ApiClient;
import com.forbitbd.automation.api.ServiceGenerator;
import com.forbitbd.automation.firebase.MyDatabaseRef;
import com.forbitbd.automation.models.Command;
import com.forbitbd.automation.models.Device;
import com.forbitbd.automation.models.Switch;
import com.forbitbd.automation.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;

    private Query query;

    public HomePresenter(HomeContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void openAddProject() {
        mView.showAddProject();
    }

    @Override
    public void updateToken(final User user) {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        Log.d("JJJJJJJJ",token);
                        user.setToken(token);
                        updateTokenToServer(user.get_id(),token);
                    }
                });

    }

    @Override
    public void requestForUserProjects(String uid) {
        if(this.query==null){
            query = MyDatabaseRef.getInstance().getDeviceRef().orderByChild("uid").equalTo(uid);
        }
        query.addChildEventListener(listener);
    }

    @Override
    public void sendCommand(Command command) {
        int value = 0;

        if(command.getCommand().equals("ON")){
           value = 1;
        }

        MyDatabaseRef.getInstance().getDeviceRef()
                .child(command.getDevice_id())
                .child("switches")
                .child(command.getSwitch_id())
                .child("state")
                .setValue(value);

    }

    @Override
    public void onDestroy() {
        if(query!=null){
            query.removeEventListener(listener);
        }
    }

    @Override
    public void updateDevice(Device device) {
        ApiClient client = ServiceGenerator.createService(ApiClient.class);
        client.updateDevice(device.getDevice_id(),device)
                .enqueue(new Callback<Device>() {
                    @Override
                    public void onResponse(Call<Device> call, Response<Device> response) {

                    }

                    @Override
                    public void onFailure(Call<Device> call, Throwable t) {

                    }
                });
//        MyDatabaseRef.getInstance().getDeviceRef()
//                .child(device.getDevice_id())
//                .child("name")
//                .setValue(device.getName());
    }

    @Override
    public void updateSwitch(Switch aSwitch) {
        int swId = Integer.parseInt(aSwitch.getId())-1;
        MyDatabaseRef.getInstance().getDeviceRef()
                .child(aSwitch.getDevice_id())
                .child("switches")
                .child(String.valueOf(swId))
                .child("name")
                .setValue(aSwitch.getName());
    }

    @Override
    public void switchClick(Switch aSwitch) {
        MyDatabaseRef.getInstance().getDeviceRef()
                .child(aSwitch.getDevice_id())
                .child("switches")
                .child(String.valueOf(Integer.parseInt(aSwitch.getId())-1))
                .child("state")
                .setValue(aSwitch.getState());
    }


    private void updateTokenToServer(String uid,String tokenStr){
        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);

        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), tokenStr);
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("token", token);

        apiClient.updateProfile(uid,null,map)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){

                        }else {

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.d("HHHH","Token Updated "+t.getMessage());
                    }
                });




    }


    private ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Device device = dataSnapshot.getValue(Device.class);

            Log.d("HHHHHHHH",device.getSwitches().size()+" ");

            mView.addDeviceToAdapter(device);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Device device = dataSnapshot.getValue(Device.class);
            mView.updateDeviceToAdapter(device);
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };



}
