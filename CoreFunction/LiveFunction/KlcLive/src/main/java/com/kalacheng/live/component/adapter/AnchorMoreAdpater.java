package com.kalacheng.live.component.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.live.R;
import com.kalacheng.util.bean.SimpleImageSrcTextBean;

import java.util.List;

public class AnchorMoreAdpater extends RecyclerView.Adapter<AnchorMoreAdpater.AnchorMoreViewHolder> {
    private Context mContext;
    private AnchorMoreItmeCallBack callBack;
    private List<SimpleImageSrcTextBean> list;
    public AnchorMoreAdpater(Context mContext,List<SimpleImageSrcTextBean> data){
        this.mContext=mContext;
        this.list = data;
    }
    @NonNull
    @Override
    public AnchorMoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.anchor_more_itme,null,false);
        AnchorMoreViewHolder holder = new AnchorMoreViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnchorMoreViewHolder holder, final int position) {
        holder.image.setBackgroundResource(list.get(position).src);
        holder.name.setText(list.get(position).text);

        holder.anchor_Lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onClick(list.get(position).text,position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AnchorMoreViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView name;
        public LinearLayout anchor_Lin;
        public AnchorMoreViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            anchor_Lin = itemView.findViewById(R.id.anchor_Lin);
        }
    }

    public void setAnchorMoreItmeCallBack(AnchorMoreItmeCallBack back){
        this.callBack = back;
    }
    public interface AnchorMoreItmeCallBack{
        public void onClick(String name,int position);
    }
}
