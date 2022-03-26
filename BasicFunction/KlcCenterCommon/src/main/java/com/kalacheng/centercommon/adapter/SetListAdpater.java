package com.kalacheng.centercommon.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.databinding.ItemSettingBinding;
import com.kalacheng.libuser.model.ApiUserIndexNode;
import com.kalacheng.util.listener.OnBeanCallback;

import java.util.ArrayList;
import java.util.List;

public class SetListAdpater extends RecyclerView.Adapter {

    private List<ApiUserIndexNode> mList = new ArrayList<>();
    OnBeanCallback<ApiUserIndexNode> mCallBack;

    public SetListAdpater(List<ApiUserIndexNode> data) {
        this.mList = data;

    }

    public void setData(List<ApiUserIndexNode> data) {
        this.mList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSettingBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_setting, parent, false);
        SetListViewHolder holder = new SetListViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((SetListViewHolder) holder).setData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class SetListViewHolder extends RecyclerView.ViewHolder {
        ItemSettingBinding binding;

        public SetListViewHolder(@NonNull ItemSettingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setCallback(mCallBack);
        }

        public void setData(ApiUserIndexNode bean) {
            binding.setViewModel(bean);

            binding.executePendingBindings();


        }
    }

    public void setOnItemCallBack(OnBeanCallback<ApiUserIndexNode> back) {
        this.mCallBack = back;
    }


}
