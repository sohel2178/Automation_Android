package com.forbitbd.automation.ui.main.sharedDevices;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.forbitbd.automation.R;
import com.forbitbd.automation.models.Command;
import com.forbitbd.automation.models.Device;
import com.forbitbd.automation.models.SharedDevice;
import com.forbitbd.automation.ui.main.MainActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SharedDevicesFragment extends Fragment implements SharedDevicesContract.View {

    private SharedDevicesPresenter mPresenter;
    private SharedDevicesAdapter adapter;

    private MainActivity activity;


    public SharedDevicesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity() instanceof MainActivity){
            this.activity = (MainActivity) getActivity();
        }
        mPresenter = new SharedDevicesPresenter(this);
        this.adapter = new SharedDevicesAdapter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shared_devices, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        mPresenter.getSharedDevices(activity.getUser().get_id());
    }

    @Override
    public void renderDevices(List<SharedDevice> sharedDeviceList) {
//        adapter.addAll(sharedDeviceList);

        for (SharedDevice x: sharedDeviceList){
           mPresenter.readFromFirebase(x.getDevice().getDevice_id());
        }
    }



    @Override
    public void addDeviceToAdapter(Device device) {
        adapter.addDevice(device);
    }

    @Override
    public void updateSwitches(Device device) {
        mPresenter.updateSwitches(device);
    }

    @Override
    public void updateDeviceInAdapter(Device device) {
        adapter.updateDevice(device);
    }
}
