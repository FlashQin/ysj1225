package com.kalacheng.shopping.seller.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.busshop.model.ShopAttrValue;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.AttributeAndValueBean;

import java.util.ArrayList;
import java.util.List;

public class AttributeValueAdapter extends RecyclerView.Adapter<AttributeValueAdapter.Holder> {

    List<AttributeAndValueBean> list = new ArrayList();

    OnItemClickListener listener;

    public AttributeValueAdapter() {
        AttributeAndValueBean bean = new AttributeAndValueBean();
        list.add(bean);
    }

    public  void addItem(){
        int index = list.size() > 1 ? list.size() - 1 : 0;
        list.add(index,new AttributeAndValueBean());
        notifyItemInserted(index);
    }
    public  void delItem(int index){
        list.remove(index);
        notifyItemRemoved(index);
    }

    public void setList(List<ShopAttrValue> attrValueList){
        for (ShopAttrValue attrValue: attrValueList){
            AttributeAndValueBean bean = new AttributeAndValueBean();
            bean.attrDTO = attrValue;
            int index = list.size() > 1 ? list.size() - 1 : 0;
            list.add(index,bean);
        }
        notifyDataSetChanged();
    }
    public List<AttributeAndValueBean> getList() {
        return list;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attribure_value, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position, @NonNull List<Object> payloads) {
        Object payload = payloads.size() > 0 ? payloads.get(0) : null;
        if (payload == null) {
            if (position == list.size()-1){
                holder.attributeEt.setBackgroundResource(R.drawable.bg_attribure_value_add);
                holder.attributeEt.setHint("自定义");
                holder.attributeEt.setFocusable(false);
                holder.attributeEt.setFocusableInTouchMode(false);
                holder.attributeEt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addItem();
                    }
                });
                holder.delIv.setVisibility(View.GONE);
            }else {
                if (position == list.size()-2){
                    holder.attributeEt.requestFocus();
                }

                AttributeAndValueBean valueBean = list.get(position);
                if (valueBean.attrDTO != null){
                    holder.attributeEt.setText(valueBean.attrDTO.name);
                }else {
                    holder.attributeEt.setText("");
                }
                holder.attributeEt.setBackgroundResource(R.drawable.bg_attribure_value);
                holder.attributeEt.setFocusable(true);
                holder.attributeEt.setFocusableInTouchMode(true);
                holder.delIv.setVisibility(View.VISIBLE);
                holder.delIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delItem(holder.getBindingAdapterPosition());
                    }
                });
                list.get(position).editText = holder.attributeEt;
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        EditText attributeEt;
        ImageView delIv;
        public Holder(@NonNull View itemView) {
            super(itemView);
            attributeEt = itemView.findViewById(R.id.attributeEt);
            delIv = itemView.findViewById(R.id.delIv);
        }
    }

    public interface OnItemClickListener {
        void onClick(String classifyTv);
    }

}
