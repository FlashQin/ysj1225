package com.kalacheng.shopping.seller.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.busshop.model.ShopGoodsCategory;
import com.kalacheng.shopping.R;

import java.util.ArrayList;
import java.util.List;

public class GoodsClassifyAdapter extends RecyclerView.Adapter<GoodsClassifyAdapter.Holder> {

    List<ShopGoodsCategory> list;

    OnItemClickListener listener;

    public List<ShopGoodsCategory> getList() {
        return list;
    }

    public GoodsClassifyAdapter() {
        list = new ArrayList<>();
    }

    public void setList(List<ShopGoodsCategory> list) {
        this.list.addAll(list);
        notifyItemRangeChanged(0,list.size()-1);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_classify, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position, @NonNull List<Object> payloads) {
        Object payload = payloads.size() > 0 ? payloads.get(0) : null;
        if (payload == null) {
            holder.classifyTv.setText(list.get(position).name);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onClick(list.get(position));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView classifyTv;

        public Holder(@NonNull View itemView) {
            super(itemView);
            classifyTv = (TextView) itemView.findViewById(R.id.classifyTv);
        }
    }

    public interface OnItemClickListener {
        void onClick(ShopGoodsCategory classifyTv);
    }

}
