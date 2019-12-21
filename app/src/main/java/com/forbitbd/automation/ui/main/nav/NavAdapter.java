package com.forbitbd.automation.ui.main.nav;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.automation.R;

import info.androidhive.fontawesome.FontTextView;


public class NavAdapter extends RecyclerView.Adapter<NavAdapter.NavHolder> {

    private LayoutInflater inflater;
    private String[] icons;
    private String[] names;
    private NavContract.View mView;

    public NavAdapter(NavigationDrawer fragment) {
        Context context = fragment.getContext();
        this.mView = fragment;
        this.inflater = LayoutInflater.from(context);
        this.icons = context.getResources().getStringArray(R.array.nav_icons);
        this.names = context.getResources().getStringArray(R.array.nav_texts);
    }

    @NonNull
    @Override
    public NavHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.nav_item,viewGroup,false);
        return new NavHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NavHolder navHolder, int i) {
        String icon = icons[i];
        String name = names[i];

        navHolder.bind(icon,name);

    }

    @Override
    public int getItemCount() {
        return icons.length;
    }


    class NavHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AppCompatTextView tvName;
        FontTextView navIcon;

        public NavHolder(@NonNull View itemView) {
            super(itemView);
            navIcon = itemView.findViewById(R.id.icon);
            tvName = itemView.findViewById(R.id.text);
            itemView.setOnClickListener(this);
        }

        public void bind(String icon, String name){
            tvName.setText(name);
            navIcon.setText(icon);
        }

        @Override
        public void onClick(View view) {
            switch (getAdapterPosition()){
                case 0:
                    mView.loadHomeFragment();
                    break;

                case 1:
                    mView.loadProfileFragment();
                    break;
                case 2:
                    mView.loadSharedDevicesFragment();
                    break;
                case 3:
                    mView.loadAboutFragment();
                    break;

                case 4:
                    mView.logout();
                    break;
            }
        }
    }
}
