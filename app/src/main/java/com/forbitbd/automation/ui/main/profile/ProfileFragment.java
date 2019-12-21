package com.forbitbd.automation.ui.main.profile;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.forbitbd.automation.R;
import com.forbitbd.automation.models.User;
import com.forbitbd.automation.ui.main.MainActivity;
import com.google.android.material.textfield.TextInputLayout;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProfileContract.View, View.OnClickListener {

    private EditText etName, etContact,etAddress,etOrganizationName;
    private TextInputLayout tiName, tiContact,tiAddress,tiOrganizationName;
    private CircleImageView ivProfile;
    private TextView tvName,tvEmail,tvBrowse;
    private Button btnUpload;

    private ProfilePresenter mPresenter;
    private User user;
    private byte[] bytes;

    private MainActivity mainActivity;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity() instanceof MainActivity){
            mainActivity = (MainActivity) getActivity();
            this.user = mainActivity.getUser();
        }

        mPresenter = new ProfilePresenter(this);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvName = view.findViewById(R.id.name);
        tvEmail = view.findViewById(R.id.email);
        tvBrowse = view.findViewById(R.id.browse);
        ivProfile = view.findViewById(R.id.iv_profile);

        etName = view.findViewById(R.id.et_name);
        etContact = view.findViewById(R.id.et_contact);
        etOrganizationName = view.findViewById(R.id.et_organization_name);
        etAddress = view.findViewById(R.id.et_address);

        tiName = view.findViewById(R.id.ti_name);
        tiContact = view.findViewById(R.id.ti_contact);
        tiOrganizationName = view.findViewById(R.id.ti_organization_name);
        tiAddress = view.findViewById(R.id.ti_address);

        btnUpload = view.findViewById(R.id.btn_upload);

        btnUpload.setOnClickListener(this);
        tvBrowse.setOnClickListener(this);

        mPresenter.bind();
    }

    @Override
    public void clearPreErrors() {
        tiName.setErrorEnabled(false);
        tiContact.setErrorEnabled(false);
        tiOrganizationName.setErrorEnabled(false);
        tiAddress.setErrorEnabled(false);

    }

    @Override
    public void showDialog() {
        mainActivity.showProgressDialog();
    }

    @Override
    public void hideDialog() {
        mainActivity.hideProgressDialog();
    }

    @Override
    public void showErrorMessage(int fieldId, String message) {
        switch (fieldId){
            case 1:
                etName.requestFocus();
                tiName.setError(message);
                break;

            case 2:
                etContact.requestFocus();
                tiContact.setError(message);
                break;

            case 3:
                etOrganizationName.requestFocus();
                tiOrganizationName.setError(message);
                break;

            case 4:
                etAddress.requestFocus();
                tiAddress.setError(message);
                break;
        }

    }


    @Override
    public void openCropImageActivity() {
        mainActivity.openCropImageActivity(true);
    }

    @Override
    public void updateUser(User user) {
        this.user= user;
        bind();
        mainActivity.updateNav(user);
    }

    // Set from Main Activity when find image OnActivity Result
    @Override
    public void setImageBytes(Bitmap bitmap, byte[] bytes) {
        this.bytes = bytes;
        ivProfile.setVisibility(View.VISIBLE);
        tvBrowse.setVisibility(View.GONE);
        ivProfile.setImageBitmap(bitmap);
    }

    @Override
    public void bind() {

        mainActivity.setTitle(user.getName()+" Profile");

        if(user.getName()!=null){
            tvName.setText(user.getName());
            etName.setText(user.getName());
        }

        if(user.getEmail()!=null){
            tvEmail.setText(user.getEmail());
        }

        if(user.getContact()!=null){
            etContact.setText(user.getContact());
        }

        if(user.getOrganization_name()!=null){
            etOrganizationName.setText(user.getOrganization_name());
        }


        if(user.getAddress()!=null){
            etAddress.setText(user.getAddress());
        }


        btnUpload.setText(getResources().getString(R.string.update));

        if(user.getImage()==null || user.getImage().equals("")){
            ivProfile.setVisibility(View.GONE);
            tvBrowse.setVisibility(View.VISIBLE);
        }else{
            Picasso.with(getContext())
                    .load(user.getImage())
                    .into(ivProfile);
        }

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(mainActivity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.browse:
                mPresenter.browseClick();
                //getCameraPermission();
                break;

            case R.id.btn_upload:
                final String name = etName.getText().toString();
                final String contact = etContact.getText().toString();
                final String organizationName = etOrganizationName.getText().toString();
                final String address = etAddress.getText().toString();

                if(user!=null){
                    user.setName(name);
                    user.setContact(contact);
                    user.setOrganization_name(organizationName);
                    user.setAddress(address);
                }

                boolean valid = mPresenter.validate(user);

                if(!valid){
                    return;
                }

                if(mainActivity.isOnline()){
                    mPresenter.updateUser(user,bytes);
                }else{
                    Toast.makeText(mainActivity, "Please turn on your internet connection", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
}
