package com.forbitbd.automation.ui.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.automation.R;
import com.forbitbd.automation.models.RegisterReq;
import com.forbitbd.automation.models.User;
import com.forbitbd.automation.ui.login.LoginActivity;
import com.forbitbd.automation.ui.main.home.HomeFragment;
import com.forbitbd.automation.ui.main.nav.NavigationDrawer;
import com.forbitbd.automation.ui.main.profile.ProfileFragment;
import com.forbitbd.automation.ui.scanner.scanner.ScannerActivity;
import com.forbitbd.automation.ui.utils.BaseActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends BaseActivity implements MainContract.View {
    private static final int SCANNER_REQUEST =5000;

    private MainPresenter mPresenter;

    private User user;
    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this);

        mPresenter.checkUser();
    }


    @Override
    public void startLoginActivity() {
        finish();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    @Override
    public void initialize(User user) {
        this.user = user;
        Log.d("YYYYYY","User Found "+user.getName());
        setUpNavigationDrawer(user);

        initializeHome(user);
        //getDrawer().requestForUser();
    }

    public void setUpNavigationDrawer(User user){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        FragmentManager manager = getSupportFragmentManager();
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationDrawer drawerFragment =
                (NavigationDrawer) manager.findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp( mDrawerLayout, toolbar,user);
        //getSupportActionBar().setTitle(Constant.HOME);
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    @Override
    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void initializeHome(User user){
        this.user = user;
        homeFragment = new HomeFragment();
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.main_container,homeFragment).commit();
    }

    @Override
    public void updateNav(User user) {
       // getDrawer().renderUI(user);
    }




    public User getUser() {
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    @Override
    public void logout() {
        if(FirebaseAuth.getInstance().getCurrentUser().getProviderData().get(1).getProviderId().equals("google.com")){
            Auth.GoogleSignInApi.signOut(getGoogleApiClient());
        }
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }

    @Override
    public void startScannerActivity() {
        Intent intent = new Intent(getApplicationContext(), ScannerActivity.class);
        startActivityForResult(intent,SCANNER_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());
                    Bitmap scaledBitMap = MyUtil.getScaledBitmap(bitmap,200,200);

                    if(getSupportFragmentManager().findFragmentById(R.id.main_container) instanceof ProfileFragment){
                        ProfileFragment profileFragment = (ProfileFragment) getSupportFragmentManager().findFragmentById(R.id.main_container);

                        File f = new File(getCacheDir(), user.get_id());
                        f.createNewFile();

                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        scaledBitMap.compress(Bitmap.CompressFormat.JPEG, 80 /*ignored for PNG*/, bos);
                        byte[] bitmapdata = bos.toByteArray();

                        FileOutputStream fos = new FileOutputStream(f);
                        fos.write(bitmapdata);
                        fos.flush();
                        fos.close();

                        profileFragment.setImageBytes(scaledBitMap,bitmapdata);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }else if(requestCode==SCANNER_REQUEST && resultCode==RESULT_OK){
            String value = data.getStringExtra("DATA");
            String[] bbb = value.split("-");


            RegisterReq registerReq = new RegisterReq(bbb[0],getUser().get_id());
            registerReq.setNumber_of_switch(Integer.parseInt(bbb[1]));

            mPresenter.registerDevice(registerReq);

        }
    }
}
