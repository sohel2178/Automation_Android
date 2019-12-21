package com.forbitbd.automation.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.automation.R;
import com.forbitbd.automation.ui.login.dialog.DialogClickListener;
import com.forbitbd.automation.ui.login.dialog.InfoDialog;
import com.forbitbd.automation.ui.main.MainActivity;
import com.forbitbd.automation.ui.signup.SignUpActivity;
import com.forbitbd.automation.ui.utils.BaseActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputLayout;


public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginContract.View {

    private static final int RC_SIGN_IN = 9001;

    private SignInButton signInButton;
    private LoginPresenter mPresenter;


    private TextView tvSignUp,tvLogin,tvForgotPassword;

    private TextInputLayout tiEmail,tiPassword;
    private EditText etEmail,etPassword;

    private InfoDialog infoDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide Status Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        mPresenter = new LoginPresenter(this);

        infoDialog = InfoDialog.getInstance();

        initView();


    }

    private void initView() {
        signInButton = findViewById(R.id.google_sign_in);
        tvSignUp = findViewById(R.id.sign_up);
        tvLogin = findViewById(R.id.login);
        tvForgotPassword = findViewById(R.id.forgot_password);


        tvSignUp.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        signInButton.setOnClickListener(this);

        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        tiEmail = findViewById(R.id.ti_email);
        tiPassword = findViewById(R.id.ti_password);
    }

    private void startResetRequestActivity(){
        //finish();
        /*Intent intent = new Intent(getApplicationContext(), ResetRequest.class);
        startActivity(intent);*/
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case RC_SIGN_IN:
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                mPresenter.startAutentication(result);
                break;
        }


    }




    @Override
    public void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(getGoogleApiClient());
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void showDialog() {
        showProgressDialog();
    }

    @Override
    public void handledatabaseError() {
        hideProgressDialog();
        Toast.makeText(this, "Error Happen in Database", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideDialogandFinish() {
        hideProgressDialog();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }


    @Override
    public void onBackPressed() {

    }

    @Override
    public void showAutheticationFailureToast() {
        Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startSignUpActivity() {
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
    }

    @Override
    public void startMainActivity() {
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void clearPreError() {
        tiEmail.setErrorEnabled(false);
        tiPassword.setErrorEnabled(false);
    }

    @Override
    public void showErrorMessage(String message, int fieldId) {
        switch (fieldId){
            case 1:
                etEmail.requestFocus();
                tiEmail.setError(message);
                break;

            case 2:
                etPassword.requestFocus();
                tiPassword.setError(message);
                break;
        }

    }

    @Override
    public void showToast(String message, int fieldId) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideDialog() {
        hideProgressDialog();
    }

    @Override
    public void showVarificationDialog() {

        Bundle bundle = new Bundle();
        bundle.putString(Constant.CONTENT,"We send a verification mail to your Email. Please verify and then Login");

        infoDialog.setListener(new DialogClickListener() {
            @Override
            public void positiveButtonClick() {
                LoginActivity.this.finish();
                //System.exit(0);
            }
        });
        infoDialog.setArguments(bundle);
        infoDialog.show(getSupportFragmentManager(),"VERIFICATION");

    }

    @Override
    public void complete() {
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }



    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.sign_up:
                mPresenter.signupClick();
                break;

            case R.id.login:
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                boolean valid = mPresenter.validate(email,password);

                if(!valid){
                    return;
                }

                mPresenter.login(email,password);
                break;

            case R.id.google_sign_in:
                mPresenter.google_click();
                break;

            case R.id.forgot_password:
                startResetRequestActivity();
                break;
        }

    }


}
