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

            ivArrow = itemView.findViewById(R.id.arrow);
            mHideLayout = itemView.findViewById(R.id.hide_container);
            mCardView = itemView.findViewById(R.id.card);

            btnShare = itemView.findViewById(R.id.share);
            btnSharedUsers = itemView.findViewById(R.id.shared_users);


            params = (LinearLayout.LayoutParams) mHideLayout.getLayoutParams();

            mCardView.setOnClickListener(this);

            btnShare.setOnClickListener(this);
            btnSharedUsers.setOnClickListener(this);

            tvName.setOnLongClickListener(this);

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

            SwitchAdapter adapter = new SwitchAdapter(mContext,device.getSwitches(),device.getDevice_id(),fragment);
            mRecyclerView.setAdapter(adapter);
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
            if(v==tvName){
                fragment.editDeviceName(device);
            }
            return false;
        }
    }
}
