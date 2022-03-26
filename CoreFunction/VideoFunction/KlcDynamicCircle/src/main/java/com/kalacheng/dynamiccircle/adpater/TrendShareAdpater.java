package com.kalacheng.dynamiccircle.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.kalacheng.dynamiccircle.R;

import java.util.ArrayList;
import java.util.List;

public class TrendShareAdpater extends RecyclerView.Adapter<TrendShareAdpater.TrendShareViewHolder> {
    private Context mContext;
    private List<String> mList =new ArrayList<>();

    private TrendShareItmeOnClick itmeOnClick;

    public TrendShareAdpater(Context mContext){
        this.mContext=mContext;
    }

    public void getData(List<String> data){
        this.mList.clear();
        this.mList=data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrendShareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.trend_share_itme,null,false);
        TrendShareViewHolder holder = new TrendShareViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrendShareViewHolder holder, final int position) {
        if (mList.get(position).equals("1")){
            holder.TrendShare_icon.setBackgroundResource(R.mipmap.icon_share_qq_1);
            holder.TrendShare_text.setText(mContext.getResources().getText(R.string.trend_share_qq));
        }else if(mList.get(position).equals("2")){
            holder.TrendShare_icon.setBackgroundResource(R.mipmap.icon_share_qzone_1);
            holder.TrendShare_text.setText(mContext.getResources().getText(R.string.trend_share_qqpyq));
        }else if(mList.get(position).equals("3")){
            holder.TrendShare_icon.setBackgroundResource(R.mipmap.icon_share_wx_1);
            holder.TrendShare_text.setText(mContext.getResources().getText(R.string.trend_share_weixin));
        }else if(mList.get(position).equals("4")){
            holder.TrendShare_icon.setBackgroundResource(R.mipmap.icon_share_pyq_1);
            holder.TrendShare_text.setText(mContext.getResources().getText(R.string.trend_share_pyq));
        }

        holder.TrendShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itmeOnClick.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TrendShareViewHolder extends RecyclerView.ViewHolder {
        public TextView TrendShare_text;
        public ImageView TrendShare_icon;
        public LinearLayout TrendShare;
        public TrendShareViewHolder(@NonNull View itemView) {
            super(itemView);
            TrendShare_icon=itemView.findViewById(R.id.TrendShare_icon);
            TrendShare_text =itemView.findViewById(R.id.TrendShare_text);
            TrendShare =itemView.findViewById(R.id.TrendShare);
        }

    }

    public void setTrendShareItmeOnClick(TrendShareItmeOnClick click){
        this.itmeOnClick=click;
    }

    public interface TrendShareItmeOnClick{
        public void onClick(int poistion);
    }
}
