package com.kalacheng.commonview.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.databinding.ItemPayMethodBinding;
import com.kalacheng.libuser.model.PayConfigDto;

import java.util.ArrayList;
import java.util.List;

public class PayMethodAdapter extends RecyclerView.Adapter<PayMethodAdapter.ViewHolder> {
    private List<PayConfigDto> mList = new ArrayList<>();
    private OnBeanCallback<PayConfigDto> onBeanCallback;

    public PayMethodAdapter(List<PayConfigDto> list) {
        mList.clear();
        mList.addAll(list);
    }

    public void setOnBeanCallback(OnBeanCallback<PayConfigDto> onBeanCallback) {
        this.onBeanCallback = onBeanCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPayMethodBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_pay_method, parent, false);
        binding.setCallback(new OnBeanCallback<PayConfigDto>() {
            @Override
            public void onClick(PayConfigDto bean) {
                if (onBeanCallback != null) {
                    onBeanCallback.onClick(bean);
                }
            }
        });
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
        if (!TextUtils.isEmpty(mList.get(position).payName)) {
            if (mList.get(position).payName.contains("支付宝")) {
                holder.binding.icon.setImageResource(R.mipmap.icon_cash_ali);
            } else if (mList.get(position).payName.contains("微信")) {
                holder.binding.icon.setImageResource(R.mipmap.icon_cash_wx);
            } else {
                holder.binding.icon.setImageResource(R.mipmap.icon_cash_bank);
            }
        } else {
            holder.binding.icon.setImageResource(R.mipmap.icon_cash_ali);
        }
        if (position == 0)
            holder.binding.line.setVisibility(View.GONE);
        else
            holder.binding.line.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemPayMethodBinding binding;

        public ViewHolder(ItemPayMethodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
