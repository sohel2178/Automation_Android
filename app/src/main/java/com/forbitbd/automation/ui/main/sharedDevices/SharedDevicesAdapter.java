package com.forbitbd.automation.ui.main.sharedDevices;

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
import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.automation.R;
import com.forbitbd.automation.models.Command;
import com.forbitbd.automation.models.Device;
import com.forbitbd.automation.models.SharedDevice;
import com.forbitbd.automation.models.Switch;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class SharedDevicesAdapter extends RecyclerView.Adapter<SharedDevicesAdapter.SharedDevicesHolder> {

    private SharedDevicesFragment sharedDevicesFragment;
    private List<Device> deviceList;
    private LayoutInflater inflater;

    private int hideContainerHeight;

    public SharedDevicesAdapter(SharedDevicesFragment sharedDevicesFragment) {
        this.sharedDevicesFragment = sharedDevicesFragment;
        this.deviceList = new ArrayList<>();
        this.inflater = LayoutInflater.from(sharedDevicesFragment.getContext());
    }


    @NonNull
    @Override
    public SharedDevicesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_device,parent,false);
        return new SharedDevicesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SharedDevicesHolder holder, int position) {

        Device device = deviceList.get(position);
        holder.bind(device);
    }


    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public void addAll(List<Device> deviceList){
        this.deviceList = deviceList;
        notifyDataSetChanged();
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


    class SharedDevicesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName,tvNameOne,tvNameTwo,tvNameThree,tvNameFour;
        ImageView ivOne,ivTwo,ivThree,ivFour;

        LinearLayout mHideLayout;
        LinearLayout.LayoutParams params;
        ImageView ivArrow;

        private FloatingActionButton ivShare,ivSharedUsers;

        private CardView mCardView;
        private boolean isExpand;

        private Context mContext;

        public SharedDevicesHolder(@NonNull View itemView) {
            super(itemView);

            mContext = itemView.getContext();

            tvName = itemView.findViewById(R.id.name);
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

            ivShare = itemView.findViewById(R.id.share);
            ivSharedUsers = itemView.findViewById(R.id.shared_users);

            ivShare.hide();
            ivSharedUsers.hide();



            params = (LinearLayout.LayoutParams) mHideLayout.getLayoutParams();

            ivOne.setOnClickListener(this);
            ivTwo.setOnClickListener(this);
            ivThree.setOnClickListener(this);
            ivFour.setOnClickListener(this);
            mCardView.setOnClickListener(this);


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

        private void collapse(){
            params = (LinearLayout.LayoutParams) mHideLayout.getLayoutParams();
            params.height = 0;
            mHideLayout.setLayoutParams(params);
            mHideLayout.requestLayout();
        }

        public void bind(Device device){

            tvName.setText(device.getName());

            Switch sw1 = device.getSwitches().get(1);
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

        @Override
        public void onClick(View view) {
            if(view==mCardView){
                animate();
            } else{
                Device device = deviceList.get(getAdapterPosition());

                if(view ==ivOne){
                    if(device.getSwitches().get(1).getState()==0){
                        device.getSwitches().get(1).setState(1);
                    }else {
                        device.getSwitches().get(1).setState(0);
                    }

                }else if(view==ivTwo){
                    if(device.getSwitches().get(2).getState()==0){
                        device.getSwitches().get(2).setState(1);
                    }else {
                        device.getSwitches().get(2).setState(0);
                    }
                }else if(view==ivThree){
                    if(device.getSwitches().get(3).getState()==0){
                        device.getSwitches().get(3).setState(1);
                    }else {
                        device.getSwitches().get(3).setState(0);
                    }
                }else if(view==ivFour){
                    if(device.getSwitches().get(4).getState()==0){
                        device.getSwitches().get(4).setState(1);
                    }else {
                        device.getSwitches().get(4).setState(0);
                    }
                }

                sharedDevicesFragment.updateSwitches(device);
            }
        }
    }

}
