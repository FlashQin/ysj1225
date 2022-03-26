package com.kalacheng.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.model.AppLiveChannel;
import com.kalacheng.main.R;
import com.kalacheng.main.databinding.ItemTagBinding;
import com.kalacheng.util.listener.OnBeanCallback;

import java.util.ArrayList;
import java.util.List;

public class LiveTagAdpater extends RecyclerView.Adapter<LiveTagAdpater.ViewHolder> {

    private List<AppLiveChannel> mList = new ArrayList<>();
    private OnBeanCallback<AppLiveChannel> mCallBack;
    public long channelId;
    boolean isEnable = true;
    Context mContext;
    boolean isBuy = false;

    public LiveTagAdpater(List<AppLiveChannel> data) {
        mList.clear();
        if (data != null) {
            mList.addAll(data);
        }
    }

    public void setData(List<AppLiveChannel> data) {
        mList.clear();
        if (data != null) {
            mList.addAll(data);
        }
        if (mList.size() > 0) {
            channelId = mList.get(0).id;
        }
        notifyDataSetChanged();
    }

    public void setEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        ItemTagBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_tag, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
        holder.binding.typeLable.setEnabled(isEnable);
        if (mList.get(position).isChecked == 1) {
            holder.binding.typeLable.setSelected(true);
        } else {
            holder.binding.typeLable.setSelected(false);
        }
        if (null != mCallBack) {
            holder.binding.setCallback(new OnBeanCallback<AppLiveChannel>() {
                @Override
                public void onClick(AppLiveChannel bean) {
                    for (AppLiveChannel appLiveChannel : mList) {
                        appLiveChannel.isChecked = 0;
                    }
                    bean.isChecked = 1;
                    channelId = bean.id;
                    mCallBack.onClick(bean);
                    notifyDataSetChanged();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemTagBinding binding;

        ViewHolder(@NonNull ItemTagBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setOnItemClickListener(OnBeanCallback<AppLiveChannel> onItemClickListener) {
        mCallBack = onItemClickListener;
    }
}
