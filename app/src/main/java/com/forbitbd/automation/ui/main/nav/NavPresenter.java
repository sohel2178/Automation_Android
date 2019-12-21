package com.forbitbd.automation.ui.main.nav;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NavPresenter implements NavContract.Presenter {

    private NavContract.View mView;
    private FirebaseUser mCurrentUser;

    public NavPresenter(NavContract.View mView) {
        this.mView = mView;
        this.mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
    }


}
