package com.kalacheng.centercommon.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.databinding.ItemPaySettingBinding;
import com.kalacheng.libuser.model.ChangeDto;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hgy on 2019/10/10.
 */

public class PaySettingAdapter extends RecyclerView.Adapter<PaySettingAdapter.ViewHolder> {

    private List<ChangeDto> mList = new ArrayList<>();

    public PaySettingAdapter(List<ChangeDto> list) {
        mList.clear();
        mList.addAll(list);
    }

    //加载更多
    public void setLoadData(List<ChangeDto> data) {
        this.mList.addAll(data);
        notifyDataSetChanged();
    }

    //刷新数据
    public void setRefreshData(List<ChangeDto> data) {
        this.mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPaySettingBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_pay_setting,
                        parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();

        ImageLoader.display(mList.get(position).avatar, holder.binding.ivAvatar, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemPaySettingBinding binding;

        public ViewHolder(ItemPaySettingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
