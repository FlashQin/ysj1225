package com.kalacheng.shopping.seller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.shopping.R;

import java.util.ArrayList;
import java.util.List;

public class GoodsAttributeAdapter extends RecyclerView.Adapter<GoodsAttributeAdapter.Holder> {

    Context mContext;
    List<String> list = new ArrayList();

    OnItemClickListener listener;

    public GoodsAttributeAdapter() {
        list.add("123456");
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_attribure, parent, false),mContext,this);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position, @NonNull List<Object> payloads) {
        Object payload = payloads.size() > 0 ? payloads.get(0) : null;
        if (payload == null) {
           holder.addAttributeTv.setText("属性"+position+1+":");
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView addAttributeTv;
        EditText addAttributeEt;
        RecyclerView recyclerView;
        AttributeValueAdapter adapter;
        public Holder(@NonNull View itemView,Context context,GoodsAttributeAdapter attributeAdapter) {
            super(itemView);
            adapter = new AttributeValueAdapter();
            addAttributeEt = itemView.findViewById(R.id.addAttributeEt);
            addAttributeTv = itemView.findViewById(R.id.addAttributeTv);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(context,2));
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
        }
    }

    public interface OnItemClickListener {
        void onClick(String classifyTv);
    }

}
