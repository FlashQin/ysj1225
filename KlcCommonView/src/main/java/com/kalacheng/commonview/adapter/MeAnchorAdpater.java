package com.kalacheng.commonview.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.buscommon.model.TaskDto;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.databinding.ItemAnchortaskBinding;

import java.util.ArrayList;
import java.util.List;

public class MeAnchorAdpater extends RecyclerView.Adapter<MeAnchorAdpater.ViewHolder> {

    private List<TaskDto> mList = new ArrayList<>();

    public MeAnchorAdpater(List<TaskDto> data) {
        this.mList = data;
    }

    public void getData(List<TaskDto> data) {
        this.mList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MeAnchorAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAnchortaskBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_anchortask, parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MeAnchorAdpater.ViewHolder holder, int position) {
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
        ItemAnchortaskBinding binding;

        public ViewHolder(@NonNull ItemAnchortaskBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
