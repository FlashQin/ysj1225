package com.kalacheng.shopping.buyer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.busshop.model.ShopLiveGoods;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.shopping.R;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class GoodsAdapter3 extends RecyclerView.Adapter<GoodsAdapter3.Holder> {

    List<ShopLiveGoods> list;


    Context mContext;

    public GoodsAdapter3(List<ShopLiveGoods> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new Holder(LayoutInflater.from(mContext).inflate(R.layout.item_goods3,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position, @NonNull List<Object> payloads) {
        Object obj = payloads.size() > 0 ? payloads.get(0):null;
        final ShopLiveGoods dto = list.get(position);
        if (obj == null){
            String[] picture = dto.goodsPicture.split(",");
            ImageLoader.display(picture.length>0?picture[0]:"",holder.goodIv);
        }
    }

    @Override
    public int getItemCount() {
        return Math.min(list.size(),3);
    }

    static class Holder extends RecyclerView.ViewHolder{
        RoundedImageView goodIv;

        public Holder(@NonNull View itemView) {
            super(itemView);
            goodIv = itemView.findViewById(R.id.goodIv);
        }
    }

}
