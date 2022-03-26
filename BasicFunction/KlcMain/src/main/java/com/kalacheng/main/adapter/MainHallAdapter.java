package com.kalacheng.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.util.listener.OnItemClickCallback;
import com.kalacheng.buscommon.model.LiveBean;
import com.kalacheng.main.R;
import com.kalacheng.main.databinding.ItemMainHomeHallBinding;

import java.util.ArrayList;
import java.util.List;

public class MainHallAdapter extends RecyclerView.Adapter<MainHallAdapter.GirlsViewHolder> {
    List<LiveBean> liveBean = new ArrayList<>();
    OnItemClickCallback itemClickCallback;

    public void setItemClickCallback(OnItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public void setliveBean(final List<LiveBean> list) {
        liveBean.clear();
        liveBean.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GirlsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMainHomeHallBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_main_home_hall,
                        parent, false);
        binding.setCallback(itemClickCallback);
        return new GirlsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MainHallAdapter.GirlsViewHolder holder, int position) {
        holder.binding.setLiveBean(liveBean.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return liveBean == null ? 0 : liveBean.size();
    }

    static class GirlsViewHolder extends RecyclerView.ViewHolder {
        ItemMainHomeHallBinding binding;

        public GirlsViewHolder(ItemMainHomeHallBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
