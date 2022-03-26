package com.kalacheng.shopping.seller.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.busshop.model.ShopAttrCompose;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.AttributeValueBean;

import java.util.ArrayList;
import java.util.List;

public class EditPriceAdapter extends RecyclerView.Adapter<EditPriceAdapter.Holder> {

    Context mContext;
    List<ShopAttrCompose> list = new ArrayList<>();
    List<AttributeValueBean> objects = new ArrayList<>();

    OnItemClickListener listener;

    public EditPriceAdapter() {

    }

    public void setList(List<ShopAttrCompose> list) {
        this.list.clear();
        if (list != null) {
            this.list.addAll(list);
        }
        notifyDataSetChanged();
        for (int i = 0; i < this.list.size(); i++) {
            objects.add(new AttributeValueBean());
        }
    }

    public List<ShopAttrCompose> getList() {
        return list;
    }

    public List<AttributeValueBean> getObjects() {
        return objects;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attribute_price, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position, @NonNull List<Object> payloads) {
        Object payload = payloads.size() > 0 ? payloads.get(0) : null;
        final ShopAttrCompose bean = list.get(position);
        holder.setIsRecyclable(false);
        if (payload == null) {
            String name2 = TextUtils.isEmpty(bean.name2) ? "" : "“" + bean.name2 + "”";
            holder.attributeTv.setText("“" + bean.name1 + "”     " + name2);
            if (bean.price != 0)
                holder.price1Et.setText(bean.price + "");
            objects.get(position).price1Et = holder.price1Et;
            holder.price1Et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    bean.price = !TextUtils.isEmpty(s) ? Double.parseDouble(s.toString()) : 0;

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            if (bean.favorablePrice != 0)
                holder.price2Et.setText(bean.favorablePrice + "");
            objects.get(position).price2Et = holder.price2Et;
            holder.price2Et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!TextUtils.isEmpty(s) && s.toString().endsWith(".")) return;
                    bean.favorablePrice = !TextUtils.isEmpty(s) ? Double.parseDouble(s.toString()) : 0;
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            if (bean.stock != 0)
                holder.countEt.setText(bean.stock + "");
            objects.get(position).countEt = holder.countEt;
            holder.countEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!TextUtils.isEmpty(s) && s.toString().endsWith(".")) return;
                    bean.stock = !TextUtils.isEmpty(s) ? Integer.parseInt(s.toString()) : 0;
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView attributeTv;
        EditText price1Et;
        EditText price2Et;
        EditText countEt;

        public Holder(@NonNull View itemView) {
            super(itemView);
            attributeTv = itemView.findViewById(R.id.attributeTv);
            price1Et = itemView.findViewById(R.id.price1Et);
            price1Et.setFilters(new InputFilter[]{new MoneyValueFilter()});
            price2Et = itemView.findViewById(R.id.price2Et);
            price2Et.setFilters(new InputFilter[]{new MoneyValueFilter()});
            countEt = itemView.findViewById(R.id.countEt);
        }
    }

    public interface OnItemClickListener {
        void onClick(String classifyTv);
    }

}
