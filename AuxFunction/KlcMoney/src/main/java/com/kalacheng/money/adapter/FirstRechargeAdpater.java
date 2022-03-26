package com.kalacheng.money.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.model.AdminGiftPack;
import com.kalacheng.money.R;
import com.kalacheng.money.databinding.FirstRechargeItemBinding;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class FirstRechargeAdpater extends RecyclerView.Adapter<FirstRechargeAdpater.FirstRechargeViewHolder> {
    private Context mContext;
    private List<AdminGiftPack> mList = new ArrayList<>();

    public FirstRechargeAdpater(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<AdminGiftPack> data) {
        this.mList.clear();
        this.mList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FirstRechargeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FirstRechargeItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.first_recharge_item, null, false);
        FirstRechargeViewHolder holder = new FirstRechargeViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FirstRechargeViewHolder holder, int position) {
        holder.binding.setViewModel(mList.get(position));
        holder.binding.executePendingBindings();

        if (!TextUtils.isEmpty(mList.get(position).action)) {
            if (mList.get(position).action.equals("coin")) {//金币
                ImageLoader.display(R.mipmap.icon_money_big, holder.binding.ivGift);
                holder.binding.tvPrice.setText(mList.get(position).typeVal + SpUtil.getInstance().getCoinUnit());
            } else if (mList.get(position).action.equals("gift")) {//礼物
                ImageLoader.display(mList.get(position).gifticon, holder.binding.ivGift);
                holder.binding.tvPrice.setText(mList.get(position).name + "x" + mList.get(position).typeVal);
            } else if (mList.get(position).action.equals("car")) {//坐骑
                ImageLoader.display(mList.get(position).gifticon, holder.binding.ivGift);
                holder.binding.tvPrice.setText(mList.get(position).name + "(" + mList.get(position).typeVal + "天)");
            } else {
                ImageLoader.display(mList.get(position).gifticon, holder.binding.ivGift);
                holder.binding.tvPrice.setText(mList.get(position).name);
            }
        } else {
            ImageLoader.display(mList.get(position).gifticon, holder.binding.ivGift);
            holder.binding.tvPrice.setText(mList.get(position).name);
        }
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class FirstRechargeViewHolder extends RecyclerView.ViewHolder {

        public FirstRechargeItemBinding binding;

        public FirstRechargeViewHolder(@NonNull FirstRechargeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
