package com.forbitbd.automation.ui.login.dialog;


import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.automation.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoDialog extends DialogFragment implements View.OnClickListener {

    private TextView tvContent,tvOK;

    String content = "";
    private boolean isError;

    private DialogClickListener listener;

    private static InfoDialog infoDialog = null;


    private InfoDialog() {
        // Required empty public constructor
    }

    public static InfoDialog getInstance(){
        if(infoDialog==null){
            infoDialog = new InfoDialog();
        }
        return infoDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        content = getArguments().getString(Constant.CONTENT);
        this.isError = getArguments().getBoolean("ERROR");
    }

    public void setListener(DialogClickListener listener){
        this.listener = listener;
    }


   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_dialog, container, false);
    }*/


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_info_dialog, null);
        initView(view);

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.MyDialog).create();
        alertDialog.setView(view);
        return alertDialog;
    }


    private void initView(View view) {

        tvContent = view.findViewById(R.id.content);
        tvOK = view.findViewById(R.id.ok);

        tvOK.setOnClickListener(this);

        if(content!=null){
            tvContent.setText(content);
        }

        if(isError){
            tvContent.setTextColor(getResources().getColor(android.R.color.holo_red_light));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.ok:
                if(listener!=null){
                    listener.positiveButtonClick();
                }
                break;
        }
    }

}
