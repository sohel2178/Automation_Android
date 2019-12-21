package com.forbitbd.automation.ui.sharedUser;


import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.forbitbd.androidutils.utils.PrebaseActivity;
import com.forbitbd.automation.R;
import com.forbitbd.automation.models.Device;
import com.forbitbd.automation.models.SharedUser;

import java.util.List;

public class SharedUserActivity extends PrebaseActivity implements SharedUserContract.View, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    private SharedUserPresenter mPresenter;
    private Device device;

    SharedUserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_user);
        mPresenter = new SharedUserPresenter(this);
        this.device = (Device) getIntent().getSerializableExtra("DEVICE");
        adapter = new SharedUserAdapter(this);

        initView();
    }

    private void initView() {
        setupToolbar(R.id.toolbar);
        getSupportActionBar().setTitle("Shared User");

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        mPresenter.getSharedUsers(device);
    }

    @Override
    public void renderAdapter(List<SharedUser> sharedUserList) {
        adapter.addSharedUserList(sharedUserList);
    }

    @Override
    public void removeItemFromAdapter(SharedUser sharedUser) {
        adapter.removeSharedUser(sharedUser);
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        SharedUser sharedUser = adapter.getSharedUser(position);
        Log.d("TTTT",sharedUser.get_id());
        mPresenter.deleteSharedUser(sharedUser);
    }
}
