package com.kalacheng.commonview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.commonview.R;
import com.kalacheng.commonview.databinding.ItemTaLiveBinding;

import com.kalacheng.util.listener.OnItemClickCallback;
import com.kalacheng.libuser.model.LiveRoomDTO;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by hgy on 2019/10/10.
 */

public class TALiveAdapter extends RecyclerView.Adapter<TALiveAdapter.ViewHolder> {

    private List<LiveRoomDTO> mList = new ArrayList<>();
    private OnItemClickCallback<LiveRoomDTO> onItemClickCallback;

    public TALiveAdapter() {
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
        ItemTaLiveBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_ta_live,
                        parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();

        holder.binding.setCallback(new OnItemClickCallback<LiveRoomDTO>() {
            @Override
            public void onClick(View view, LiveRoomDTO item) {
                if (onItemClickCallback != null) {
                    onItemClickCallback.onClick(view, item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemTaLiveBinding binding;

        public ViewHolder(ItemTaLiveBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setOnItemClickCallback(OnItemClickCallback<LiveRoomDTO> onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }
}
