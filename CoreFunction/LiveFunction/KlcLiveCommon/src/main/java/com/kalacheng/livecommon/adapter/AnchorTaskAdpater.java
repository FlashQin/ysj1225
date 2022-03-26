package com.kalacheng.livecommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.buscommon.model.TaskDto;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.databinding.AnchorTaskItmeBinding;

import java.util.ArrayList;
import java.util.List;

public class AnchorTaskAdpater extends RecyclerView.Adapter<AnchorTaskAdpater.ViewHolder> {
    private List<TaskDto> mList = new ArrayList<>();
    private Context mContext;

    public AnchorTaskAdpater(Context mContext) {
        this.mContext = mContext;
    }

    public void getData(List<TaskDto> data) {
        this.mList.clear();
        this.mList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AnchorTaskAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AnchorTaskItmeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.anchor_task_itme, parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnchorTaskAdpater.ViewHolder holder, int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AnchorTaskItmeBinding binding;

        public ViewHolder(@NonNull AnchorTaskItmeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
