package com.kalacheng.commonview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.commonview.R;
import com.kalacheng.commonview.databinding.OpenGuardItmeBinding;
import com.kalacheng.libuser.model.ApiGuard;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.SpUtil;

import java.util.ArrayList;
import java.util.List;

public class OpenGuardAdpater extends RecyclerView.Adapter<OpenGuardAdpater.OpenGuardViewHolder> {
    private Context mContext;
    private List<ApiGuard> mList = new ArrayList<>();
    private OnItmeCallBack callBack;
    private int clickPoistion = -1;

    public OpenGuardAdpater(Context mContext) {
        this.mContext = mContext;
    }

    public void getApiGuard(List<ApiGuard> data) {
        this.mList.clear();
        this.mList = data;
        notifyDataSetChanged();
    }

    public void getPoistion(int position) {
        this.clickPoistion = position;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OpenGuardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OpenGuardItmeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.open_guard_itme, parent, false);

        OpenGuardViewHolder holder = new OpenGuardViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OpenGuardViewHolder holder, final int position) {
        holder.binding.setViewModel(mList.get(position));
        if (clickPoistion == position) {
            holder.binding.guardRe.setBackgroundResource(R.drawable.bg_open_guard_click);
        } else {
            holder.binding.guardRe.setBackgroundResource(R.drawable.bg_open_guard);
        }
        if (mList.get(position).lengthType == 0) {
            holder.binding.guardDay.setText(String.valueOf(mList.get(position).length) + " 天");
        } else if (mList.get(position).lengthType == 1) {
            holder.binding.guardDay.setText(String.valueOf(mList.get(position).length) + " 月");
        } else if (mList.get(position).lengthType == 2) {
            holder.binding.guardDay.setText(String.valueOf(mList.get(position).length) + " 年");
        }
        holder.binding.guardNum.setText(DecimalFormatUtils.isIntegerDouble(mList.get(position).coin) + SpUtil.getInstance().getCoinUnit());
        holder.binding.executePendingBindings();

        holder.binding.guardRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class OpenGuardViewHolder extends RecyclerView.ViewHolder {
        OpenGuardItmeBinding binding;

        public OpenGuardViewHolder(OpenGuardItmeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setOnItmeCallBack(OnItmeCallBack back) {
        this.callBack = back;
    }

    public interface OnItmeCallBack {
        public void onClick(int poistion);
    }
}
