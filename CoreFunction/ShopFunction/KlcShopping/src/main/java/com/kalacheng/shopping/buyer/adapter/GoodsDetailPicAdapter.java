package com.kalacheng.shopping.buyer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.shopping.R;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cc.shinichi.library.ImagePreview;

public class GoodsDetailPicAdapter extends RecyclerView.Adapter<GoodsDetailPicAdapter.Holder> {

    List<String> list = new ArrayList<>();

    Context mContext;

    public void setList(List<String> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_picture,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position, @NonNull List<Object> payloads) {
        Object obj = payloads.size() > 0 ? payloads.get(0):null;
        String dto = list.get(position);
        if (obj == null){
           ImageLoader.display(dto,holder.pic);
           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   ImagePreview.getInstance().setContext(mContext).setIndex(position).setImageList(list).setShowDownButton(false).start();
               }
           });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder{
        ImageView pic;


        public Holder(@NonNull View itemView) {
            super(itemView);
            pic = (ImageView) itemView;
        }
    }

}
