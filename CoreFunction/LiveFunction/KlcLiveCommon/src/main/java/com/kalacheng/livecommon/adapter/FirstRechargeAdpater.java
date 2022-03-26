package com.kalacheng.livecommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.model.AdminGiftPack;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.databinding.FirstRechargeItmeBinding;

import java.util.ArrayList;
import java.util.List;

public class FirstRechargeAdpater extends RecyclerView.Adapter<FirstRechargeAdpater.FirstRechargeViewHolder> {
    private Context mContext;
    private List<AdminGiftPack> mList = new ArrayList<>();
    public FirstRechargeAdpater(Context mContext){
        this.mContext=mContext;
    }
    public void setData(List<AdminGiftPack> data){
        this.mList.clear();
        this.mList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FirstRechargeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FirstRechargeItmeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.first_recharge_itme,null,false);
        FirstRechargeViewHolder holder = new FirstRechargeViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FirstRechargeViewHolder holder, int position) {
        holder.binding.setViewModel(mList.get(position));
        holder.binding.executePendingBindings();
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class FirstRechargeViewHolder extends RecyclerView.ViewHolder{

        public FirstRechargeItmeBinding binding;
        public FirstRechargeViewHolder(@NonNull FirstRechargeItmeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
