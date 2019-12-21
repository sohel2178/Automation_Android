package com.forbitbd.automation.ui.searchUser;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.forbitbd.androidutils.utils.PrebaseActivity;
import com.forbitbd.automation.R;
import com.forbitbd.automation.models.Device;
import com.forbitbd.automation.models.User;
import com.forbitbd.automation.ui.login.dialog.DialogClickListener;
import com.forbitbd.automation.ui.login.dialog.InfoDialog;

import java.util.List;

public class SearchUserActivity extends PrebaseActivity implements SearchUserContract.View , SearchView.OnQueryTextListener{

    private SearchUserPresenter mPresenter;

    private Device device;

    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        this.device = (Device) getIntent().getSerializableExtra("DEVICE");
        this.mPresenter = new SearchUserPresenter(this);
        this.adapter = new UserAdapter(this);

        initView();
    }

    private void initView() {
        setupToolbar(R.id.toolbar);
        getSupportActionBar().setTitle(getString(R.string.search_user));

        RecyclerView rvUsers = findViewById(R.id.rv_user);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        rvUsers.setAdapter(adapter);
        SearchView mSearchView  = findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if(newText.length()==1){
            mPresenter.loadData(newText);
        }else{
            mPresenter.filter(newText);
        }
        return false;
    }

    @Override
    public void filter(String query) {
        adapter.getFilter().filter(query);
    }

    @Override
    public void addUsersToAdapter(List<User> userList) {
        adapter.clear();
        adapter.addUsers(userList);
    }

    @Override
    public void shareDevice(User user) {
        mPresenter.shareDevice(user,device);
    }

    @Override
    public void showSuccessDialog() {
        final InfoDialog infoDialog =InfoDialog.getInstance();
        Bundle bundle = new Bundle();
        bundle.putString("CONTENT","Shared Vehicle Successfully");
        infoDialog.setArguments(bundle);
        infoDialog.setListener(new DialogClickListener() {
            @Override
            public void positiveButtonClick() {
                infoDialog.dismiss();
            }
        });
        infoDialog.show(getSupportFragmentManager(),"HHHH");
    }

    @Override
    public void showErrorDialog() {
        final InfoDialog infoDialog =InfoDialog.getInstance();
        Bundle bundle = new Bundle();
        bundle.putString("CONTENT","Already Shared this Vehicle to this User");
        bundle.putBoolean("ERROR",true);
        infoDialog.setArguments(bundle);
        infoDialog.setListener(new DialogClickListener() {
            @Override
            public void positiveButtonClick() {
                infoDialog.dismiss();
            }
        });
        infoDialog.show(getSupportFragmentManager(),"HHHH");
    }
}
