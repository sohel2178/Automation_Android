package com.forbitbd.automation.ui.sharedUser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.automation.R;
import com.forbitbd.automation.models.SharedUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sohel on 04-05-18.
 */

public class SharedUserAdapter extends RecyclerView.Adapter<SharedUserAdapter.SharedUserHolder> {

    private Context context;
    private List<SharedUser> sharedUserList;
    private LayoutInflater inflater;


    public SharedUserAdapter(Context context) {
        this.context = context;
        this.sharedUserList = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public SharedUserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_shared_user,parent,false);
        return new SharedUserHolder(view);
    }


    public void removeItem(int position) {
        sharedUserList.remove(position);
        notifyItemRemoved(position);
    }

    public void removeSharedUser(SharedUser sharedUser){
        int position = getPosition(sharedUser);

        if(position != -1){
            removeItem(position);
        }
    }

    private int getPosition(SharedUser sharedUser){
        for (SharedUser x: sharedUserList){
            if(x.get_id().equals(sharedUser.get_id())){
                return sharedUserList.indexOf(x);
            }
        }
        return -1;
    }

    public SharedUser getSharedUser(int position){
        return sharedUserList.get(position);
    }

    @Override
    public void onBindViewHolder(final SharedUserHolder holder, int position) {
        SharedUser sharedUser = sharedUserList.get(position);

        holder.bind(sharedUser);




    }

    public void addSharedUser(SharedUser sharedUser){
        sharedUserList.add(sharedUser);
        int pos = sharedUserList.indexOf(sharedUser);
        notifyItemInserted(pos);
    }

    public void addSharedUserList(List<SharedUser> sharedUserList){
        this.sharedUserList = sharedUserList;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return sharedUserList.size();
    }

    class SharedUserHolder extends RecyclerView.ViewHolder{

        CircleImageView ivImage;
        TextView tvName,tvContact;
        public RelativeLayout viewBackground, viewForeground;

        public SharedUserHolder(View itemView) {
            super(itemView);

            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);

            ivImage = itemView.findViewById(R.id.image);
            tvName = itemView.findViewById(R.id.name);
            tvContact = itemView.findViewById(R.id.contact);

        }

        public void bind(SharedUser sharedUser){
            if(sharedUser.getUser().getImage() != null || !sharedUser.getUser().getImage().equals("")){
                Picasso.with(context)
                        .load(sharedUser.getUser().getImage())
                        .into(ivImage);
            }

            tvName.setText(sharedUser.getUser().getName());
            tvContact.setText(sharedUser.getUser().getContact());
        }

    }
}
