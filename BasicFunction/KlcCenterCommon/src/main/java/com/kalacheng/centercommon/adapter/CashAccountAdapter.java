package com.kalacheng.centercommon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.databinding.ItemCashAccountBinding;
import com.kalacheng.libuser.model.AppUsersCashAccount;
import com.kalacheng.util.listener.OnBeanCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hgy on 2019/10/10.
 */

public class CashAccountAdapter extends RecyclerView.Adapter<CashAccountAdapter.ViewHolder> {

    private List<AppUsersCashAccount> mList = new ArrayList<>();
    private OnBeanCallback<AppUsersCashAccount> mCallBack;
    private OnBeanCallback<AppUsersCashAccount> longClickCallBack;

    public CashAccountAdapter(List<AppUsersCashAccount> list) {
        mList.clear();
        mList.addAll(list);
    }

    //加载更多
    public void setLoadData(List<AppUsersCashAccount> data) {
        this.mList.addAll(data);
        notifyDataSetChanged();
    }

    //刷新数据
    public void setRefreshData(List<AppUsersCashAccount> data) {
        this.mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCashAccountBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_cash_account,
                        parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
        if (mList.get(position).type == 1) {
            holder.binding.icon.setImageResource(R.mipmap.icon_cash_ali);
            holder.binding.name.setText("支付宝");
        } else if (mList.get(position).type == 2) {
            holder.binding.icon.setImageResource(R.mipmap.icon_cash_wx);
            holder.binding.name.setText("微信");
        } else if (mList.get(position).type == 3) {
            holder.binding.icon.setImageResource(R.mipmap.icon_cash_bank);
            holder.binding.name.setText(mList.get(position).accountBank);
        }
        holder.binding.setCallback(mCallBack);
        holder.binding.rlAll.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longClickCallBack.onClick(mList.get(position));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCashAccountBinding binding;

        public ViewHolder(ItemCashAccountBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void setOnItemClickListener(OnBeanCallback<AppUsersCashAccount> onItemClickListener) {
        mCallBack = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnBeanCallback<AppUsersCashAccount> onItemLongClickListener) {
        longClickCallBack = onItemLongClickListener;
    }
}
