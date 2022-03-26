package com.kalacheng.money.adapter;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.model.AppUsersCharge;
import com.kalacheng.money.R;
import com.kalacheng.money.databinding.ItemCoinBinding;
import com.kalacheng.util.utils.SpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxf on 2018/10/23.
 */

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.CoinViewHolder> {
    private List<AppUsersCharge> coinList = new ArrayList<>();
    private int mSelectPosition = -1;

    public CoinAdapter() {

    }

    public void setData(List<AppUsersCharge> list) {
        coinList.clear();
        coinList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<AppUsersCharge> list) {
        coinList.addAll(list);
        notifyDataSetChanged();
    }

    public List<AppUsersCharge> getData() {
        return coinList;
    }

    public int getSelectPosition() {
        return mSelectPosition;
    }

    @Override
    public CoinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCoinBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_coin, parent, false);
        return new CoinViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinViewHolder holder, final int position) {
        holder.binding.setBean(coinList.get(position));
        holder.binding.executePendingBindings();
        holder.binding.tvCoinCompany.setText(SpUtil.getInstance().getCoinUnit());
        holder.binding.tvMoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//中划线
        holder.binding.tvMoney.getPaint().setAntiAlias(true); //去掉锯齿

        //bean.money==0
        if(coinList.get(position).money == 0 ||  coinList.get(position).money == coinList.get(position).discountMoney) {
            holder.binding.setIsShowMoney(true);
        }else holder.binding.setIsShowMoney(false);


        if (null == coinList.get(position).dicountDesr || TextUtils.isEmpty(coinList.get(position).dicountDesr)) {
            holder.binding.layoutDiscount.setVisibility(View.GONE);
        } else {
            holder.binding.layoutDiscount.setVisibility(View.VISIBLE);
        }
        if (mSelectPosition == position) {
            holder.binding.layoutCoinContent.setBackgroundResource(R.drawable.bg_coin_rule_select);
            holder.binding.tvCoin.setTextColor(Color.parseColor("#FF5E0D"));
            holder.binding.tvCoinCompany.setTextColor(Color.parseColor("#FF5E0D"));
            holder.binding.tvDiscountMoney.setTextColor(Color.parseColor("#FF5E0D"));
        } else {
            holder.binding.layoutCoinContent.setBackgroundResource(R.drawable.bg_coin_rule_unselect);
            holder.binding.tvCoin.setTextColor(Color.parseColor("#2F2F2F"));
            holder.binding.tvCoinCompany.setTextColor(Color.parseColor("#2F2F2F"));
            holder.binding.tvDiscountMoney.setTextColor(Color.parseColor("#999999"));
        }
        holder.binding.layoutCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectPosition == position) {
                    mSelectPosition = -1;
                } else {
                    mSelectPosition = position;
                }
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return coinList == null ? 0 : coinList.size();
    }

    static class CoinViewHolder extends RecyclerView.ViewHolder {
        ItemCoinBinding binding;

        public CoinViewHolder(ItemCoinBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

    }

}
