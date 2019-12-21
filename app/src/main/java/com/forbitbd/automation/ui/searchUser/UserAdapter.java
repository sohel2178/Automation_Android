package com.forbitbd.automation.ui.searchUser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.automation.R;
import com.forbitbd.automation.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> implements Filterable {

    private List<User> userList;
    private List<User> originalList;
    private SearchUserActivity activity;
    private LayoutInflater inflater;

    public UserAdapter(SearchUserActivity activity) {
        this.activity = activity;
        this.userList = new ArrayList<>();
        this.inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_user,parent,false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void addUsers(List<User> userList){
        this.userList.addAll(userList);
        notifyDataSetChanged();
    }

    public void clear(){
        this.userList.clear();
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        if(originalList==null){
            this.originalList= userList;
        }
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                List<User> filteredList = new ArrayList<>();

                if(charString.isEmpty()){
                    filteredList = originalList;
                }else{
                    List<User> tmpList = new ArrayList<>();
                    for (User x: originalList){
                        if(x.getEmail().toLowerCase().startsWith(charString)){
                            tmpList.add(x);
                        }
                    }

                    filteredList = tmpList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values=filteredList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                userList = (List<User>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }


    class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvName,tvContact;
        ImageView ivImage;
        Button btnShare;

        public UserHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.name);
            tvContact = itemView.findViewById(R.id.contact);
            ivImage = itemView.findViewById(R.id.image);
            btnShare = itemView.findViewById(R.id.share);

            btnShare.setOnClickListener(this);
        }

        public void bind(User user){
            tvName.setText(user.getName());
            tvContact.setText(user.getContact());

            if(!user.getImage().isEmpty()){
                Picasso.with(activity)
                        .load(user.getImage()).into(ivImage);
            }


        }

        @Override
        public void onClick(View view) {
            activity.shareDevice(userList.get(getAdapterPosition()));
        }
    }
}
