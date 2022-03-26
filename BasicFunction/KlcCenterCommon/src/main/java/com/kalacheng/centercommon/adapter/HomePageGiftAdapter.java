package com.kalacheng.centercommon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.buscommon.model.GiftWallDto;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.databinding.ItemHomepageGiftBinding;
import com.kalacheng.util.utils.CheckDoubleClick;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hgy on 2019/10/10.
 */

public class HomePageGiftAdapter extends RecyclerView.Adapter<HomePageGiftAdapter.ViewHolder> {

    private List<GiftWallDto> mList = new ArrayList<>();
    private OnBeanCallback<GiftWallDto> itemClickCallback;
    private boolean forbidClick;//是否禁止点击

    public HomePageGiftAdapter(List<GiftWallDto> list) {
        mList.clear();
        mList.addAll(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHomepageGiftBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_homepage_gift,
                        parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();

        if (!forbidClick) {
            holder.binding.layoutHomePageGift.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    if (itemClickCallback != null) {
                        itemClickCallback.onClick(mList.get(position));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemHomepageGiftBinding binding;

        public ViewHolder(ItemHomepageGiftBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    /**
     * 设置点击回调
     */
    public void setOnItemClickCallback(OnBeanCallback<GiftWallDto> clickCallback) {
        this.itemClickCallback = clickCallback;
    }

    /**
     * 禁止点击事件
     */
    public void setForbidClick(boolean forbidClick) {
        this.forbidClick = forbidClick;
    }
}
