package com.forbitbd.automation.dialog;


import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.forbitbd.androidutils.utils.Constant;
import com.forbitbd.automation.R;
import com.forbitbd.automation.models.Device;
import com.forbitbd.automation.models.Switch;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditDialog extends DialogFragment implements View.OnClickListener {

    private Device device;
    private Switch aSwitch;

    private TextView tvTitle,tvUpdate,tvCancel;

    private EditText etName;

    private EditListener listener;


    public EditDialog() {
        // Required empty public constructor
    }

    public void setListener(EditListener listener){
        this.listener = listener;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.device = (Device) getArguments().getSerializable("DEVICE");
        this.aSwitch = (Switch) getArguments().getSerializable("SWITCH");
    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_dialog, container, false);
    }
*/

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_edit_dialog, null);
        initView(view);
        
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.MyDialog).create();

        //AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.ThemeOverlay_AppCompat_Dialog);
        alertDialog.setView(view);
        return alertDialog;
    }

    private void initView(View view) {
        tvTitle = view.findViewById(R.id.title);
        tvCancel = view.findViewById(R.id.cancel);
        tvUpdate = view.findViewById(R.id.update);

        etName = view.findViewById(R.id.name);

        tvCancel.setOnClickListener(this);
        tvUpdate.setOnClickListener(this);

        if(device!=null){
            tvTitle.setText("Edit Device");
            etName.setText(device.getName());
        }else {
            tvTitle.setText("Edit Switch");
            etName.setText(aSwitch.getName());
        }
    }

    @Override
    public void onClick(View v) {
        if(v==tvCancel){
            dismiss();
        }else if(v==tvUpdate){
            String name = etName.getText().toString();

            if(!name.equals("")){
                dismiss();
                if(device!=null){
                    device.setName(name);

                    if(listener!=null){
                        listener.updateDevice(device);
                    }

                }else if(aSwitch !=null){
                    aSwitch.setName(name);

                    if(listener!=null){
                        listener.updateSwitch(aSwitch);
                    }
                }
            }
        }
    }
}
