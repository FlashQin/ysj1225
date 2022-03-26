package com.kalacheng.centercommon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.databinding.ItemHomepageLiveBinding;
import com.kalacheng.libuser.model.LiveDto;
import com.kalacheng.util.listener.OnItemClickCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hgy on 2019/10/10.
 */

public class HomePageLiveAdapter extends RecyclerView.Adapter<HomePageLiveAdapter.ViewHolder> {

    private List<LiveDto> mList = new ArrayList<>();
    private OnItemClickCallback<LiveDto> onItemClickCallback;

    public HomePageLiveAdapter() {

    }

    //加载更多
    public void setLoadData(List<LiveDto> data) {
        this.mList.addAll(data);
        notifyDataSetChanged();
    }

    //刷新数据
    public void setRefreshData(List<LiveDto> data) {
        this.mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHomepageLiveBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_homepage_live,
                        parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();

        holder.binding.setCallback(new OnItemClickCallback<LiveDto>() {
            @Override
            public void onClick(View view, LiveDto item) {
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
        ItemHomepageLiveBinding binding;

        public ViewHolder(ItemHomepageLiveBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setOnItemClickCallback(OnItemClickCallback<LiveDto> onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }
}
