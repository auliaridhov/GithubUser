package com.harvdev.githubuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.harvdev.githubuser.R;
import com.harvdev.githubuser.model.Items;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.HolderUsers> {

    private List<Items> mList;
    private Context ctx;

    public UsersAdapter (Context ctx, List<Items> mList){
        this.ctx = ctx;
        this.mList = mList;
    }


    @NonNull
    @Override
    public HolderUsers onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = (View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_users, viewGroup, false);
        HolderUsers holderUsers = new HolderUsers(layout);
        return holderUsers;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderUsers holderUsers, int i) {
        Items items = mList.get(i);

        holderUsers.username.setText(items.getLogin());
        Picasso.get().load(items.getAvatar_url()).placeholder(R.drawable.ic_baseline_image_24).into(holderUsers.avatar);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderUsers extends RecyclerView.ViewHolder{

        TextView username;
        ImageView avatar;

        public HolderUsers (View v){
            super(v);

            username = v.findViewById(R.id.tvusername);
            avatar = v.findViewById(R.id.ivAvatar);


        }
    }

}
