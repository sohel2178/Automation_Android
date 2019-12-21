package com.forbitbd.automation.ui.main.home;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.automation.R;
import com.forbitbd.automation.dialog.EditDialog;
import com.forbitbd.automation.dialog.EditListener;
import com.forbitbd.automation.models.Command;
import com.forbitbd.automation.models.Device;
import com.forbitbd.automation.models.Switch;
import com.forbitbd.automation.ui.main.MainActivity;
import com.forbitbd.automation.ui.searchUser.SearchUserActivity;
import com.forbitbd.automation.ui.sharedUser.SharedUserActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import info.androidhive.fontawesome.FontDrawable;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements  HomeContract.View, EditListener {

    private MainActivity mainActivity;
    private HomePresenter mPresenter;

    private RecyclerView mRecyclerView;

    private DeviceAdapter adapter;

    private FloatingActionButton btnScanner;



    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getActivity() instanceof MainActivity){
            mainActivity = (MainActivity) getActivity();

            Log.d("YYYYY",mainActivity.getUser().getEmail());
        }

        this.adapter = new DeviceAdapter(this);


        mPresenter = new HomePresenter(this);

        mPresenter.updateToken(mainActivity.getUser());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);

        mPresenter.requestForUserProjects(mainActivity.getUser().get_id());

        btnScanner = view.findViewById(R.id.scanner);
        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.startScannerActivity();
            }
        });
    }

    @Override
    public void onResume() {
        mainActivity.setTitle(getString(R.string.app_name));
        super.onResume();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }


    @Override
    public void showAddProject() {
//        ProjectAdd projectAdd = new ProjectAdd();
//        projectAdd.setCancelable(false);
//        projectAdd.show(getFragmentManager(),"SHOW");
    }

    @Override
    public void addDeviceToAdapter(Device device) {
        adapter.addDevice(device);
    }

    @Override
    public void updateDeviceToAdapter(Device device) {
        adapter.updateDevice(device);
    }

    @Override
    public void sendCommand(Command command) {
        mPresenter.sendCommand(command);
    }

    @Override
    public void editDeviceName(Device device) {
        EditDialog editDialog = new EditDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("DEVICE",device);
        editDialog.setListener(this);
        editDialog.setArguments(bundle);
        editDialog.show(getChildFragmentManager(),"SHOW");
    }

    @Override
    public void editSwitch(Switch aSwitch) {
        EditDialog editDialog = new EditDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("SWITCH",aSwitch);
        editDialog.setListener(this);
        editDialog.setArguments(bundle);
        editDialog.show(getChildFragmentManager(),"SHOW");
    }

    @Override
    public void startSearchUserActivity(Device device) {
        Intent intent = new Intent(getContext(), SearchUserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("DEVICE",device);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void startSharedUserActivity(Device device) {

        Intent intent = new Intent(getContext(), SharedUserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("DEVICE",device);
        intent.putExtras(bundle);
        startActivity(intent);

    }


    @Override
    public void updateDevice(Device device) {
        mPresenter.updateDevice(device);
    }

    @Override
    public void updateSwitch(Switch aSwitch) {
        mPresenter.updateSwitch(aSwitch);
    }
}
