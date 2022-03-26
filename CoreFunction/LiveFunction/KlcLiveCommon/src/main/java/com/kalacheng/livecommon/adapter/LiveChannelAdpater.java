package com.kalacheng.livecommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.kalacheng.libuser.model.AppLiveChannel;
import com.kalacheng.livecommon.R;

import java.util.ArrayList;
import java.util.List;

public class LiveChannelAdpater extends RecyclerView.Adapter<LiveChannelAdpater.LiveChannelViewHolder> {
    private Context mContext;
    private List<AppLiveChannel> list = new ArrayList<>();
    private int ClickPostion = -1;
    private LiveChannelItmeCallBack callBack;

    public LiveChannelAdpater(Context mContext){
        this.mContext= mContext;
    }

    public void setAppLiveChannel(List<AppLiveChannel> data){
        this.list.clear();
        this.list =data;
        notifyDataSetChanged();
    }
    public void setClickPostion(int postion){
        this .ClickPostion = postion;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LiveChannelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.live_channel_itme,null,false);
        LiveChannelViewHolder holder = new LiveChannelViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull LiveChannelViewHolder holder, final int position) {
        holder.channel_name.setText(list.get(position).title);

        if (ClickPostion == position){
            holder.channel_name.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.channel_name.setBackgroundResource(R.drawable.bg_channel_violet);
        }else {
            holder.channel_name.setTextColor(mContext.getResources().getColor(R.color.textColor6));
            holder.channel_name.setBackgroundResource(R.drawable.bg_channel_white);
        }

        holder.channel_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LiveChannelViewHolder extends RecyclerView.ViewHolder{
        public TextView channel_name;
        public LiveChannelViewHolder(@NonNull View itemView) {
            super(itemView);
            channel_name = itemView.findViewById(R.id.channel_name);
        }
    }

    public interface LiveChannelItmeCallBack{
        public void onClick(int position);
    }

    public void setLiveChannelItmeCallBack(LiveChannelItmeCallBack back){
        this.callBack =back;
    }
}
