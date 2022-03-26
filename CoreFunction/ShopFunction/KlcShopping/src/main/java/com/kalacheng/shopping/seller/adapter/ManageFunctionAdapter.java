package com.kalacheng.shopping.seller.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.shopping.R;
import com.kalacheng.util.utils.CheckDoubleClick;

import java.util.ArrayList;
import java.util.List;

public class ManageFunctionAdapter extends RecyclerView.Adapter<ManageFunctionAdapter.Holder> {

    List<Function> functions = new ArrayList<>();
    OnItemClickListener listener;
    public ManageFunctionAdapter() {
        functions.add(new Function(R.mipmap.icon_wodeshouru,"我的收入"));
        functions.add(new Function(R.mipmap.icon_tianjiashangpin,"添加商品"));
        functions.add(new Function(R.mipmap.icon_shangjiajianjie,"商家简介"));
        //functions.add(new Function(R.mipmap.icon_youhuiquan,"优惠券"));
        functions.add(new Function(R.mipmap.icon_shangpinguanli,"商品管理"));
        //functions.add(new Function(R.mipmap.icon_manjianhuodong,"满减活动"));
        functions.add(new Function(R.mipmap.icon_zhibogouwu,"直播预告"));
        functions.add(new Function(R.mipmap.icon_xiaodianyulan,"小店预览"));
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_manage_function,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position, @NonNull List<Object> payloads) {
        Object payload = payloads.size()>0 ?payloads.get(0):null;
        if (payload == null){
            final Function function = functions.get(position);
            holder.function.setCompoundDrawablesRelativeWithIntrinsicBounds(0,function.img,0,0);
            holder.function.setText(function.function);
            holder.function.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick())return;
                    if (listener != null){
                        listener.onClick(function.function);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return Math.min(functions.size(), 8);
    }

    static class Holder extends RecyclerView.ViewHolder{
        TextView function;
        public Holder(@NonNull View itemView) {
            super(itemView);
            function = (TextView) itemView;
        }
    }

    static class Function{
        int img;

        String function;

        public Function(int img, String function) {
            this.img = img;
            this.function = function;
        }

        public int getImg() {
            return img;
        }

        public String getFunction() {
            return function;
        }
    }

    public interface OnItemClickListener{
        void onClick(String function);
    }

}
