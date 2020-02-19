package com.forbitbd.automation.ui.main.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.automation.R;
import com.forbitbd.automation.firebase.MyDatabaseRef;
import com.forbitbd.automation.models.Switch;

import java.util.List;

public class SwitchAdapter extends RecyclerView.Adapter<SwitchAdapter.SwitchHolder> {

    private Context context;
    private List<Switch> switchList;
    private LayoutInflater inflater;
    private String device_id;

    private SwitchListener listener;

    public SwitchAdapter(Context context, List<Switch> switchList,String device_id,SwitchListener listener) {
        this.context = context;
        this.switchList = switchList;
        this.device_id = device_id;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public SwitchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_switch,parent,false);
        return new SwitchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SwitchHolder holder, int position) {
        Switch sw = switchList.get(position);
        holder.bind(sw);
    }

    @Override
    public int getItemCount() {
        return switchList.size();
    }

    class SwitchHolder extends RecyclerView.ViewHolder implements View.OnClickListener , View.OnLongClickListener {

        TextView swName;
        ImageView swImage;
        CardView mCard;

        public SwitchHolder(@NonNull View itemView) {
            super(itemView);
            swName = itemView.findViewById(R.id.name);
            swImage = itemView.findViewById(R.id.image);
            mCard = itemView.findViewById(R.id.image_card);

            mCard.setOnClickListener(this);
            swName.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Switch sw = switchList.get(getAdapterPosition());

            if(sw.getState()==0){
                sw.setState(1);
            }else{
                sw.setState(0);
            }

            listener.onClickSwitch(sw);


        }

        public void bind(Switch aSwitch){
            swName.setText(aSwitch.getName());

            if(aSwitch.getState()==0){
                swImage.setImageResource(R.drawable.off);
            }else {
                swImage.setImageResource(R.drawable.on);
            }
        }

        @Override
        public boolean onLongClick(View v) {

            listener.editSwitch(switchList.get(getAdapterPosition()));

            return false;
        }
    }
}
