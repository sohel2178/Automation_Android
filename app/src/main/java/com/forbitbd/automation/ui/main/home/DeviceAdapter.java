package com.forbitbd.automation.ui.main.home;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.automation.R;
import com.forbitbd.automation.models.Command;
import com.forbitbd.automation.models.Device;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceHolder> {


    private HomeFragment fragment;
    private List<Device> deviceList;
    private LayoutInflater inflater;

    private int hideContainerHeight;

    public DeviceAdapter(HomeFragment fragment) {
        this.fragment = fragment;
        this.inflater = LayoutInflater.from(fragment.getContext());
        this.deviceList = new ArrayList<>();
    }

    @NonNull
    @Override
    public DeviceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_device,parent,false);
        return new DeviceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceHolder holder, int position) {
        Device device = deviceList.get(position);
        holder.bind(device);
    }

    public void addDevice(Device device){
        this.deviceList.add(device);
        int position = deviceList.indexOf(device);
        notifyItemInserted(position);
    }

    public void updateDevice(Device device){
        int position = getPosition(device);
        if(position!=-1){
            deviceList.set(position,device);
            notifyItemChanged(position);
        }
    }


    private int getPosition(Device device){
        for (Device x:deviceList){
            if(x.getDevice_id().equals(device.getDevice_id())){
                return deviceList.indexOf(x);
            }
        }
        return -1;
    }

    public void setHideContainerHeight(int height){
        if(height!=0 && hideContainerHeight==0){
            hideContainerHeight = height;
        }
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    class DeviceHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView tvName;
//        ImageView ivOne,ivTwo,ivThree,ivFour;

        LinearLayout mHideLayout;
        LinearLayout.LayoutParams params;
        ImageView ivArrow;

        private MaterialButton btnShare,btnSharedUsers,btnPowerSave;

        private CardView mCardView;
        private boolean isExpand;

        private Context mContext;

        private RecyclerView mRecyclerView;

        public DeviceHolder(@NonNull View itemView) {
            super(itemView);

            mContext = itemView.getContext();

            tvName = itemView.findViewById(R.id.name);

            mRecyclerView = itemView.findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
            /*tvNameOne = itemView.findViewById(R.id.name_one);
            tvNameTwo = itemView.findViewById(R.id.name_two);
            tvNameThree = itemView.findViewById(R.id.name_three);
            tvNameFour = itemView.findViewById(R.id.name_four);

            ivOne = itemView.findViewById(R.id.image_one);
            ivTwo = itemView.findViewById(R.id.image_two);
            ivThree = itemView.findViewById(R.id.image_three);
            ivFour = itemView.findViewById(R.id.image_four);*/

            ivArrow = itemView.findViewById(R.id.arrow);
            mHideLayout = itemView.findViewById(R.id.hide_container);
            mCardView = itemView.findViewById(R.id.card);

            btnShare = itemView.findViewById(R.id.share);
            btnSharedUsers = itemView.findViewById(R.id.shared_users);

           /* FontDrawable share = new FontDrawable(mContext,R.string.fa_share_solid,true,false);
            FontDrawable users = new FontDrawable(mContext,R.string.fa_users_solid,true,false);
            share.setTextColor(ContextCompat.getColor(mContext, android.R.color.white));
            users.setTextColor(ContextCompat.getColor(mContext, android.R.color.white));

            ivShare.setImageDrawable(share);
            ivSharedUsers.setImageDrawable(users);*/



            params = (LinearLayout.LayoutParams) mHideLayout.getLayoutParams();

           /* ivOne.setOnClickListener(this);
            ivTwo.setOnClickListener(this);
            ivThree.setOnClickListener(this);
            ivFour.setOnClickListener(this);*/
            mCardView.setOnClickListener(this);

            btnShare.setOnClickListener(this);
            btnSharedUsers.setOnClickListener(this);

            tvName.setOnLongClickListener(this);
            /*tvNameOne.setOnLongClickListener(this);
            tvNameTwo.setOnLongClickListener(this);
            tvNameThree.setOnLongClickListener(this);
            tvNameFour.setOnLongClickListener(this);*/

            if(hideContainerHeight<=0){
                ViewTreeObserver observer = mHideLayout.getViewTreeObserver();
                observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mHideLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        setHideContainerHeight(mHideLayout.getMeasuredHeight());
                        collapse();
                    }
                });
            }
        }


        public void bind(Device device){
            tvName.setText(device.getName());

            SwitchAdapter adapter = new SwitchAdapter(mContext,device.getSwitches(),device.getDevice_id());
            mRecyclerView.setAdapter(adapter);

            /*Switch sw1 = device.getSwitches().get(1);
            tvNameOne.setText(sw1.getName());
            if(sw1.getState()==0){
                ivOne.setImageResource(R.drawable.light_off);
            }else {
                ivOne.setImageResource(R.drawable.light_on);
            }

            Switch sw2 = device.getSwitches().get(2);
            tvNameTwo.setText(sw2.getName());
            if(sw2.getState()==0){
                ivTwo.setImageResource(R.drawable.light_off);
            }else {
                ivTwo.setImageResource(R.drawable.light_on);
            }

            Switch sw3 = device.getSwitches().get(3);
            tvNameThree.setText(sw3.getName());
            if(sw3.getState()==0){
                ivThree.setImageResource(R.drawable.light_off);
            }else {
                ivThree.setImageResource(R.drawable.light_on);
            }

            Switch sw4 = device.getSwitches().get(4);
            tvNameFour.setText(sw4.getName());
            if(sw4.getState()==0){
                ivFour.setImageResource(R.drawable.light_off);
            }else {
                ivFour.setImageResource(R.drawable.light_on);
            }*/
        }

        @Override
        public void onClick(View v) {

            if(v==mCardView){
                animate();
            }else if(v==btnShare){
                fragment.startSearchUserActivity(deviceList.get(getAdapterPosition()));
            }else if(v==btnSharedUsers){
                fragment.startSharedUserActivity(deviceList.get(getAdapterPosition()));
            }
            else{
                Device device = deviceList.get(getAdapterPosition());
                Command command = new Command();
                command.setDevice_id(device.getDevice_id());
                command.setCommand_type("CHANGE_STATE");

                /*if(v ==ivOne){
                    command.setSwitch_id("1");
                    if(device.getSwitches().get(1).getState()==0){
                        command.setCommand("ON");
                    }else {
                        command.setCommand("OFF");
                    }

                }else if(v==ivTwo){
                    command.setSwitch_id("2");
                    if(device.getSwitches().get(2).getState()==0){
                        command.setCommand("ON");
                    }else {
                        command.setCommand("OFF");
                    }
                }else if(v==ivThree){
                    command.setSwitch_id("3");
                    if(device.getSwitches().get(3).getState()==0){
                        command.setCommand("ON");
                    }else {
                        command.setCommand("OFF");
                    }
                }else if(v==ivFour){
                    command.setSwitch_id("4");
                    if(device.getSwitches().get(4).getState()==0){
                        command.setCommand("ON");
                    }else {
                        command.setCommand("OFF");
                    }
                }*/

                fragment.sendCommand(command);
            }



        }


        private void animate(){

            ValueAnimator animator = ValueAnimator.ofFloat(1,hideContainerHeight);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(300);

            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int value;
                    int rotation;

                    if(isExpand){
                        value = (int) (hideContainerHeight*(1-valueAnimator.getAnimatedFraction()));
                        rotation = (int) ((1-valueAnimator.getAnimatedFraction())*180);
                    }else {
                        value = (int) (hideContainerHeight*valueAnimator.getAnimatedFraction());
                        rotation = (int) (180*valueAnimator.getAnimatedFraction());
                    }

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,value);
                    mHideLayout.setLayoutParams(params);
                    ivArrow.setRotation(rotation);
                    mHideLayout.requestLayout();

                    if(valueAnimator.getAnimatedFraction()==1){
                        isExpand = !isExpand;
                    }
                }
            });

            animator.start();

        }


        private void collapse(){
            params = (LinearLayout.LayoutParams) mHideLayout.getLayoutParams();
            params.height = 0;
            mHideLayout.setLayoutParams(params);
            mHideLayout.requestLayout();
        }

        @Override
        public boolean onLongClick(View v) {
            Device device = deviceList.get(getAdapterPosition());
            /*if(v==tvName){
                fragment.editDeviceName(device);
            }else if(v==tvNameOne){
                Switch aSwitch = device.getSwitches().get(1);
                aSwitch.setDevice_id(device.getDevice_id());
                fragment.editSwitch(aSwitch);
            }else if(v==tvNameTwo){
                Switch aSwitch = device.getSwitches().get(2);
                aSwitch.setDevice_id(device.getDevice_id());
                fragment.editSwitch(aSwitch);
            }else if(v==tvNameThree){
                Switch aSwitch = device.getSwitches().get(3);
                aSwitch.setDevice_id(device.getDevice_id());
                fragment.editSwitch(aSwitch);
            }else if(v==tvNameFour){
                Switch aSwitch = device.getSwitches().get(4);
                aSwitch.setDevice_id(device.getDevice_id());
                fragment.editSwitch(aSwitch);
            }*/
            return false;
        }
    }
}
