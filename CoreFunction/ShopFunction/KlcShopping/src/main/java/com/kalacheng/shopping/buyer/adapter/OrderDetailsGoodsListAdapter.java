package com.kalacheng.shopping.buyer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.busshop.model.ShopSubOrder;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.shopping.R;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsGoodsListAdapter extends RecyclerView.Adapter<OrderDetailsGoodsListAdapter.Holder> {

    Context context;
    List<ShopSubOrder> list;

    public OrderDetailsGoodsListAdapter() {
        list = new ArrayList<>();
    }

    public void setList(List<ShopSubOrder> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_details_goods, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position, @NonNull List<Object> payloads) {
        Object payload = payloads.size() > 0 ? payloads.get(0) : null;
        ShopSubOrder bean = list.get(position);
        if (payload == null) {
            ImageLoader.display(bean.goodsPicture.split(",")[0], holder.goodsPicIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            holder.goodsNameTv.setText(bean.goodsName);
            holder.skuNameTv.setText(bean.skuName);
            holder.goodsPriceTv.setText("¥ " + DecimalFormatUtils.toTwo(bean.goodsPrice));
            holder.countTv.setText("x" + bean.goodsNum);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        RoundedImageView goodsPicIv;
        TextView goodsNameTv;
        TextView skuNameTv;
        TextView goodsPriceTv;
        TextView countTv;

        Holder(@NonNull View itemView) {
            super(itemView);

            goodsPicIv = itemView.findViewById(R.id.goodsPicIv);
            goodsNameTv = itemView.findViewById(R.id.goodsNameTv);
            skuNameTv = itemView.findViewById(R.id.skuNameTv);
            goodsPriceTv = itemView.findViewById(R.id.goodsPriceTv);
            countTv = itemView.findViewById(R.id.countTv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    ARouter.getInstance().build(ARouterPath.GoodsDetailsActivity).withLong(ARouterValueNameConfig.goodsId, list.get(getBindingAdapterPosition()).goodsId).navigation();
                }
            });
        }
    }

}
