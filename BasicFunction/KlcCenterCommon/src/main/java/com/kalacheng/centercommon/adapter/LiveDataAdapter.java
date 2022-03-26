package com.kalacheng.centercommon.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.databinding.ItemLiveDataBinding;

import com.kalacheng.libuser.model.LiveRoomDTO;

import com.kalacheng.util.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hgy on 2019/10/10.
 */

public class LiveDataAdapter extends RecyclerView.Adapter<LiveDataAdapter.ViewHolder> {

    private List<LiveRoomDTO> mList = new ArrayList<>();

    public LiveDataAdapter() {
    }

    //加载更多
    public void setLoadData(List<LiveRoomDTO> data) {
        this.mList.addAll(data);
        notifyDataSetChanged();
    }

    //刷新数据
    public void setRefreshData(List<LiveRoomDTO> data) {
        this.mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLiveDataBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_live_data,
                        parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LiveRoomDTO liveDat=  mList.get(position);
        holder.binding.setBean(liveDat);
        holder.binding.executePendingBindings();

        DateUtil startTime = new DateUtil(mList.get(position).startTime);
        holder.binding.tvStartTime.setText(startTime.getDateToFormat("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemLiveDataBinding binding;

        public ViewHolder(ItemLiveDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
