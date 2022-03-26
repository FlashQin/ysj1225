package com.kalacheng.centercommon.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.buscommon.model.TaskDto;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.databinding.ItemUserTaskBinding;

import java.util.ArrayList;
import java.util.List;

public class UserTaskAdapter extends RecyclerView.Adapter<UserTaskAdapter.ViewHolder> {

    private List<TaskDto> mList = new ArrayList<>();
    private OnBeanCallback<TaskDto> mCallBack;

    public UserTaskAdapter() {

    }

    public void setData(List<TaskDto> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<TaskDto> data) {
        mList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserTaskBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_user_task, parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
        if (mList.get(position).status == 0) {
            holder.binding.tvTaskStatus.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.binding.tvTaskStatus.setTextColor(Color.parseColor("#BBBBBB"));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemUserTaskBinding binding;

        public ViewHolder(@NonNull ItemUserTaskBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setCallback(mCallBack);
        }
    }


    public void setOnItemClickListener(OnBeanCallback<TaskDto> onItemClickListener) {
        mCallBack = onItemClickListener;
    }
}
