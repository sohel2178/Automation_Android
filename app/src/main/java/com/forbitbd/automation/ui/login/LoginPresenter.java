package com.forbitbd.automation.ui.login;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.forbitbd.androidutils.utils.MyUtil;
import com.forbitbd.automation.api.ApiClient;
import com.forbitbd.automation.api.ServiceGenerator;
import com.forbitbd.automation.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private FirebaseAuth mAuth;



    public LoginPresenter(LoginContract.View mView) {
        this.mView = mView;
        this.mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void signupClick() {
        mView.startSignUpActivity();
    }

    @Override
    public void google_click() {
        mView.googleSignIn();
    }

    @Override
    public boolean validate(String email, String password) {
        mView.clearPreError();

        if(email.equals("")){
            mView.showErrorMessage("Empty Value Not Allowed",1);
            return false;
        }

        if(!MyUtil.isValidEmail(email)){
            mView.showErrorMessage("Email is not Valid",1);
            return false;
        }

        if(password.equals("")){
            mView.showErrorMessage("Empty Value Not Allowed",2);
            return false;
        }

        return true;
    }

    @Override
    public void login(String email, String password) {

        mView.showDialog();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) mView, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mView.hideDialog();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("HHHHH", "createUserWithEmail:success");
                            final FirebaseUser user = mAuth.getCurrentUser();

                            if(user.isEmailVerified()){
                                //hideProgressDialog();
                                Log.d("HHHHHH","Email Verified");
                                registerToDatabase(user);

                            }else {
                                mAuth.signOut();
                                mView.showVarificationDialog();
                            }


                        } else {
                            mView.hideDialog();
                            mView.showToast("Authentication Failed",3);
                        }

                        // ...
                    }
                });

    }

    public void startAutentication(GoogleSignInResult result) {
        mView.showDialog();
        if (result.isSuccess()) {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = result.getSignInAccount();
            firebaseAuthWithGoogle(account);
        } else {

            mView.hideDialog();
            Log.d("UUUUU","Not Success "+ result.getStatus().getStatusCode());
            mView.showAutheticationFailureToast();
        }

    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user!=null){
                        registerToDatabase(user);
                    }

                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mView.handledatabaseError();
            }
        });
    }

    private void registerToDatabase(FirebaseUser user){
        User usr = new User();

        if(user.getEmail()!=null){
            usr.setEmail(user.getEmail());
        }

        if(user.getDisplayName()!=null){
            usr.setName(user.getDisplayName());
        }

        if(user.getPhoneNumber()!=null){
            usr.setContact(user.getPhoneNumber());
        }

        if(user.getPhotoUrl()!=null){
            usr.setImage(user.getPhotoUrl().toString());
        }

        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);

        Call<User> call = apiClient.register(usr);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code()==201){
                    mView.startMainActivity();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


//        Log.d("UUUUU",user.getDisplayName());
//        Log.d("UUUUU",user.getPhoneNumber()+"");
//        Log.d("UUUUU",user.getEmail()+"");
//        Log.d("UUUUU",user.getMetadata().toString()+"");
    }
}
