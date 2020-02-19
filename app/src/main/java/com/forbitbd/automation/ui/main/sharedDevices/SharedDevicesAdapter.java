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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.automation.R;
import com.forbitbd.automation.models.Device;
import com.forbitbd.automation.models.Switch;
import com.forbitbd.automation.ui.main.home.SwitchAdapter;
import com.google.android.material.button.MaterialButton;

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

    public void updateSwitch(Switch aSwitch){
        Device device = getDevice(aSwitch);

        if(device !=null){
            device.getSwitches().set((Integer.parseInt(aSwitch.getId())-1),aSwitch);
        }

        notifyItemChanged(deviceList.indexOf(device));
    }

    private Device getDevice(Switch aSwitch){
        for (Device x: deviceList){
            if(x.getDevice_id().equals(aSwitch.getDevice_id())){
                return x;
            }
        }

        return null;
    }


    class SharedDevicesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName;
        LinearLayout mHideLayout;
        LinearLayout.LayoutParams params;
        ImageView ivArrow;

        private MaterialButton ivShare,ivSharedUsers,btnPowerSave;

        private CardView mCardView;
        private boolean isExpand;

        private Context mContext;

        private RecyclerView mRecyclerView;

        public SharedDevicesHolder(@NonNull View itemView) {
            super(itemView);

            mContext = itemView.getContext();

            tvName = itemView.findViewById(R.id.name);

            mRecyclerView = itemView.findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,2));

            ivArrow = itemView.findViewById(R.id.arrow);
            mHideLayout = itemView.findViewById(R.id.hide_container);
            mCardView = itemView.findViewById(R.id.card);

            ivShare = itemView.findViewById(R.id.share);
            ivSharedUsers = itemView.findViewById(R.id.shared_users);
            btnPowerSave = itemView.findViewById(R.id.power_save);

            ivShare.setVisibility(View.GONE);
            ivSharedUsers.setVisibility(View.GONE);
            btnPowerSave.setVisibility(View.GONE);



            params = (LinearLayout.LayoutParams) mHideLayout.getLayoutParams();

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

            SwitchAdapter adapter = new SwitchAdapter(mContext,device.getSwitches(),device.getDevice_id(),sharedDevicesFragment);
            mRecyclerView.setAdapter(adapter);


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

//                if(view ==ivOne){
//                    if(device.getSwitches().get(1).getState()==0){
//                        device.getSwitches().get(1).setState(1);
//                    }else {
//                        device.getSwitches().get(1).setState(0);
//                    }
//
//                }else if(view==ivTwo){
//                    if(device.getSwitches().get(2).getState()==0){
//                        device.getSwitches().get(2).setState(1);
//                    }else {
//                        device.getSwitches().get(2).setState(0);
//                    }
//                }else if(view==ivThree){
//                    if(device.getSwitches().get(3).getState()==0){
//                        device.getSwitches().get(3).setState(1);
//                    }else {
//                        device.getSwitches().get(3).setState(0);
//                    }
//                }else if(view==ivFour){
//                    if(device.getSwitches().get(4).getState()==0){
//                        device.getSwitches().get(4).setState(1);
//                    }else {
//                        device.getSwitches().get(4).setState(0);
//                    }
//                }
//
//                sharedDevicesFragment.updateSwitches(device);
            }
        }
    }

}
