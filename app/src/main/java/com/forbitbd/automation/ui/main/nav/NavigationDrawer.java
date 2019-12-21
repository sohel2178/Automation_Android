package com.forbitbd.automation.ui.main.nav;


import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.automation.R;
import com.forbitbd.automation.models.User;
import com.forbitbd.automation.ui.main.MainActivity;
import com.forbitbd.automation.ui.main.home.HomeFragment;
import com.forbitbd.automation.ui.main.profile.ProfileFragment;
import com.forbitbd.automation.ui.main.sharedDevices.SharedDevicesFragment;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawer extends Fragment implements View.OnClickListener,NavContract.View {


    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;



    // View Initialize Here
    private CircleImageView ivProfile;
    private TextView tvName,tvEmail;

    private RecyclerView mRecyclerView;
    private NavAdapter adapter;


    //private LinearLayout rvHome,rvUpdateInfo, rvAboutUs, rvEngineeringSupport, rvLogOut;

    private NavPresenter mPresenter;
    private User user;

    private MainActivity mainActivity;


    public NavigationDrawer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getActivity() instanceof MainActivity){
            mainActivity = (MainActivity) getActivity();
        }



        mPresenter = new NavPresenter(this);
        adapter = new NavAdapter(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_navigation_drawer, container, false);


        //Initialize View
        initView(view);

        return view;
    }

    private void initView(View view) {

        ivProfile = view.findViewById(R.id.iv_profile);
        tvName = view.findViewById(R.id.name);
        tvEmail = view.findViewById(R.id.email);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);



    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }



    public void setUp(DrawerLayout layout, final Toolbar toolbar, User user) {
        this.user = user;
        renderUI();

        mDrawerLayout = layout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),mDrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                super.onDrawerSlide(drawerView, slideOffset);
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            /*case R.id.home:
                mPresenter.loadHomeFragment();
                break;
            case R.id.update_info:
                mPresenter.loadProfileFragment();

                break;
            case R.id.about_us:
                mPresenter.loadAboutFragment();
                break;
            case R.id.engineering_support:
                mPresenter.loadEngineerFragment();
                break;
            case R.id.logout:
                mPresenter.logout();
                break;*/

        }
    }

    @Override
    public void hideDialog() {
        mainActivity.hideProgressDialog();
    }

    @Override
    public void showDialog() {
        mainActivity.showProgressDialog();
    }

    @Override
    public void renderUI() {
        if(user.getImage()!=null && !user.getImage().equals("")){
            Picasso.with(getContext())
                    .load(user.getImage())
                    .into(ivProfile);
        }

        if(user.getName()!=null){
            tvName.setText(user.getName());
        }

        if(user.getEmail()!=null){
            tvEmail.setText(user.getEmail());
        }

    }

    @Override
    public void loadHomeFragment() {
        mDrawerLayout.closeDrawer(Gravity.LEFT);

        if(!(getFragmentManager().findFragmentById(R.id.main_container) instanceof HomeFragment)){
            getFragmentManager().beginTransaction().replace(R.id.main_container,new HomeFragment()).commit();
        }
    }

    @Override
    public void loadProfileFragment() {
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        if(!(getFragmentManager().findFragmentById(R.id.main_container) instanceof ProfileFragment)){
            getFragmentManager().beginTransaction().replace(R.id.main_container,new ProfileFragment()).commit();
        }
    }

    @Override
    public void loadSharedDevicesFragment() {
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        if(!(getFragmentManager().findFragmentById(R.id.main_container) instanceof SharedDevicesFragment)){
            getFragmentManager().beginTransaction().replace(R.id.main_container,new SharedDevicesFragment()).commit();
        }
    }

    @Override
    public void loadAboutFragment() {
        /*mDrawerLayout.closeDrawer(Gravity.START);
        if(!(getFragmentManager().findFragmentById(R.id.main_container) instanceof AboutUsFragment)){
            getFragmentManager().beginTransaction().replace(R.id.main_container,new AboutUsFragment())
                    .commit();
        }*/
    }



    @Override
    public void logout() {
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        if(getActivity() instanceof MainActivity){
            MainActivity activity = (MainActivity) getActivity();
            activity.logout();
            activity.startLoginActivity();
        }
    }


    public void updateNav(String userName, String email, String photoUri){
        if (userName != null) {
            tvName.setText(userName);
        }

        if (email != null) {
            tvEmail.setText(email);
        }

        if(photoUri !=null){
            if (!photoUri.equals("")) {
                Picasso.with(getContext())
                        .load(photoUri)
                        .into(ivProfile);
            }
        }
    }
}
