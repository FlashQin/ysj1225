package com.kalacheng.livecommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.model.ApiUsersLiveWish;
import com.makeramen.roundedimageview.RoundedImageView;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class OpenLiveWishAdpater extends RecyclerView.Adapter<OpenLiveWishAdpater.OpenLiveWishViewHolder> {
    private Context mContext;
    public List<ApiUsersLiveWish> liveWishes = new ArrayList<>();
    public OpenLiveWishAdpater(Context mContext){
        this.mContext = mContext;
    }
    public void setWish(List<ApiUsersLiveWish> data){
        this.liveWishes.clear();
        this.liveWishes = data;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public OpenLiveWishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(mContext).inflate(R.layout.openlivewish_itme,null,false);
        OpenLiveWishViewHolder holder = new OpenLiveWishViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OpenLiveWishViewHolder holder, int position) {
        holder.OpenLiveWish_Name.setText(liveWishes.get(position).giftname);
        holder.OpenLiveWish_Number.setText("*"+liveWishes.get(position).num);
        ImageLoader.display(liveWishes.get(position).gifticon,holder.OpenLiveWish_HeadImage);
    }


    @Override
    public int getItemCount() {
        if (liveWishes.size()>2){
            return 2;
        }else {
            return liveWishes.size();
        }

    }
    public class OpenLiveWishViewHolder extends RecyclerView.ViewHolder{
        public RoundedImageView OpenLiveWish_HeadImage;
        public TextView OpenLiveWish_Name;
        public TextView OpenLiveWish_Number;
        public OpenLiveWishViewHolder(@NonNull View itemView) {
            super(itemView);
            OpenLiveWish_HeadImage = itemView.findViewById(R.id.OpenLiveWish_HeadImage);
            OpenLiveWish_Name = itemView.findViewById(R.id.OpenLiveWish_Name);
            OpenLiveWish_Number = itemView.findViewById(R.id.OpenLiveWish_Number);
        }
    }
}
