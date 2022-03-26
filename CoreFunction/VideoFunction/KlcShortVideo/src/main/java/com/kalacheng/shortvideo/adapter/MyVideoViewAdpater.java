package com.kalacheng.shortvideo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.libuser.model.ApiShortVideoDto;
import com.kalacheng.shortvideo.R;
import com.kalacheng.shortvideo.databinding.ItemMyViewBinding;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MyVideoViewAdpater extends RecyclerView.Adapter<MyVideoViewAdpater.ViewHolder> {

    private List<ApiShortVideoDto> mList = new ArrayList<>();
    private OnBeanCallback<ApiShortVideoDto> mCallBack;

    public MyVideoViewAdpater() {

    }

    public void setData(List<ApiShortVideoDto> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<ApiShortVideoDto> data) {
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public List<ApiShortVideoDto> getData() {
        return mList;
    }

    @NonNull
    @Override
    public MyVideoViewAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMyViewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_my_view, parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyVideoViewAdpater.ViewHolder holder, final int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();

        // 私密
        if (mList.get(position).isPrivate == 1 && mList.get(position).isPay == 0) {
            holder.binding.ivImagesShadow.setVisibility(View.VISIBLE);
            // 私密
            if (mList.get(position).type == 1) {
                ImageLoader.displayBlur(mList.get(position).thumb, holder.binding.ivImagesShadow);
            } else if (mList.get(position).type == 2) {
                String[] strings = mList.get(position).images.trim().split(",");
                if (strings.length > 0) {
                    ImageLoader.displayBlur(strings[0], holder.binding.ivImagesShadow);
                }
            }
        } else {
            //  免费/已付费/免费次数观看过
            holder.binding.ivImagesShadow.setVisibility(View.GONE);
        }

        if (null != mCallBack)
            holder.binding.setCallback(mCallBack);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemMyViewBinding binding;

        public ViewHolder(@NonNull ItemMyViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setOnItemClickListener(OnBeanCallback<ApiShortVideoDto> onItemClickListener) {
        mCallBack = onItemClickListener;
    }
}
